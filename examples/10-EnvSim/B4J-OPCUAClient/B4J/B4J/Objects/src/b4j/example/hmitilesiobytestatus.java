package b4j.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;

public class hmitilesiobytestatus extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    public static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new  anywheresoftware.b4j.objects.FxBA("b4j.example", "b4j.example.hmitilesiobytestatus", this);
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            ba.htSubs = htSubs;
             
        }
        if (BA.isShellModeRuntimeCheck(ba))
                this.getClass().getMethod("_class_globals", b4j.example.hmitilesiobytestatus.class).invoke(this, new Object[] {null});
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
public b4j.example.main _main = null;
public b4j.example.b4xpages _b4xpages = null;
public b4j.example.b4xcollections _b4xcollections = null;
public b4j.example.hmitilesioutils _hmitilesioutils = null;
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 19;BA.debugLine="Private Sub Class_Globals";
 //BA.debugLineNum = 20;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 23;BA.debugLine="Public TEXT_COLOR As String = \"#0f172a\"";
_text_color = "#0f172a";
 //BA.debugLineNum = 24;BA.debugLine="Public TEXT_SIZE As Int = 24";
_text_size = (int) (24);
 //BA.debugLineNum = 25;BA.debugLine="Public PinsAttached() As Byte = Array As Byte (1,";
_pinsattached = new byte[]{(byte) (1),(byte) (1),(byte) (1),(byte) (1),(byte) (1),(byte) (1),(byte) (1),(byte) (1)};
 //BA.debugLineNum = 27;BA.debugLine="Private mState						As Boolean";
_mstate = false;
 //BA.debugLineNum = 28;BA.debugLine="Private mValue						As String";
_mvalue = "";
 //BA.debugLineNum = 29;BA.debugLine="Private mParentPanel 				As B4XView		'ignore Loca";
_mparentpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private mWebView					As WebView		'ignore Local We";
_mwebview = new anywheresoftware.b4j.objects.WebViewWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private mEventName 					As String";
_meventname = "";
 //BA.debugLineNum = 32;BA.debugLine="Private mCallBack 					As Object";
_mcallback = new Object();
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _pnl,anywheresoftware.b4j.objects.WebViewWrapper _wv,String _evt,Object _cb) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 36;BA.debugLine="Public Sub Initialize(pnl As B4XView, wv As WebVie";
 //BA.debugLineNum = 37;BA.debugLine="mParentPanel = pnl";
_mparentpanel = _pnl;
 //BA.debugLineNum = 38;BA.debugLine="mWebView = wv";
_mwebview = _wv;
 //BA.debugLineNum = 39;BA.debugLine="mEventName = evt";
_meventname = _evt;
 //BA.debugLineNum = 40;BA.debugLine="mCallBack = cb";
_mcallback = _cb;
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 145;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_Click";
if (_xui.SubExists(ba,_mcallback,_meventname+"_Click",(int) (1))) { 
 //BA.debugLineNum = 146;BA.debugLine="CallSubDelayed3(mCallBack, mEventName & \"_Click";
__c.CallSubDelayed3(ba,_mcallback,_meventname+"_Click",(Object)(_mstate),(Object)(_mvalue));
 };
 };
 //BA.debugLineNum = 151;BA.debugLine="Dim result As HMITouchResult";
_result = new b4j.example.hmitilesio._hmitouchresult();
 //BA.debugLineNum = 152;BA.debugLine="result.Initialize";
_result.Initialize();
 //BA.debugLineNum = 153;BA.debugLine="result.State = mState";
_result.State /*Object*/  = (Object)(_mstate);
 //BA.debugLineNum = 154;BA.debugLine="result.Value = mValue";
_result.Value /*String*/  = _mvalue;
 //BA.debugLineNum = 155;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 156;BA.debugLine="End Sub";
return null;
}
public String  _settile(String _header,String _footer,byte[] _pins,String _value) throws Exception{
String _js = "";
 //BA.debugLineNum = 50;BA.debugLine="Public Sub SetTile(Header As String, _ 				   Foot";
 //BA.debugLineNum = 55;BA.debugLine="PinsAttached = Pins";
_pinsattached = _pins;
 //BA.debugLineNum = 57;BA.debugLine="Dim js As String = $\" 		var head = document.getEl";
_js = ("\n"+"		var head = document.getElementById(\"tile-header\");\n"+"		var foot = document.getElementById(\"tile-footer\");\n"+"		if(head) {\n"+"			head.textContent = \""+__c.SmartStringFormatter("",(Object)(_header))+"\";\n"+"		};\n"+"			\n"+"		if(foot) {\n"+"			foot.textContent = \""+__c.SmartStringFormatter("",(Object)(_footer))+"\";\n"+"		};\n"+"	");
 //BA.debugLineNum = 68;BA.debugLine="js = $\"${js}${UpdateByteStatus(Value.As(Byte), Pi";
_js = (""+__c.SmartStringFormatter("",(Object)(_js))+""+__c.SmartStringFormatter("",(Object)(_updatebytestatus((int) (((byte)(Double.parseDouble(_value)))),_pins)))+"");
 //BA.debugLineNum = 70;BA.debugLine="UpdateTile(js)";
_updatetile(_js);
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public String  _updatebytestatus(int _statusbyte,byte[] _pins) throws Exception{
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
int _i = 0;
boolean _isenabled = false;
String _urlfill = "";
int _bitposition = 0;
boolean _ison = false;
String _hexstr = "";
 //BA.debugLineNum = 90;BA.debugLine="Private Sub UpdateByteStatus(StatusByte As Int, Pi";
 //BA.debugLineNum = 91;BA.debugLine="Dim sb As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 92;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 95;BA.debugLine="If Pins.Length <> 8 Then";
if (_pins.length!=8) { 
 //BA.debugLineNum = 96;BA.debugLine="Log(\"[UpdateByteStatus][E] PinsAttached array mu";
__c.LogImpl("918087942","[UpdateByteStatus][E] PinsAttached array must contain exactly 8 elements.",0);
 //BA.debugLineNum = 97;BA.debugLine="Return \"\"";
if (true) return "";
 };
 //BA.debugLineNum = 101;BA.debugLine="For i = 0 To 7";
{
final int step7 = 1;
final int limit7 = (int) (7);
_i = (int) (0) ;
for (;_i <= limit7 ;_i = _i + step7 ) {
 //BA.debugLineNum = 103;BA.debugLine="Dim IsEnabled As Boolean = (Pins(i) = 1)";
_isenabled = (_pins[_i]==1);
 //BA.debugLineNum = 105;BA.debugLine="Dim UrlFill As String";
_urlfill = "";
 //BA.debugLineNum = 106;BA.debugLine="If Not(IsEnabled) Then";
if (__c.Not(_isenabled)) { 
 //BA.debugLineNum = 107;BA.debugLine="UrlFill = \"url(#stateDisabled)\"";
_urlfill = "url(#stateDisabled)";
 }else {
 //BA.debugLineNum = 111;BA.debugLine="Dim BitPosition As Int = 7 - i";
_bitposition = (int) (7-_i);
 //BA.debugLineNum = 113;BA.debugLine="Dim IsOn As Boolean = (Bit.And(StatusByte, Bit.";
_ison = (__c.Bit.And(_statusbyte,__c.Bit.ShiftLeft((int) (1),_bitposition))!=0);
 //BA.debugLineNum = 114;BA.debugLine="If IsOn Then";
if (_ison) { 
 //BA.debugLineNum = 115;BA.debugLine="UrlFill = \"url(#stateOn)\"";
_urlfill = "url(#stateOn)";
 }else {
 //BA.debugLineNum = 117;BA.debugLine="UrlFill = \"url(#stateOff)\"";
_urlfill = "url(#stateOff)";
 };
 };
 //BA.debugLineNum = 122;BA.debugLine="sb.Append($\"var el${i} = document.getElementById";
_sb.Append(("var el"+__c.SmartStringFormatter("",(Object)(_i))+" = document.getElementById(\"bit"+__c.SmartStringFormatter("",(Object)(_i))+"\"); if(el"+__c.SmartStringFormatter("",(Object)(_i))+"){el"+__c.SmartStringFormatter("",(Object)(_i))+".setAttribute(\"fill\", \""+__c.SmartStringFormatter("",(Object)(_urlfill))+"\");};"));
 }
};
 //BA.debugLineNum = 126;BA.debugLine="Dim HexStr As String = Bit.ToHexString(StatusByte";
_hexstr = __c.Bit.ToHexString(_statusbyte).toUpperCase(anywheresoftware.b4a.keywords.Common.stringLocale);
 //BA.debugLineNum = 127;BA.debugLine="If HexStr.Length = 1 Then HexStr = \"0\" & HexStr '";
if (_hexstr.length()==1) { 
_hexstr = "0"+_hexstr;};
 //BA.debugLineNum = 128;BA.debugLine="sb.Append($\"var ft = document.getElementById(\"til";
_sb.Append(("var ft = document.getElementById(\"tile-footer\"); if(ft){ft.textContent = \"0x"+__c.SmartStringFormatter("",(Object)(_hexstr.toUpperCase(anywheresoftware.b4a.keywords.Common.stringLocale)))+"\";};"));
 //BA.debugLineNum = 130;BA.debugLine="Return sb.ToString";
if (true) return _sb.ToString();
 //BA.debugLineNum = 131;BA.debugLine="End Sub";
return "";
}
public void  _updatetile(String _js) throws Exception{
ResumableSub_UpdateTile rsub = new ResumableSub_UpdateTile(this,_js);
rsub.resume(ba, null);
}
public static class ResumableSub_UpdateTile extends BA.ResumableSub {
public ResumableSub_UpdateTile(b4j.example.hmitilesiobytestatus parent,String _js) {
this.parent = parent;
this._js = _js;
}
b4j.example.hmitilesiobytestatus parent;
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
 //BA.debugLineNum = 78;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 79;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 80;BA.debugLine="Log($\"[ByteStatus.UpdateTile][E] Can not update";
parent.__c.LogImpl("918022403",("[ByteStatus.UpdateTile][E] Can not update the tile."),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 82;BA.debugLine="End Sub";
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
