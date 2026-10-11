package b4j.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;

public class hmitilesiowatchdog extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    public static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new  anywheresoftware.b4j.objects.FxBA("b4j.example", "b4j.example.hmitilesiowatchdog", this);
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            ba.htSubs = htSubs;
             
        }
        if (BA.isShellModeRuntimeCheck(ba))
                this.getClass().getMethod("_class_globals", b4j.example.hmitilesiowatchdog.class).invoke(this, new Object[] {null});
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
public int _status_ok = 0;
public int _status_warning = 0;
public int _status_error = 0;
public int _mlatency = 0;
public String _munit = "";
public b4j.example.main _main = null;
public b4j.example.b4xpages _b4xpages = null;
public b4j.example.b4xcollections _b4xcollections = null;
public b4j.example.hmitilesioutils _hmitilesioutils = null;
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 23;BA.debugLine="Private Sub Class_Globals";
 //BA.debugLineNum = 24;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 31;BA.debugLine="Private mState						As Boolean";
_mstate = false;
 //BA.debugLineNum = 32;BA.debugLine="Private mValue						As String";
_mvalue = "";
 //BA.debugLineNum = 33;BA.debugLine="Private mParentPanel 				As B4XView		'ignore Loca";
_mparentpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private mWebView					As WebView		'ignore Local We";
_mwebview = new anywheresoftware.b4j.objects.WebViewWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private mEventName 					As String";
_meventname = "";
 //BA.debugLineNum = 36;BA.debugLine="Private mCallBack 					As Object";
_mcallback = new Object();
 //BA.debugLineNum = 39;BA.debugLine="Public STATUS_OK As Int = 0";
_status_ok = (int) (0);
 //BA.debugLineNum = 40;BA.debugLine="Public STATUS_WARNING As Int = 1";
_status_warning = (int) (1);
 //BA.debugLineNum = 41;BA.debugLine="Public STATUS_ERROR As Int = 2";
_status_error = (int) (2);
 //BA.debugLineNum = 44;BA.debugLine="Private mLatency As Int = 12 ' Default latency";
_mlatency = (int) (12);
 //BA.debugLineNum = 45;BA.debugLine="Private mUnit As String = \"ms\"";
_munit = "ms";
 //BA.debugLineNum = 46;BA.debugLine="End Sub";
return "";
}
public int  _getlatency() throws Exception{
 //BA.debugLineNum = 142;BA.debugLine="Public Sub getLatency As Int";
 //BA.debugLineNum = 143;BA.debugLine="Return mLatency";
if (true) return _mlatency;
 //BA.debugLineNum = 144;BA.debugLine="End Sub";
return 0;
}
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _pnl,anywheresoftware.b4j.objects.WebViewWrapper _wv,String _evt,Object _cb) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 49;BA.debugLine="Public Sub Initialize(pnl As B4XView, wv As WebVie";
 //BA.debugLineNum = 50;BA.debugLine="mParentPanel = pnl";
_mparentpanel = _pnl;
 //BA.debugLineNum = 51;BA.debugLine="mWebView = wv";
_mwebview = _wv;
 //BA.debugLineNum = 52;BA.debugLine="mEventName = evt";
_meventname = _evt;
 //BA.debugLineNum = 53;BA.debugLine="mCallBack = cb";
_mcallback = _cb;
 //BA.debugLineNum = 54;BA.debugLine="End Sub";
return "";
}
public b4j.example.hmitilesio._hmitouchresult  _processtouchhandler(b4j.example.hmitilesio._hmitouchdata _data) throws Exception{
b4j.example.hmitilesio._hmitouchresult _result = null;
 //BA.debugLineNum = 150;BA.debugLine="Public Sub ProcessTouchHandler(Data As HMITouchDat";
 //BA.debugLineNum = 152;BA.debugLine="mState = Data.State";
_mstate = _data.State /*boolean*/ ;
 //BA.debugLineNum = 153;BA.debugLine="mValue = Data.Value";
_mvalue = _data.Value /*String*/ ;
 //BA.debugLineNum = 156;BA.debugLine="If Data.Action = HMITilesIOUtils.ACTION_DOWN Then";
if (_data.Action /*int*/ ==_hmitilesioutils._action_down /*int*/ ) { 
 //BA.debugLineNum = 158;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_Click";
if (_xui.SubExists(ba,_mcallback,_meventname+"_Click",(int) (1))) { 
 //BA.debugLineNum = 159;BA.debugLine="CallSubDelayed3(mCallBack, mEventName & \"_Click";
__c.CallSubDelayed3(ba,_mcallback,_meventname+"_Click",(Object)(_mstate),(Object)(_mvalue));
 };
 };
 //BA.debugLineNum = 164;BA.debugLine="Dim result As HMITouchResult";
_result = new b4j.example.hmitilesio._hmitouchresult();
 //BA.debugLineNum = 165;BA.debugLine="result.Initialize";
_result.Initialize();
 //BA.debugLineNum = 166;BA.debugLine="result.State = mState";
_result.State /*Object*/  = (Object)(_mstate);
 //BA.debugLineNum = 167;BA.debugLine="result.Value = mValue";
_result.Value /*String*/  = _mvalue;
 //BA.debugLineNum = 168;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 169;BA.debugLine="End Sub";
return null;
}
public String  _setlatency(int _value) throws Exception{
 //BA.debugLineNum = 139;BA.debugLine="Public Sub setLatency(Value As Int)";
 //BA.debugLineNum = 140;BA.debugLine="mLatency = Value";
_mlatency = _value;
 //BA.debugLineNum = 141;BA.debugLine="End Sub";
return "";
}
public String  _settile(String _header,String _footer,String _value) throws Exception{
String _dotcolor = "";
String _panelcolor = "";
String _textcolor = "";
String _valuetext = "";
int _statuscode = 0;
String _js = "";
 //BA.debugLineNum = 66;BA.debugLine="Public Sub SetTile(Header As String, Footer As Str";
 //BA.debugLineNum = 68;BA.debugLine="Dim dotColor As String   = \"#22c55e\"  ' Green Ind";
_dotcolor = "#22c55e";
 //BA.debugLineNum = 69;BA.debugLine="Dim panelColor As String = \"#1e293b\"  ' Slate Dar";
_panelcolor = "#1e293b";
 //BA.debugLineNum = 70;BA.debugLine="Dim textColor As String  = \"#38bdf8\"  ' High-Cont";
_textcolor = "#38bdf8";
 //BA.debugLineNum = 71;BA.debugLine="Dim valueText As String  = mLatency & \"ms\"";
_valuetext = BA.NumberToString(_mlatency)+"ms";
 //BA.debugLineNum = 72;BA.debugLine="mState = True";
_mstate = __c.True;
 //BA.debugLineNum = 74;BA.debugLine="Dim StatusCode As Int = 0";
_statuscode = (int) (0);
 //BA.debugLineNum = 75;BA.debugLine="If IsNumber(Value) Then StatusCode = Value";
if (__c.IsNumber(_value)) { 
_statuscode = (int)(Double.parseDouble(_value));};
 //BA.debugLineNum = 77;BA.debugLine="Select StatusCode";
switch (BA.switchObjectToInt(_statuscode,_status_ok,_status_warning,_status_error,(int) (3))) {
case 0: {
 //BA.debugLineNum = 79;BA.debugLine="dotColor   = \"#22c55e\"";
_dotcolor = "#22c55e";
 //BA.debugLineNum = 80;BA.debugLine="panelColor = \"#1e293b\"";
_panelcolor = "#1e293b";
 //BA.debugLineNum = 81;BA.debugLine="textColor  = \"#38bdf8\"";
_textcolor = "#38bdf8";
 //BA.debugLineNum = 82;BA.debugLine="valueText  = mLatency & mUnit";
_valuetext = BA.NumberToString(_mlatency)+_munit;
 //BA.debugLineNum = 83;BA.debugLine="mState     = True";
_mstate = __c.True;
 break; }
case 1: {
 //BA.debugLineNum = 86;BA.debugLine="dotColor   = \"#eab308\"   ' Warning Amber";
_dotcolor = "#eab308";
 //BA.debugLineNum = 87;BA.debugLine="panelColor = \"#2d2a10\"   ' Dim Warning Alert En";
_panelcolor = "#2d2a10";
 //BA.debugLineNum = 88;BA.debugLine="textColor  = \"#eab308\"";
_textcolor = "#eab308";
 //BA.debugLineNum = 89;BA.debugLine="valueText  = mLatency & mUnit";
_valuetext = BA.NumberToString(_mlatency)+_munit;
 //BA.debugLineNum = 90;BA.debugLine="mState     = False";
_mstate = __c.False;
 break; }
case 2: 
case 3: {
 //BA.debugLineNum = 93;BA.debugLine="dotColor   = \"#ef4444\"   ' Direct Danger Indica";
_dotcolor = "#ef4444";
 //BA.debugLineNum = 94;BA.debugLine="panelColor = \"#451a1a\"   ' Industrial Red Enclo";
_panelcolor = "#451a1a";
 //BA.debugLineNum = 95;BA.debugLine="textColor  = \"#fca5a5\"   ' Soft Light-Red for M";
_textcolor = "#fca5a5";
 //BA.debugLineNum = 96;BA.debugLine="valueText  = \"FAIL\"";
_valuetext = "FAIL";
 //BA.debugLineNum = 97;BA.debugLine="mState     = False";
_mstate = __c.False;
 break; }
}
;
 //BA.debugLineNum = 101;BA.debugLine="Header = Header.Replace(\"'\", \"\\'\")";
_header = _header.replace("'","\\'");
 //BA.debugLineNum = 102;BA.debugLine="Footer = Footer.Replace(\"'\", \"\\'\")";
_footer = _footer.replace("'","\\'");
 //BA.debugLineNum = 104;BA.debugLine="Dim js As String = $\"         var head = document";
_js = ("\n"+"        var head = document.getElementById(\"tile-header\");\n"+"        var foot = document.getElementById(\"tile-footer\");\n"+"        var val = document.getElementById(\"value-display\");\n"+"        var dot = document.getElementById(\"status-dot\");\n"+"        var pnl = document.getElementById(\"status-panel-bg\");\n"+"        \n"+"        if(head) { head.textContent = \""+__c.SmartStringFormatter("",(Object)(_header))+"\"; };\n"+"        if(foot) { foot.textContent = \""+__c.SmartStringFormatter("",(Object)(_footer))+"\"; };\n"+"        if(val)  { \n"+"            val.textContent = \""+__c.SmartStringFormatter("",(Object)(_valuetext))+"\"; \n"+"            val.setAttribute(\"fill\", \""+__c.SmartStringFormatter("",(Object)(_textcolor))+"\");\n"+"        };\n"+"        if(dot)  { dot.setAttribute(\"fill\", \""+__c.SmartStringFormatter("",(Object)(_dotcolor))+"\"); };\n"+"        if(pnl)  { \n"+"            pnl.setAttribute(\"fill\", \""+__c.SmartStringFormatter("",(Object)(_panelcolor))+"\"); \n"+"            pnl.setAttribute(\"stroke\", \""+__c.SmartStringFormatter("",(Object)(_textcolor))+"\");\n"+"        };\n"+"    ");
 //BA.debugLineNum = 123;BA.debugLine="UpdateTile(js)";
_updatetile(_js);
 //BA.debugLineNum = 124;BA.debugLine="End Sub";
return "";
}
public void  _updatetile(String _js) throws Exception{
ResumableSub_UpdateTile rsub = new ResumableSub_UpdateTile(this,_js);
rsub.resume(ba, null);
}
public static class ResumableSub_UpdateTile extends BA.ResumableSub {
public ResumableSub_UpdateTile(b4j.example.hmitilesiowatchdog parent,String _js) {
this.parent = parent;
this._js = _js;
}
b4j.example.hmitilesiowatchdog parent;
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
 //BA.debugLineNum = 131;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 132;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 133;BA.debugLine="Log($\"[Watchdog.UpdateTile][E] Can not update th";
parent.__c.LogImpl("926017795",("[Watchdog.UpdateTile][E] Can not update the tile."),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 135;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _complete(boolean _result) throws Exception{
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
if (BA.fastSubCompare(sub, "PROCESSTOUCHHANDLER"))
	return _processtouchhandler((b4j.example.hmitilesio._hmitouchdata) args[0]);
return BA.SubDelegator.SubNotFound;
}
}
