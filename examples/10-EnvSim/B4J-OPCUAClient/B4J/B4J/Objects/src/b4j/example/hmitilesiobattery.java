package b4j.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;

public class hmitilesiobattery extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    public static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new  anywheresoftware.b4j.objects.FxBA("b4j.example", "b4j.example.hmitilesiobattery", this);
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            ba.htSubs = htSubs;
             
        }
        if (BA.isShellModeRuntimeCheck(ba))
                this.getClass().getMethod("_class_globals", b4j.example.hmitilesiobattery.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public String _text_color = "";
public int _text_size = 0;
public boolean _mstate = false;
public String _mvalue = "";
public anywheresoftware.b4a.objects.B4XViewWrapper _mparentpanel = null;
public anywheresoftware.b4j.objects.WebViewWrapper _mwebview = null;
public String _meventname = "";
public Object _mcallback = null;
public b4j.example.main _main = null;
public b4j.example.b4xpages _b4xpages = null;
public b4j.example.b4xcollections _b4xcollections = null;
public b4j.example.hmitilesioutils _hmitilesioutils = null;
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Private Sub Class_Globals";
 //BA.debugLineNum = 13;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 16;BA.debugLine="Public TEXT_COLOR As String = \"#0f172a\"";
_text_color = "#0f172a";
 //BA.debugLineNum = 17;BA.debugLine="Public TEXT_SIZE As Int = 24";
_text_size = (int) (24);
 //BA.debugLineNum = 19;BA.debugLine="Private mState						As Boolean";
_mstate = false;
 //BA.debugLineNum = 20;BA.debugLine="Private mValue						As String";
_mvalue = "";
 //BA.debugLineNum = 21;BA.debugLine="Private mParentPanel 				As B4XView		'ignore Loca";
_mparentpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private mWebView					As WebView		'ignore Local We";
_mwebview = new anywheresoftware.b4j.objects.WebViewWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private mEventName 					As String";
_meventname = "";
 //BA.debugLineNum = 24;BA.debugLine="Private mCallBack 					As Object";
_mcallback = new Object();
 //BA.debugLineNum = 25;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _pnl,anywheresoftware.b4j.objects.WebViewWrapper _wv,String _evt,Object _cb) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 28;BA.debugLine="Public Sub Initialize(pnl As B4XView, wv As WebVie";
 //BA.debugLineNum = 29;BA.debugLine="mParentPanel = pnl";
_mparentpanel = _pnl;
 //BA.debugLineNum = 30;BA.debugLine="mWebView = wv";
_mwebview = _wv;
 //BA.debugLineNum = 31;BA.debugLine="mEventName = evt";
_meventname = _evt;
 //BA.debugLineNum = 32;BA.debugLine="mCallBack = cb";
_mcallback = _cb;
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return "";
}
public b4j.example.hmitilesio._hmitouchresult  _processtouchhandler(b4j.example.hmitilesio._hmitouchdata _data) throws Exception{
b4j.example.hmitilesio._hmitouchresult _result = null;
 //BA.debugLineNum = 106;BA.debugLine="Public Sub ProcessTouchHandler(Data As HMITouchDat";
 //BA.debugLineNum = 108;BA.debugLine="mState = Data.State";
_mstate = _data.State /*boolean*/ ;
 //BA.debugLineNum = 109;BA.debugLine="mValue = Data.Value";
_mvalue = _data.Value /*String*/ ;
 //BA.debugLineNum = 112;BA.debugLine="If Data.Action = HMITilesIOUtils.ACTION_DOWN Then";
if (_data.Action /*int*/ ==_hmitilesioutils._action_down /*int*/ ) { 
 //BA.debugLineNum = 114;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_Click";
if (_xui.SubExists(ba,_mcallback,_meventname+"_Click",(int) (1))) { 
 //BA.debugLineNum = 115;BA.debugLine="CallSubDelayed3(mCallBack, mEventName & \"_Click";
__c.CallSubDelayed3(ba,_mcallback,_meventname+"_Click",(Object)(_mstate),(Object)(_mvalue));
 };
 };
 //BA.debugLineNum = 120;BA.debugLine="Dim result As HMITouchResult";
_result = new b4j.example.hmitilesio._hmitouchresult();
 //BA.debugLineNum = 121;BA.debugLine="result.Initialize";
_result.Initialize();
 //BA.debugLineNum = 122;BA.debugLine="result.State = mState";
_result.State /*Object*/  = (Object)(_mstate);
 //BA.debugLineNum = 123;BA.debugLine="result.Value = mValue";
_result.Value /*String*/  = _mvalue;
 //BA.debugLineNum = 124;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 125;BA.debugLine="End Sub";
return null;
}
public String  _settile(String _header,String _footer,String _value) throws Exception{
double _batterypct = 0;
String _escapedheader = "";
String _escapedfooter = "";
String _js_pct = "";
String _js = "";
 //BA.debugLineNum = 41;BA.debugLine="Public Sub SetTile(Header As String, _";
 //BA.debugLineNum = 46;BA.debugLine="If Not(IsNumber(Value)) Then";
if (__c.Not(__c.IsNumber(_value))) { 
 //BA.debugLineNum = 47;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 49;BA.debugLine="Dim BatteryPct As Double = Value.As(Double)";
_batterypct = ((double)(Double.parseDouble(_value)));
 //BA.debugLineNum = 52;BA.debugLine="If BatteryPct < 0 Then BatteryPct = 0";
if (_batterypct<0) { 
_batterypct = 0;};
 //BA.debugLineNum = 53;BA.debugLine="If BatteryPct > 100 Then BatteryPct = 100";
if (_batterypct>100) { 
_batterypct = 100;};
 //BA.debugLineNum = 55;BA.debugLine="Dim EscapedHeader As String = Header.Replace(\"'\",";
_escapedheader = _header.replace("'","\\'").replace("\"","\\\"");
 //BA.debugLineNum = 56;BA.debugLine="Dim EscapedFooter As String = Footer.Replace(\"'\",";
_escapedfooter = _footer.replace("'","\\'").replace("\"","\\\"");
 //BA.debugLineNum = 57;BA.debugLine="Dim JS_Pct As String = NumberFormat2(BatteryPct,";
_js_pct = __c.NumberFormat2(_batterypct,(int) (1),(int) (0),(int) (0),__c.False);
 //BA.debugLineNum = 60;BA.debugLine="Dim js As String = $\"         var head = document";
_js = ("\n"+"        var head = document.getElementById('tile-header');\n"+"        var foot = document.getElementById('tile-footer');\n"+"        var valTxt = document.getElementById('current-value');\n"+"        \n"+"        var lvl1 = document.getElementById('bat-lvl1');\n"+"        var lvl2 = document.getElementById('bat-lvl2');\n"+"        var lvl3 = document.getElementById('bat-lvl3');\n"+"        var lvl4 = document.getElementById('bat-lvl4');\n"+"\n"+"        if (head) { head.textContent = `"+__c.SmartStringFormatter("",(Object)(_escapedheader))+"`; };\n"+"        if (foot) { foot.textContent = `"+__c.SmartStringFormatter("",(Object)(_escapedfooter))+"`; };\n"+"        if (valTxt) { valTxt.textContent = `"+__c.SmartStringFormatter("",(Object)(_js_pct))+"%`; };\n"+"\n"+"        if (lvl1 && lvl2 && lvl3 && lvl4) {\n"+"            var pct = parseInt(`"+__c.SmartStringFormatter("",(Object)(_js_pct))+"`);\n"+"            \n"+"            var color = '#22c55e'; \n"+"            if (pct <= 20) { color = '#ef4444'; }       \n"+"            else if (pct <= 50) { color = '#f59e0b'; }  \n"+"\n"+"            lvl1.setAttribute('fill', pct > 5   ? color : 'none');\n"+"            lvl2.setAttribute('fill', pct >= 25 ? color : 'none');\n"+"            lvl3.setAttribute('fill', pct >= 50 ? color : 'none');\n"+"            lvl4.setAttribute('fill', pct >= 75 ? color : 'none');\n"+"        }\n"+"    ");
 //BA.debugLineNum = 88;BA.debugLine="UpdateTile(js)";
_updatetile(_js);
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return "";
}
public void  _updatetile(String _js) throws Exception{
ResumableSub_UpdateTile rsub = new ResumableSub_UpdateTile(this,_js);
rsub.resume(ba, null);
}
public static class ResumableSub_UpdateTile extends BA.ResumableSub {
public ResumableSub_UpdateTile(b4j.example.hmitilesiobattery parent,String _js) {
this.parent = parent;
this._js = _js;
}
b4j.example.hmitilesiobattery parent;
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
 //BA.debugLineNum = 96;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 97;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 98;BA.debugLine="Log($\"[Battery.UpdateTile][E] Can not update the";
parent.__c.LogImpl("922675459",("[Battery.UpdateTile][E] Can not update the tile."),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 100;BA.debugLine="End Sub";
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
