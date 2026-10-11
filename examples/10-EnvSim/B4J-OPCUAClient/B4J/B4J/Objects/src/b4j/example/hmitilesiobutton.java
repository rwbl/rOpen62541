package b4j.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;

public class hmitilesiobutton extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    public static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new  anywheresoftware.b4j.objects.FxBA("b4j.example", "b4j.example.hmitilesiobutton", this);
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            ba.htSubs = htSubs;
             
        }
        if (BA.isShellModeRuntimeCheck(ba))
                this.getClass().getMethod("_class_globals", b4j.example.hmitilesiobutton.class).invoke(this, new Object[] {null});
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
public b4j.example.main _main = null;
public b4j.example.b4xpages _b4xpages = null;
public b4j.example.b4xcollections _b4xcollections = null;
public b4j.example.hmitilesioutils _hmitilesioutils = null;
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Private Sub Class_Globals";
 //BA.debugLineNum = 13;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 15;BA.debugLine="Private mState						As Boolean";
_mstate = false;
 //BA.debugLineNum = 16;BA.debugLine="Private mValue						As String";
_mvalue = "";
 //BA.debugLineNum = 17;BA.debugLine="Private mParentPanel 				As B4XView		'ignore Loca";
_mparentpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private mWebView					As WebView		'ignore Local We";
_mwebview = new anywheresoftware.b4j.objects.WebViewWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private mEventName 					As String";
_meventname = "";
 //BA.debugLineNum = 20;BA.debugLine="Private mCallBack 					As Object";
_mcallback = new Object();
 //BA.debugLineNum = 21;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _pnl,anywheresoftware.b4j.objects.WebViewWrapper _wv,String _evt,Object _cb) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 24;BA.debugLine="Public Sub Initialize(pnl As B4XView, wv As WebVie";
 //BA.debugLineNum = 25;BA.debugLine="mParentPanel = pnl";
_mparentpanel = _pnl;
 //BA.debugLineNum = 26;BA.debugLine="mWebView = wv";
_mwebview = _wv;
 //BA.debugLineNum = 27;BA.debugLine="mEventName = evt";
_meventname = _evt;
 //BA.debugLineNum = 28;BA.debugLine="mCallBack = cb";
_mcallback = _cb;
 //BA.debugLineNum = 29;BA.debugLine="End Sub";
return "";
}
public b4j.example.hmitilesio._hmitouchresult  _processtouchhandler(b4j.example.hmitilesio._hmitouchdata _data) throws Exception{
b4j.example.hmitilesio._hmitouchresult _result = null;
 //BA.debugLineNum = 96;BA.debugLine="Public Sub ProcessTouchHandler(Data As HMITouchDat";
 //BA.debugLineNum = 98;BA.debugLine="mState = Data.State";
_mstate = _data.State /*boolean*/ ;
 //BA.debugLineNum = 99;BA.debugLine="mValue = Data.Value";
_mvalue = _data.Value /*String*/ ;
 //BA.debugLineNum = 102;BA.debugLine="If Data.Action = HMITilesIOUtils.ACTION_DOWN Then";
if (_data.Action /*int*/ ==_hmitilesioutils._action_down /*int*/ ) { 
 //BA.debugLineNum = 104;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_Click";
if (_xui.SubExists(ba,_mcallback,_meventname+"_Click",(int) (1))) { 
 //BA.debugLineNum = 105;BA.debugLine="CallSubDelayed3(mCallBack, mEventName & \"_Click";
__c.CallSubDelayed3(ba,_mcallback,_meventname+"_Click",(Object)(_mstate),(Object)(_mvalue));
 };
 };
 //BA.debugLineNum = 110;BA.debugLine="Dim result As HMITouchResult";
_result = new b4j.example.hmitilesio._hmitouchresult();
 //BA.debugLineNum = 111;BA.debugLine="result.Initialize";
_result.Initialize();
 //BA.debugLineNum = 112;BA.debugLine="result.State = mState";
_result.State /*Object*/  = (Object)(_mstate);
 //BA.debugLineNum = 113;BA.debugLine="result.Value = mValue";
_result.Value /*String*/  = _mvalue;
 //BA.debugLineNum = 114;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 115;BA.debugLine="End Sub";
return null;
}
public String  _settile(String _header,String _footer,boolean _state) throws Exception{
String _js = "";
 //BA.debugLineNum = 37;BA.debugLine="Public Sub SetTile(Header As String, _ 					Footer";
 //BA.debugLineNum = 40;BA.debugLine="Dim js As String";
_js = "";
 //BA.debugLineNum = 43;BA.debugLine="Header = Header.Replace(\"'\", \"\\'\")";
_header = _header.replace("'","\\'");
 //BA.debugLineNum = 44;BA.debugLine="Footer = Footer.Replace(\"'\", \"\\'\")";
_footer = _footer.replace("'","\\'");
 //BA.debugLineNum = 46;BA.debugLine="If State Then";
if (_state) { 
 //BA.debugLineNum = 47;BA.debugLine="js = $\"             var head = document.getEleme";
_js = ("\n"+"            var head = document.getElementById(\"tile-header\");\n"+"            var foot = document.getElementById(\"tile-footer\");\n"+"            var lens = document.getElementById(\"led-lens\");\n"+"            if(head) { head.textContent = \""+__c.SmartStringFormatter("",(Object)(_header))+"\"; };\n"+"            if(foot) {\n"+"                foot.textContent = \""+__c.SmartStringFormatter("",(Object)(_footer))+"\";\n"+"                foot.setAttribute(\"fill\", \"#64748b\");\n"+"            };\n"+"            if(lens) {\n"+"                lens.setAttribute(\"fill\", \"url(#ledGreen)\");\n"+"                lens.setAttribute(\"filter\", \"url(#lensGlow)\");\n"+"            };\n"+"        ");
 }else {
 //BA.debugLineNum = 62;BA.debugLine="js = $\"             var head = document.getEleme";
_js = ("\n"+"            var head = document.getElementById(\"polygon-header\");\n"+"            var head = document.getElementById(\"tile-header\");\n"+"            var foot = document.getElementById(\"tile-footer\");\n"+"            var lens = document.getElementById(\"led-lens\");\n"+"            if(head) { head.textContent = \""+__c.SmartStringFormatter("",(Object)(_header))+"\"; };\n"+"            if(foot) {\n"+"                foot.textContent = \""+__c.SmartStringFormatter("",(Object)(_footer))+"\";\n"+"                foot.setAttribute(\"fill\", \"#64748b\");\n"+"            };\n"+"            if(lens) {\n"+"                lens.setAttribute(\"fill\", \"url(#ledOff)\");\n"+"                lens.removeAttribute(\"filter\");\n"+"            };\n"+"        ");
 };
 //BA.debugLineNum = 78;BA.debugLine="UpdateTile(js)";
_updatetile(_js);
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
return "";
}
public void  _updatetile(String _js) throws Exception{
ResumableSub_UpdateTile rsub = new ResumableSub_UpdateTile(this,_js);
rsub.resume(ba, null);
}
public static class ResumableSub_UpdateTile extends BA.ResumableSub {
public ResumableSub_UpdateTile(b4j.example.hmitilesiobutton parent,String _js) {
this.parent = parent;
this._js = _js;
}
b4j.example.hmitilesiobutton parent;
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
 //BA.debugLineNum = 86;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 87;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 88;BA.debugLine="Log($\"[Button.UpdateTile][E] Can not update the";
parent.__c.LogImpl("922347779",("[Button.UpdateTile][E] Can not update the tile."),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 90;BA.debugLine="End Sub";
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
