package b4j.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;

public class hmitilesiosevensegment extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    public static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new  anywheresoftware.b4j.objects.FxBA("b4j.example", "b4j.example.hmitilesiosevensegment", this);
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            ba.htSubs = htSubs;
             
        }
        if (BA.isShellModeRuntimeCheck(ba))
                this.getClass().getMethod("_class_globals", b4j.example.hmitilesiosevensegment.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public String _font_file = "";
public String _font_base64_placeholder = "";
public String _text_color = "";
public int _text_size = 0;
public String _err_text = "";
public String _err_color = "";
public int _min_value = 0;
public int _max_value = 0;
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
 //BA.debugLineNum = 16;BA.debugLine="Private Sub Class_Globals";
 //BA.debugLineNum = 17;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 20;BA.debugLine="Private FONT_FILE 	As String = \"digital.ttf\"";
_font_file = "digital.ttf";
 //BA.debugLineNum = 23;BA.debugLine="Private FONT_BASE64_PLACEHOLDER As String = \"#FON";
_font_base64_placeholder = "#FONT_BASE64_PLACEHOLDER#";
 //BA.debugLineNum = 26;BA.debugLine="Public TEXT_COLOR 	As String = \"#cbd5e1\"";
_text_color = "#cbd5e1";
 //BA.debugLineNum = 27;BA.debugLine="Public TEXT_SIZE 	As Int = 24";
_text_size = (int) (24);
 //BA.debugLineNum = 30;BA.debugLine="Private ERR_TEXT 	As String = \"Err\"";
_err_text = "Err";
 //BA.debugLineNum = 31;BA.debugLine="Private ERR_COLOR	As String = \"#ef4444\" ' Alarm R";
_err_color = "#ef4444";
 //BA.debugLineNum = 34;BA.debugLine="Private MIN_VALUE 	As Int = 0";
_min_value = (int) (0);
 //BA.debugLineNum = 35;BA.debugLine="Private MAX_VALUE 	As Int = 9999";
_max_value = (int) (9999);
 //BA.debugLineNum = 37;BA.debugLine="Private mState						As Boolean";
_mstate = false;
 //BA.debugLineNum = 38;BA.debugLine="Private mValue						As String";
_mvalue = "";
 //BA.debugLineNum = 39;BA.debugLine="Private mParentPanel 				As B4XView		'ignore Loca";
_mparentpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private mWebView					As WebView		'ignore Local We";
_mwebview = new anywheresoftware.b4j.objects.WebViewWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private mEventName 					As String";
_meventname = "";
 //BA.debugLineNum = 42;BA.debugLine="Private mCallBack 					As Object";
_mcallback = new Object();
 //BA.debugLineNum = 43;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _pnl,anywheresoftware.b4j.objects.WebViewWrapper _wv,String _evt,Object _cb) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 46;BA.debugLine="Public Sub Initialize(pnl As B4XView, wv As WebVie";
 //BA.debugLineNum = 47;BA.debugLine="mParentPanel = pnl";
_mparentpanel = _pnl;
 //BA.debugLineNum = 48;BA.debugLine="mWebView = wv";
_mwebview = _wv;
 //BA.debugLineNum = 49;BA.debugLine="mEventName = evt";
_meventname = _evt;
 //BA.debugLineNum = 50;BA.debugLine="mCallBack = cb";
_mcallback = _cb;
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return "";
}
public String  _loadtemplate(String _template) throws Exception{
byte[] _fontbytes = null;
anywheresoftware.b4a.objects.StringUtils _su = null;
String _base64string = "";
String _rawsvgcode = "";
String _finalizedsvgcontent = "";
 //BA.debugLineNum = 55;BA.debugLine="Public Sub LoadTemplate(template As String) As Str";
 //BA.debugLineNum = 57;BA.debugLine="Dim fontBytes() As Byte = Bit.InputStreamToBytes(";
_fontbytes = __c.Bit.InputStreamToBytes((java.io.InputStream)(__c.File.OpenInput(__c.File.getDirAssets(),_font_file).getObject()));
 //BA.debugLineNum = 60;BA.debugLine="Dim su As StringUtils";
_su = new anywheresoftware.b4a.objects.StringUtils();
 //BA.debugLineNum = 61;BA.debugLine="Dim base64String As String = su.EncodeBase64(font";
_base64string = _su.EncodeBase64(_fontbytes);
 //BA.debugLineNum = 64;BA.debugLine="Dim rawSvgCode As String = template";
_rawsvgcode = _template;
 //BA.debugLineNum = 67;BA.debugLine="Dim finalizedSvgContent As String = rawSvgCode.Re";
_finalizedsvgcontent = _rawsvgcode.replace(_font_base64_placeholder,_base64string);
 //BA.debugLineNum = 69;BA.debugLine="Return finalizedSvgContent";
if (true) return _finalizedsvgcontent;
 //BA.debugLineNum = 70;BA.debugLine="End Sub";
return "";
}
public b4j.example.hmitilesio._hmitouchresult  _processtouchhandler(b4j.example.hmitilesio._hmitouchdata _data) throws Exception{
b4j.example.hmitilesio._hmitouchresult _result = null;
 //BA.debugLineNum = 175;BA.debugLine="Public Sub ProcessTouchHandler(Data As HMITouchDat";
 //BA.debugLineNum = 177;BA.debugLine="mState = Data.State";
_mstate = _data.State /*boolean*/ ;
 //BA.debugLineNum = 178;BA.debugLine="mValue = Data.Value";
_mvalue = _data.Value /*String*/ ;
 //BA.debugLineNum = 181;BA.debugLine="If Data.Action = HMITilesIOUtils.ACTION_DOWN Then";
if (_data.Action /*int*/ ==_hmitilesioutils._action_down /*int*/ ) { 
 //BA.debugLineNum = 183;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_Click";
if (_xui.SubExists(ba,_mcallback,_meventname+"_Click",(int) (1))) { 
 //BA.debugLineNum = 184;BA.debugLine="CallSubDelayed3(mCallBack, mEventName & \"_Click";
__c.CallSubDelayed3(ba,_mcallback,_meventname+"_Click",(Object)(_mstate),(Object)(_mvalue));
 };
 };
 //BA.debugLineNum = 189;BA.debugLine="Dim result As HMITouchResult";
_result = new b4j.example.hmitilesio._hmitouchresult();
 //BA.debugLineNum = 190;BA.debugLine="result.Initialize";
_result.Initialize();
 //BA.debugLineNum = 191;BA.debugLine="result.State = mState";
_result.State /*Object*/  = (Object)(_mstate);
 //BA.debugLineNum = 192;BA.debugLine="result.Value = mValue";
_result.Value /*String*/  = _mvalue;
 //BA.debugLineNum = 193;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 194;BA.debugLine="End Sub";
return null;
}
public String  _settile(String _header,String _footer,float _minvalue,float _maxvalue,String _value) throws Exception{
String _textcolor = "";
double _numvalue = 0;
byte _numberofdigits = (byte)0;
int _dotindex = 0;
String _basedigits = "";
String _decimalpart = "";
int _targetlength = 0;
int _currentdigitslength = 0;
String _js = "";
 //BA.debugLineNum = 79;BA.debugLine="Public Sub SetTile(Header As String, _ 				   Foot";
 //BA.debugLineNum = 85;BA.debugLine="Dim textcolor As String = TEXT_COLOR";
_textcolor = _text_color;
 //BA.debugLineNum = 87;BA.debugLine="Header = Header.Replace(\"'\", \"\\'\")";
_header = _header.replace("'","\\'");
 //BA.debugLineNum = 88;BA.debugLine="Footer = Footer.Replace(\"'\", \"\\'\")";
_footer = _footer.replace("'","\\'");
 //BA.debugLineNum = 92;BA.debugLine="If IsNumber(Value) Then";
if (__c.IsNumber(_value)) { 
 //BA.debugLineNum = 94;BA.debugLine="Dim numValue As Double = Value";
_numvalue = (double)(Double.parseDouble(_value));
 //BA.debugLineNum = 97;BA.debugLine="If MinValue < MIN_VALUE Then MinValue = MIN_VALU";
if (_minvalue<_min_value) { 
_minvalue = (float) (_min_value);};
 //BA.debugLineNum = 98;BA.debugLine="If MaxValue > MAX_VALUE Then MaxValue = MAX_VALU";
if (_maxvalue>_max_value) { 
_maxvalue = (float) (_max_value);};
 //BA.debugLineNum = 101;BA.debugLine="If numValue < MinValue Or numValue > MaxValue Th";
if (_numvalue<_minvalue || _numvalue>_maxvalue) { 
 //BA.debugLineNum = 102;BA.debugLine="Value = ERR_TEXT";
_value = _err_text;
 //BA.debugLineNum = 103;BA.debugLine="textcolor = ERR_COLOR";
_textcolor = _err_color;
 }else {
 //BA.debugLineNum = 105;BA.debugLine="Dim NumberOfDigits As Byte = 4";
_numberofdigits = (byte) (4);
 //BA.debugLineNum = 107;BA.debugLine="If MaxValue < 10 Then";
if (_maxvalue<10) { 
 //BA.debugLineNum = 108;BA.debugLine="NumberOfDigits = 1";
_numberofdigits = (byte) (1);
 }else if(_maxvalue<100) { 
 //BA.debugLineNum = 110;BA.debugLine="NumberOfDigits = 2";
_numberofdigits = (byte) (2);
 }else if(_maxvalue<1000) { 
 //BA.debugLineNum = 112;BA.debugLine="NumberOfDigits = 3";
_numberofdigits = (byte) (3);
 }else {
 //BA.debugLineNum = 114;BA.debugLine="NumberOfDigits = 4 ' Standard default cap for";
_numberofdigits = (byte) (4);
 };
 //BA.debugLineNum = 118;BA.debugLine="Dim dotIndex As Int = Value.IndexOf(\".\")";
_dotindex = _value.indexOf(".");
 //BA.debugLineNum = 119;BA.debugLine="Dim baseDigits As String = Value";
_basedigits = _value;
 //BA.debugLineNum = 120;BA.debugLine="Dim decimalPart As String = \"\"";
_decimalpart = "";
 //BA.debugLineNum = 122;BA.debugLine="If dotIndex > -1 Then";
if (_dotindex>-1) { 
 //BA.debugLineNum = 123;BA.debugLine="baseDigits = Value.SubString2(0, dotIndex)";
_basedigits = _value.substring((int) (0),_dotindex);
 //BA.debugLineNum = 124;BA.debugLine="decimalPart = Value.SubString(dotIndex)";
_decimalpart = _value.substring(_dotindex);
 };
 //BA.debugLineNum = 129;BA.debugLine="Dim targetLength As Int = NumberOfDigits";
_targetlength = (int) (_numberofdigits);
 //BA.debugLineNum = 130;BA.debugLine="Dim currentDigitsLength As Int = baseDigits.Len";
_currentdigitslength = (int) (_basedigits.length()+_decimalpart.replace(".","").length());
 //BA.debugLineNum = 133;BA.debugLine="If currentDigitsLength < targetLength Then";
if (_currentdigitslength<_targetlength) { 
 //BA.debugLineNum = 134;BA.debugLine="Do While (baseDigits.Length + decimalPart.Repl";
while ((_basedigits.length()+_decimalpart.replace(".","").length())<_targetlength) {
 //BA.debugLineNum = 135;BA.debugLine="baseDigits = \"0\" & baseDigits";
_basedigits = "0"+_basedigits;
 }
;
 };
 //BA.debugLineNum = 139;BA.debugLine="Value = baseDigits & decimalPart";
_value = _basedigits+_decimalpart;
 };
 };
 //BA.debugLineNum = 144;BA.debugLine="Dim js As String = $\"         var head = document";
_js = ("\n"+"        var head = document.getElementById(\"tile-header\");\n"+"        var foot = document.getElementById(\"tile-footer\");\n"+"        var txt = document.getElementById(\"seg-text\");\n"+"        \n"+"        if(head) { head.textContent = \""+__c.SmartStringFormatter("",(Object)(_header))+"\"; };\n"+"        if(foot) { foot.textContent = \""+__c.SmartStringFormatter("",(Object)(_footer))+"\"; };\n"+"        if(txt)  { \n"+"			txt.textContent = \""+__c.SmartStringFormatter("",(Object)(_value))+"\"; \n"+"			txt.setAttribute(\"fill\", \""+__c.SmartStringFormatter("",(Object)(_textcolor))+"\");\n"+"			txt.style.filter = \"drop-shadow(0px 0px 2px "+__c.SmartStringFormatter("",(Object)(_textcolor))+")\";\n"+"		};\n"+"    ");
 //BA.debugLineNum = 157;BA.debugLine="UpdateTile(js)";
_updatetile(_js);
 //BA.debugLineNum = 158;BA.debugLine="End Sub";
return "";
}
public void  _updatetile(String _js) throws Exception{
ResumableSub_UpdateTile rsub = new ResumableSub_UpdateTile(this,_js);
rsub.resume(ba, null);
}
public static class ResumableSub_UpdateTile extends BA.ResumableSub {
public ResumableSub_UpdateTile(b4j.example.hmitilesiosevensegment parent,String _js) {
this.parent = parent;
this._js = _js;
}
b4j.example.hmitilesiosevensegment parent;
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
 //BA.debugLineNum = 165;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 166;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 167;BA.debugLine="Log($\"[SevenSegment.UpdateTile][E] Can not updat";
parent.__c.LogImpl("916252931",("[SevenSegment.UpdateTile][E] Can not update the tile."),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 169;BA.debugLine="End Sub";
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
