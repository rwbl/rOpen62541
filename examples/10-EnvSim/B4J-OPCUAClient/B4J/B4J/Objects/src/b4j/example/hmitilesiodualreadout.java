package b4j.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;

public class hmitilesiodualreadout extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    public static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new  anywheresoftware.b4j.objects.FxBA("b4j.example", "b4j.example.hmitilesiodualreadout", this);
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            ba.htSubs = htSubs;
             
        }
        if (BA.isShellModeRuntimeCheck(ba))
                this.getClass().getMethod("_class_globals", b4j.example.hmitilesiodualreadout.class).invoke(this, new Object[] {null});
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
public String _mvalueleft = "";
public String _mvalueright = "";
public String _munitleft = "";
public String _munitright = "";
public b4j.example.main _main = null;
public b4j.example.b4xpages _b4xpages = null;
public b4j.example.b4xcollections _b4xcollections = null;
public b4j.example.hmitilesioutils _hmitilesioutils = null;
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Private Sub Class_Globals";
 //BA.debugLineNum = 17;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 20;BA.debugLine="Public TEXT_COLOR As String = \"#0f172a\"";
_text_color = "#0f172a";
 //BA.debugLineNum = 21;BA.debugLine="Public TEXT_SIZE As Int = 24";
_text_size = (int) (24);
 //BA.debugLineNum = 23;BA.debugLine="Private mState						As Boolean";
_mstate = false;
 //BA.debugLineNum = 24;BA.debugLine="Private mValue						As String";
_mvalue = "";
 //BA.debugLineNum = 25;BA.debugLine="Private mParentPanel 				As B4XView		'ignore Loca";
_mparentpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private mWebView					As WebView		'ignore Local We";
_mwebview = new anywheresoftware.b4j.objects.WebViewWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private mEventName 					As String";
_meventname = "";
 //BA.debugLineNum = 28;BA.debugLine="Private mCallBack 					As Object";
_mcallback = new Object();
 //BA.debugLineNum = 31;BA.debugLine="Private mValueLeft					As String";
_mvalueleft = "";
 //BA.debugLineNum = 32;BA.debugLine="Private mValueRight					As String";
_mvalueright = "";
 //BA.debugLineNum = 33;BA.debugLine="Private mUnitLeft					As String";
_munitleft = "";
 //BA.debugLineNum = 34;BA.debugLine="Private mUnitRight					As String";
_munitright = "";
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _pnl,anywheresoftware.b4j.objects.WebViewWrapper _wv,String _evt,Object _cb) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 39;BA.debugLine="Public Sub Initialize(pnl As B4XView, wv As WebVie";
 //BA.debugLineNum = 40;BA.debugLine="mParentPanel = pnl";
_mparentpanel = _pnl;
 //BA.debugLineNum = 41;BA.debugLine="mWebView = wv";
_mwebview = _wv;
 //BA.debugLineNum = 42;BA.debugLine="mEventName = evt";
_meventname = _evt;
 //BA.debugLineNum = 43;BA.debugLine="mCallBack = cb";
_mcallback = _cb;
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
return "";
}
public b4j.example.hmitilesio._hmitouchresult  _processtouchhandler(b4j.example.hmitilesio._hmitouchdata _data) throws Exception{
b4j.example.hmitilesio._hmitouchresult _result = null;
 //BA.debugLineNum = 105;BA.debugLine="Public Sub ProcessTouchHandler(Data As HMITouchDat";
 //BA.debugLineNum = 107;BA.debugLine="mState = Data.State";
_mstate = _data.State /*boolean*/ ;
 //BA.debugLineNum = 108;BA.debugLine="mValue = Data.Value";
_mvalue = _data.Value /*String*/ ;
 //BA.debugLineNum = 111;BA.debugLine="If Data.Action = HMITilesIOUtils.ACTION_DOWN Then";
if (_data.Action /*int*/ ==_hmitilesioutils._action_down /*int*/ ) { 
 //BA.debugLineNum = 113;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_Click";
if (_xui.SubExists(ba,_mcallback,_meventname+"_Click",(int) (1))) { 
 //BA.debugLineNum = 114;BA.debugLine="CallSubDelayed3(mCallBack, mEventName & \"_Click";
__c.CallSubDelayed3(ba,_mcallback,_meventname+"_Click",(Object)(_mstate),(Object)(_mvalue));
 };
 };
 //BA.debugLineNum = 119;BA.debugLine="Dim result As HMITouchResult";
_result = new b4j.example.hmitilesio._hmitouchresult();
 //BA.debugLineNum = 120;BA.debugLine="result.Initialize";
_result.Initialize();
 //BA.debugLineNum = 121;BA.debugLine="result.State = mState";
_result.State /*Object*/  = (Object)(_mstate);
 //BA.debugLineNum = 122;BA.debugLine="result.Value = mValue";
_result.Value /*String*/  = _mvalue;
 //BA.debugLineNum = 123;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 124;BA.debugLine="End Sub";
return null;
}
public String  _settile(String _header,String _footer,String _value) throws Exception{
String[] _items = null;
String _js = "";
 //BA.debugLineNum = 52;BA.debugLine="Public Sub SetTile(Header As String, _ 				   Foot";
 //BA.debugLineNum = 56;BA.debugLine="Dim items() As String = Regex.Split(\";\", Value)";
_items = __c.Regex.Split(";",_value);
 //BA.debugLineNum = 57;BA.debugLine="If items.Length <> 4 Then";
if (_items.length!=4) { 
 //BA.debugLineNum = 58;BA.debugLine="Log($\"[DualReadOut.SetTile][E] Value \"${Value}\"";
__c.LogImpl("922937606",("[DualReadOut.SetTile][E] Value \""+__c.SmartStringFormatter("",(Object)(_value))+"\" wrong number of items. Expect 4, got "+__c.SmartStringFormatter("",(Object)(_items.length))+""),0);
 //BA.debugLineNum = 59;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 63;BA.debugLine="mValueLeft	= items(0)";
_mvalueleft = _items[(int) (0)];
 //BA.debugLineNum = 64;BA.debugLine="mUnitLeft	= items(1)";
_munitleft = _items[(int) (1)];
 //BA.debugLineNum = 65;BA.debugLine="mValueRight	= items(2)";
_mvalueright = _items[(int) (2)];
 //BA.debugLineNum = 66;BA.debugLine="mUnitRight	= items(3)";
_munitright = _items[(int) (3)];
 //BA.debugLineNum = 70;BA.debugLine="Dim js As String = $\" 		var head = document.getEl";
_js = ("\n"+"		var head = document.getElementById(\"tile-header\");\n"+"		var foot = document.getElementById(\"tile-footer\");\n"+"		var valueleft = document.getElementById(\"value-left\");\n"+"		var unitleft = document.getElementById(\"unit-left\");\n"+"		var valueright = document.getElementById(\"value-right\");\n"+"		var unitright = document.getElementById(\"unit-right\");\n"+"\n"+"		if(head) { head.textContent = \""+__c.SmartStringFormatter("",(Object)(_header))+"\"; };\n"+"		if(foot) { foot.textContent = \""+__c.SmartStringFormatter("",(Object)(_footer))+"\"; };\n"+"\n"+"\n"+"		if(valueleft) { valueleft.textContent = \""+__c.SmartStringFormatter("",(Object)(_mvalueleft))+"\"; };\n"+"		if(unitleft) { unitleft.textContent = \""+__c.SmartStringFormatter("",(Object)(_munitleft))+"\"; };\n"+"		if(valueright) { valueright.textContent = \""+__c.SmartStringFormatter("",(Object)(_mvalueright))+"\"; };\n"+"		if(unitright) { unitright.textContent = \""+__c.SmartStringFormatter("",(Object)(_munitright))+"\"; }\n"+"	");
 //BA.debugLineNum = 87;BA.debugLine="UpdateTile(js)";
_updatetile(_js);
 //BA.debugLineNum = 88;BA.debugLine="End Sub";
return "";
}
public void  _updatetile(String _js) throws Exception{
ResumableSub_UpdateTile rsub = new ResumableSub_UpdateTile(this,_js);
rsub.resume(ba, null);
}
public static class ResumableSub_UpdateTile extends BA.ResumableSub {
public ResumableSub_UpdateTile(b4j.example.hmitilesiodualreadout parent,String _js) {
this.parent = parent;
this._js = _js;
}
b4j.example.hmitilesiodualreadout parent;
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
 //BA.debugLineNum = 95;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 96;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 97;BA.debugLine="Log($\"[DualReadOut.UpdateTile][E] Can not update";
parent.__c.LogImpl("923003139",("[DualReadOut.UpdateTile][E] Can not update the tile."),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
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
