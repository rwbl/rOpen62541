package b4j.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;

public class hmitilesiopanel extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    public static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new  anywheresoftware.b4j.objects.FxBA("b4j.example", "b4j.example.hmitilesiopanel", this);
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            ba.htSubs = htSubs;
             
        }
        if (BA.isShellModeRuntimeCheck(ba))
                this.getClass().getMethod("_class_globals", b4j.example.hmitilesiopanel.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public byte _min_channel_index = (byte)0;
public byte _max_channel_index = (byte)0;
public byte _total_channel_count = (byte)0;
public int _state_off = 0;
public int _state_on = 0;
public int _state_disabled = 0;
public int _state_error = 0;
public int[] _mchannels = null;
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
 //BA.debugLineNum = 14;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 15;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 18;BA.debugLine="Private Const MIN_CHANNEL_INDEX			As Byte = 0";
_min_channel_index = (byte) (0);
 //BA.debugLineNum = 19;BA.debugLine="Private Const MAX_CHANNEL_INDEX			As Byte = 7";
_max_channel_index = (byte) (7);
 //BA.debugLineNum = 20;BA.debugLine="Private Const TOTAL_CHANNEL_COUNT		As Byte = 8";
_total_channel_count = (byte) (8);
 //BA.debugLineNum = 23;BA.debugLine="Public Const STATE_OFF					As Int = 0      ' Chan";
_state_off = (int) (0);
 //BA.debugLineNum = 24;BA.debugLine="Public Const STATE_ON					As Int = 1      ' Chann";
_state_on = (int) (1);
 //BA.debugLineNum = 25;BA.debugLine="Public Const STATE_DISABLED				As Int = 2 		' Cha";
_state_disabled = (int) (2);
 //BA.debugLineNum = 26;BA.debugLine="Public Const STATE_ERROR				As Int = 3		' Error f";
_state_error = (int) (3);
 //BA.debugLineNum = 29;BA.debugLine="Private mChannels(TOTAL_CHANNEL_COUNT)	As Int			'";
_mchannels = new int[(int) (_total_channel_count)];
;
 //BA.debugLineNum = 31;BA.debugLine="Private mState							As Boolean		' Holds state";
_mstate = false;
 //BA.debugLineNum = 32;BA.debugLine="Private mValue							As String       ' Holds the";
_mvalue = "";
 //BA.debugLineNum = 33;BA.debugLine="Private mParentPanel 					As B4XView		' Local pan";
_mparentpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private mWebView						As WebView		' Local WebView";
_mwebview = new anywheresoftware.b4j.objects.WebViewWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private mEventName 						As String 'ignore";
_meventname = "";
 //BA.debugLineNum = 36;BA.debugLine="Private mCallBack 						As Object 'ignore";
_mcallback = new Object();
 //BA.debugLineNum = 37;BA.debugLine="End Sub";
return "";
}
public int  _getchannelstate(byte _channel) throws Exception{
 //BA.debugLineNum = 192;BA.debugLine="Public Sub GetChannelState(channel As Byte) As Int";
 //BA.debugLineNum = 193;BA.debugLine="If channel < MIN_CHANNEL_INDEX Or channel > MAX_C";
if (_channel<_min_channel_index || _channel>_max_channel_index) { 
if (true) return _state_error;};
 //BA.debugLineNum = 194;BA.debugLine="Return mChannels(channel)";
if (true) return _mchannels[(int) (_channel)];
 //BA.debugLineNum = 195;BA.debugLine="End Sub";
return 0;
}
public int[]  _getmchannels() throws Exception{
 //BA.debugLineNum = 60;BA.debugLine="Public Sub getmChannels As Int()";
 //BA.debugLineNum = 61;BA.debugLine="Return mChannels";
if (true) return _mchannels;
 //BA.debugLineNum = 62;BA.debugLine="End Sub";
return null;
}
public String  _getstate() throws Exception{
int _state = 0;
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
int _i = 0;
 //BA.debugLineNum = 201;BA.debugLine="Public Sub GetState As String";
 //BA.debugLineNum = 202;BA.debugLine="Dim state As Int";
_state = 0;
 //BA.debugLineNum = 203;BA.debugLine="Dim sb As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 204;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 206;BA.debugLine="For i = MIN_CHANNEL_INDEX To MAX_CHANNEL_INDEX";
{
final int step4 = 1;
final int limit4 = (int) (_max_channel_index);
_i = (int) (_min_channel_index) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
 //BA.debugLineNum = 207;BA.debugLine="state = mChannels(i)";
_state = _mchannels[_i];
 //BA.debugLineNum = 208;BA.debugLine="If state <> STATE_DISABLED Then";
if (_state!=_state_disabled) { 
 //BA.debugLineNum = 209;BA.debugLine="sb.Append(state)";
_sb.Append(BA.NumberToString(_state));
 };
 }
};
 //BA.debugLineNum = 212;BA.debugLine="Return sb.ToString";
if (true) return _sb.ToString();
 //BA.debugLineNum = 213;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _pnl,anywheresoftware.b4j.objects.WebViewWrapper _wv,String _evt,Object _cb) throws Exception{
innerInitialize(_ba);
int _i = 0;
 //BA.debugLineNum = 41;BA.debugLine="Public Sub Initialize(pnl As B4XView, wv As WebVie";
 //BA.debugLineNum = 42;BA.debugLine="mParentPanel = pnl";
_mparentpanel = _pnl;
 //BA.debugLineNum = 43;BA.debugLine="mWebView = wv";
_mwebview = _wv;
 //BA.debugLineNum = 44;BA.debugLine="mEventName = evt";
_meventname = _evt;
 //BA.debugLineNum = 45;BA.debugLine="mCallBack = cb";
_mcallback = _cb;
 //BA.debugLineNum = 48;BA.debugLine="For i = MIN_CHANNEL_INDEX To MAX_CHANNEL_INDEX";
{
final int step5 = 1;
final int limit5 = (int) (_max_channel_index);
_i = (int) (_min_channel_index) ;
for (;_i <= limit5 ;_i = _i + step5 ) {
 //BA.debugLineNum = 49;BA.debugLine="mChannels(i) = STATE_DISABLED";
_mchannels[_i] = _state_disabled;
 }
};
 //BA.debugLineNum = 51;BA.debugLine="mValue = \"\"";
_mvalue = "";
 //BA.debugLineNum = 52;BA.debugLine="End Sub";
return "";
}
public b4j.example.hmitilesio._hmitouchresult  _processtouchhandler(b4j.example.hmitilesio._hmitouchdata _data) throws Exception{
float _panelwidth = 0f;
float _panelheight = 0f;
float _svgtouchx = 0f;
float _svgtouchy = 0f;
int _selectedchannel = 0;
int _currentstate = 0;
int _newstate = 0;
b4j.example.hmitilesio._hmitouchresult _result = null;
 //BA.debugLineNum = 278;BA.debugLine="Public Sub ProcessTouchHandler(Data As HMITouchDat";
 //BA.debugLineNum = 280;BA.debugLine="mState = Data.State";
_mstate = _data.State /*boolean*/ ;
 //BA.debugLineNum = 281;BA.debugLine="mValue = Data.Value";
_mvalue = _data.Value /*String*/ ;
 //BA.debugLineNum = 284;BA.debugLine="If Data.Action = HMITilesIOUtils.ACTION_DOWN Then";
if (_data.Action /*int*/ ==_hmitilesioutils._action_down /*int*/ ) { 
 //BA.debugLineNum = 286;BA.debugLine="Dim PanelWidth As Float = mParentPanel.Width";
_panelwidth = (float) (_mparentpanel.getWidth());
 //BA.debugLineNum = 287;BA.debugLine="Dim PanelHeight As Float = mParentPanel.Height";
_panelheight = (float) (_mparentpanel.getHeight());
 //BA.debugLineNum = 290;BA.debugLine="Dim svgTouchX As Float = (Data.X / PanelWidth) *";
_svgtouchx = (float) ((_data.X /*float*/ /(double)_panelwidth)*120);
 //BA.debugLineNum = 291;BA.debugLine="Dim svgTouchY As Float = (Data.Y / PanelHeight)";
_svgtouchy = (float) ((_data.Y /*float*/ /(double)_panelheight)*120);
 //BA.debugLineNum = 294;BA.debugLine="Dim SelectedChannel As Int = -1";
_selectedchannel = (int) (-1);
 //BA.debugLineNum = 297;BA.debugLine="If svgTouchY >= 32 And svgTouchY <= 57 Then";
if (_svgtouchy>=32 && _svgtouchy<=57) { 
 //BA.debugLineNum = 298;BA.debugLine="If svgTouchX >= 12 And svgTouchX <= 32 Then Sel";
if (_svgtouchx>=12 && _svgtouchx<=32) { 
_selectedchannel = (int) (0);};
 //BA.debugLineNum = 299;BA.debugLine="If svgTouchX >= 38 And svgTouchX <= 58 Then Sel";
if (_svgtouchx>=38 && _svgtouchx<=58) { 
_selectedchannel = (int) (1);};
 //BA.debugLineNum = 300;BA.debugLine="If svgTouchX >= 64 And svgTouchX <= 84 Then Sel";
if (_svgtouchx>=64 && _svgtouchx<=84) { 
_selectedchannel = (int) (2);};
 //BA.debugLineNum = 301;BA.debugLine="If svgTouchX >= 90 And svgTouchX <= 110 Then Se";
if (_svgtouchx>=90 && _svgtouchx<=110) { 
_selectedchannel = (int) (3);};
 }else if(_svgtouchy>=64 && _svgtouchy<=89) { 
 //BA.debugLineNum = 303;BA.debugLine="If svgTouchX >= 12 And svgTouchX <= 32 Then Sel";
if (_svgtouchx>=12 && _svgtouchx<=32) { 
_selectedchannel = (int) (4);};
 //BA.debugLineNum = 304;BA.debugLine="If svgTouchX >= 38 And svgTouchX <= 58 Then Sel";
if (_svgtouchx>=38 && _svgtouchx<=58) { 
_selectedchannel = (int) (5);};
 //BA.debugLineNum = 305;BA.debugLine="If svgTouchX >= 64 And svgTouchX <= 84 Then Sel";
if (_svgtouchx>=64 && _svgtouchx<=84) { 
_selectedchannel = (int) (6);};
 //BA.debugLineNum = 306;BA.debugLine="If svgTouchX >= 90 And svgTouchX <= 110 Then Se";
if (_svgtouchx>=90 && _svgtouchx<=110) { 
_selectedchannel = (int) (7);};
 };
 //BA.debugLineNum = 310;BA.debugLine="If SelectedChannel >= 0 Then";
if (_selectedchannel>=0) { 
 //BA.debugLineNum = 314;BA.debugLine="If GetChannelState(SelectedChannel) <> STATE_DI";
if (_getchannelstate((byte) (_selectedchannel))!=_state_disabled) { 
 //BA.debugLineNum = 317;BA.debugLine="Dim CurrentState As Int = GetChannelState(Sele";
_currentstate = _getchannelstate((byte) (_selectedchannel));
 //BA.debugLineNum = 318;BA.debugLine="Dim NewState As Int = STATE_OFF";
_newstate = _state_off;
 //BA.debugLineNum = 321;BA.debugLine="NewState = IIf(CurrentState = STATE_OFF, STATE";
_newstate = (int)(BA.ObjectToNumber(((_currentstate==_state_off) ? ((Object)(_state_on)) : ((Object)(_state_off)))));
 //BA.debugLineNum = 325;BA.debugLine="SetChannelState(SelectedChannel, NewState)";
_setchannelstate((byte) (_selectedchannel),_newstate);
 //BA.debugLineNum = 328;BA.debugLine="mState = IIf(NewState = STATE_ON, True, False)";
_mstate = BA.ObjectToBoolean(((_newstate==_state_on) ? ((Object)(__c.True)) : ((Object)(__c.False))));
 //BA.debugLineNum = 329;BA.debugLine="mValue = GetState";
_mvalue = _getstate();
 //BA.debugLineNum = 332;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_Cli";
if (_xui.SubExists(ba,_mcallback,_meventname+"_Click",(int) (1))) { 
 //BA.debugLineNum = 333;BA.debugLine="CallSubDelayed3(mCallBack, mEventName & \"_Cli";
__c.CallSubDelayed3(ba,_mcallback,_meventname+"_Click",(Object)(_mstate),(Object)((BA.NumberToString(_selectedchannel))));
 };
 };
 };
 };
 //BA.debugLineNum = 341;BA.debugLine="Dim result As HMITouchResult";
_result = new b4j.example.hmitilesio._hmitouchresult();
 //BA.debugLineNum = 342;BA.debugLine="result.Initialize";
_result.Initialize();
 //BA.debugLineNum = 343;BA.debugLine="result.State = mState";
_result.State /*Object*/  = (Object)(_mstate);
 //BA.debugLineNum = 344;BA.debugLine="result.Value = mValue";
_result.Value /*String*/  = _mvalue;
 //BA.debugLineNum = 346;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 347;BA.debugLine="End Sub";
return null;
}
public String  _setchannels(int[] _values) throws Exception{
int _i = 0;
 //BA.debugLineNum = 54;BA.debugLine="Public Sub setChannels(values() As Int)";
 //BA.debugLineNum = 55;BA.debugLine="mChannels = values";
_mchannels = _values;
 //BA.debugLineNum = 56;BA.debugLine="For i = MIN_CHANNEL_INDEX To MAX_CHANNEL_INDEX";
{
final int step2 = 1;
final int limit2 = (int) (_max_channel_index);
_i = (int) (_min_channel_index) ;
for (;_i <= limit2 ;_i = _i + step2 ) {
 //BA.debugLineNum = 57;BA.debugLine="SetChannelVisibility(i, IIf(mChannels(i) = 1, Tr";
_setchannelvisibility((byte) (_i),BA.ObjectToBoolean(((_mchannels[_i]==1) ? ((Object)(__c.True)) : ((Object)(__c.False)))));
 }
};
 //BA.debugLineNum = 59;BA.debugLine="End Sub";
return "";
}
public void  _setchannelstate(byte _channel,int _newstate) throws Exception{
ResumableSub_SetChannelState rsub = new ResumableSub_SetChannelState(this,_channel,_newstate);
rsub.resume(ba, null);
}
public static class ResumableSub_SetChannelState extends BA.ResumableSub {
public ResumableSub_SetChannelState(b4j.example.hmitilesiopanel parent,byte _channel,int _newstate) {
this.parent = parent;
this._channel = _channel;
this._newstate = _newstate;
}
b4j.example.hmitilesiopanel parent;
byte _channel;
int _newstate;
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
 //BA.debugLineNum = 168;BA.debugLine="If channel < MIN_CHANNEL_INDEX Or channel > MAX_C";
if (true) break;

case 1:
//if
this.state = 6;
if (_channel<parent._min_channel_index || _channel>parent._max_channel_index) { 
this.state = 3;
;}if (true) break;

case 3:
//C
this.state = 6;
if (true) return ;
if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 169;BA.debugLine="If mChannels(channel) = STATE_DISABLED Then Retur";
if (true) break;

case 7:
//if
this.state = 12;
if (parent._mchannels[(int) (_channel)]==parent._state_disabled) { 
this.state = 9;
;}if (true) break;

case 9:
//C
this.state = 12;
if (true) return ;
if (true) break;

case 12:
//C
this.state = 13;
;
 //BA.debugLineNum = 171;BA.debugLine="If mChannels(channel) <> newstate Then";
if (true) break;

case 13:
//if
this.state = 20;
if (parent._mchannels[(int) (_channel)]!=_newstate) { 
this.state = 15;
}if (true) break;

case 15:
//C
this.state = 16;
 //BA.debugLineNum = 172;BA.debugLine="mChannels(channel) = newstate";
parent._mchannels[(int) (_channel)] = _newstate;
 //BA.debugLineNum = 175;BA.debugLine="mValue = GetState";
parent._mvalue = parent._getstate();
 //BA.debugLineNum = 178;BA.debugLine="Dim js As String = UpdateLayout";
_js = parent._updatelayout();
 //BA.debugLineNum = 181;BA.debugLine="js = js & $\"var foot = document.getElementById(\"";
_js = _js+("var foot = document.getElementById(\"tile-footer\"); if(foot){foot.textContent = \""+parent.__c.SmartStringFormatter("",(Object)(parent._mvalue))+"\";};");
 //BA.debugLineNum = 184;BA.debugLine="Wait For (HMITilesIOUtils.ExecuteJS(mWebView, js";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 21;
return;
case 21:
//C
this.state = 16;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 185;BA.debugLine="If Not(result) Then";
if (true) break;

case 16:
//if
this.state = 19;
if (parent.__c.Not(_result)) { 
this.state = 18;
}if (true) break;

case 18:
//C
this.state = 19;
 //BA.debugLineNum = 186;BA.debugLine="Log($\"[SetChannelState][E] Can not set the chan";
parent.__c.LogImpl("921233684",("[SetChannelState][E] Can not set the channel for tile IOPanel"),0);
 if (true) break;

case 19:
//C
this.state = 20;
;
 if (true) break;

case 20:
//C
this.state = -1;
;
 //BA.debugLineNum = 189;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _complete(boolean _result) throws Exception{
}
public void  _setchanneltext(byte _channelnr,String _text) throws Exception{
ResumableSub_SetChannelText rsub = new ResumableSub_SetChannelText(this,_channelnr,_text);
rsub.resume(ba, null);
}
public static class ResumableSub_SetChannelText extends BA.ResumableSub {
public ResumableSub_SetChannelText(b4j.example.hmitilesiopanel parent,byte _channelnr,String _text) {
this.parent = parent;
this._channelnr = _channelnr;
this._text = _text;
}
b4j.example.hmitilesiopanel parent;
byte _channelnr;
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
 //BA.debugLineNum = 221;BA.debugLine="If channelnr < MIN_CHANNEL_INDEX Or channelnr > M";
if (true) break;

case 1:
//if
this.state = 4;
if (_channelnr<parent._min_channel_index || _channelnr>parent._max_channel_index) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 222;BA.debugLine="Log(\"[SetChannelText][E] Invalid channel layout";
parent.__c.LogImpl("921430275","[SetChannelText][E] Invalid channel layout index. Must be 0-7.",0);
 //BA.debugLineNum = 223;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 4:
//C
this.state = 5;
;
 //BA.debugLineNum = 227;BA.debugLine="Dim CleansedText As String = text";
_cleansedtext = _text;
 //BA.debugLineNum = 228;BA.debugLine="If CleansedText.Length > 3 Then";
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
 //BA.debugLineNum = 229;BA.debugLine="CleansedText = CleansedText.SubString2(0, 3)";
_cleansedtext = _cleansedtext.substring((int) (0),(int) (3));
 if (true) break;

case 8:
//C
this.state = 9;
;
 //BA.debugLineNum = 233;BA.debugLine="Dim js As String = $\" 		var txtEl = document.getE";
_js = ("\n"+"		var txtEl = document.getElementById(\"ch-txt"+parent.__c.SmartStringFormatter("",(Object)(_channelnr))+"\");\n"+"		if (txtEl) { txtEl.textContent = \""+parent.__c.SmartStringFormatter("",(Object)(_cleansedtext))+"\"; };\n"+"	");
 //BA.debugLineNum = 239;BA.debugLine="Wait For (HMITilesIOUtils.ExecuteJS(mWebView, js.";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js.replace(BA.ObjectToString(parent.__c.Chr((int) (10)))," ").replace(BA.ObjectToString(parent.__c.Chr((int) (13)))," ")));
this.state = 13;
return;
case 13:
//C
this.state = 9;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 240;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 241;BA.debugLine="Log($\"[SetChannelText][E] Can not set the channe";
parent.__c.LogImpl("921430294",("[SetChannelText][E] Can not set the channel text for tile IOPanel"),0);
 if (true) break;

case 12:
//C
this.state = -1;
;
 //BA.debugLineNum = 243;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _setchannelvisibility(byte _channelnr,boolean _visible) throws Exception{
ResumableSub_SetChannelVisibility rsub = new ResumableSub_SetChannelVisibility(this,_channelnr,_visible);
rsub.resume(ba, null);
}
public static class ResumableSub_SetChannelVisibility extends BA.ResumableSub {
public ResumableSub_SetChannelVisibility(b4j.example.hmitilesiopanel parent,byte _channelnr,boolean _visible) {
this.parent = parent;
this._channelnr = _channelnr;
this._visible = _visible;
}
b4j.example.hmitilesiopanel parent;
byte _channelnr;
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
 //BA.debugLineNum = 249;BA.debugLine="If channelnr < MIN_CHANNEL_INDEX Or channelnr > M";
if (true) break;

case 1:
//if
this.state = 4;
if (_channelnr<parent._min_channel_index || _channelnr>parent._max_channel_index) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 250;BA.debugLine="Log(\"[SetStateVisibility][E] Invalid channel lay";
parent.__c.LogImpl("921495811","[SetStateVisibility][E] Invalid channel layout index. Must be 0-7.",0);
 //BA.debugLineNum = 251;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 4:
//C
this.state = 5;
;
 //BA.debugLineNum = 255;BA.debugLine="Dim displayStyle As String = \"block\"";
_displaystyle = "block";
 //BA.debugLineNum = 256;BA.debugLine="If Not(visible) Then displayStyle = \"none\"";
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
 //BA.debugLineNum = 259;BA.debugLine="Dim js As String = $\" 		var rectEl = document.get";
_js = ("\n"+"		var rectEl = document.getElementById(\"ch"+parent.__c.SmartStringFormatter("",(Object)(_channelnr))+"\");\n"+"		if (rectEl) { \n"+"			rectEl.parentElement.style.display = \""+parent.__c.SmartStringFormatter("",(Object)(_displaystyle))+"\"; \n"+"		};\n"+"	");
 //BA.debugLineNum = 267;BA.debugLine="Wait For (HMITilesIOUtils.ExecuteJS(mWebView, js.";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js.replace(BA.ObjectToString(parent.__c.Chr((int) (10)))," ").replace(BA.ObjectToString(parent.__c.Chr((int) (13)))," ")));
this.state = 15;
return;
case 15:
//C
this.state = 11;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 268;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 269;BA.debugLine="Log($\"[SetStateVisibility][E] Can not alter visi";
parent.__c.LogImpl("921495830",("[SetStateVisibility][E] Can not alter visibility for tile IOPanel"),0);
 if (true) break;

case 14:
//C
this.state = -1;
;
 //BA.debugLineNum = 271;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public String  _settile(String _header,String _footer,String _value) throws Exception{
int _activecount = 0;
int _i = 0;
String _charstate = "";
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
String _determinedfooter = "";
String _js = "";
 //BA.debugLineNum = 71;BA.debugLine="Public Sub SetTile(Header As String, _ 				   Foot";
 //BA.debugLineNum = 75;BA.debugLine="mValue = Value";
_mvalue = _value;
 //BA.debugLineNum = 78;BA.debugLine="Dim ActiveCount As Int = mValue.Length";
_activecount = _mvalue.length();
 //BA.debugLineNum = 80;BA.debugLine="For i = MIN_CHANNEL_INDEX To MAX_CHANNEL_INDEX";
{
final int step3 = 1;
final int limit3 = (int) (_max_channel_index);
_i = (int) (_min_channel_index) ;
for (;_i <= limit3 ;_i = _i + step3 ) {
 //BA.debugLineNum = 81;BA.debugLine="If i < ActiveCount Then";
if (_i<_activecount) { 
 //BA.debugLineNum = 83;BA.debugLine="Dim CharState As String = mValue.CharAt(i)";
_charstate = BA.ObjectToString(_mvalue.charAt(_i));
 //BA.debugLineNum = 84;BA.debugLine="If CharState = \"1\" Then";
if ((_charstate).equals("1")) { 
 //BA.debugLineNum = 85;BA.debugLine="mChannels(i) = STATE_ON";
_mchannels[_i] = _state_on;
 }else {
 //BA.debugLineNum = 87;BA.debugLine="mChannels(i) = STATE_OFF";
_mchannels[_i] = _state_off;
 };
 }else {
 //BA.debugLineNum = 91;BA.debugLine="mChannels(i) = STATE_DISABLED";
_mchannels[_i] = _state_disabled;
 };
 }
};
 //BA.debugLineNum = 96;BA.debugLine="Dim sb As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 97;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 98;BA.debugLine="sb.Append($\" 		var head = document.getElementById";
_sb.Append(("\n"+"		var head = document.getElementById(\"tile-header\");\n"+"		if(head) { head.textContent = \""+__c.SmartStringFormatter("",(Object)(_header))+"\"; };\n"+"	"));
 //BA.debugLineNum = 104;BA.debugLine="sb.Append(UpdateLayout)";
_sb.Append(_updatelayout());
 //BA.debugLineNum = 107;BA.debugLine="Dim DeterminedFooter As String = Footer";
_determinedfooter = _footer;
 //BA.debugLineNum = 108;BA.debugLine="If DeterminedFooter = \"\" Then DeterminedFooter =";
if ((_determinedfooter).equals("")) { 
_determinedfooter = _mvalue;};
 //BA.debugLineNum = 109;BA.debugLine="sb.Append($\" 		var foot = document.getElementById";
_sb.Append(("\n"+"		var foot = document.getElementById(\"tile-footer\");\n"+"		if(foot) { foot.textContent = \""+__c.SmartStringFormatter("",(Object)(_determinedfooter))+"\"; };\n"+"	"));
 //BA.debugLineNum = 114;BA.debugLine="Dim js As String = sb.ToString";
_js = _sb.ToString();
 //BA.debugLineNum = 115;BA.debugLine="UpdateTile(js)";
_updatetile(_js);
 //BA.debugLineNum = 116;BA.debugLine="End Sub";
return "";
}
public String  _updatelayout() throws Exception{
String _displaystyle = "";
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
int _i = 0;
String _urlfill = "";
 //BA.debugLineNum = 130;BA.debugLine="Public Sub UpdateLayout As String";
 //BA.debugLineNum = 131;BA.debugLine="Dim displaystyle As String";
_displaystyle = "";
 //BA.debugLineNum = 132;BA.debugLine="Dim sb As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 134;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 136;BA.debugLine="For i = MIN_CHANNEL_INDEX To MAX_CHANNEL_INDEX";
{
final int step4 = 1;
final int limit4 = (int) (_max_channel_index);
_i = (int) (_min_channel_index) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
 //BA.debugLineNum = 137;BA.debugLine="Dim UrlFill As String";
_urlfill = "";
 //BA.debugLineNum = 138;BA.debugLine="Select mChannels(i)";
switch (BA.switchObjectToInt(_mchannels[_i],_state_off,_state_on,_state_disabled)) {
case 0: {
 //BA.debugLineNum = 140;BA.debugLine="UrlFill = \"url(#stateOff)\"";
_urlfill = "url(#stateOff)";
 //BA.debugLineNum = 141;BA.debugLine="displaystyle = HMITilesIOUtils.DISPLAY_STYLE_B";
_displaystyle = _hmitilesioutils._display_style_block /*String*/ ;
 break; }
case 1: {
 //BA.debugLineNum = 143;BA.debugLine="UrlFill = \"url(#stateOn)\"";
_urlfill = "url(#stateOn)";
 //BA.debugLineNum = 144;BA.debugLine="displaystyle = HMITilesIOUtils.DISPLAY_STYLE_B";
_displaystyle = _hmitilesioutils._display_style_block /*String*/ ;
 break; }
case 2: {
 //BA.debugLineNum = 146;BA.debugLine="UrlFill = \"url(#stateDisabled)\"";
_urlfill = "url(#stateDisabled)";
 //BA.debugLineNum = 147;BA.debugLine="displaystyle = HMITilesIOUtils.DISPLAY_STYLE_N";
_displaystyle = _hmitilesioutils._display_style_none /*String*/ ;
 break; }
default: {
 //BA.debugLineNum = 149;BA.debugLine="UrlFill = \"url(#stateDisabled)\"";
_urlfill = "url(#stateDisabled)";
 //BA.debugLineNum = 150;BA.debugLine="displaystyle = HMITilesIOUtils.DISPLAY_STYLE_B";
_displaystyle = _hmitilesioutils._display_style_block /*String*/ ;
 break; }
}
;
 //BA.debugLineNum = 153;BA.debugLine="sb.Append($\" 			var el${i} = document.getElement";
_sb.Append(("\n"+"			var el"+__c.SmartStringFormatter("",(Object)(_i))+" = document.getElementById(\"ch"+__c.SmartStringFormatter("",(Object)(_i))+"\"); \n"+"			if(el"+__c.SmartStringFormatter("",(Object)(_i))+"){\n"+"				el"+__c.SmartStringFormatter("",(Object)(_i))+".setAttribute(\"fill\", \""+__c.SmartStringFormatter("",(Object)(_urlfill))+"\");\n"+"				el"+__c.SmartStringFormatter("",(Object)(_i))+".parentElement.style.display = \""+__c.SmartStringFormatter("",(Object)(_displaystyle))+"\"; \n"+"			};\n"+"		"));
 }
};
 //BA.debugLineNum = 162;BA.debugLine="Return sb.ToString";
if (true) return _sb.ToString();
 //BA.debugLineNum = 163;BA.debugLine="End Sub";
return "";
}
public void  _updatetile(String _js) throws Exception{
ResumableSub_UpdateTile rsub = new ResumableSub_UpdateTile(this,_js);
rsub.resume(ba, null);
}
public static class ResumableSub_UpdateTile extends BA.ResumableSub {
public ResumableSub_UpdateTile(b4j.example.hmitilesiopanel parent,String _js) {
this.parent = parent;
this._js = _js;
}
b4j.example.hmitilesiopanel parent;
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
 //BA.debugLineNum = 123;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 124;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 125;BA.debugLine="Log($\"[IOPanel.UpdateTile][E] Can not update the";
parent.__c.LogImpl("921102595",("[IOPanel.UpdateTile][E] Can not update the tile."),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 127;BA.debugLine="End Sub";
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
