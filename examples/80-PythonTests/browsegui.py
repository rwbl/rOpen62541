import sys
import asyncio
import threading
from PySide6.QtWidgets import (QApplication, QMainWindow, QWidget, QVBoxLayout, 
                             QHBoxLayout, QLineEdit, QPushButton, QTreeWidget, 
                             QTreeWidgetItem, QLabel, QSplitter)
from PySide6.QtCore import Qt, QThread, Signal
from asyncua import Client

# Custom Thread Worker to handle all async industrial networking cleanly
class OPCUAWorker(QThread):
    # Safe cross-thread signals to send updates back to our main interface layout
    connected_signal = Signal(bool, object)
    children_signal = Signal(object, list)
    value_signal = Signal(str, object)
    error_signal = Signal(str)

    def __init__(self, url):
        super().__init__()
        self.url = url
        self.loop = None
        self.client = None
        self.active_subscription = None
        self.current_subscribed_node = None

    def run(self):
        # Create an independent, dedicated async execution loop for this background thread
        self.loop = asyncio.new_event_loop()
        asyncio.set_event_loop(self.loop)
        self.loop.run_until_complete(self.async_run())

    async def async_run(self):
        self.client = Client(url=self.url)
        try:
            await self.client.connect()
            root_node = self.client.nodes.root
            self.connected_signal.emit(True, root_node)
            
            # Keep the background thread alive processing internal async socket registers
            while self.client:
                await asyncio.sleep(0.1)
        except Exception as e:
            self.connected_signal.emit(False, None)
            self.error_signal.emit(str(e))

    def fetch_children(self, tree_item, node):
        if self.loop and self.loop.is_running():
            asyncio.run_coroutine_threadsafe(self._async_fetch_children(tree_item, node), self.loop)

    async def _async_fetch_children(self, tree_item, node):
        try:
            children = await node.get_children()
            results = []
            for child in children:
                b_name = await child.read_browse_name()
                sub_children = await child.get_children()
                has_sub = len(sub_children) > 0
                results.append((b_name.Name, child, has_sub))
            self.children_signal.emit(tree_item, results)
        except Exception as e:
            self.error_signal.emit(str(e))

    def select_node(self, node):
        if self.loop and self.loop.is_running():
            asyncio.run_coroutine_threadsafe(self._async_select_node(node), self.loop)

    async def _async_select_node(self, node):
        try:
            # Teardown previous subscription contexts cleanly
            if self.active_subscription:
                await self.active_subscription.delete()
                self.active_subscription = None

            n_class = await node.read_node_class()
            
            if n_class.name == "Variable":
                val = await node.read_value()
                self.value_signal.emit(n_class.name, val)
                
                # Create a subscription loop context ticking every 500ms
                self.active_subscription = await self.client.create_subscription(500, self)
                
                # --- MODERN API FIX: Changed from subscribe_data_changes to subscribe_data_change ---
                await self.active_subscription.subscribe_data_change(node)
                
            elif n_class.name == "Method":
                self.value_signal.emit(n_class.name, "[Executable Method]")
            else:
                self.value_signal.emit(n_class.name, "---")
        except Exception as e:
            self.error_signal.emit(str(e))

    # --- MODERN API FIX: Exact lower-case callback method signature expected by asyncua ---
    def datachange_notification(self, node, val, data):
        # Safely emit the live updated value over to the PySide6 main GUI thread
        self.value_signal.emit("Variable", val)

    def datachange_notification(self, node, val, data):
        # Intercepts incoming network pushes and safely emits them to the GUI thread
        self.value_signal.emit("Variable", val)

    def disconnect_client(self):
        if self.loop and self.loop.is_running():
            asyncio.run_coroutine_threadsafe(self._async_disconnect(), self.loop)

    async def _async_disconnect(self):
        try:
            # 1. Cleanly delete subscription connections
            if self.active_subscription:
                await self.active_subscription.delete()
                self.active_subscription = None
            
            # 2. Break connection sockets with your ESP32-S3 server
            if self.client:
                await self.client.disconnect()
                self.client = None
        except Exception:
            pass
        finally:
            # 3. CRITICAL CLEAN EXIT FIX: Gather all remaining background tasks 
            # and cancel them cleanly so the loop doesn't panic on shutdown!
            if self.loop and self.loop.is_running():
                pending_tasks = asyncio.all_tasks(self.loop)
                for task in pending_tasks:
                    task.cancel()
                
                # Stop the loop safely on the next execution tick
                self.loop.stop()

# Main Graphical User Interface Panel Windows Layout
class OPCUABrowserApp(QMainWindow):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("rOpen62541 Industrial Node Browser & Monitor")
        self.resize(1000, 600)
        
        self.worker = None
        self.node_map = {}

        main_widget = QWidget()
        self.setCentralWidget(main_widget)
        main_layout = QVBoxLayout(main_widget)

        conn_layout = QHBoxLayout()
        self.url_input = QLineEdit("opc.tcp://192.168.1.175:4840")
        self.btn_connect = QPushButton("Connect Server")
        self.btn_connect.clicked.connect(self.on_connect_clicked)
        conn_layout.addWidget(QLabel("Endpoint URL:"))
        conn_layout.addWidget(self.url_input)
        conn_layout.addWidget(self.btn_connect)
        main_layout.addLayout(conn_layout)

        splitter = QSplitter(Qt.Horizontal)
        main_layout.addWidget(splitter)

        tree_container = QWidget()
        tree_layout = QVBoxLayout(tree_container)
        tree_layout.addWidget(QLabel("OPC UA Address Space Tree Hierarchy:"))
        self.tree = QTreeWidget()
        self.tree.setHeaderLabels(["Browse Name", "Node ID"])
        self.tree.itemExpanded.connect(self.on_item_expanded)
        self.tree.itemClicked.connect(self.on_item_clicked)
        tree_layout.addWidget(self.tree)
        splitter.addWidget(tree_container)

        monitor_container = QWidget()
        monitor_layout = QVBoxLayout(monitor_container)
        monitor_layout.addWidget(QLabel("Live Selected Metric Data Monitor:"))
        
        self.lbl_node_name = QLabel("Selected Node: None")
        self.lbl_node_name.setStyleSheet("font-size: 16px; font-weight: bold; color: #2c3e50;")
        self.lbl_node_id = QLabel("Node ID: None")
        self.lbl_node_class = QLabel("Node Class: None")
        self.lbl_node_val = QLabel("---")
        self.lbl_node_val.setStyleSheet("font-size: 48px; font-weight: bold; color: #27ae60; margin: 20px 0;")
        
        monitor_layout.addWidget(self.lbl_node_name)
        monitor_layout.addWidget(self.lbl_node_id)
        monitor_layout.addWidget(self.lbl_node_class)
        monitor_layout.addWidget(self.lbl_node_val)
        monitor_layout.addStretch()
        splitter.addWidget(monitor_container)

    def on_connect_clicked(self):
        if self.worker and self.worker.isRunning():
            self.worker.disconnect_client()
            self.worker.wait()
            self.worker = None
            self.btn_connect.setText("Connect Server")
            self.tree.clear()
            self.lbl_node_val.setText("---")
            return

        self.btn_connect.setText("Connecting...")
        self.worker = OPCUAWorker(self.url_input.text())
        
        # Connect Thread signals to GUI slots
        self.worker.connected_signal.connect(self.handle_connection)
        self.worker.children_signal.connect(self.handle_children)
        self.worker.value_signal.connect(self.handle_value)
        self.worker.error_signal.connect(print)
        
        self.worker.start()

    def handle_connection(self, success, root_node):
        if success:
            self.btn_connect.setText("Disconnect")
            root_item = QTreeWidgetItem(self.tree, ["Root", str(root_node)])
            root_item.setChildIndicatorPolicy(QTreeWidgetItem.ShowIndicator)
            self.node_map[root_item] = root_node
            root_item.setExpanded(True)
        else:
            self.btn_connect.setText("Connect Server")
            self.lbl_node_val.setText("Conn Failed")

    def on_item_expanded(self, item):
        node = self.node_map.get(item)
        if node and self.worker:
            self.worker.fetch_children(item, node)

    def handle_children(self, parent_item, child_data_list):
        parent_item.takeChildren()
        for name, child_node, has_sub in child_data_list:
            child_item = QTreeWidgetItem(parent_item, [name, str(child_node)])
            self.node_map[child_item] = child_node
            if has_sub:
                child_item.setChildIndicatorPolicy(QTreeWidgetItem.ShowIndicator)

    def on_item_clicked(self, item, column):
        node = self.node_map.get(item)
        if node and self.worker:
            self.lbl_node_name.setText(f"Selected Node: {item.text(0)}")
            self.lbl_node_id.setText(f"Node ID: {item.text(1)}")
            self.worker.select_node(node)

    def handle_value(self, node_class, val):
        self.lbl_node_class.setText(f"Node Class: {node_class}")
        if isinstance(val, float):
            self.lbl_node_val.setText(f"{val:.2f}")
        else:
            self.lbl_node_val.setText(str(val))

    def closeEvent(self, event):
        if self.worker:
            self.worker.disconnect_client()
            self.worker.wait()
        event.accept()

if __name__ == "__main__":
    app = QApplication(sys.argv)
    browser = OPCUABrowserApp()
    browser.show()
    sys.exit(app.exec())
