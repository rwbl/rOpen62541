package b4j.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;

public class hmitilesioselector extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    public static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new  anywheresoftware.b4j.objects.FxBA("b4j.example", "b4j.example.hmitilesioselector", this);
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            ba.htSubs = htSubs;
             
        }
        if (BA.isShellModeRuntimeCheck(ba))
                this.getClass().getMethod("_class_globals", b4j.example.hmitilesioselector.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public String _text_color = "";
public int _text_size = 0;
public anywheresoftware.b4a.objects.collections.List _mitems = null;
public int _mselectedindex = 0;
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
 //BA.debugLineNum = 15;BA.debugLine="Private Sub Class_Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 19;BA.debugLine="Public TEXT_COLOR As String = \"#0f172a\"";
_text_color = "#0f172a";
 //BA.debugLineNum = 20;BA.debugLine="Public TEXT_SIZE As Int = 24";
_text_size = (int) (24);
 //BA.debugLineNum = 22;BA.debugLine="Private mItems As List";
_mitems = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 23;BA.debugLine="Private mSelectedIndex As Int = -1";
_mselectedindex = (int) (-1);
 //BA.debugLineNum = 24;BA.debugLine="Private mState						As Boolean";
_mstate = false;
 //BA.debugLineNum = 25;BA.debugLine="Private mValue						As String";
_mvalue = "";
 //BA.debugLineNum = 26;BA.debugLine="Private mParentPanel 				As B4XView		'ignore Loca";
_mparentpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private mWebView					As WebView		'ignore Local We";
_mwebview = new anywheresoftware.b4j.objects.WebViewWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private mEventName 					As String";
_meventname = "";
 //BA.debugLineNum = 29;BA.debugLine="Private mCallBack 					As Object";
_mcallback = new Object();
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return "";
}
public String  _getcounterfooter() throws Exception{
 //BA.debugLineNum = 76;BA.debugLine="Public Sub GetCounterFooter As String";
 //BA.debugLineNum = 77;BA.debugLine="If mItems.Size > 0 Then";
if (_mitems.getSize()>0) { 
 //BA.debugLineNum = 78;BA.debugLine="Return $\"${mSelectedIndex + 1} of ${mItems.Size}";
if (true) return (""+__c.SmartStringFormatter("",(Object)(_mselectedindex+1))+" of "+__c.SmartStringFormatter("",(Object)(_mitems.getSize()))+"");
 }else {
 //BA.debugLineNum = 80;BA.debugLine="Return \"\"";
if (true) return "";
 };
 //BA.debugLineNum = 82;BA.debugLine="End Sub";
return "";
}
public String  _getcurrentvalue() throws Exception{
 //BA.debugLineNum = 56;BA.debugLine="Public Sub GetCurrentValue As String";
 //BA.debugLineNum = 57;BA.debugLine="If mSelectedIndex >= 0 And mSelectedIndex < mItem";
if (_mselectedindex>=0 && _mselectedindex<_mitems.getSize()) { 
 //BA.debugLineNum = 58;BA.debugLine="Return mItems.Get(mSelectedIndex)";
if (true) return BA.ObjectToString(_mitems.Get(_mselectedindex));
 };
 //BA.debugLineNum = 60;BA.debugLine="Return \"\"";
if (true) return "";
 //BA.debugLineNum = 61;BA.debugLine="End Sub";
return "";
}
public String  _getnextvalue() throws Exception{
 //BA.debugLineNum = 64;BA.debugLine="Public Sub GetNextValue As String";
 //BA.debugLineNum = 65;BA.debugLine="If mItems.Size = 0 Then Return \"\"";
if (_mitems.getSize()==0) { 
if (true) return "";};
 //BA.debugLineNum = 67;BA.debugLine="mSelectedIndex = mSelectedIndex + 1";
_mselectedindex = (int) (_mselectedindex+1);
 //BA.debugLineNum = 68;BA.debugLine="If mSelectedIndex > mItems.Size - 1 Then";
if (_mselectedindex>_mitems.getSize()-1) { 
 //BA.debugLineNum = 69;BA.debugLine="mSelectedIndex = 0";
_mselectedindex = (int) (0);
 };
 //BA.debugLineNum = 72;BA.debugLine="Return mItems.Get(mSelectedIndex)";
if (true) return BA.ObjectToString(_mitems.Get(_mselectedindex));
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _pnl,anywheresoftware.b4j.objects.WebViewWrapper _wv,String _evt,Object _cb) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 33;BA.debugLine="Public Sub Initialize(pnl As B4XView, wv As WebVie";
 //BA.debugLineNum = 34;BA.debugLine="mParentPanel = pnl";
_mparentpanel = _pnl;
 //BA.debugLineNum = 35;BA.debugLine="mWebView = wv";
_mwebview = _wv;
 //BA.debugLineNum = 36;BA.debugLine="mEventName = evt";
_meventname = _evt;
 //BA.debugLineNum = 37;BA.debugLine="mCallBack = cb";
_mcallback = _cb;
 //BA.debugLineNum = 38;BA.debugLine="mItems.Initialize";
_mitems.Initialize();
 //BA.debugLineNum = 39;BA.debugLine="End Sub";
return "";
}
public b4j.example.hmitilesio._hmitouchresult  _processtouchhandler(b4j.example.hmitilesio._hmitouchdata _data) throws Exception{
b4j.example.hmitilesio._hmitouchresult _result = null;
 //BA.debugLineNum = 137;BA.debugLine="Public Sub ProcessTouchHandler(Data As HMITouchDat";
 //BA.debugLineNum = 139;BA.debugLine="mState = Data.State";
_mstate = _data.State /*boolean*/ ;
 //BA.debugLineNum = 140;BA.debugLine="mValue = Data.Value";
_mvalue = _data.Value /*String*/ ;
 //BA.debugLineNum = 143;BA.debugLine="If Data.Action = HMITilesIOUtils.ACTION_DOWN Then";
if (_data.Action /*int*/ ==_hmitilesioutils._action_down /*int*/ ) { 
 //BA.debugLineNum = 145;BA.debugLine="mValue = GetNextValue";
_mvalue = _getnextvalue();
 //BA.debugLineNum = 146;BA.debugLine="UpdateValue(mValue)";
_updatevalue(_mvalue);
 //BA.debugLineNum = 149;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_Click";
if (_xui.SubExists(ba,_mcallback,_meventname+"_Click",(int) (1))) { 
 //BA.debugLineNum = 150;BA.debugLine="CallSubDelayed3(mCallBack, mEventName & \"_Click";
__c.CallSubDelayed3(ba,_mcallback,_meventname+"_Click",(Object)(_mstate),(Object)(_mvalue));
 };
 };
 //BA.debugLineNum = 155;BA.debugLine="Dim result As HMITouchResult";
_result = new b4j.example.hmitilesio._hmitouchresult();
 //BA.debugLineNum = 156;BA.debugLine="result.Initialize";
_result.Initialize();
 //BA.debugLineNum = 157;BA.debugLine="result.State = mState";
_result.State /*Object*/  = (Object)(_mstate);
 //BA.debugLineNum = 158;BA.debugLine="result.Value = mValue";
_result.Value /*String*/  = _mvalue;
 //BA.debugLineNum = 159;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 160;BA.debugLine="End Sub";
return null;
}
public boolean  _setitems(anywheresoftware.b4a.objects.collections.List _items) throws Exception{
 //BA.debugLineNum = 43;BA.debugLine="Public Sub SetItems(items As List) As Boolean";
 //BA.debugLineNum = 44;BA.debugLine="mItems.Clear";
_mitems.Clear();
 //BA.debugLineNum = 45;BA.debugLine="mItems = items";
_mitems = _items;
 //BA.debugLineNum = 46;BA.debugLine="If mItems.Size > 0 Then";
if (_mitems.getSize()>0) { 
 //BA.debugLineNum = 47;BA.debugLine="mSelectedIndex = 0";
_mselectedindex = (int) (0);
 //BA.debugLineNum = 48;BA.debugLine="Return True";
if (true) return __c.True;
 }else {
 //BA.debugLineNum = 50;BA.debugLine="mSelectedIndex = -1";
_mselectedindex = (int) (-1);
 //BA.debugLineNum = 51;BA.debugLine="Return False";
if (true) return __c.False;
 };
 //BA.debugLineNum = 53;BA.debugLine="End Sub";
return false;
}
public String  _settile(String _header,String _footer,String _value) throws Exception{
String _js = "";
 //BA.debugLineNum = 90;BA.debugLine="Public Sub SetTile(Header As String, Footer As Str";
 //BA.debugLineNum = 91;BA.debugLine="Dim js As String = $\" 		var head = document.getEl";
_js = ("\n"+"		var head = document.getElementById(\"tile-header\");\n"+"		var foot = document.getElementById(\"tile-footer\");\n"+"		var txt = document.getElementById(\"value-display\");\n"+"\n"+"		if(head) { head.textContent = \""+__c.SmartStringFormatter("",(Object)(_header))+"\"; };\n"+"		if(foot) { foot.textContent = \""+__c.SmartStringFormatter("",(Object)(_footer))+"\"; };\n"+"		if(txt) {\n"+"			txt.textContent = \""+__c.SmartStringFormatter("",(Object)(_value))+"\";\n"+"			txt.setAttribute(\"font-size\", \""+__c.SmartStringFormatter("",(Object)(_text_size))+"\");\n"+"			txt.setAttribute(\"fill\", \""+__c.SmartStringFormatter("",(Object)(_text_color))+"\");\n"+"		}\n"+"	");
 //BA.debugLineNum = 104;BA.debugLine="UpdateTile(js)";
_updatetile(_js);
 //BA.debugLineNum = 105;BA.debugLine="End Sub";
return "";
}
public void  _updatetile(String _js) throws Exception{
ResumableSub_UpdateTile rsub = new ResumableSub_UpdateTile(this,_js);
rsub.resume(ba, null);
}
public static class ResumableSub_UpdateTile extends BA.ResumableSub {
public ResumableSub_UpdateTile(b4j.example.hmitilesioselector parent,String _js) {
this.parent = parent;
this._js = _js;
}
b4j.example.hmitilesioselector parent;
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
 //BA.debugLineNum = 112;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 113;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 114;BA.debugLine="Log($\"[Selector.UpdateTile][E] Can not update th";
parent.__c.LogImpl("919660803",("[Selector.UpdateTile][E] Can not update the tile."),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 116;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _complete(boolean _result) throws Exception{
}
public void  _updatevalue(String _value) throws Exception{
ResumableSub_UpdateValue rsub = new ResumableSub_UpdateValue(this,_value);
rsub.resume(ba, null);
}
public static class ResumableSub_UpdateValue extends BA.ResumableSub {
public ResumableSub_UpdateValue(b4j.example.hmitilesioselector parent,String _value) {
this.parent = parent;
this._value = _value;
}
b4j.example.hmitilesioselector parent;
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
 //BA.debugLineNum = 119;BA.debugLine="Dim js As String = $\" 		var txt = document.getEle";
_js = ("\n"+"		var txt = document.getElementById(\"value-display\");\n"+"		if(txt) {\n"+"			txt.textContent = \""+parent.__c.SmartStringFormatter("",(Object)(_value))+"\";\n"+"			txt.setAttribute(\"font-size\", \""+parent.__c.SmartStringFormatter("",(Object)(parent._text_size))+"\");\n"+"			txt.setAttribute(\"fill\", \""+parent.__c.SmartStringFormatter("",(Object)(parent._text_color))+"\");\n"+"		}\n"+"	");
 //BA.debugLineNum = 127;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 128;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 129;BA.debugLine="Log($\"[Selector.UpdateValue][E] Can not update v";
parent.__c.LogImpl("919726347",("[Selector.UpdateValue][E] Can not update value"),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 131;BA.debugLine="End Sub";
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
