package b4j.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;

public class hmitilesioslider extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    public static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new  anywheresoftware.b4j.objects.FxBA("b4j.example", "b4j.example.hmitilesioslider", this);
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            ba.htSubs = htSubs;
             
        }
        if (BA.isShellModeRuntimeCheck(ba))
                this.getClass().getMethod("_class_globals", b4j.example.hmitilesioslider.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public boolean _mstate = false;
public String _mvalue = "";
public anywheresoftware.b4a.objects.B4XViewWrapper _mparentpanel = null;
public anywheresoftware.b4j.objects.WebViewWrapper _mwebview = null;
public String _meventname = "";
public Object _mcallback = null;
public float _mminvalue = 0f;
public float _mmaxvalue = 0f;
public b4j.example.main _main = null;
public b4j.example.b4xpages _b4xpages = null;
public b4j.example.b4xcollections _b4xcollections = null;
public b4j.example.hmitilesioutils _hmitilesioutils = null;
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Private Sub Class_Globals";
 //BA.debugLineNum = 13;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 18;BA.debugLine="Private mState						As Boolean";
_mstate = false;
 //BA.debugLineNum = 19;BA.debugLine="Private mValue						As String";
_mvalue = "";
 //BA.debugLineNum = 20;BA.debugLine="Private mParentPanel 				As B4XView		'ignore Loca";
_mparentpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Private mWebView					As WebView		'ignore Local We";
_mwebview = new anywheresoftware.b4j.objects.WebViewWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private mEventName 					As String";
_meventname = "";
 //BA.debugLineNum = 23;BA.debugLine="Private mCallBack 					As Object";
_mcallback = new Object();
 //BA.debugLineNum = 25;BA.debugLine="Private mMinValue					As Float";
_mminvalue = 0f;
 //BA.debugLineNum = 26;BA.debugLine="Private mMaxValue					As Float";
_mmaxvalue = 0f;
 //BA.debugLineNum = 27;BA.debugLine="End Sub";
return "";
}
public float  _getmaxvalue() throws Exception{
 //BA.debugLineNum = 47;BA.debugLine="Public Sub getMaxValue As Float";
 //BA.debugLineNum = 48;BA.debugLine="Return mMaxValue";
if (true) return _mmaxvalue;
 //BA.debugLineNum = 49;BA.debugLine="End Sub";
return 0f;
}
public float  _getminvalue() throws Exception{
 //BA.debugLineNum = 40;BA.debugLine="Public Sub getMinValue As Float";
 //BA.debugLineNum = 41;BA.debugLine="Return mMinValue";
if (true) return _mminvalue;
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return 0f;
}
public String  _init(boolean _showmovement) throws Exception{
String _movement = "";
String _js = "";
 //BA.debugLineNum = 52;BA.debugLine="Public Sub Init(showmovement As Boolean)";
 //BA.debugLineNum = 53;BA.debugLine="Dim movement As String = \"\"";
_movement = "";
 //BA.debugLineNum = 54;BA.debugLine="If showmovement Then";
if (_showmovement) { 
 //BA.debugLineNum = 55;BA.debugLine="movement = $\"window.location.href = \"http://slid";
_movement = ("window.location.href = \"http://slider?val=\" + finalValue;");
 };
 //BA.debugLineNum = 58;BA.debugLine="Dim js As String = $\"         (function() {";
_js = ("\n"+"        (function() {\n"+"            var zone = document.getElementById(\"hmi-touch-zone\");\n"+"            var handle = document.getElementById(\"hmi-handle\");\n"+"            var prog = document.getElementById(\"hmi-progress\");\n"+"            var txt = document.getElementById(\"slider-val\");\n"+"			var val = 0;\n"+"            \n"+"            if (zone && handle && prog) {\n"+"                var isDragging = false;\n"+"                \n"+"                function processMovement(e) {\n"+"                    var clientX = e.clientX;\n"+"                    if (e.touches && e.touches.length > 0) {\n"+"                        clientX = e.touches[0].clientX;\n"+"                    };\n"+"                    \n"+"                    var rect = zone.getBoundingClientRect();\n"+"                    if (!rect || rect.width === 0) {\n"+"                        return;\n"+"                    };\n"+"                    \n"+"                    var touchX = ((clientX - rect.left) / rect.width) * 120;\n"+"                    \n"+"                    if (touchX < 20) { touchX = 20; };\n"+"                    if (touchX > 100) { touchX = 100; };\n"+"                    \n"+"                    var pct = (touchX - 20) / 80;\n"+"                    var finalValue = Math.round(pct * 100);\n"+"					val = finalValue;\n"+"					\n"+"                    handle.setAttribute(\"x\", (touchX - 5).toString());\n"+"                    prog.setAttribute(\"x2\", touchX.toString());\n"+"                    if (txt) { txt.textContent = finalValue; };\n"+"\n"+"					"+__c.SmartStringFormatter("",(Object)(_movement))+"\n"+"\n"+"                };\n"+"\n"+"                zone.addEventListener(\"mousedown\", function(e) {\n"+"                    isDragging = true;\n"+"                    processMovement(e);\n"+"                });\n"+"\n"+"                window.addEventListener(\"mousemove\", function(e) {\n"+"                    if (isDragging) { processMovement(e); };\n"+"                });\n"+"\n"+"                window.addEventListener(\"mouseup\", function() {\n"+"                    isDragging = false;\n"+"                    window.location.href = \"http://slider?val=\" + val;\n"+"                });\n"+"\n"+"                zone.addEventListener(\"touchstart\", function(e) {\n"+"                    isDragging = true;\n"+"                    processMovement(e);\n"+"                    e.preventDefault();\n"+"                }, {passive: false});\n"+"\n"+"                window.addEventListener(\"touchmove\", function(e) {\n"+"                    if (isDragging) { processMovement(e); };\n"+"                }, {passive: false});\n"+"\n"+"                window.addEventListener(\"touchend\", function() {\n"+"                    isDragging = false;\n"+"                });\n"+"            };\n"+"        })();\n"+"    ");
 //BA.debugLineNum = 128;BA.debugLine="InitTile(js)";
_inittile(_js);
 //BA.debugLineNum = 129;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _pnl,anywheresoftware.b4j.objects.WebViewWrapper _wv,String _evt,Object _cb) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 30;BA.debugLine="Public Sub Initialize(pnl As B4XView, wv As WebVie";
 //BA.debugLineNum = 31;BA.debugLine="mParentPanel = pnl";
_mparentpanel = _pnl;
 //BA.debugLineNum = 32;BA.debugLine="mWebView = wv";
_mwebview = _wv;
 //BA.debugLineNum = 33;BA.debugLine="mEventName = evt";
_meventname = _evt;
 //BA.debugLineNum = 34;BA.debugLine="mCallBack = cb";
_mcallback = _cb;
 //BA.debugLineNum = 35;BA.debugLine="End Sub";
return "";
}
public void  _inittile(String _js) throws Exception{
ResumableSub_InitTile rsub = new ResumableSub_InitTile(this,_js);
rsub.resume(ba, null);
}
public static class ResumableSub_InitTile extends BA.ResumableSub {
public ResumableSub_InitTile(b4j.example.hmitilesioslider parent,String _js) {
this.parent = parent;
this._js = _js;
}
b4j.example.hmitilesioslider parent;
String _js;
boolean _result;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 136;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 137;BA.debugLine="If Not(result) Then";
if (true) break;

case 1:
//if
this.state = 4;
if (parent.__c.Not(_result)) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 138;BA.debugLine="Log($\"[Slider.InitTile][E] Can not init the tile";
parent.__c.LogImpl("916842755",("[Slider.InitTile][E] Can not init the tile."),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 140;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _complete(boolean _result) throws Exception{
}
public b4j.example.hmitilesio._hmitouchresult  _processtouchhandler(b4j.example.hmitilesio._hmitouchdata _data) throws Exception{
float _panelwidth = 0f;
float _svgtouchx = 0f;
float _pct = 0f;
int _finalvalue = 0;
b4j.example.hmitilesio._hmitouchresult _result = null;
 //BA.debugLineNum = 247;BA.debugLine="Public Sub ProcessTouchHandler(Data As HMITouchDat";
 //BA.debugLineNum = 249;BA.debugLine="mState = Data.State";
_mstate = _data.State /*boolean*/ ;
 //BA.debugLineNum = 250;BA.debugLine="mValue = Data.Value";
_mvalue = _data.Value /*String*/ ;
 //BA.debugLineNum = 254;BA.debugLine="If Data.Action = HMITilesIOUtils.ACTION_DOWN Or _";
if (_data.Action /*int*/ ==_hmitilesioutils._action_down /*int*/  || _data.Action /*int*/ ==_hmitilesioutils._action_move /*int*/ ) { 
 //BA.debugLineNum = 256;BA.debugLine="Dim PanelWidth As Float = mParentPanel.Width";
_panelwidth = (float) (_mparentpanel.getWidth());
 //BA.debugLineNum = 257;BA.debugLine="Dim svgTouchX As Float = (Data.X / PanelWidth) *";
_svgtouchx = (float) ((_data.X /*float*/ /(double)_panelwidth)*120);
 //BA.debugLineNum = 259;BA.debugLine="If svgTouchX < 20 Then svgTouchX = 20";
if (_svgtouchx<20) { 
_svgtouchx = (float) (20);};
 //BA.debugLineNum = 260;BA.debugLine="If svgTouchX > 100 Then svgTouchX = 100";
if (_svgtouchx>100) { 
_svgtouchx = (float) (100);};
 //BA.debugLineNum = 262;BA.debugLine="Dim pct As Float = (svgTouchX - 20) / 80";
_pct = (float) ((_svgtouchx-20)/(double)80);
 //BA.debugLineNum = 263;BA.debugLine="Dim finalValue As Int = Round(pct * 100)";
_finalvalue = (int) (__c.Round(_pct*100));
 //BA.debugLineNum = 266;BA.debugLine="If finalValue <> mValue Then";
if (_finalvalue!=(double)(Double.parseDouble(_mvalue))) { 
 //BA.debugLineNum = 268;BA.debugLine="mValue = finalValue";
_mvalue = BA.NumberToString(_finalvalue);
 //BA.debugLineNum = 270;BA.debugLine="UpdateSlider(svgTouchX, mValue)";
_updateslider(_svgtouchx,_mvalue);
 //BA.debugLineNum = 273;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_Clic";
if (_xui.SubExists(ba,_mcallback,_meventname+"_Click",(int) (1))) { 
 //BA.debugLineNum = 274;BA.debugLine="CallSubDelayed3(mCallBack, mEventName & \"_Clic";
__c.CallSubDelayed3(ba,_mcallback,_meventname+"_Click",(Object)(_mstate),(Object)(_mvalue));
 };
 };
 };
 //BA.debugLineNum = 280;BA.debugLine="Dim result As HMITouchResult";
_result = new b4j.example.hmitilesio._hmitouchresult();
 //BA.debugLineNum = 281;BA.debugLine="result.Initialize";
_result.Initialize();
 //BA.debugLineNum = 282;BA.debugLine="result.State = mState";
_result.State /*Object*/  = (Object)(_mstate);
 //BA.debugLineNum = 283;BA.debugLine="result.Value = mValue";
_result.Value /*String*/  = _mvalue;
 //BA.debugLineNum = 284;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 285;BA.debugLine="End Sub";
return null;
}
public String  _setmaxvalue(float _value) throws Exception{
 //BA.debugLineNum = 44;BA.debugLine="Public Sub setMaxValue(value As Float)";
 //BA.debugLineNum = 45;BA.debugLine="mMaxValue = value";
_mmaxvalue = _value;
 //BA.debugLineNum = 46;BA.debugLine="End Sub";
return "";
}
public String  _setminvalue(float _value) throws Exception{
 //BA.debugLineNum = 37;BA.debugLine="Public Sub setMinValue(value As Float)";
 //BA.debugLineNum = 38;BA.debugLine="mMinValue = value";
_mminvalue = _value;
 //BA.debugLineNum = 39;BA.debugLine="End Sub";
return "";
}
public String  _settile(String _header,String _footer,int _minvalue,int _maxvalue,int _value) throws Exception{
float _handlex = 0f;
float _rectoriginx = 0f;
String _js = "";
 //BA.debugLineNum = 183;BA.debugLine="Public Sub SetTile(Header As String, _ 				   Foot";
 //BA.debugLineNum = 190;BA.debugLine="Value = Max(MinValue, Min(MaxValue, Value))";
_value = (int) (__c.Max(_minvalue,__c.Min(_maxvalue,_value)));
 //BA.debugLineNum = 191;BA.debugLine="mValue = Value";
_mvalue = BA.NumberToString(_value);
 //BA.debugLineNum = 194;BA.debugLine="Dim handleX As Float = 20.0 + ((Value / 100.0) *";
_handlex = (float) (20.0+((_value/(double)100.0)*80.0));
 //BA.debugLineNum = 195;BA.debugLine="Dim rectOriginX As Float = handleX - 5.0";
_rectoriginx = (float) (_handlex-5.0);
 //BA.debugLineNum = 197;BA.debugLine="Header = Header.Replace(\"'\", \"\\'\")";
_header = _header.replace("'","\\'");
 //BA.debugLineNum = 198;BA.debugLine="Footer = Footer.Replace(\"'\", \"\\'\")";
_footer = _footer.replace("'","\\'");
 //BA.debugLineNum = 200;BA.debugLine="Dim js As String = $\"         var head = document";
_js = ("\n"+"        var head = document.getElementById(\"tile-header\");\n"+"        var foot = document.getElementById(\"tile-footer\");\n"+"        var handle = document.getElementById(\"hmi-handle\");\n"+"        var prog = document.getElementById(\"hmi-progress\");\n"+"        var txt = document.getElementById(\"slider-val\");\n"+"        \n"+"        if(head) { head.textContent = \""+__c.SmartStringFormatter("",(Object)(_header))+"\"; };\n"+"        if(foot) { foot.textContent = \""+__c.SmartStringFormatter("",(Object)(_footer))+"\"; };\n"+"        if(txt) { txt.textContent = \""+__c.SmartStringFormatter("",(Object)(_value))+"\"; };\n"+"        if(handle) { handle.setAttribute(\"x\", \""+__c.SmartStringFormatter("",(Object)(_rectoriginx))+"\"); };\n"+"        if(prog) { prog.setAttribute(\"x2\", \""+__c.SmartStringFormatter("",(Object)(_handlex))+"\"); };\n"+"    ");
 //BA.debugLineNum = 213;BA.debugLine="UpdateTile(js)";
_updatetile(_js);
 //BA.debugLineNum = 214;BA.debugLine="End Sub";
return "";
}
public void  _updateslider(float _x,String _value) throws Exception{
ResumableSub_UpdateSlider rsub = new ResumableSub_UpdateSlider(this,_x,_value);
rsub.resume(ba, null);
}
public static class ResumableSub_UpdateSlider extends BA.ResumableSub {
public ResumableSub_UpdateSlider(b4j.example.hmitilesioslider parent,float _x,String _value) {
this.parent = parent;
this._x = _x;
this._value = _value;
}
b4j.example.hmitilesioslider parent;
float _x;
String _value;
String _js = "";
boolean _result;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 228;BA.debugLine="Dim js As String = $\" 						var handle = document";
_js = ("\n"+"						var handle = document.getElementById(\"hmi-handle\");\n"+"						var prog = document.getElementById(\"hmi-progress\");\n"+"						var txt = document.getElementById(\"slider-val\");\n"+"						\n"+"						if (handle) { handle.setAttribute(\"x\", \""+parent.__c.SmartStringFormatter("",(Object)(_x-5))+"\"); }\n"+"						if (prog)   { prog.setAttribute(\"x2\", \""+parent.__c.SmartStringFormatter("",(Object)(_x))+"\"); }\n"+"						if (txt)    { txt.textContent = \""+parent.__c.SmartStringFormatter("",(Object)(_value))+"\"; }\n"+"					");
 //BA.debugLineNum = 237;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 238;BA.debugLine="If Not(result) Then";
if (true) break;

case 1:
//if
this.state = 4;
if (parent.__c.Not(_result)) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 239;BA.debugLine="Log($\"[Slider.UpdateSlider][E] Can not update va";
parent.__c.LogImpl("917039372",("[Slider.UpdateSlider][E] Can not update value"),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 241;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _updatetile(String _js) throws Exception{
ResumableSub_UpdateTile rsub = new ResumableSub_UpdateTile(this,_js);
rsub.resume(ba, null);
}
public static class ResumableSub_UpdateTile extends BA.ResumableSub {
public ResumableSub_UpdateTile(b4j.example.hmitilesioslider parent,String _js) {
this.parent = parent;
this._js = _js;
}
b4j.example.hmitilesioslider parent;
String _js;
boolean _result;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 221;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 222;BA.debugLine="If Not(result) Then";
if (true) break;

case 1:
//if
this.state = 4;
if (parent.__c.Not(_result)) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 223;BA.debugLine="Log($\"[Slider.UpdateTile][E] Can not update the";
parent.__c.LogImpl("916973827",("[Slider.UpdateTile][E] Can not update the tile."),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 225;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
if (BA.fastSubCompare(sub, "PROCESSTOUCHHANDLER"))
	return _processtouchhandler((b4j.example.hmitilesio._hmitouchdata) args[0]);
return BA.SubDelegator.SubNotFound;
}
}
