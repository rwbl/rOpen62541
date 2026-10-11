package b4j.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;

public class hmitilesiospinner extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    public static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new  anywheresoftware.b4j.objects.FxBA("b4j.example", "b4j.example.hmitilesiospinner", this);
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            ba.htSubs = htSubs;
             
        }
        if (BA.isShellModeRuntimeCheck(ba))
                this.getClass().getMethod("_class_globals", b4j.example.hmitilesiospinner.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public String _text_color = "";
public int _text_size = 0;
public byte[] _pinsattached = null;
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
 //BA.debugLineNum = 13;BA.debugLine="Private Sub Class_Globals";
 //BA.debugLineNum = 14;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 17;BA.debugLine="Public TEXT_COLOR As String = \"#0f172a\"";
_text_color = "#0f172a";
 //BA.debugLineNum = 18;BA.debugLine="Public TEXT_SIZE As Int = 24";
_text_size = (int) (24);
 //BA.debugLineNum = 19;BA.debugLine="Public PinsAttached() As Byte = Array As Byte (1,";
_pinsattached = new byte[]{(byte) (1),(byte) (1),(byte) (1),(byte) (1),(byte) (1),(byte) (1),(byte) (1),(byte) (1)};
 //BA.debugLineNum = 21;BA.debugLine="Private mState						As Boolean";
_mstate = false;
 //BA.debugLineNum = 22;BA.debugLine="Private mValue						As String";
_mvalue = "";
 //BA.debugLineNum = 23;BA.debugLine="Private mParentPanel 				As B4XView		'ignore Loca";
_mparentpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private mWebView					As WebView		'ignore Local We";
_mwebview = new anywheresoftware.b4j.objects.WebViewWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private mEventName 					As String";
_meventname = "";
 //BA.debugLineNum = 26;BA.debugLine="Private mCallBack 					As Object";
_mcallback = new Object();
 //BA.debugLineNum = 28;BA.debugLine="Private mMinValue					As Float";
_mminvalue = 0f;
 //BA.debugLineNum = 29;BA.debugLine="Private mMaxValue					As Float";
_mmaxvalue = 0f;
 //BA.debugLineNum = 30;BA.debugLine="End Sub";
return "";
}
public float  _getmaxvalue() throws Exception{
 //BA.debugLineNum = 50;BA.debugLine="Public Sub getMaxValue As Float";
 //BA.debugLineNum = 51;BA.debugLine="Return mMaxValue";
if (true) return _mmaxvalue;
 //BA.debugLineNum = 52;BA.debugLine="End Sub";
return 0f;
}
public float  _getminvalue() throws Exception{
 //BA.debugLineNum = 43;BA.debugLine="Public Sub getMinValue As Float";
 //BA.debugLineNum = 44;BA.debugLine="Return mMinValue";
if (true) return _mminvalue;
 //BA.debugLineNum = 45;BA.debugLine="End Sub";
return 0f;
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
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return "";
}
public b4j.example.hmitilesio._hmitouchresult  _processtouchhandler(b4j.example.hmitilesio._hmitouchdata _data) throws Exception{
float _panelwidth = 0f;
float _svgtouchx = 0f;
boolean _valuechanged = false;
b4j.example.hmitilesio._hmitouchresult _result = null;
 //BA.debugLineNum = 124;BA.debugLine="Public Sub ProcessTouchHandler(Data As HMITouchDat";
 //BA.debugLineNum = 126;BA.debugLine="mState = Data.State";
_mstate = _data.State /*boolean*/ ;
 //BA.debugLineNum = 127;BA.debugLine="mValue = Data.Value";
_mvalue = _data.Value /*String*/ ;
 //BA.debugLineNum = 130;BA.debugLine="If Data.Action = HMITilesIOUtils.ACTION_DOWN Then";
if (_data.Action /*int*/ ==_hmitilesioutils._action_down /*int*/ ) { 
 //BA.debugLineNum = 132;BA.debugLine="Dim PanelWidth As Float = mParentPanel.Width";
_panelwidth = (float) (_mparentpanel.getWidth());
 //BA.debugLineNum = 135;BA.debugLine="Dim svgTouchX As Float = (Data.X / PanelWidth) *";
_svgtouchx = (float) ((_data.X /*float*/ /(double)_panelwidth)*120);
 //BA.debugLineNum = 137;BA.debugLine="Dim ValueChanged As Boolean = False";
_valuechanged = __c.False;
 //BA.debugLineNum = 140;BA.debugLine="If svgTouchX <= 40 Then";
if (_svgtouchx<=40) { 
 //BA.debugLineNum = 142;BA.debugLine="mValue = mValue - 1";
_mvalue = BA.NumberToString((double)(Double.parseDouble(_mvalue))-1);
 //BA.debugLineNum = 143;BA.debugLine="ValueChanged = True";
_valuechanged = __c.True;
 }else if(_svgtouchx>=80) { 
 //BA.debugLineNum = 146;BA.debugLine="mValue = mValue + 1";
_mvalue = BA.NumberToString((double)(Double.parseDouble(_mvalue))+1);
 //BA.debugLineNum = 147;BA.debugLine="ValueChanged = True";
_valuechanged = __c.True;
 };
 //BA.debugLineNum = 151;BA.debugLine="If ValueChanged Then";
if (_valuechanged) { 
 //BA.debugLineNum = 154;BA.debugLine="mValue = Max(mMinValue, Min(mMaxValue, mValue.A";
_mvalue = BA.NumberToString(__c.Max(_mminvalue,__c.Min(_mmaxvalue,((float)(Double.parseDouble(_mvalue))))));
 //BA.debugLineNum = 157;BA.debugLine="UpdateValue(mValue)";
_updatevalue(_mvalue);
 //BA.debugLineNum = 160;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_Clic";
if (_xui.SubExists(ba,_mcallback,_meventname+"_Click",(int) (1))) { 
 //BA.debugLineNum = 161;BA.debugLine="CallSubDelayed3(mCallBack, mEventName & \"_Clic";
__c.CallSubDelayed3(ba,_mcallback,_meventname+"_Click",(Object)(_mstate),(Object)(_mvalue));
 };
 };
 };
 //BA.debugLineNum = 168;BA.debugLine="Dim result As HMITouchResult";
_result = new b4j.example.hmitilesio._hmitouchresult();
 //BA.debugLineNum = 169;BA.debugLine="result.Initialize";
_result.Initialize();
 //BA.debugLineNum = 170;BA.debugLine="result.State = mState";
_result.State /*Object*/  = (Object)(_mstate);
 //BA.debugLineNum = 171;BA.debugLine="result.Value = mValue";
_result.Value /*String*/  = _mvalue;
 //BA.debugLineNum = 173;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 174;BA.debugLine="End Sub";
return null;
}
public String  _setmaxvalue(float _value) throws Exception{
 //BA.debugLineNum = 47;BA.debugLine="Public Sub setMaxValue(value As Float)";
 //BA.debugLineNum = 48;BA.debugLine="mMaxValue = value";
_mmaxvalue = _value;
 //BA.debugLineNum = 49;BA.debugLine="End Sub";
return "";
}
public String  _setminvalue(float _value) throws Exception{
 //BA.debugLineNum = 40;BA.debugLine="Public Sub setMinValue(value As Float)";
 //BA.debugLineNum = 41;BA.debugLine="mMinValue = value";
_mminvalue = _value;
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public String  _settile(String _header,String _footer,float _minvalue,float _maxvalue,String _value) throws Exception{
String _js = "";
 //BA.debugLineNum = 61;BA.debugLine="Public Sub SetTile(Header As String, _ 				   Foot";
 //BA.debugLineNum = 68;BA.debugLine="Value = Max(MinValue, Min(MaxValue, Value.As(Floa";
_value = BA.NumberToString(__c.Max(_minvalue,__c.Min(_maxvalue,((float)(Double.parseDouble(_value))))));
 //BA.debugLineNum = 71;BA.debugLine="Dim js As String = $\" 		var head = document.getEl";
_js = ("\n"+"		var head = document.getElementById(\"tile-header\");\n"+"		var foot = document.getElementById(\"tile-footer\");\n"+"		var txt = document.getElementById(\"value-display\");\n"+"\n"+"		if(head) { \n"+"			head.textContent = \""+__c.SmartStringFormatter("",(Object)(_header))+"\"; \n"+"		};\n"+"		\n"+"		if(foot) { \n"+"			foot.textContent = \""+__c.SmartStringFormatter("",(Object)(_footer))+"\"; \n"+"		};\n"+"\n"+"		if (txt) {\n"+"			txt.textContent = \""+__c.SmartStringFormatter("",(Object)(_value))+"\";\n"+"			txt.setAttribute(\"font-size\", \""+__c.SmartStringFormatter("",(Object)(_text_size))+"\");\n"+"			txt.setAttribute(\"fill\", \""+__c.SmartStringFormatter("",(Object)(_text_color))+"\");\n"+"		}\n"+"	");
 //BA.debugLineNum = 90;BA.debugLine="UpdateTile(js)";
_updatetile(_js);
 //BA.debugLineNum = 91;BA.debugLine="End Sub";
return "";
}
public void  _updatetile(String _js) throws Exception{
ResumableSub_UpdateTile rsub = new ResumableSub_UpdateTile(this,_js);
rsub.resume(ba, null);
}
public static class ResumableSub_UpdateTile extends BA.ResumableSub {
public ResumableSub_UpdateTile(b4j.example.hmitilesiospinner parent,String _js) {
this.parent = parent;
this._js = _js;
}
b4j.example.hmitilesiospinner parent;
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
 //BA.debugLineNum = 98;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 99;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 100;BA.debugLine="Log($\"[Spinner.UpdateTile][E] Can not update the";
parent.__c.LogImpl("919005443",("[Spinner.UpdateTile][E] Can not update the tile."),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 102;BA.debugLine="End Sub";
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
public ResumableSub_UpdateValue(b4j.example.hmitilesiospinner parent,String _value) {
this.parent = parent;
this._value = _value;
}
b4j.example.hmitilesiospinner parent;
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
 //BA.debugLineNum = 105;BA.debugLine="Dim js As String = $\" 		var txt = document.getEle";
_js = ("\n"+"		var txt = document.getElementById(\"value-display\");\n"+"		if(txt) {\n"+"			txt.textContent = \""+parent.__c.SmartStringFormatter("",(Object)(_value))+"\";\n"+"			txt.setAttribute(\"font-size\", \""+parent.__c.SmartStringFormatter("",(Object)(parent._text_size))+"\");\n"+"			txt.setAttribute(\"fill\", \""+parent.__c.SmartStringFormatter("",(Object)(parent._text_color))+"\");\n"+"		}\n"+"	");
 //BA.debugLineNum = 114;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 115;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 116;BA.debugLine="Log($\"[Spinner.UpdateValue][E] Can not update va";
parent.__c.LogImpl("919070988",("[Spinner.UpdateValue][E] Can not update value"),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 118;BA.debugLine="End Sub";
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
