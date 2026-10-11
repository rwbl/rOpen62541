package b4j.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;

public class hmitilesiomultistate extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    public static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new  anywheresoftware.b4j.objects.FxBA("b4j.example", "b4j.example.hmitilesiomultistate", this);
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            ba.htSubs = htSubs;
             
        }
        if (BA.isShellModeRuntimeCheck(ba))
                this.getClass().getMethod("_class_globals", b4j.example.hmitilesiomultistate.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public byte _min_state_index = (byte)0;
public byte _max_state_index = (byte)0;
public byte _total_state_count = (byte)0;
public byte[] _mstates = null;
public boolean _mstate = false;
public int _mvalue = 0;
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
 //BA.debugLineNum = 23;BA.debugLine="Private Const MIN_STATE_INDEX		As Byte = 0";
_min_state_index = (byte) (0);
 //BA.debugLineNum = 24;BA.debugLine="Private Const MAX_STATE_INDEX		As Byte = 7";
_max_state_index = (byte) (7);
 //BA.debugLineNum = 25;BA.debugLine="Private Const TOTAL_STATE_COUNT		As Byte = 8	'ign";
_total_state_count = (byte) (8);
 //BA.debugLineNum = 28;BA.debugLine="Private mStates() As Byte";
_mstates = new byte[(int) (0)];
;
 //BA.debugLineNum = 31;BA.debugLine="Private mState						As Boolean		' Holds state";
_mstate = false;
 //BA.debugLineNum = 32;BA.debugLine="Private mValue As Int";
_mvalue = 0;
 //BA.debugLineNum = 33;BA.debugLine="Private mParentPanel 				As B4XView		' Local pane";
_mparentpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private mWebView					As WebView		' Local WebView";
_mwebview = new anywheresoftware.b4j.objects.WebViewWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private mEventName 					As String 'ignore";
_meventname = "";
 //BA.debugLineNum = 36;BA.debugLine="Private mCallBack 					As Object 'ignore";
_mcallback = new Object();
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
public boolean  _getstate(byte _statenr) throws Exception{
 //BA.debugLineNum = 86;BA.debugLine="Public Sub GetState(statenr As Byte) As Boolean";
 //BA.debugLineNum = 88;BA.debugLine="If statenr < MIN_STATE_INDEX Or statenr > MAX_STA";
if (_statenr<_min_state_index || _statenr>_max_state_index) { 
 //BA.debugLineNum = 89;BA.debugLine="Log(\"[GetState][E] Invalid state number. Must be";
__c.LogImpl("920185091","[GetState][E] Invalid state number. Must be 0-7.",0);
 //BA.debugLineNum = 90;BA.debugLine="Return False";
if (true) return __c.False;
 };
 //BA.debugLineNum = 94;BA.debugLine="Return (mStates(statenr) = 1)";
if (true) return (_mstates[(int) (_statenr)]==1);
 //BA.debugLineNum = 95;BA.debugLine="End Sub";
return false;
}
public byte[]  _getstates() throws Exception{
 //BA.debugLineNum = 60;BA.debugLine="Public Sub getStates As Byte()";
 //BA.debugLineNum = 61;BA.debugLine="Return mStates";
if (true) return _mstates;
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _getstatetext(byte _statenr) throws Exception{
ResumableSub_GetStateText rsub = new ResumableSub_GetStateText(this,_statenr);
rsub.resume(ba, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_GetStateText extends BA.ResumableSub {
public ResumableSub_GetStateText(b4j.example.hmitilesiomultistate parent,byte _statenr) {
this.parent = parent;
this._statenr = _statenr;
}
b4j.example.hmitilesiomultistate parent;
byte _statenr;
String _js = "";
String _result;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
{
parent.__c.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = 1;
 //BA.debugLineNum = 129;BA.debugLine="If statenr < MIN_STATE_INDEX Or statenr > MAX_STA";
if (true) break;

case 1:
//if
this.state = 4;
if (_statenr<parent._min_state_index || _statenr>parent._max_state_index) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 130;BA.debugLine="Log(\"[GetStateText][E] Invalid state layout inde";
parent.__c.LogImpl("920316163","[GetStateText][E] Invalid state layout index. Must be 0-7.",0);
 //BA.debugLineNum = 131;BA.debugLine="Return \"\"";
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(""));return;};
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 135;BA.debugLine="Dim js As String = $\" 		(function() { 			var txtE";
_js = ("\n"+"		(function() {\n"+"			var txtEl = document.getElementById(\"state-txt"+parent.__c.SmartStringFormatter("",(Object)(_statenr))+"\");\n"+"			return txtEl ? txtEl.textContent : \"\";\n"+"		})();\n"+"	");
 //BA.debugLineNum = 143;BA.debugLine="Wait For (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = -1;
_result = (String) result[0];
;
 //BA.debugLineNum = 145;BA.debugLine="Return result";
if (true) {
parent.__c.ReturnFromResumableSub(this,(Object)(_result));return;};
 //BA.debugLineNum = 146;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _complete(String _result) throws Exception{
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
 //BA.debugLineNum = 46;BA.debugLine="mStates = Array As Byte(1,1,1,1,1,1,1,1)";
_mstates = new byte[]{(byte) (1),(byte) (1),(byte) (1),(byte) (1),(byte) (1),(byte) (1),(byte) (1),(byte) (1)};
 //BA.debugLineNum = 47;BA.debugLine="End Sub";
return "";
}
public b4j.example.hmitilesio._hmitouchresult  _processtouchhandler(b4j.example.hmitilesio._hmitouchdata _data) throws Exception{
float _panelwidth = 0f;
float _panelheight = 0f;
float _svgtouchx = 0f;
float _svgtouchy = 0f;
int _chosenstate = 0;
b4j.example.hmitilesio._hmitouchresult _result = null;
 //BA.debugLineNum = 279;BA.debugLine="Public Sub ProcessTouchHandler(Data As HMITouchDat";
 //BA.debugLineNum = 281;BA.debugLine="mState = Data.State";
_mstate = _data.State /*boolean*/ ;
 //BA.debugLineNum = 282;BA.debugLine="mValue = Data.Value";
_mvalue = (int)(Double.parseDouble(_data.Value /*String*/ ));
 //BA.debugLineNum = 285;BA.debugLine="If Data.Action = HMITilesIOUtils.ACTION_DOWN Then";
if (_data.Action /*int*/ ==_hmitilesioutils._action_down /*int*/ ) { 
 //BA.debugLineNum = 286;BA.debugLine="Dim PanelWidth As Float = mParentPanel.Width";
_panelwidth = (float) (_mparentpanel.getWidth());
 //BA.debugLineNum = 287;BA.debugLine="Dim PanelHeight As Float = mParentPanel.Height";
_panelheight = (float) (_mparentpanel.getHeight());
 //BA.debugLineNum = 290;BA.debugLine="Dim svgTouchX As Float = (Data.X / PanelWidth) *";
_svgtouchx = (float) ((_data.X /*float*/ /(double)_panelwidth)*120);
 //BA.debugLineNum = 291;BA.debugLine="Dim svgTouchY As Float = (Data.Y / PanelHeight)";
_svgtouchy = (float) ((_data.Y /*float*/ /(double)_panelheight)*120);
 //BA.debugLineNum = 294;BA.debugLine="Dim ChosenState As Int = -1";
_chosenstate = (int) (-1);
 //BA.debugLineNum = 297;BA.debugLine="If svgTouchY >= 32 And svgTouchY <= 57 Then";
if (_svgtouchy>=32 && _svgtouchy<=57) { 
 //BA.debugLineNum = 298;BA.debugLine="If svgTouchX >= 12 And svgTouchX <= 32 Then Cho";
if (_svgtouchx>=12 && _svgtouchx<=32) { 
_chosenstate = (int) (0);};
 //BA.debugLineNum = 299;BA.debugLine="If svgTouchX >= 38 And svgTouchX <= 58 Then Cho";
if (_svgtouchx>=38 && _svgtouchx<=58) { 
_chosenstate = (int) (1);};
 //BA.debugLineNum = 300;BA.debugLine="If svgTouchX >= 64 And svgTouchX <= 84 Then Cho";
if (_svgtouchx>=64 && _svgtouchx<=84) { 
_chosenstate = (int) (2);};
 //BA.debugLineNum = 301;BA.debugLine="If svgTouchX >= 90 And svgTouchX <= 110 Then Ch";
if (_svgtouchx>=90 && _svgtouchx<=110) { 
_chosenstate = (int) (3);};
 }else if(_svgtouchy>=64 && _svgtouchy<=89) { 
 //BA.debugLineNum = 305;BA.debugLine="If svgTouchX >= 12 And svgTouchX <= 32 Then Cho";
if (_svgtouchx>=12 && _svgtouchx<=32) { 
_chosenstate = (int) (4);};
 //BA.debugLineNum = 306;BA.debugLine="If svgTouchX >= 38 And svgTouchX <= 58 Then Cho";
if (_svgtouchx>=38 && _svgtouchx<=58) { 
_chosenstate = (int) (5);};
 //BA.debugLineNum = 307;BA.debugLine="If svgTouchX >= 64 And svgTouchX <= 84 Then Cho";
if (_svgtouchx>=64 && _svgtouchx<=84) { 
_chosenstate = (int) (6);};
 //BA.debugLineNum = 308;BA.debugLine="If svgTouchX >= 90 And svgTouchX <= 110 Then Ch";
if (_svgtouchx>=90 && _svgtouchx<=110) { 
_chosenstate = (int) (7);};
 };
 //BA.debugLineNum = 312;BA.debugLine="If ChosenState >= 0 Then";
if (_chosenstate>=0) { 
 //BA.debugLineNum = 316;BA.debugLine="If GetState(ChosenState)Then";
if (_getstate((byte) (_chosenstate))) { 
 //BA.debugLineNum = 319;BA.debugLine="mValue = ChosenState";
_mvalue = _chosenstate;
 //BA.debugLineNum = 322;BA.debugLine="UpdateStates(mValue, getStates)";
_updatestates(_mvalue,_getstates());
 //BA.debugLineNum = 325;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_Cli";
if (_xui.SubExists(ba,_mcallback,_meventname+"_Click",(int) (1))) { 
 //BA.debugLineNum = 326;BA.debugLine="CallSubDelayed3(mCallBack, mEventName & \"_Cli";
__c.CallSubDelayed3(ba,_mcallback,_meventname+"_Click",(Object)(_mstate),(Object)((BA.NumberToString(_chosenstate))));
 };
 };
 };
 };
 //BA.debugLineNum = 333;BA.debugLine="Dim result As HMITouchResult";
_result = new b4j.example.hmitilesio._hmitouchresult();
 //BA.debugLineNum = 334;BA.debugLine="result.Initialize";
_result.Initialize();
 //BA.debugLineNum = 335;BA.debugLine="result.State = mState";
_result.State /*Object*/  = (Object)(_mstate);
 //BA.debugLineNum = 336;BA.debugLine="result.Value = mValue";
_result.Value /*String*/  = BA.NumberToString(_mvalue);
 //BA.debugLineNum = 338;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 339;BA.debugLine="End Sub";
return null;
}
public String  _setstate(byte _statenr,boolean _state) throws Exception{
 //BA.debugLineNum = 66;BA.debugLine="Public Sub SetState(statenr As Byte, state As Bool";
 //BA.debugLineNum = 68;BA.debugLine="If statenr < MIN_STATE_INDEX Or statenr > MAX_STA";
if (_statenr<_min_state_index || _statenr>_max_state_index) { 
 //BA.debugLineNum = 69;BA.debugLine="Log(\"[SetState][E] Invalid state number. Must be";
__c.LogImpl("920119555","[SetState][E] Invalid state number. Must be 0-7.",0);
 //BA.debugLineNum = 70;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 74;BA.debugLine="If state = True Then";
if (_state==__c.True) { 
 //BA.debugLineNum = 75;BA.debugLine="mStates(statenr) = 1";
_mstates[(int) (_statenr)] = (byte) (1);
 }else {
 //BA.debugLineNum = 77;BA.debugLine="mStates(statenr) = 0";
_mstates[(int) (_statenr)] = (byte) (0);
 };
 //BA.debugLineNum = 79;BA.debugLine="End Sub";
return "";
}
public String  _setstates(byte[] _values) throws Exception{
int _i = 0;
 //BA.debugLineNum = 54;BA.debugLine="Public Sub setStates(values() As Byte)";
 //BA.debugLineNum = 55;BA.debugLine="mStates = values";
_mstates = _values;
 //BA.debugLineNum = 56;BA.debugLine="For i = MIN_STATE_INDEX To MAX_STATE_INDEX";
{
final int step2 = 1;
final int limit2 = (int) (_max_state_index);
_i = (int) (_min_state_index) ;
for (;_i <= limit2 ;_i = _i + step2 ) {
 //BA.debugLineNum = 57;BA.debugLine="SetStateVisibility(i, IIf(mStates(i) = 1, True,";
_setstatevisibility((byte) (_i),BA.ObjectToBoolean(((_mstates[_i]==1) ? ((Object)(__c.True)) : ((Object)(__c.False)))));
 }
};
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return "";
}
public void  _setstatetext(byte _statenr,String _text) throws Exception{
ResumableSub_SetStateText rsub = new ResumableSub_SetStateText(this,_statenr,_text);
rsub.resume(ba, null);
}
public static class ResumableSub_SetStateText extends BA.ResumableSub {
public ResumableSub_SetStateText(b4j.example.hmitilesiomultistate parent,byte _statenr,String _text) {
this.parent = parent;
this._statenr = _statenr;
this._text = _text;
}
b4j.example.hmitilesiomultistate parent;
byte _statenr;
String _text;
String _cleansedtext = "";
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
 //BA.debugLineNum = 103;BA.debugLine="If statenr < MIN_STATE_INDEX Or statenr > MAX_STA";
if (true) break;

case 1:
//if
this.state = 4;
if (_statenr<parent._min_state_index || _statenr>parent._max_state_index) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 104;BA.debugLine="Log(\"[SetStateText][E] Invalid state layout inde";
parent.__c.LogImpl("920250627","[SetStateText][E] Invalid state layout index. Must be 0-7.",0);
 //BA.debugLineNum = 105;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 4:
//C
this.state = 5;
;
 //BA.debugLineNum = 109;BA.debugLine="Dim CleansedText As String = text";
_cleansedtext = _text;
 //BA.debugLineNum = 110;BA.debugLine="If CleansedText.Length > 3 Then";
if (true) break;

case 5:
//if
this.state = 8;
if (_cleansedtext.length()>3) { 
this.state = 7;
}if (true) break;

case 7:
//C
this.state = 8;
 //BA.debugLineNum = 111;BA.debugLine="CleansedText = CleansedText.SubString2(0, 3)";
_cleansedtext = _cleansedtext.substring((int) (0),(int) (3));
 if (true) break;

case 8:
//C
this.state = 9;
;
 //BA.debugLineNum = 115;BA.debugLine="Dim js As String = $\" 		var txtEl = document.getE";
_js = ("\n"+"		var txtEl = document.getElementById(\"state-txt"+parent.__c.SmartStringFormatter("",(Object)(_statenr))+"\");\n"+"		if (txtEl) { txtEl.textContent = \""+parent.__c.SmartStringFormatter("",(Object)(_cleansedtext))+"\"; };\n"+"	");
 //BA.debugLineNum = 121;BA.debugLine="Wait For (HMITilesIOUtils.ExecuteJS(mWebView, js.";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js.replace(BA.ObjectToString(parent.__c.Chr((int) (10)))," ").replace(BA.ObjectToString(parent.__c.Chr((int) (13)))," ")));
this.state = 13;
return;
case 13:
//C
this.state = 9;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 122;BA.debugLine="If Not(result) Then";
if (true) break;

case 9:
//if
this.state = 12;
if (parent.__c.Not(_result)) { 
this.state = 11;
}if (true) break;

case 11:
//C
this.state = 12;
 //BA.debugLineNum = 123;BA.debugLine="Log($\"[SetStateText][E] Can not set the state te";
parent.__c.LogImpl("920250646",("[SetStateText][E] Can not set the state text for tile IOPanel"),0);
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 125;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _setstatevisibility(byte _statenr,boolean _visible) throws Exception{
ResumableSub_SetStateVisibility rsub = new ResumableSub_SetStateVisibility(this,_statenr,_visible);
rsub.resume(ba, null);
}
public static class ResumableSub_SetStateVisibility extends BA.ResumableSub {
public ResumableSub_SetStateVisibility(b4j.example.hmitilesiomultistate parent,byte _statenr,boolean _visible) {
this.parent = parent;
this._statenr = _statenr;
this._visible = _visible;
}
b4j.example.hmitilesiomultistate parent;
byte _statenr;
boolean _visible;
String _displaystyle = "";
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
 //BA.debugLineNum = 156;BA.debugLine="If statenr < MIN_STATE_INDEX Or statenr > MAX_STA";
if (true) break;

case 1:
//if
this.state = 4;
if (_statenr<parent._min_state_index || _statenr>parent._max_state_index) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 157;BA.debugLine="Log(\"[SetStateVisibility][E] Invalid state layou";
parent.__c.LogImpl("920381699","[SetStateVisibility][E] Invalid state layout index. Must be 0-7.",0);
 //BA.debugLineNum = 158;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 4:
//C
this.state = 5;
;
 //BA.debugLineNum = 162;BA.debugLine="Dim displayStyle As String = \"block\"";
_displaystyle = "block";
 //BA.debugLineNum = 163;BA.debugLine="If Not(visible) Then displayStyle = \"none\"";
if (true) break;

case 5:
//if
this.state = 10;
if (parent.__c.Not(_visible)) { 
this.state = 7;
;}if (true) break;

case 7:
//C
this.state = 10;
_displaystyle = "none";
if (true) break;

case 10:
//C
this.state = 11;
;
 //BA.debugLineNum = 166;BA.debugLine="Dim js As String = $\" 		var rectEl = document.get";
_js = ("\n"+"		var rectEl = document.getElementById(\"state"+parent.__c.SmartStringFormatter("",(Object)(_statenr))+"\");\n"+"		if (rectEl) { \n"+"			rectEl.parentElement.style.display = \""+parent.__c.SmartStringFormatter("",(Object)(_displaystyle))+"\"; \n"+"		};\n"+"	");
 //BA.debugLineNum = 174;BA.debugLine="Wait For (HMITilesIOUtils.ExecuteJS(mWebView, js.";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js.replace(BA.ObjectToString(parent.__c.Chr((int) (10)))," ").replace(BA.ObjectToString(parent.__c.Chr((int) (13)))," ")));
this.state = 15;
return;
case 15:
//C
this.state = 11;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 175;BA.debugLine="If Not(result) Then";
if (true) break;

case 11:
//if
this.state = 14;
if (parent.__c.Not(_result)) { 
this.state = 13;
}if (true) break;

case 13:
//C
this.state = 14;
 //BA.debugLineNum = 176;BA.debugLine="Log($\"[SetStateVisibility][E] Can not alter visi";
parent.__c.LogImpl("920381718",("[SetStateVisibility][E] Can not alter visibility for tile IOPanel"),0);
 if (true) break;

case 14:
//C
this.state = -1;
;
 //BA.debugLineNum = 178;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public String  _settile(String _header,String _footer,byte[] _states,String _value) throws Exception{
String _js = "";
 //BA.debugLineNum = 187;BA.debugLine="Public Sub SetTile(Header As String, _ 				   Foot";
 //BA.debugLineNum = 192;BA.debugLine="setStates(States)";
_setstates(_states);
 //BA.debugLineNum = 194;BA.debugLine="mValue = Value.As(Int) ' Sync internal state trac";
_mvalue = ((int)(Double.parseDouble(_value)));
 //BA.debugLineNum = 196;BA.debugLine="Dim js As String = $\" 		var head = document.getEl";
_js = ("\n"+"		var head = document.getElementById(\"tile-header\");\n"+"		var foot = document.getElementById(\"tile-footer\");\n"+"		if(head) {\n"+"			head.textContent = \""+__c.SmartStringFormatter("",(Object)(_header))+"\";\n"+"		};\n"+"	");
 //BA.debugLineNum = 203;BA.debugLine="js = $\"${js}${UpdateLayout(mValue, getStates)}\"$";
_js = (""+__c.SmartStringFormatter("",(Object)(_js))+""+__c.SmartStringFormatter("",(Object)(_updatelayout(_mvalue,_getstates())))+"");
 //BA.debugLineNum = 204;BA.debugLine="UpdateTile(js)";
_updatetile(_js);
 //BA.debugLineNum = 205;BA.debugLine="End Sub";
return "";
}
public String  _updatelayout(int _activestate,byte[] _states) throws Exception{
String _displaystyle = "";
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
int _i = 0;
boolean _isconfiguredstate = false;
String _urlfill = "";
 //BA.debugLineNum = 218;BA.debugLine="Private Sub UpdateLayout(ActiveState As Int, state";
 //BA.debugLineNum = 219;BA.debugLine="Dim displaystyle As String";
_displaystyle = "";
 //BA.debugLineNum = 220;BA.debugLine="Dim sb As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 222;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 225;BA.debugLine="If states.Length <> 8 Then";
if (_states.length!=8) { 
 //BA.debugLineNum = 226;BA.debugLine="Log(\"[MultiState.UpdateLayout][E] States array m";
__c.LogImpl("920578312","[MultiState.UpdateLayout][E] States array must contain exactly 8 elements.",0);
 //BA.debugLineNum = 227;BA.debugLine="Return \"\"";
if (true) return "";
 };
 //BA.debugLineNum = 231;BA.debugLine="For i = MIN_STATE_INDEX To MAX_STATE_INDEX";
{
final int step8 = 1;
final int limit8 = (int) (_max_state_index);
_i = (int) (_min_state_index) ;
for (;_i <= limit8 ;_i = _i + step8 ) {
 //BA.debugLineNum = 232;BA.debugLine="Dim IsConfiguredState As Boolean = (states(i) =";
_isconfiguredstate = (_states[_i]==1);
 //BA.debugLineNum = 234;BA.debugLine="Dim UrlFill As String";
_urlfill = "";
 //BA.debugLineNum = 235;BA.debugLine="If Not(IsConfiguredState) Then";
if (__c.Not(_isconfiguredstate)) { 
 //BA.debugLineNum = 237;BA.debugLine="UrlFill = \"url(#stateDisabled)\"";
_urlfill = "url(#stateDisabled)";
 //BA.debugLineNum = 238;BA.debugLine="displaystyle = HMITilesIOUtils.DISPLAY_STYLE_NO";
_displaystyle = _hmitilesioutils._display_style_none /*String*/ ;
 }else {
 //BA.debugLineNum = 242;BA.debugLine="If i = ActiveState Then";
if (_i==_activestate) { 
 //BA.debugLineNum = 243;BA.debugLine="UrlFill = \"url(#stateActive)\"";
_urlfill = "url(#stateActive)";
 }else {
 //BA.debugLineNum = 245;BA.debugLine="UrlFill = \"url(#stateInactive)\"";
_urlfill = "url(#stateInactive)";
 };
 //BA.debugLineNum = 247;BA.debugLine="displaystyle = HMITilesIOUtils.DISPLAY_STYLE_BL";
_displaystyle = _hmitilesioutils._display_style_block /*String*/ ;
 };
 //BA.debugLineNum = 251;BA.debugLine="sb.Append($\" 			var el${i} = document.getElement";
_sb.Append(("\n"+"			var el"+__c.SmartStringFormatter("",(Object)(_i))+" = document.getElementById(\"state"+__c.SmartStringFormatter("",(Object)(_i))+"\"); \n"+"			if(el"+__c.SmartStringFormatter("",(Object)(_i))+"){\n"+"				el"+__c.SmartStringFormatter("",(Object)(_i))+".setAttribute(\"fill\", \""+__c.SmartStringFormatter("",(Object)(_urlfill))+"\");};\n"+"				el"+__c.SmartStringFormatter("",(Object)(_i))+".parentElement.style.display = \""+__c.SmartStringFormatter("",(Object)(_displaystyle))+"\"; \n"+"			"));
 }
};
 //BA.debugLineNum = 258;BA.debugLine="Return sb.tostring";
if (true) return _sb.ToString();
 //BA.debugLineNum = 259;BA.debugLine="End Sub";
return "";
}
public void  _updatestates(int _activestate,byte[] _states) throws Exception{
ResumableSub_UpdateStates rsub = new ResumableSub_UpdateStates(this,_activestate,_states);
rsub.resume(ba, null);
}
public static class ResumableSub_UpdateStates extends BA.ResumableSub {
public ResumableSub_UpdateStates(b4j.example.hmitilesiomultistate parent,int _activestate,byte[] _states) {
this.parent = parent;
this._activestate = _activestate;
this._states = _states;
}
b4j.example.hmitilesiomultistate parent;
int _activestate;
byte[] _states;
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
 //BA.debugLineNum = 268;BA.debugLine="Dim js As String = UpdateLayout(ActiveState, Stat";
_js = parent._updatelayout(_activestate,_states);
 //BA.debugLineNum = 269;BA.debugLine="Wait For (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 270;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 271;BA.debugLine="Log($\"[MultiState.UpdateStates][E] Can not updat";
parent.__c.LogImpl("920643844",("[MultiState.UpdateStates][E] Can not update tile IOMultiState"),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 273;BA.debugLine="End Sub";
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
public ResumableSub_UpdateTile(b4j.example.hmitilesiomultistate parent,String _js) {
this.parent = parent;
this._js = _js;
}
b4j.example.hmitilesiomultistate parent;
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
 //BA.debugLineNum = 212;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 213;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 214;BA.debugLine="Log($\"[MultiState.UpdateTile][E] Can not update";
parent.__c.LogImpl("920512771",("[MultiState.UpdateTile][E] Can not update the tile."),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 216;BA.debugLine="End Sub";
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
