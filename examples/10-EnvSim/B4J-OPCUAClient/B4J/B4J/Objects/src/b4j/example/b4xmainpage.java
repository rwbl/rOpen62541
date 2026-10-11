package b4j.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;

public class b4xmainpage extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    public static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new  anywheresoftware.b4j.objects.FxBA("b4j.example", "b4j.example.b4xmainpage", this);
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            ba.htSubs = htSubs;
             
        }
        if (BA.isShellModeRuntimeCheck(ba))
                this.getClass().getMethod("_class_globals", b4j.example.b4xmainpage.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public String _version = "";
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _root = null;
public b4j.example.hmitilesio _tileconnect = null;
public b4j.example.hmitilesio _tileconnected = null;
public b4j.example.hmitilesio _tiletemperature = null;
public b4j.example.hmitilesio _tilehumidity = null;
public b4j.example.hmitilesio _tiletemperaturegauge = null;
public b4j.example.hmitilesio _tilehumiditygauge = null;
public String _endpoint = "";
public int _sampling_interval = 0;
public com.simplysoftware.OPCUAClient.OPCUAClient _opcclient = null;
public boolean _isconnected = false;
public String _node_temperature = "";
public String _node_humidity = "";
public String _node_trigger = "";
public b4j.example.main _main = null;
public b4j.example.b4xpages _b4xpages = null;
public b4j.example.b4xcollections _b4xcollections = null;
public b4j.example.hmitilesioutils _hmitilesioutils = null;
public anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _b4xpage_closerequest() throws Exception{
ResumableSub_B4XPage_CloseRequest rsub = new ResumableSub_B4XPage_CloseRequest(this);
rsub.resume(ba, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_B4XPage_CloseRequest extends BA.ResumableSub {
public ResumableSub_B4XPage_CloseRequest(b4j.example.b4xmainpage parent) {
this.parent = parent;
}
b4j.example.b4xmainpage parent;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
try {

        switch (state) {
            case -1:
{
parent.__c.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = 1;
 //BA.debugLineNum = 75;BA.debugLine="Log(\"[B4XPage_CloseRequest] Forcefully closing OP";
parent.__c.LogImpl("4720897","[B4XPage_CloseRequest] Forcefully closing OPC UA Client socket channels.",0);
 //BA.debugLineNum = 76;BA.debugLine="Try";
if (true) break;

case 1:
//try
this.state = 10;
this.catchState = 9;
this.state = 3;
if (true) break;

case 3:
//C
this.state = 4;
this.catchState = 9;
 //BA.debugLineNum = 78;BA.debugLine="If OpcClient.IsConnected Then";
if (true) break;

case 4:
//if
this.state = 7;
if (parent._opcclient.IsConnected()) { 
this.state = 6;
}if (true) break;

case 6:
//C
this.state = 7;
 //BA.debugLineNum = 79;BA.debugLine="OpcClient.Disconnect";
parent._opcclient.Disconnect();
 if (true) break;

case 7:
//C
this.state = 10;
;
 if (true) break;

case 9:
//C
this.state = 10;
this.catchState = 0;
 //BA.debugLineNum = 82;BA.debugLine="Log(\"Exception caught during shutdown: \" & LastE";
parent.__c.LogImpl("4720904","Exception caught during shutdown: "+parent.__c.LastException(ba).getMessage(),0);
 if (true) break;
if (true) break;

case 10:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 84;BA.debugLine="Return True";
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(parent.__c.True));return;};
 //BA.debugLineNum = 85;BA.debugLine="End Sub";
if (true) break;
}} 
       catch (Exception e0) {
			
if (catchState == 0)
    throw e0;
else {
    state = catchState;
ba.setLastException(e0);}
            }
        }
    }
}
public void  _b4xpage_created(anywheresoftware.b4a.objects.B4XViewWrapper _root1) throws Exception{
ResumableSub_B4XPage_Created rsub = new ResumableSub_B4XPage_Created(this,_root1);
rsub.resume(ba, null);
}
public static class ResumableSub_B4XPage_Created extends BA.ResumableSub {
public ResumableSub_B4XPage_Created(b4j.example.b4xmainpage parent,anywheresoftware.b4a.objects.B4XViewWrapper _root1) {
this.parent = parent;
this._root1 = _root1;
}
b4j.example.b4xmainpage parent;
anywheresoftware.b4a.objects.B4XViewWrapper _root1;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = -1;
 //BA.debugLineNum = 53;BA.debugLine="Root = Root1";
parent._root = _root1;
 //BA.debugLineNum = 54;BA.debugLine="Root.LoadLayout(\"MainPage\")";
parent._root.LoadLayout("MainPage",ba);
 //BA.debugLineNum = 57;BA.debugLine="B4XPages.SetTitle(Me, VERSION)";
parent._b4xpages._settitle /*String*/ (parent,(Object)(parent._version));
 //BA.debugLineNum = 58;BA.debugLine="Root.Color = xui.Color_LightGray";
parent._root.setColor(parent._xui.Color_LightGray);
 //BA.debugLineNum = 61;BA.debugLine="Sleep(50)	' MANDATORY";
parent.__c.Sleep(ba,this,(int) (50));
this.state = 1;
return;
case 1:
//C
this.state = -1;
;
 //BA.debugLineNum = 62;BA.debugLine="TileConnect.State = False";
parent._tileconnect._setstate /*boolean*/ (parent.__c.False);
 //BA.debugLineNum = 63;BA.debugLine="TileConnect.Footer = \"\"";
parent._tileconnect._setfooter /*String*/ ("");
 //BA.debugLineNum = 64;BA.debugLine="TileConnected.Value = \"Disconnected\"";
parent._tileconnected._setvalue /*String*/ ("Disconnected");
 //BA.debugLineNum = 65;BA.debugLine="TileConnected.ValueFontSize = 14";
parent._tileconnected._setvaluefontsize /*int*/ ((int) (14));
 //BA.debugLineNum = 66;BA.debugLine="TileConnected.Footer = \"\"";
parent._tileconnected._setfooter /*String*/ ("");
 //BA.debugLineNum = 69;BA.debugLine="OpcClient.Initialize(\"OpcClient\")";
parent._opcclient.Initialize(ba,"OpcClient");
 //BA.debugLineNum = 70;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 19;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 21;BA.debugLine="Private VERSION As String = \"rOpen62541 OPC UA Se";
_version = "rOpen62541 OPC UA Server EnvSim v20261002";
 //BA.debugLineNum = 24;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 25;BA.debugLine="Private Root As B4XView";
_root = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private TileConnect As HMITilesIO";
_tileconnect = new b4j.example.hmitilesio();
 //BA.debugLineNum = 29;BA.debugLine="Private TileConnected As HMITilesIO";
_tileconnected = new b4j.example.hmitilesio();
 //BA.debugLineNum = 30;BA.debugLine="Private TileTemperature As HMITilesIO";
_tiletemperature = new b4j.example.hmitilesio();
 //BA.debugLineNum = 31;BA.debugLine="Private TileHumidity As HMITilesIO";
_tilehumidity = new b4j.example.hmitilesio();
 //BA.debugLineNum = 32;BA.debugLine="Private TileTemperatureGauge As HMITilesIO";
_tiletemperaturegauge = new b4j.example.hmitilesio();
 //BA.debugLineNum = 33;BA.debugLine="Private TileHumidityGauge As HMITilesIO";
_tilehumiditygauge = new b4j.example.hmitilesio();
 //BA.debugLineNum = 36;BA.debugLine="Private ENDPOINT 			As String = \"opc.tcp://192.16";
_endpoint = "opc.tcp://192.168.1.175:4840";
 //BA.debugLineNum = 37;BA.debugLine="Private SAMPLING_INTERVAL 	As Int = 1000	'ms";
_sampling_interval = (int) (1000);
 //BA.debugLineNum = 38;BA.debugLine="Private OpcClient 			As OPCUAClient";
_opcclient = new com.simplysoftware.OPCUAClient.OPCUAClient();
 //BA.debugLineNum = 39;BA.debugLine="Private IsConnected 		As Boolean";
_isconnected = false;
 //BA.debugLineNum = 41;BA.debugLine="Private NODE_TEMPERATURE 	As String = \"ns=1;s=Tem";
_node_temperature = "ns=1;s=Temperature";
 //BA.debugLineNum = 42;BA.debugLine="Private NODE_HUMIDITY 		As String = \"ns=1;s=Humid";
_node_humidity = "ns=1;s=Humidity";
 //BA.debugLineNum = 43;BA.debugLine="Private NODE_TRIGGER 		As String = \"ns=1;s=Trigge";
_node_trigger = "ns=1;s=Trigger";
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return "";
}
public String  _connect_noauth() throws Exception{
 //BA.debugLineNum = 188;BA.debugLine="Sub Connect_NoAuth";
 //BA.debugLineNum = 189;BA.debugLine="OpcClient.Connect(ENDPOINT)";
_opcclient.Connect(_endpoint);
 //BA.debugLineNum = 190;BA.debugLine="End Sub";
return "";
}
public String  _connecttoserver() throws Exception{
 //BA.debugLineNum = 91;BA.debugLine="Public Sub ConnectToServer";
 //BA.debugLineNum = 92;BA.debugLine="OpcClient.Connect(ENDPOINT)";
_opcclient.Connect(_endpoint);
 //BA.debugLineNum = 93;BA.debugLine="End Sub";
return "";
}
public String  _getnodeidentifier(String _msg) throws Exception{
String _result = "";
String[] _components = null;
 //BA.debugLineNum = 227;BA.debugLine="Public Sub GetNodeIdentifier(msg As String) As Str";
 //BA.debugLineNum = 228;BA.debugLine="Dim result As String = \"\"";
_result = "";
 //BA.debugLineNum = 229;BA.debugLine="Dim components() As String = Regex.Split(\";\", msg";
_components = __c.Regex.Split(";",_msg);
 //BA.debugLineNum = 230;BA.debugLine="If components.length = 2 Then";
if (_components.length==2) { 
 //BA.debugLineNum = 231;BA.debugLine="result = components(1).Replace(\"s=\", \"\")";
_result = _components[(int) (1)].replace("s=","");
 };
 //BA.debugLineNum = 233;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 234;BA.debugLine="End Sub";
return "";
}
public String  _gettime() throws Exception{
 //BA.debugLineNum = 216;BA.debugLine="Public Sub GetTime As String";
 //BA.debugLineNum = 217;BA.debugLine="Return $\"${DateTime.Time(DateTime.Now)}\"$";
if (true) return (""+__c.SmartStringFormatter("",(Object)(__c.DateTime.Time(__c.DateTime.getNow())))+"");
 //BA.debugLineNum = 218;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 46;BA.debugLine="Public Sub Initialize";
 //BA.debugLineNum = 47;BA.debugLine="B4XPages.GetManager.LogEvents = True";
_b4xpages._getmanager /*b4j.example.b4xpagesmanager*/ ()._logevents /*boolean*/  = __c.True;
 //BA.debugLineNum = 48;BA.debugLine="End Sub";
return "";
}
public String  _opcclient_connected() throws Exception{
 //BA.debugLineNum = 130;BA.debugLine="Sub OpcClient_Connected";
 //BA.debugLineNum = 131;BA.debugLine="Log(\"[Connected] Connected to ESP32 OPC UA Server";
__c.LogImpl("41048577","[Connected] Connected to ESP32 OPC UA Server!",0);
 //BA.debugLineNum = 132;BA.debugLine="IsConnected = True";
_isconnected = __c.True;
 //BA.debugLineNum = 134;BA.debugLine="ReadNodes";
_readnodes();
 //BA.debugLineNum = 136;BA.debugLine="SubscribeNodes";
_subscribenodes();
 //BA.debugLineNum = 139;BA.debugLine="TileConnect.State = IsConnected";
_tileconnect._setstate /*boolean*/ (_isconnected);
 //BA.debugLineNum = 140;BA.debugLine="TileConnected.Value = \"Connected\"";
_tileconnected._setvalue /*String*/ ("Connected");
 //BA.debugLineNum = 141;BA.debugLine="End Sub";
return "";
}
public String  _opcclient_connectionerror(String _error) throws Exception{
 //BA.debugLineNum = 158;BA.debugLine="Sub OpcClient_ConnectionError(Error As String)";
 //BA.debugLineNum = 159;BA.debugLine="IsConnected = False";
_isconnected = __c.False;
 //BA.debugLineNum = 160;BA.debugLine="TileConnect.State = IsConnected";
_tileconnect._setstate /*boolean*/ (_isconnected);
 //BA.debugLineNum = 161;BA.debugLine="TileConnected.Value = \"Disconnected\"";
_tileconnected._setvalue /*String*/ ("Disconnected");
 //BA.debugLineNum = 162;BA.debugLine="Log($\"[ConnectionError][E] ${Error}\"$)";
__c.LogImpl("41179652",("[ConnectionError][E] "+__c.SmartStringFormatter("",(Object)(_error))+""),0);
 //BA.debugLineNum = 163;BA.debugLine="End Sub";
return "";
}
public String  _opcclient_disconnected() throws Exception{
 //BA.debugLineNum = 144;BA.debugLine="Sub OpcClient_Disconnected";
 //BA.debugLineNum = 145;BA.debugLine="IsConnected = False";
_isconnected = __c.False;
 //BA.debugLineNum = 146;BA.debugLine="TileConnect.State = IsConnected";
_tileconnect._setstate /*boolean*/ (_isconnected);
 //BA.debugLineNum = 147;BA.debugLine="TileConnected.Value = \"Disconnected\"";
_tileconnected._setvalue /*String*/ ("Disconnected");
 //BA.debugLineNum = 149;BA.debugLine="TileTemperature.Value = 0";
_tiletemperature._setvalue /*String*/ (BA.NumberToString(0));
 //BA.debugLineNum = 150;BA.debugLine="TileHumidity.Value = 0";
_tilehumidity._setvalue /*String*/ (BA.NumberToString(0));
 //BA.debugLineNum = 151;BA.debugLine="TileTemperatureGauge.Value = 0";
_tiletemperaturegauge._setvalue /*String*/ (BA.NumberToString(0));
 //BA.debugLineNum = 152;BA.debugLine="TileHumidityGauge.Value = 0";
_tilehumiditygauge._setvalue /*String*/ (BA.NumberToString(0));
 //BA.debugLineNum = 154;BA.debugLine="Log(\"[Disconnected]\")";
__c.LogImpl("41114122","[Disconnected]",0);
 //BA.debugLineNum = 155;BA.debugLine="End Sub";
return "";
}
public String  _opcclient_nodevaluechanged(String _nodeid,Object _value) throws Exception{
 //BA.debugLineNum = 181;BA.debugLine="Sub OpcClient_NodeValueChanged (NodeId As String,";
 //BA.debugLineNum = 182;BA.debugLine="Log($\"[NodeValueChanged] Node: ${NodeId} | Value:";
__c.LogImpl("41376257",("[NodeValueChanged] Node: "+__c.SmartStringFormatter("",(Object)(_nodeid))+" | Value: "+__c.SmartStringFormatter("",_value)+""),0);
 //BA.debugLineNum = 183;BA.debugLine="UpdateHMITiles(NodeId, Value)";
_updatehmitiles(_nodeid,_value);
 //BA.debugLineNum = 184;BA.debugLine="End Sub";
return "";
}
public String  _opcclient_readresult(String _nodeid,Object _value,String _status) throws Exception{
 //BA.debugLineNum = 174;BA.debugLine="Sub OpcClient_ReadResult (NodeId As String, Value";
 //BA.debugLineNum = 175;BA.debugLine="Log($\"[ReadResult] Node: ${NodeId} | Value: ${Val";
__c.LogImpl("41310721",("[ReadResult] Node: "+__c.SmartStringFormatter("",(Object)(_nodeid))+" | Value: "+__c.SmartStringFormatter("",_value)+" | Status: "+__c.SmartStringFormatter("",(Object)(_status))+""),0);
 //BA.debugLineNum = 176;BA.debugLine="UpdateHMITiles(NodeId, Value)";
_updatehmitiles(_nodeid,_value);
 //BA.debugLineNum = 177;BA.debugLine="End Sub";
return "";
}
public String  _opcclient_subscriptionvaluechanged(String _nodeid,Object _value,long _timestamp) throws Exception{
 //BA.debugLineNum = 167;BA.debugLine="Sub OpcClient_SubscriptionValueChanged (NodeId As";
 //BA.debugLineNum = 168;BA.debugLine="Log($\"[SubscriptionValueChanged] Node: ${NodeId}";
__c.LogImpl("41245185",("[SubscriptionValueChanged] Node: "+__c.SmartStringFormatter("",(Object)(_nodeid))+" | Value: "+__c.SmartStringFormatter("",_value)+" | Time: "+__c.SmartStringFormatter("",(Object)(_timestamp))+""),0);
 //BA.debugLineNum = 169;BA.debugLine="UpdateHMITiles(NodeId, Value)";
_updatehmitiles(_nodeid,_value);
 //BA.debugLineNum = 170;BA.debugLine="End Sub";
return "";
}
public String  _opcclient_writeresult(String _nodeid,boolean _success) throws Exception{
 //BA.debugLineNum = 192;BA.debugLine="Sub OpcClient_WriteResult(NodeId As String, Succes";
 //BA.debugLineNum = 193;BA.debugLine="Log(\"WriteResult: \" & NodeId & \" success=\" & Succ";
__c.LogImpl("41507329","WriteResult: "+_nodeid+" success="+BA.ObjectToString(_success),0);
 //BA.debugLineNum = 194;BA.debugLine="End Sub";
return "";
}
public String  _readnodes() throws Exception{
 //BA.debugLineNum = 104;BA.debugLine="Public Sub ReadNodes";
 //BA.debugLineNum = 105;BA.debugLine="OpcClient.Read(NODE_TEMPERATURE)";
_opcclient.Read(_node_temperature);
 //BA.debugLineNum = 106;BA.debugLine="OpcClient.Read(NODE_HUMIDITY)";
_opcclient.Read(_node_humidity);
 //BA.debugLineNum = 107;BA.debugLine="End Sub";
return "";
}
public String  _subscribenodes() throws Exception{
 //BA.debugLineNum = 95;BA.debugLine="Public Sub SubscribeNodes";
 //BA.debugLineNum = 99;BA.debugLine="OpcClient.Subscribe(NODE_TEMPERATURE, SAMPLING_IN";
_opcclient.Subscribe(_node_temperature,_sampling_interval);
 //BA.debugLineNum = 100;BA.debugLine="OpcClient.Subscribe(NODE_HUMIDITY, SAMPLING_INTER";
_opcclient.Subscribe(_node_humidity,_sampling_interval);
 //BA.debugLineNum = 102;BA.debugLine="End Sub";
return "";
}
public String  _tileconnect_click(boolean _state,String _value) throws Exception{
 //BA.debugLineNum = 201;BA.debugLine="Private Sub TileConnect_Click(State As Boolean, Va";
 //BA.debugLineNum = 202;BA.debugLine="State = Not(State)";
_state = __c.Not(_state);
 //BA.debugLineNum = 203;BA.debugLine="If State Then";
if (_state) { 
 //BA.debugLineNum = 204;BA.debugLine="TileConnected.Value = \"Connecting\"";
_tileconnected._setvalue /*String*/ ("Connecting");
 //BA.debugLineNum = 205;BA.debugLine="ConnectToServer";
_connecttoserver();
 }else {
 //BA.debugLineNum = 207;BA.debugLine="TileConnected.Value = \"Disconnecting\"";
_tileconnected._setvalue /*String*/ ("Disconnecting");
 //BA.debugLineNum = 208;BA.debugLine="OpcClient.Disconnect";
_opcclient.Disconnect();
 };
 //BA.debugLineNum = 210;BA.debugLine="End Sub";
return "";
}
public String  _tolog(String _msg) throws Exception{
 //BA.debugLineNum = 220;BA.debugLine="Public Sub ToLog(msg As String)";
 //BA.debugLineNum = 221;BA.debugLine="Log($\"${GetTime} ${msg}\"$)";
__c.LogImpl("41703937",(""+__c.SmartStringFormatter("",(Object)(_gettime()))+" "+__c.SmartStringFormatter("",(Object)(_msg))+""),0);
 //BA.debugLineNum = 222;BA.debugLine="End Sub";
return "";
}
public String  _updatehmitiles(String _nodeid,Object _value) throws Exception{
String _s = "";
float _v = 0f;
 //BA.debugLineNum = 109;BA.debugLine="Public Sub UpdateHMITiles(NodeId As String, Value";
 //BA.debugLineNum = 110;BA.debugLine="Dim s As String = GetNodeIdentifier(NodeId)";
_s = _getnodeidentifier(_nodeid);
 //BA.debugLineNum = 111;BA.debugLine="Dim v As Float = Value";
_v = (float)(BA.ObjectToNumber(_value));
 //BA.debugLineNum = 112;BA.debugLine="Select s";
switch (BA.switchObjectToInt(_s,"Temperature","Humidity")) {
case 0: {
 //BA.debugLineNum = 114;BA.debugLine="TileTemperature.Value = v";
_tiletemperature._setvalue /*String*/ (BA.NumberToString(_v));
 //BA.debugLineNum = 115;BA.debugLine="TileTemperatureGauge.Value = v";
_tiletemperaturegauge._setvalue /*String*/ (BA.NumberToString(_v));
 //BA.debugLineNum = 116;BA.debugLine="TileTemperatureGauge.Footer = GetTime";
_tiletemperaturegauge._setfooter /*String*/ (_gettime());
 break; }
case 1: {
 //BA.debugLineNum = 118;BA.debugLine="TileHumidity.Value = v";
_tilehumidity._setvalue /*String*/ (BA.NumberToString(_v));
 //BA.debugLineNum = 119;BA.debugLine="TileHumidityGauge.Value = v";
_tilehumiditygauge._setvalue /*String*/ (BA.NumberToString(_v));
 //BA.debugLineNum = 120;BA.debugLine="TileHumidityGauge.Footer = GetTime";
_tilehumiditygauge._setfooter /*String*/ (_gettime());
 break; }
}
;
 //BA.debugLineNum = 122;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
