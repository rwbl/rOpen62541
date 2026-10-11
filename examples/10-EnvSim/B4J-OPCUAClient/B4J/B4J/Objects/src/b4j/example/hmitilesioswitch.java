package b4j.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;

public class hmitilesioswitch extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    public static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new  anywheresoftware.b4j.objects.FxBA("b4j.example", "b4j.example.hmitilesioswitch", this);
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            ba.htSubs = htSubs;
             
        }
        if (BA.isShellModeRuntimeCheck(ba))
                this.getClass().getMethod("_class_globals", b4j.example.hmitilesioswitch.class).invoke(this, new Object[] {null});
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
 //BA.debugLineNum = 13;BA.debugLine="Private Sub Class_Globals";
 //BA.debugLineNum = 14;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 16;BA.debugLine="Private mState						As Boolean";
_mstate = false;
 //BA.debugLineNum = 17;BA.debugLine="Private mValue						As String";
_mvalue = "";
 //BA.debugLineNum = 18;BA.debugLine="Private mParentPanel 				As B4XView		'ignore Loca";
_mparentpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Private mWebView					As WebView		'ignore Local We";
_mwebview = new anywheresoftware.b4j.objects.WebViewWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Private mEventName 					As String";
_meventname = "";
 //BA.debugLineNum = 21;BA.debugLine="Private mCallBack 					As Object";
_mcallback = new Object();
 //BA.debugLineNum = 22;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _pnl,anywheresoftware.b4j.objects.WebViewWrapper _wv,String _evt,Object _cb) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 25;BA.debugLine="Public Sub Initialize(pnl As B4XView, wv As WebVie";
 //BA.debugLineNum = 26;BA.debugLine="mParentPanel = pnl";
_mparentpanel = _pnl;
 //BA.debugLineNum = 27;BA.debugLine="mWebView = wv";
_mwebview = _wv;
 //BA.debugLineNum = 28;BA.debugLine="mEventName = evt";
_meventname = _evt;
 //BA.debugLineNum = 29;BA.debugLine="mCallBack = cb";
_mcallback = _cb;
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return "";
}
public b4j.example.hmitilesio._hmitouchresult  _processtouchhandler(b4j.example.hmitilesio._hmitouchdata _data) throws Exception{
b4j.example.hmitilesio._hmitouchresult _result = null;
 //BA.debugLineNum = 125;BA.debugLine="Public Sub ProcessTouchHandler(Data As HMITouchDat";
 //BA.debugLineNum = 127;BA.debugLine="mState = Data.State";
_mstate = _data.State /*boolean*/ ;
 //BA.debugLineNum = 128;BA.debugLine="mValue = Data.Value";
_mvalue = _data.Value /*String*/ ;
 //BA.debugLineNum = 131;BA.debugLine="If Data.Action = HMITilesIOUtils.ACTION_DOWN Then";
if (_data.Action /*int*/ ==_hmitilesioutils._action_down /*int*/ ) { 
 //BA.debugLineNum = 133;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_Click";
if (_xui.SubExists(ba,_mcallback,_meventname+"_Click",(int) (1))) { 
 //BA.debugLineNum = 134;BA.debugLine="CallSubDelayed3(mCallBack, mEventName & \"_Click";
__c.CallSubDelayed3(ba,_mcallback,_meventname+"_Click",(Object)(_mstate),(Object)(_mvalue));
 };
 };
 //BA.debugLineNum = 139;BA.debugLine="Dim result As HMITouchResult";
_result = new b4j.example.hmitilesio._hmitouchresult();
 //BA.debugLineNum = 140;BA.debugLine="result.Initialize";
_result.Initialize();
 //BA.debugLineNum = 141;BA.debugLine="result.State = mState";
_result.State /*Object*/  = (Object)(_mstate);
 //BA.debugLineNum = 142;BA.debugLine="result.Value = mValue";
_result.Value /*String*/  = _mvalue;
 //BA.debugLineNum = 143;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 144;BA.debugLine="End Sub";
return null;
}
public String  _settile(String _header,String _footer,boolean _state) throws Exception{
String _js = "";
 //BA.debugLineNum = 38;BA.debugLine="Public Sub SetTile(Header As String, _ 				   Foot";
 //BA.debugLineNum = 42;BA.debugLine="Dim js As String";
_js = "";
 //BA.debugLineNum = 47;BA.debugLine="Header = Header.Replace(\"'\", \"\\'\")";
_header = _header.replace("'","\\'");
 //BA.debugLineNum = 48;BA.debugLine="Footer = Footer.Replace(\"'\", \"\\'\")";
_footer = _footer.replace("'","\\'");
 //BA.debugLineNum = 50;BA.debugLine="If State Then";
if (_state) { 
 //BA.debugLineNum = 52;BA.debugLine="js = $\"             var head = document.getEleme";
_js = ("\n"+"            var head = document.getElementById(\"tile-header\");\n"+"            var foot = document.getElementById(\"tile-footer\");\n"+"            var topSeg = document.getElementById(\"rocker-top\");\n"+"            var topSym = document.getElementById(\"rocker-symbol-i\");\n"+"            var botSeg = document.getElementById(\"rocker-bottom\");\n"+"            var botSym = document.getElementById(\"rocker-symbol-o\");\n"+"            \n"+"            if(head) { head.textContent = \""+__c.SmartStringFormatter("",(Object)(_header))+"\"; };\n"+"            if(foot) { foot.textContent = \""+__c.SmartStringFormatter("",(Object)(_footer))+"\"; };\n"+"            if(topSeg) {\n"+"                topSeg.setAttribute(\"fill\", \"url(#greenGlow)\");\n"+"                topSeg.setAttribute(\"stroke\", \"#86efac\");\n"+"                topSeg.setAttribute(\"height\", \"29\");\n"+"                topSym.setAttribute(\"stroke\", \"#ffffff\");\n"+"                topSym.setAttribute(\"filter\", \"url(#neonGlow)\");\n"+"            };\n"+"            if(botSeg) {\n"+"                botSeg.setAttribute(\"fill\", \"#14532d\");\n"+"                botSeg.setAttribute(\"stroke\", \"#052e16\");\n"+"                botSeg.setAttribute(\"y\", \"62\");\n"+"                botSeg.setAttribute(\"height\", \"25\");\n"+"                botSym.setAttribute(\"stroke\", \"#166534\");\n"+"                botSym.removeAttribute(\"filter\");\n"+"            };\n"+"        ");
 }else {
 //BA.debugLineNum = 80;BA.debugLine="js = $\"             var head = document.getEleme";
_js = ("\n"+"            var head = document.getElementById(\"tile-header\");\n"+"            var foot = document.getElementById(\"tile-footer\");\n"+"            var topSeg = document.getElementById(\"rocker-top\");\n"+"            var topSym = document.getElementById(\"rocker-symbol-i\");\n"+"            var botSeg = document.getElementById(\"rocker-bottom\");\n"+"            var botSym = document.getElementById(\"rocker-symbol-o\");\n"+"            \n"+"            if(head) { head.textContent = \""+__c.SmartStringFormatter("",(Object)(_header))+"\"; };\n"+"            if(foot) { foot.textContent = \""+__c.SmartStringFormatter("",(Object)(_footer))+"\"; };\n"+"            if(topSeg) {\n"+"                topSeg.setAttribute(\"fill\", \"#450a0a\");\n"+"                topSeg.setAttribute(\"stroke\", \"#1e0202\");\n"+"                topSeg.setAttribute(\"height\", \"26\");\n"+"                topSym.setAttribute(\"stroke\", \"#991b1b\");\n"+"                topSym.removeAttribute(\"filter\");\n"+"            };\n"+"            if(botSeg) {\n"+"                botSeg.setAttribute(\"fill\", \"url(#redGlow)\");\n"+"                botSeg.setAttribute(\"stroke\", \"#fca5a5\");\n"+"                botSeg.setAttribute(\"y\", \"60\");\n"+"                botSeg.setAttribute(\"height\", \"27\");\n"+"                botSym.setAttribute(\"stroke\", \"#ffffff\");\n"+"                botSym.setAttribute(\"filter\", \"url(#neonGlow)\");\n"+"            };\n"+"        ");
 };
 //BA.debugLineNum = 107;BA.debugLine="UpdateTile(js)";
_updatetile(_js);
 //BA.debugLineNum = 108;BA.debugLine="End Sub";
return "";
}
public void  _updatetile(String _js) throws Exception{
ResumableSub_UpdateTile rsub = new ResumableSub_UpdateTile(this,_js);
rsub.resume(ba, null);
}
public static class ResumableSub_UpdateTile extends BA.ResumableSub {
public ResumableSub_UpdateTile(b4j.example.hmitilesioswitch parent,String _js) {
this.parent = parent;
this._js = _js;
}
b4j.example.hmitilesioswitch parent;
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
 //BA.debugLineNum = 115;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 116;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 117;BA.debugLine="Log($\"[Switch.UpdateTile][E] Can not update the";
parent.__c.LogImpl("917367043",("[Switch.UpdateTile][E] Can not update the tile."),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 119;BA.debugLine="End Sub";
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
