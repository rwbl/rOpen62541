package b4j.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;

public class hmitilesio extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    public static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new  anywheresoftware.b4j.objects.FxBA("b4j.example", "b4j.example.hmitilesio", this);
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            ba.htSubs = htSubs;
             
        }
        if (BA.isShellModeRuntimeCheck(ba))
                this.getClass().getMethod("_class_globals", b4j.example.hmitilesio.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public String _version = "";
public String _tile_battery = "";
public String _tile_button = "";
public String _tile_bytestatus = "";
public String _tile_compass = "";
public String _tile_dualreadout = "";
public String _tile_gauge = "";
public String _tile_iopanel = "";
public String _tile_iconindicator = "";
public String _tile_spinner = "";
public String _tile_ledpanel = "";
public String _tile_multistate = "";
public String _tile_readout = "";
public String _tile_selector = "";
public String _tile_sevensegment = "";
public String _tile_signal = "";
public String _tile_slider = "";
public String _tile_switch = "";
public String _tile_tiltgauge = "";
public String _tile_timer = "";
public String _tile_trendchart = "";
public String _tile_verticalmeter = "";
public String _tile_watchdog = "";
public String _segment_green = "";
public String _segment_yellow = "";
public String _segment_red = "";
public String _segment_green_color = "";
public String _segment_yellow_color = "";
public String _segment_red_color = "";
public anywheresoftware.b4a.objects.B4XViewWrapper _basepane = null;
public Object _tag = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public anywheresoftware.b4j.objects.WebViewWrapper _webviewsvg = null;
public anywheresoftware.b4a.objects.B4XViewWrapper _panelwebviewsvg = null;
public String _mtiletype = "";
public String _mheader = "";
public String _mfooter = "";
public String _mvalue = "";
public float _mminvalue = 0f;
public float _mmaxvalue = 0f;
public int _mgreenmaxpct = 0;
public int _myellowmaxpct = 0;
public String _mbackgroundcolor = "";
public boolean _mstate = false;
public anywheresoftware.b4a.objects.collections.List _mitems = null;
public String _meventname = "";
public Object _mcallback = null;
public b4j.example.hmitilesiobattery _instancebattery = null;
public b4j.example.hmitilesiobutton _instancebutton = null;
public b4j.example.hmitilesiobytestatus _instancebytestatus = null;
public b4j.example.hmitilesiocompass _instancecompass = null;
public b4j.example.hmitilesiodualreadout _instancedualreadout = null;
public b4j.example.hmitilesiogauge _instancegauge = null;
public b4j.example.hmitilesiopanel _instanceiopanel = null;
public b4j.example.hmitilesioiconindicator _instanceiconindicator = null;
public b4j.example.hmitilesioledpanel _instanceledpanel = null;
public b4j.example.hmitilesiomultistate _instancemultistate = null;
public b4j.example.hmitilesioreadout _instancereadout = null;
public b4j.example.hmitilesioselector _instanceselector = null;
public b4j.example.hmitilesiosevensegment _instancesevensegment = null;
public b4j.example.hmitilesiosignal _instancesignal = null;
public b4j.example.hmitilesioslider _instanceslider = null;
public b4j.example.hmitilesiospinner _instancespinner = null;
public b4j.example.hmitilesioswitch _instanceswitch = null;
public b4j.example.hmitilesiotiltgauge _instancetiltgauge = null;
public b4j.example.hmitilesiotimer _instancetimer = null;
public b4j.example.hmitilesiotrendchart _instancetrendchart = null;
public b4j.example.hmitilesioverticalmeter _instanceverticalmeter = null;
public b4j.example.hmitilesiowatchdog _instancewatchdog = null;
public String _image_markup_placeholder = "";
public String _image_markup_file = "";
public String _imagemarkup = "";
public int _mheaderfontsize = 0;
public String _mheaderfontcolor = "";
public int _mvaluefontsize = 0;
public String _mvaluefontcolor = "";
public int _mfooterfontsize = 0;
public String _mfooterfontcolor = "";
public boolean _misready = false;
public b4j.example.main _main = null;
public b4j.example.b4xpages _b4xpages = null;
public b4j.example.b4xcollections _b4xcollections = null;
public b4j.example.hmitilesioutils _hmitilesioutils = null;
public static class _hmitouchdata{
public boolean IsInitialized;
public int Action;
public float X;
public float Y;
public boolean State;
public String Value;
public void Initialize() {
IsInitialized = true;
Action = 0;
X = 0f;
Y = 0f;
State = false;
Value = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _hmitouchresult{
public boolean IsInitialized;
public Object State;
public String Value;
public void Initialize() {
IsInitialized = true;
State = new Object();
Value = "";
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public String  _afterloadlayout(anywheresoftware.b4a.objects.collections.Map _props) throws Exception{
int _clr = 0;
 //BA.debugLineNum = 180;BA.debugLine="Private Sub AfterLoadLayout(Props As Map)	'ignore";
 //BA.debugLineNum = 181;BA.debugLine="BasePane.LoadLayout(\"hmitilesio\")";
_basepane.LoadLayout("hmitilesio",ba);
 //BA.debugLineNum = 184;BA.debugLine="mTileType 			= Props.GetDefault(\"tiletype\", \"noas";
_mtiletype = BA.ObjectToString(_props.GetDefault((Object)("tiletype"),(Object)("noasset")));
 //BA.debugLineNum = 185;BA.debugLine="mTileType			= mTileType.ToUpperCase";
_mtiletype = _mtiletype.toUpperCase(anywheresoftware.b4a.keywords.Common.stringLocale);
 //BA.debugLineNum = 186;BA.debugLine="mHeader 			= Props.GetDefault(\"header\", \"\")";
_mheader = BA.ObjectToString(_props.GetDefault((Object)("header"),(Object)("")));
 //BA.debugLineNum = 187;BA.debugLine="mFooter 			= Props.GetDefault(\"footer\", \"\")";
_mfooter = BA.ObjectToString(_props.GetDefault((Object)("footer"),(Object)("")));
 //BA.debugLineNum = 188;BA.debugLine="mValue 				= Props.GetDefault(\"value\", 0)";
_mvalue = BA.ObjectToString(_props.GetDefault((Object)("value"),(Object)(0)));
 //BA.debugLineNum = 189;BA.debugLine="mMinValue 			= Props.GetDefault(\"minvalue\", 0)";
_mminvalue = (float)(BA.ObjectToNumber(_props.GetDefault((Object)("minvalue"),(Object)(0))));
 //BA.debugLineNum = 190;BA.debugLine="mMaxValue 			= Props.GetDefault(\"maxvalue\", 100)";
_mmaxvalue = (float)(BA.ObjectToNumber(_props.GetDefault((Object)("maxvalue"),(Object)(100))));
 //BA.debugLineNum = 191;BA.debugLine="mGreenMaxPct		= Props.GetDefault(\"greenmaxpct\", 7";
_mgreenmaxpct = (int)(BA.ObjectToNumber(_props.GetDefault((Object)("greenmaxpct"),(Object)(70))));
 //BA.debugLineNum = 192;BA.debugLine="mYellowMaxPct		= Props.GetDefault(\"yellowmaxpct\",";
_myellowmaxpct = (int)(BA.ObjectToNumber(_props.GetDefault((Object)("yellowmaxpct"),(Object)(100))));
 //BA.debugLineNum = 193;BA.debugLine="mState 				= Props.GetDefault(\"state\", False)";
_mstate = BA.ObjectToBoolean(_props.GetDefault((Object)("state"),(Object)(__c.False)));
 //BA.debugLineNum = 195;BA.debugLine="Dim clr As Int		= xui.PaintOrColorToColor(Props.G";
_clr = _xui.PaintOrColorToColor(_props.Get((Object)("backgroundcolor")));
 //BA.debugLineNum = 197;BA.debugLine="mBackgroundColor	= $\"#${Bit.ToHexString(clr).SubS";
_mbackgroundcolor = ("#"+__c.SmartStringFormatter("",(Object)(__c.Bit.ToHexString(_clr).substring((int) (2))))+"");
 //BA.debugLineNum = 200;BA.debugLine="ImageMarkup = File.ReadString(File.DirAssets, IMA";
_imagemarkup = __c.File.ReadString(__c.File.getDirAssets(),_image_markup_file);
 //BA.debugLineNum = 203;BA.debugLine="InitInstance";
_initinstance();
 //BA.debugLineNum = 206;BA.debugLine="ApplyStyle";
_applystyle();
 //BA.debugLineNum = 207;BA.debugLine="Base_Resize(BasePane.Width, BasePane.Height)";
_base_resize(_basepane.getWidth(),_basepane.getHeight());
 //BA.debugLineNum = 208;BA.debugLine="End Sub";
return "";
}
public String  _applystyle() throws Exception{
 //BA.debugLineNum = 314;BA.debugLine="Private Sub ApplyStyle";
 //BA.debugLineNum = 315;BA.debugLine="BasePane.Color = 0xFF4A5560";
_basepane.setColor(((int)0xff4a5560));
 //BA.debugLineNum = 316;BA.debugLine="BasePane.SetColorAndBorder(BasePane.Color, 0, 0,";
_basepane.SetColorAndBorder(_basepane.getColor(),0,(int) (0),0);
 //BA.debugLineNum = 317;BA.debugLine="End Sub";
return "";
}
public String  _base_resize(double _width,double _height) throws Exception{
 //BA.debugLineNum = 210;BA.debugLine="Private Sub Base_Resize (Width As Double, Height A";
 //BA.debugLineNum = 211;BA.debugLine="If Not(WebViewSVG.IsInitialized) Then Return";
if (__c.Not(_webviewsvg.IsInitialized())) { 
if (true) return "";};
 //BA.debugLineNum = 212;BA.debugLine="DrawImage";
_drawimage();
 //BA.debugLineNum = 213;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 54;BA.debugLine="Private Sub Class_Globals";
 //BA.debugLineNum = 56;BA.debugLine="Public Const VERSION As String =\"HMITilesIO 0.8.0";
_version = "HMITilesIO 0.8.0 (Build 2026-09-25)";
 //BA.debugLineNum = 60;BA.debugLine="Private TILE_BATTERY As String 			= \"BATTERY\"";
_tile_battery = "BATTERY";
 //BA.debugLineNum = 61;BA.debugLine="Private TILE_BUTTON As String 			= \"BUTTON\"";
_tile_button = "BUTTON";
 //BA.debugLineNum = 62;BA.debugLine="Private TILE_BYTESTATUS As String 		= \"BYTESTATUS";
_tile_bytestatus = "BYTESTATUS";
 //BA.debugLineNum = 63;BA.debugLine="Private TILE_COMPASS As String 			= \"COMPASS\"";
_tile_compass = "COMPASS";
 //BA.debugLineNum = 64;BA.debugLine="Private TILE_DUALREADOUT As String 		= \"DUALREADO";
_tile_dualreadout = "DUALREADOUT";
 //BA.debugLineNum = 65;BA.debugLine="Private TILE_GAUGE As String 			= \"GAUGE\"";
_tile_gauge = "GAUGE";
 //BA.debugLineNum = 66;BA.debugLine="Private TILE_IOPANEL As String 			= \"IOPANEL\"";
_tile_iopanel = "IOPANEL";
 //BA.debugLineNum = 67;BA.debugLine="Private TILE_ICONINDICATOR As String 	= \"ICONINDI";
_tile_iconindicator = "ICONINDICATOR";
 //BA.debugLineNum = 68;BA.debugLine="Private TILE_SPINNER As String 			= \"SPINNER\"";
_tile_spinner = "SPINNER";
 //BA.debugLineNum = 69;BA.debugLine="Private TILE_LEDPANEL As String 		= \"LEDPANEL\"";
_tile_ledpanel = "LEDPANEL";
 //BA.debugLineNum = 70;BA.debugLine="Private TILE_MULTISTATE As String 		= \"MULTISTATE";
_tile_multistate = "MULTISTATE";
 //BA.debugLineNum = 71;BA.debugLine="Private TILE_READOUT As String 			= \"READOUT\"";
_tile_readout = "READOUT";
 //BA.debugLineNum = 72;BA.debugLine="Private TILE_SELECTOR As String 		= \"SELECTOR\"";
_tile_selector = "SELECTOR";
 //BA.debugLineNum = 73;BA.debugLine="Private TILE_SEVENSEGMENT As String 	= \"SEVENSEGM";
_tile_sevensegment = "SEVENSEGMENT";
 //BA.debugLineNum = 74;BA.debugLine="Private TILE_SIGNAL As String 			= \"SIGNAL\"";
_tile_signal = "SIGNAL";
 //BA.debugLineNum = 75;BA.debugLine="Private TILE_SLIDER As String 			= \"SLIDER\"";
_tile_slider = "SLIDER";
 //BA.debugLineNum = 76;BA.debugLine="Private TILE_SWITCH As String 			= \"SWITCH\"";
_tile_switch = "SWITCH";
 //BA.debugLineNum = 77;BA.debugLine="Private TILE_TILTGAUGE As String 		= \"TILTGAUGE\"";
_tile_tiltgauge = "TILTGAUGE";
 //BA.debugLineNum = 78;BA.debugLine="Private TILE_TIMER As String 			= \"TIMER\"";
_tile_timer = "TIMER";
 //BA.debugLineNum = 79;BA.debugLine="Private TILE_TRENDCHART As String 		= \"TRENDCHART";
_tile_trendchart = "TRENDCHART";
 //BA.debugLineNum = 80;BA.debugLine="Private TILE_VERTICALMETER As String 	= \"VERTICAL";
_tile_verticalmeter = "VERTICALMETER";
 //BA.debugLineNum = 81;BA.debugLine="Private TILE_WATCHDOG As String 		= \"WATCHDOG\"";
_tile_watchdog = "WATCHDOG";
 //BA.debugLineNum = 84;BA.debugLine="Public SEGMENT_GREEN			As String = \"green\"";
_segment_green = "green";
 //BA.debugLineNum = 85;BA.debugLine="Public SEGMENT_YELLOW			As String = \"yellow\"";
_segment_yellow = "yellow";
 //BA.debugLineNum = 86;BA.debugLine="Public SEGMENT_RED				As String = \"red\"";
_segment_red = "red";
 //BA.debugLineNum = 87;BA.debugLine="Public SEGMENT_GREEN_COLOR 		As String = \"#22c55e";
_segment_green_color = "#22c55e";
 //BA.debugLineNum = 88;BA.debugLine="Public SEGMENT_YELLOW_COLOR		As String = \"#eab308";
_segment_yellow_color = "#eab308";
 //BA.debugLineNum = 89;BA.debugLine="Public SEGMENT_RED_COLOR 		As String = \"#ef4444\"";
_segment_red_color = "#ef4444";
 //BA.debugLineNum = 92;BA.debugLine="Type HMITouchData (Action As Int, X As Float, Y A";
;
 //BA.debugLineNum = 94;BA.debugLine="Type HMITouchResult (State As Object, Value As St";
;
 //BA.debugLineNum = 97;BA.debugLine="Public BasePane As B4XView";
_basepane = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 98;BA.debugLine="Public Tag As Object";
_tag = new Object();
 //BA.debugLineNum = 101;BA.debugLine="Private xui As XUI 'ignore";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 102;BA.debugLine="Private WebViewSVG As WebView";
_webviewsvg = new anywheresoftware.b4j.objects.WebViewWrapper();
 //BA.debugLineNum = 103;BA.debugLine="Private PanelWebViewSVG As B4XView";
_panelwebviewsvg = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 106;BA.debugLine="Private mTileType As String";
_mtiletype = "";
 //BA.debugLineNum = 107;BA.debugLine="Private mHeader As String";
_mheader = "";
 //BA.debugLineNum = 108;BA.debugLine="Private mFooter As String";
_mfooter = "";
 //BA.debugLineNum = 109;BA.debugLine="Private mValue As String";
_mvalue = "";
 //BA.debugLineNum = 110;BA.debugLine="Private mMinValue As Float";
_mminvalue = 0f;
 //BA.debugLineNum = 111;BA.debugLine="Private mMaxValue As Float";
_mmaxvalue = 0f;
 //BA.debugLineNum = 112;BA.debugLine="Private mGreenMaxPct As Int";
_mgreenmaxpct = 0;
 //BA.debugLineNum = 113;BA.debugLine="Private mYellowMaxPct As Int";
_myellowmaxpct = 0;
 //BA.debugLineNum = 114;BA.debugLine="Private mBackgroundColor As String";
_mbackgroundcolor = "";
 //BA.debugLineNum = 115;BA.debugLine="Private mState As Boolean";
_mstate = false;
 //BA.debugLineNum = 118;BA.debugLine="Private mItems As List										' Selector";
_mitems = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 121;BA.debugLine="Private mEventName As String 'ignore";
_meventname = "";
 //BA.debugLineNum = 122;BA.debugLine="Private mCallBack As Object 'ignore";
_mcallback = new Object();
 //BA.debugLineNum = 125;BA.debugLine="Public InstanceBattery 			As HMITilesIOBattery";
_instancebattery = new b4j.example.hmitilesiobattery();
 //BA.debugLineNum = 126;BA.debugLine="Public InstanceButton 			As HMITilesIOButton";
_instancebutton = new b4j.example.hmitilesiobutton();
 //BA.debugLineNum = 127;BA.debugLine="Public InstanceByteStatus 		As HMITilesIOByteStat";
_instancebytestatus = new b4j.example.hmitilesiobytestatus();
 //BA.debugLineNum = 128;BA.debugLine="Public InstanceCompass 			As HMITilesIOCompass";
_instancecompass = new b4j.example.hmitilesiocompass();
 //BA.debugLineNum = 129;BA.debugLine="Public InstanceDualReadOut 		As HMITilesIODualRea";
_instancedualreadout = new b4j.example.hmitilesiodualreadout();
 //BA.debugLineNum = 130;BA.debugLine="Public InstanceGauge 			As HMITilesIOGauge";
_instancegauge = new b4j.example.hmitilesiogauge();
 //BA.debugLineNum = 131;BA.debugLine="Public InstanceIOPanel 			As HMITilesIOPanel";
_instanceiopanel = new b4j.example.hmitilesiopanel();
 //BA.debugLineNum = 132;BA.debugLine="Public InstanceIconIndicator 	As HMITilesIOIconIn";
_instanceiconindicator = new b4j.example.hmitilesioiconindicator();
 //BA.debugLineNum = 133;BA.debugLine="Public InstanceLEDPanel 		As HMITilesIOLEDPanel";
_instanceledpanel = new b4j.example.hmitilesioledpanel();
 //BA.debugLineNum = 134;BA.debugLine="Public InstanceMultiState 		As HMITilesIOMultiSta";
_instancemultistate = new b4j.example.hmitilesiomultistate();
 //BA.debugLineNum = 135;BA.debugLine="Public InstanceReadOut 			As HMITilesIOReadOut";
_instancereadout = new b4j.example.hmitilesioreadout();
 //BA.debugLineNum = 136;BA.debugLine="Public InstanceSelector 		As HMITilesIOSelector";
_instanceselector = new b4j.example.hmitilesioselector();
 //BA.debugLineNum = 137;BA.debugLine="Public InstanceSevenSegment		As HMITilesIOSevenSe";
_instancesevensegment = new b4j.example.hmitilesiosevensegment();
 //BA.debugLineNum = 138;BA.debugLine="Public InstanceSignal 			As HMITilesIOSignal";
_instancesignal = new b4j.example.hmitilesiosignal();
 //BA.debugLineNum = 139;BA.debugLine="Public InstanceSlider 			As HMITilesIOSlider";
_instanceslider = new b4j.example.hmitilesioslider();
 //BA.debugLineNum = 140;BA.debugLine="Public InstanceSpinner 			As HMITilesIOSpinner";
_instancespinner = new b4j.example.hmitilesiospinner();
 //BA.debugLineNum = 141;BA.debugLine="Public InstanceSwitch 			As HMITilesIOSwitch";
_instanceswitch = new b4j.example.hmitilesioswitch();
 //BA.debugLineNum = 142;BA.debugLine="Public InstanceTiltGauge 		As HMITilesIOTiltGauge";
_instancetiltgauge = new b4j.example.hmitilesiotiltgauge();
 //BA.debugLineNum = 143;BA.debugLine="Public InstanceTimer 			As HMITilesIOTimer";
_instancetimer = new b4j.example.hmitilesiotimer();
 //BA.debugLineNum = 144;BA.debugLine="Public InstanceTrendChart 		As HMITilesIOTrendCha";
_instancetrendchart = new b4j.example.hmitilesiotrendchart();
 //BA.debugLineNum = 145;BA.debugLine="Public InstanceVerticalMeter	As HMITilesIOVertica";
_instanceverticalmeter = new b4j.example.hmitilesioverticalmeter();
 //BA.debugLineNum = 146;BA.debugLine="Public InstanceWatchdog			As HMITilesIOWatchdog";
_instancewatchdog = new b4j.example.hmitilesiowatchdog();
 //BA.debugLineNum = 149;BA.debugLine="Private IMAGE_MARKUP_PLACEHOLDER As String = \"#IM";
_image_markup_placeholder = "#IMAGE_PLACEHOLDER#";
 //BA.debugLineNum = 151;BA.debugLine="Private IMAGE_MARKUP_FILE As String = \"image.html";
_image_markup_file = "image.html";
 //BA.debugLineNum = 152;BA.debugLine="Private ImageMarkup As String";
_imagemarkup = "";
 //BA.debugLineNum = 156;BA.debugLine="Private mHeaderFontSize 		As Int = 11";
_mheaderfontsize = (int) (11);
 //BA.debugLineNum = 157;BA.debugLine="Private mHeaderFontColor 		As String = \"#94a3b8\"";
_mheaderfontcolor = "#94a3b8";
 //BA.debugLineNum = 158;BA.debugLine="Private mValueFontSize 			As Int = 24";
_mvaluefontsize = (int) (24);
 //BA.debugLineNum = 159;BA.debugLine="Private mValueFontColor 		As String = \"#0f172a\"";
_mvaluefontcolor = "#0f172a";
 //BA.debugLineNum = 160;BA.debugLine="Private mFooterFontSize 		As Int = 10";
_mfooterfontsize = (int) (10);
 //BA.debugLineNum = 161;BA.debugLine="Private mFooterFontColor 		As String = \"64748b\"";
_mfooterfontcolor = "64748b";
 //BA.debugLineNum = 163;BA.debugLine="Private mIsReady As Boolean = False";
_misready = __c.False;
 //BA.debugLineNum = 164;BA.debugLine="End Sub";
return "";
}
public String  _designercreateview(Object _base,anywheresoftware.b4j.objects.LabelWrapper _lbl,anywheresoftware.b4a.objects.collections.Map _props) throws Exception{
 //BA.debugLineNum = 173;BA.debugLine="Private Sub DesignerCreateView (Base As Object, Lb";
 //BA.debugLineNum = 174;BA.debugLine="BasePane = Base";
_basepane = (anywheresoftware.b4a.objects.B4XViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.B4XViewWrapper(), (java.lang.Object)(_base));
 //BA.debugLineNum = 175;BA.debugLine="Tag = BasePane.Tag";
_tag = _basepane.getTag();
 //BA.debugLineNum = 176;BA.debugLine="BasePane.Tag = Me";
_basepane.setTag(this);
 //BA.debugLineNum = 177;BA.debugLine="CallSubDelayed2(Me, \"AfterLoadLayout\", Props)";
__c.CallSubDelayed2(ba,this,"AfterLoadLayout",(Object)(_props));
 //BA.debugLineNum = 178;BA.debugLine="End Sub";
return "";
}
public String  _drawimage() throws Exception{
String _filename = "";
String _xmlmarkup = "";
 //BA.debugLineNum = 326;BA.debugLine="Public Sub DrawImage";
 //BA.debugLineNum = 328;BA.debugLine="Dim filename As String = $\"${mTileType.ToLowerCas";
_filename = (""+__c.SmartStringFormatter("",(Object)(_mtiletype.toLowerCase(anywheresoftware.b4a.keywords.Common.stringLocale)))+".svg");
 //BA.debugLineNum = 329;BA.debugLine="Try";
try { //BA.debugLineNum = 331;BA.debugLine="Dim xmlmarkup As String = File.ReadString(File.D";
_xmlmarkup = __c.File.ReadString(__c.File.getDirAssets(),_filename);
 } 
       catch (Exception e5) {
			ba.setLastException(e5); //BA.debugLineNum = 333;BA.debugLine="Log($\"[Public Sub DrawImage][E] can not load SVG";
__c.LogImpl("913172743",("[Public Sub DrawImage][E] can not load SVG "+__c.SmartStringFormatter("",(Object)(_filename))+" "+__c.SmartStringFormatter("",(Object)(__c.LastException(ba).getMessage()))+""),0);
 //BA.debugLineNum = 334;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 338;BA.debugLine="Select mTileType";
switch (BA.switchObjectToInt(_mtiletype,_tile_sevensegment)) {
case 0: {
 //BA.debugLineNum = 341;BA.debugLine="xmlmarkup = InstanceSevenSegment.LoadTemplate(x";
_xmlmarkup = _instancesevensegment._loadtemplate /*String*/ (_xmlmarkup);
 break; }
}
;
 //BA.debugLineNum = 345;BA.debugLine="WebViewSVG.LoadHtml(ImageMarkup.Replace(IMAGE_MAR";
_webviewsvg.LoadHtml(_imagemarkup.replace(_image_markup_placeholder,_xmlmarkup));
 //BA.debugLineNum = 346;BA.debugLine="End Sub";
return "";
}
public String  _getbackgroundcolor() throws Exception{
 //BA.debugLineNum = 686;BA.debugLine="Public Sub getBackgroundColor As String";
 //BA.debugLineNum = 687;BA.debugLine="Return mBackgroundColor";
if (true) return _mbackgroundcolor;
 //BA.debugLineNum = 688;BA.debugLine="End Sub";
return "";
}
public String  _getfooter() throws Exception{
 //BA.debugLineNum = 619;BA.debugLine="Public Sub getFooter As String";
 //BA.debugLineNum = 620;BA.debugLine="Return mFooter";
if (true) return _mfooter;
 //BA.debugLineNum = 621;BA.debugLine="End Sub";
return "";
}
public String  _getfooterfontcolor() throws Exception{
 //BA.debugLineNum = 657;BA.debugLine="Public Sub getFooterFontColor As String";
 //BA.debugLineNum = 658;BA.debugLine="Return mFooterFontColor";
if (true) return _mfooterfontcolor;
 //BA.debugLineNum = 659;BA.debugLine="End Sub";
return "";
}
public int  _getfooterfontsize() throws Exception{
 //BA.debugLineNum = 638;BA.debugLine="Public Sub getFooterFontSize As Int";
 //BA.debugLineNum = 639;BA.debugLine="Return mFooterFontSize";
if (true) return _mfooterfontsize;
 //BA.debugLineNum = 640;BA.debugLine="End Sub";
return 0;
}
public String  _getheader() throws Exception{
 //BA.debugLineNum = 558;BA.debugLine="Public Sub getHeader As String";
 //BA.debugLineNum = 559;BA.debugLine="Return mHeader";
if (true) return _mheader;
 //BA.debugLineNum = 560;BA.debugLine="End Sub";
return "";
}
public String  _getheaderfontcolor() throws Exception{
 //BA.debugLineNum = 596;BA.debugLine="Public Sub getHeaderFontColor As String";
 //BA.debugLineNum = 597;BA.debugLine="Return mHeaderFontColor";
if (true) return _mheaderfontcolor;
 //BA.debugLineNum = 598;BA.debugLine="End Sub";
return "";
}
public int  _getheaderfontsize() throws Exception{
 //BA.debugLineNum = 577;BA.debugLine="Public Sub getHeaderFontSize As Int";
 //BA.debugLineNum = 578;BA.debugLine="Return mHeaderFontSize";
if (true) return _mheaderfontsize;
 //BA.debugLineNum = 579;BA.debugLine="End Sub";
return 0;
}
public anywheresoftware.b4a.objects.collections.List  _getitems() throws Exception{
 //BA.debugLineNum = 535;BA.debugLine="Public Sub getItems As List";
 //BA.debugLineNum = 536;BA.debugLine="Return mItems";
if (true) return _mitems;
 //BA.debugLineNum = 537;BA.debugLine="End Sub";
return null;
}
public boolean  _getstate() throws Exception{
 //BA.debugLineNum = 392;BA.debugLine="Public Sub getState As Boolean";
 //BA.debugLineNum = 393;BA.debugLine="Return mState";
if (true) return _mstate;
 //BA.debugLineNum = 394;BA.debugLine="End Sub";
return false;
}
public String  _gettiletype() throws Exception{
 //BA.debugLineNum = 361;BA.debugLine="Public Sub getTileType As String";
 //BA.debugLineNum = 362;BA.debugLine="Return mTileType";
if (true) return _mtiletype;
 //BA.debugLineNum = 363;BA.debugLine="End Sub";
return "";
}
public String  _getvalue() throws Exception{
 //BA.debugLineNum = 457;BA.debugLine="Public Sub getValue As String";
 //BA.debugLineNum = 458;BA.debugLine="Return mValue";
if (true) return _mvalue;
 //BA.debugLineNum = 459;BA.debugLine="End Sub";
return "";
}
public String  _getvaluefontcolor() throws Exception{
 //BA.debugLineNum = 523;BA.debugLine="Public Sub getValueFontColor As String";
 //BA.debugLineNum = 524;BA.debugLine="Return mValueFontColor";
if (true) return _mvaluefontcolor;
 //BA.debugLineNum = 525;BA.debugLine="End Sub";
return "";
}
public int  _getvaluefontsize() throws Exception{
 //BA.debugLineNum = 490;BA.debugLine="Public Sub getValueFontSize As Int";
 //BA.debugLineNum = 491;BA.debugLine="Return mValueFontSize";
if (true) return _mvaluefontsize;
 //BA.debugLineNum = 492;BA.debugLine="End Sub";
return 0;
}
public String  _initialize(anywheresoftware.b4a.BA _ba,Object _callback,String _eventname) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 166;BA.debugLine="Public Sub Initialize (Callback As Object, EventNa";
 //BA.debugLineNum = 167;BA.debugLine="mEventName = EventName";
_meventname = _eventname;
 //BA.debugLineNum = 168;BA.debugLine="mCallBack = Callback";
_mcallback = _callback;
 //BA.debugLineNum = 169;BA.debugLine="mItems.Initialize";
_mitems.Initialize();
 //BA.debugLineNum = 170;BA.debugLine="End Sub";
return "";
}
public String  _initinstance() throws Exception{
byte[] _pins = null;
byte[] _states = null;
 //BA.debugLineNum = 221;BA.debugLine="Private Sub InitInstance";
 //BA.debugLineNum = 222;BA.debugLine="mIsReady = False";
_misready = __c.False;
 //BA.debugLineNum = 224;BA.debugLine="Select mTileType";
switch (BA.switchObjectToInt(_mtiletype,_tile_battery,_tile_button,_tile_bytestatus,_tile_compass,_tile_dualreadout,_tile_gauge,_tile_iconindicator,_tile_iopanel,_tile_ledpanel,_tile_multistate,_tile_readout,_tile_selector,_tile_sevensegment,_tile_signal,_tile_slider,_tile_spinner,_tile_switch,_tile_trendchart,_tile_tiltgauge,_tile_timer,_tile_verticalmeter,_tile_watchdog)) {
case 0: {
 //BA.debugLineNum = 226;BA.debugLine="InstanceBattery.Initialize(PanelWebViewSVG, Web";
_instancebattery._initialize /*String*/ (ba,_panelwebviewsvg,_webviewsvg,_meventname,_mcallback);
 //BA.debugLineNum = 227;BA.debugLine="InstanceBattery.SetTile(mHeader, mFooter, mValu";
_instancebattery._settile /*String*/ (_mheader,_mfooter,_mvalue);
 break; }
case 1: {
 //BA.debugLineNum = 229;BA.debugLine="InstanceButton.Initialize(PanelWebViewSVG, WebV";
_instancebutton._initialize /*String*/ (ba,_panelwebviewsvg,_webviewsvg,_meventname,_mcallback);
 //BA.debugLineNum = 230;BA.debugLine="InstanceButton.SetTile(mHeader, mFooter, mState";
_instancebutton._settile /*String*/ (_mheader,_mfooter,_mstate);
 break; }
case 2: {
 //BA.debugLineNum = 232;BA.debugLine="InstanceByteStatus.Initialize(PanelWebViewSVG,";
_instancebytestatus._initialize /*String*/ (ba,_panelwebviewsvg,_webviewsvg,_meventname,_mcallback);
 //BA.debugLineNum = 233;BA.debugLine="Dim pins() As Byte = Array As Byte(1,1,1,1,1,1,";
_pins = new byte[]{(byte) (1),(byte) (1),(byte) (1),(byte) (1),(byte) (1),(byte) (1),(byte) (1),(byte) (1)};
 //BA.debugLineNum = 234;BA.debugLine="InstanceByteStatus.SetTile(mHeader, mFooter, pi";
_instancebytestatus._settile /*String*/ (_mheader,_mfooter,_pins,_mvalue);
 break; }
case 3: {
 //BA.debugLineNum = 236;BA.debugLine="InstanceCompass.Initialize(PanelWebViewSVG, Web";
_instancecompass._initialize /*String*/ (ba,_panelwebviewsvg,_webviewsvg,_meventname,_mcallback);
 //BA.debugLineNum = 237;BA.debugLine="If mValue.Length = 0 Then mValue = 0";
if (_mvalue.length()==0) { 
_mvalue = BA.NumberToString(0);};
 //BA.debugLineNum = 238;BA.debugLine="InstanceCompass.SetTile(mHeader, mFooter, mValu";
_instancecompass._settile /*String*/ (_mheader,_mfooter,_mvalue);
 break; }
case 4: {
 //BA.debugLineNum = 240;BA.debugLine="InstanceDualReadOut.Initialize(PanelWebViewSVG,";
_instancedualreadout._initialize /*String*/ (ba,_panelwebviewsvg,_webviewsvg,_meventname,_mcallback);
 //BA.debugLineNum = 241;BA.debugLine="InstanceDualReadOut.SetTile(mHeader, mFooter, m";
_instancedualreadout._settile /*String*/ (_mheader,_mfooter,_mvalue);
 break; }
case 5: {
 //BA.debugLineNum = 243;BA.debugLine="InstanceGauge.Initialize(PanelWebViewSVG, WebVi";
_instancegauge._initialize /*String*/ (ba,_panelwebviewsvg,_webviewsvg,_meventname,_mcallback);
 //BA.debugLineNum = 244;BA.debugLine="If mValue.Length = 0 Then mValue = 0";
if (_mvalue.length()==0) { 
_mvalue = BA.NumberToString(0);};
 //BA.debugLineNum = 245;BA.debugLine="InstanceGauge.SetTile(mHeader, mFooter, mMinVal";
_instancegauge._settile /*String*/ (_mheader,_mfooter,_mminvalue,_mmaxvalue,(float) (_mgreenmaxpct),(float) (_myellowmaxpct),(float)(Double.parseDouble(_mvalue)));
 break; }
case 6: {
 //BA.debugLineNum = 247;BA.debugLine="InstanceIconIndicator.Initialize(PanelWebViewSV";
_instanceiconindicator._initialize /*String*/ (ba,_panelwebviewsvg,_webviewsvg,_meventname,_mcallback);
 //BA.debugLineNum = 248;BA.debugLine="InstanceIconIndicator.SetTile(mHeader, mFooter,";
_instanceiconindicator._settile /*String*/ (_mheader,_mfooter,_mvalue);
 break; }
case 7: {
 //BA.debugLineNum = 250;BA.debugLine="InstanceIOPanel.Initialize(PanelWebViewSVG, Web";
_instanceiopanel._initialize /*String*/ (ba,_panelwebviewsvg,_webviewsvg,_meventname,_mcallback);
 //BA.debugLineNum = 251;BA.debugLine="InstanceIOPanel.SetTile(mHeader, mFooter, mValu";
_instanceiopanel._settile /*String*/ (_mheader,_mfooter,_mvalue);
 break; }
case 8: {
 //BA.debugLineNum = 253;BA.debugLine="InstanceLEDPanel.Initialize(PanelWebViewSVG, We";
_instanceledpanel._initialize /*String*/ (ba,_panelwebviewsvg,_webviewsvg,_meventname,_mcallback);
 //BA.debugLineNum = 254;BA.debugLine="InstanceLEDPanel.SetTile(mHeader, mFooter, mSta";
_instanceledpanel._settile /*String*/ (_mheader,_mfooter,_mstate);
 break; }
case 9: {
 //BA.debugLineNum = 256;BA.debugLine="InstanceMultiState.Initialize(PanelWebViewSVG,";
_instancemultistate._initialize /*String*/ (ba,_panelwebviewsvg,_webviewsvg,_meventname,_mcallback);
 //BA.debugLineNum = 257;BA.debugLine="Dim states() As Byte = Array As Byte(0,0,0,0,0,";
_states = new byte[]{(byte) (0),(byte) (0),(byte) (0),(byte) (0),(byte) (0),(byte) (0),(byte) (0),(byte) (0)};
 //BA.debugLineNum = 258;BA.debugLine="InstanceMultiState.SetTile(mHeader, mFooter, st";
_instancemultistate._settile /*String*/ (_mheader,_mfooter,_states,_mvalue);
 break; }
case 10: {
 //BA.debugLineNum = 260;BA.debugLine="InstanceReadOut.Initialize(PanelWebViewSVG, Web";
_instancereadout._initialize /*String*/ (ba,_panelwebviewsvg,_webviewsvg,_meventname,_mcallback);
 //BA.debugLineNum = 261;BA.debugLine="InstanceReadOut.SetTile(mHeader, mFooter, mValu";
_instancereadout._settile /*String*/ (_mheader,_mfooter,_mvalue);
 break; }
case 11: {
 //BA.debugLineNum = 263;BA.debugLine="InstanceSelector.Initialize(PanelWebViewSVG, We";
_instanceselector._initialize /*String*/ (ba,_panelwebviewsvg,_webviewsvg,_meventname,_mcallback);
 //BA.debugLineNum = 264;BA.debugLine="InstanceSelector.SetTile(mHeader, mFooter, mVal";
_instanceselector._settile /*String*/ (_mheader,_mfooter,_mvalue);
 break; }
case 12: {
 //BA.debugLineNum = 266;BA.debugLine="InstanceSevenSegment.Initialize(PanelWebViewSVG";
_instancesevensegment._initialize /*String*/ (ba,_panelwebviewsvg,_webviewsvg,_meventname,_mcallback);
 //BA.debugLineNum = 267;BA.debugLine="InstanceSevenSegment.SetTile(mHeader, mFooter,";
_instancesevensegment._settile /*String*/ (_mheader,_mfooter,_mminvalue,_mmaxvalue,_mvalue);
 break; }
case 13: {
 //BA.debugLineNum = 269;BA.debugLine="InstanceSignal.Initialize(PanelWebViewSVG, WebV";
_instancesignal._initialize /*String*/ (ba,_panelwebviewsvg,_webviewsvg,_meventname,_mcallback);
 //BA.debugLineNum = 270;BA.debugLine="If mValue.Length = 0 Then mValue = 0";
if (_mvalue.length()==0) { 
_mvalue = BA.NumberToString(0);};
 //BA.debugLineNum = 271;BA.debugLine="InstanceSignal.SetTile(mHeader, mFooter, mValue";
_instancesignal._settile /*String*/ (_mheader,_mfooter,_mvalue);
 break; }
case 14: {
 //BA.debugLineNum = 273;BA.debugLine="InstanceSlider.Initialize(PanelWebViewSVG, WebV";
_instanceslider._initialize /*String*/ (ba,_panelwebviewsvg,_webviewsvg,_meventname,_mcallback);
 //BA.debugLineNum = 274;BA.debugLine="If mValue.Length = 0 Then mValue = 0";
if (_mvalue.length()==0) { 
_mvalue = BA.NumberToString(0);};
 //BA.debugLineNum = 275;BA.debugLine="InstanceSlider.SetTile(mHeader, mFooter, mMinVa";
_instanceslider._settile /*String*/ (_mheader,_mfooter,(int) (_mminvalue),(int) (_mmaxvalue),(int)(Double.parseDouble(_mvalue)));
 break; }
case 15: {
 //BA.debugLineNum = 277;BA.debugLine="InstanceSpinner.Initialize(PanelWebViewSVG, Web";
_instancespinner._initialize /*String*/ (ba,_panelwebviewsvg,_webviewsvg,_meventname,_mcallback);
 //BA.debugLineNum = 278;BA.debugLine="If mValue.Length = 0 Then mValue = 0";
if (_mvalue.length()==0) { 
_mvalue = BA.NumberToString(0);};
 //BA.debugLineNum = 279;BA.debugLine="InstanceSpinner.SetTile(mHeader, mFooter, mMinV";
_instancespinner._settile /*String*/ (_mheader,_mfooter,_mminvalue,_mmaxvalue,_mvalue);
 break; }
case 16: {
 //BA.debugLineNum = 281;BA.debugLine="InstanceSwitch.Initialize(PanelWebViewSVG, WebV";
_instanceswitch._initialize /*String*/ (ba,_panelwebviewsvg,_webviewsvg,_meventname,_mcallback);
 //BA.debugLineNum = 282;BA.debugLine="InstanceSwitch.SetTile(mHeader, mFooter, mState";
_instanceswitch._settile /*String*/ (_mheader,_mfooter,_mstate);
 break; }
case 17: {
 //BA.debugLineNum = 284;BA.debugLine="InstanceTrendChart.Initialize(PanelWebViewSVG,";
_instancetrendchart._initialize /*String*/ (ba,_panelwebviewsvg,_webviewsvg,_meventname,_mcallback);
 //BA.debugLineNum = 285;BA.debugLine="If mValue.Length = 0 Then mValue = 0";
if (_mvalue.length()==0) { 
_mvalue = BA.NumberToString(0);};
 //BA.debugLineNum = 286;BA.debugLine="InstanceTrendChart.SetTile(mHeader, mFooter, mV";
_instancetrendchart._settile /*String*/ (_mheader,_mfooter,_mvalue);
 break; }
case 18: {
 //BA.debugLineNum = 288;BA.debugLine="InstanceTiltGauge.Initialize(PanelWebViewSVG, W";
_instancetiltgauge._initialize /*String*/ (ba,_panelwebviewsvg,_webviewsvg,_meventname,_mcallback);
 //BA.debugLineNum = 289;BA.debugLine="If mValue.Length = 0 Then mValue = 0";
if (_mvalue.length()==0) { 
_mvalue = BA.NumberToString(0);};
 //BA.debugLineNum = 290;BA.debugLine="InstanceTiltGauge.SetTile(mHeader, mFooter, mMi";
_instancetiltgauge._settile /*String*/ (_mheader,_mfooter,_mminvalue,_mmaxvalue,(float)(Double.parseDouble(_mvalue)),_instancetiltgauge._getinverted /*boolean*/ ());
 break; }
case 19: {
 //BA.debugLineNum = 292;BA.debugLine="InstanceTimer.Initialize(PanelWebViewSVG, WebVi";
_instancetimer._initialize /*String*/ (ba,_panelwebviewsvg,_webviewsvg,_meventname,_mcallback);
 //BA.debugLineNum = 293;BA.debugLine="If mValue.Length = 0 Then mValue = 0";
if (_mvalue.length()==0) { 
_mvalue = BA.NumberToString(0);};
 //BA.debugLineNum = 294;BA.debugLine="InstanceTimer.SetTile(mHeader, mFooter, mValue)";
_instancetimer._settile /*String*/ (_mheader,_mfooter,_mvalue);
 break; }
case 20: {
 //BA.debugLineNum = 296;BA.debugLine="InstanceVerticalMeter.Initialize(PanelWebViewSV";
_instanceverticalmeter._initialize /*String*/ (ba,_panelwebviewsvg,_webviewsvg,_meventname,_mcallback);
 //BA.debugLineNum = 297;BA.debugLine="If mValue.Length = 0 Then mValue = 0";
if (_mvalue.length()==0) { 
_mvalue = BA.NumberToString(0);};
 //BA.debugLineNum = 298;BA.debugLine="InstanceVerticalMeter.SetTile(mHeader, mFooter,";
_instanceverticalmeter._settile /*String*/ (_mheader,_mfooter,"#22c55e",_mminvalue,_mmaxvalue,(float)(Double.parseDouble(_mvalue)));
 break; }
case 21: {
 //BA.debugLineNum = 300;BA.debugLine="InstanceWatchdog.Initialize(PanelWebViewSVG, We";
_instancewatchdog._initialize /*String*/ (ba,_panelwebviewsvg,_webviewsvg,_meventname,_mcallback);
 //BA.debugLineNum = 301;BA.debugLine="If mValue.Length = 0 Then mValue = 0";
if (_mvalue.length()==0) { 
_mvalue = BA.NumberToString(0);};
 //BA.debugLineNum = 302;BA.debugLine="InstanceWatchdog.SetTile(mHeader, mFooter, mVal";
_instancewatchdog._settile /*String*/ (_mheader,_mfooter,_mvalue);
 break; }
default: {
 //BA.debugLineNum = 304;BA.debugLine="Return";
if (true) return "";
 break; }
}
;
 //BA.debugLineNum = 306;BA.debugLine="End Sub";
return "";
}
public String  _panelwebviewsvg_touch(int _action,float _x,float _y) throws Exception{
Object _targetinstance = null;
b4j.example.hmitilesio._hmitouchdata _touchdata = null;
b4j.example.hmitilesio._hmitouchresult _res = null;
 //BA.debugLineNum = 760;BA.debugLine="Private Sub PanelWebViewSVG_Touch (Action As Int,";
 //BA.debugLineNum = 762;BA.debugLine="If Action = HMITilesIOUtils.ACTION_UP Then Return";
if (_action==_hmitilesioutils._action_up /*int*/ ) { 
if (true) return "";};
 //BA.debugLineNum = 764;BA.debugLine="Dim targetInstance As Object = Null";
_targetinstance = __c.Null;
 //BA.debugLineNum = 765;BA.debugLine="Dim touchData As HMITouchData";
_touchdata = new b4j.example.hmitilesio._hmitouchdata();
 //BA.debugLineNum = 768;BA.debugLine="touchData.Initialize";
_touchdata.Initialize();
 //BA.debugLineNum = 769;BA.debugLine="touchData.Action = Action";
_touchdata.Action /*int*/  = _action;
 //BA.debugLineNum = 770;BA.debugLine="touchData.X = x";
_touchdata.X /*float*/  = _x;
 //BA.debugLineNum = 771;BA.debugLine="touchData.Y = y";
_touchdata.Y /*float*/  = _y;
 //BA.debugLineNum = 772;BA.debugLine="touchData.state = mState";
_touchdata.State /*boolean*/  = _mstate;
 //BA.debugLineNum = 773;BA.debugLine="touchData.value = mValue";
_touchdata.Value /*String*/  = _mvalue;
 //BA.debugLineNum = 776;BA.debugLine="Dim targetInstance As Object = Null";
_targetinstance = __c.Null;
 //BA.debugLineNum = 779;BA.debugLine="Select mTileType";
switch (BA.switchObjectToInt(_mtiletype,_tile_battery,_tile_button,_tile_bytestatus,_tile_compass,_tile_dualreadout,_tile_gauge,_tile_iconindicator,_tile_iopanel,_tile_multistate,_tile_ledpanel,_tile_readout,_tile_selector,_tile_sevensegment,_tile_signal,_tile_slider,_tile_spinner,_tile_switch,_tile_tiltgauge,_tile_timer,_tile_trendchart,_tile_verticalmeter,_tile_watchdog)) {
case 0: {
 //BA.debugLineNum = 780;BA.debugLine="Case TILE_BATTERY:			targetInstance = InstanceBa";
_targetinstance = (Object)(_instancebattery);
 break; }
case 1: {
 //BA.debugLineNum = 781;BA.debugLine="Case TILE_BUTTON:			targetInstance = InstanceBut";
_targetinstance = (Object)(_instancebutton);
 break; }
case 2: {
 //BA.debugLineNum = 782;BA.debugLine="Case TILE_BYTESTATUS:		targetInstance = Instance";
_targetinstance = (Object)(_instancebytestatus);
 break; }
case 3: {
 //BA.debugLineNum = 783;BA.debugLine="Case TILE_COMPASS:			targetInstance = InstanceCo";
_targetinstance = (Object)(_instancecompass);
 break; }
case 4: {
 //BA.debugLineNum = 784;BA.debugLine="Case TILE_DUALREADOUT:		targetInstance = Instanc";
_targetinstance = (Object)(_instancedualreadout);
 break; }
case 5: {
 //BA.debugLineNum = 785;BA.debugLine="Case TILE_GAUGE:			targetInstance = InstanceGaug";
_targetinstance = (Object)(_instancegauge);
 break; }
case 6: {
 //BA.debugLineNum = 786;BA.debugLine="Case TILE_ICONINDICATOR:	targetInstance = Instan";
_targetinstance = (Object)(_instanceiconindicator);
 break; }
case 7: {
 //BA.debugLineNum = 787;BA.debugLine="Case TILE_IOPANEL:			targetInstance = InstanceIO";
_targetinstance = (Object)(_instanceiopanel);
 break; }
case 8: {
 //BA.debugLineNum = 788;BA.debugLine="Case TILE_MULTISTATE:		targetInstance = Instance";
_targetinstance = (Object)(_instancemultistate);
 break; }
case 9: {
 //BA.debugLineNum = 789;BA.debugLine="Case TILE_LEDPANEL:			targetInstance = InstanceL";
_targetinstance = (Object)(_instanceledpanel);
 break; }
case 10: {
 //BA.debugLineNum = 790;BA.debugLine="Case TILE_READOUT:			targetInstance = InstanceRe";
_targetinstance = (Object)(_instancereadout);
 break; }
case 11: {
 //BA.debugLineNum = 791;BA.debugLine="Case TILE_SELECTOR:			targetInstance = InstanceS";
_targetinstance = (Object)(_instanceselector);
 break; }
case 12: {
 //BA.debugLineNum = 792;BA.debugLine="Case TILE_SEVENSEGMENT:		targetInstance = Instan";
_targetinstance = (Object)(_instancesevensegment);
 break; }
case 13: {
 //BA.debugLineNum = 793;BA.debugLine="Case TILE_SIGNAL:			targetInstance = InstanceSig";
_targetinstance = (Object)(_instancesignal);
 break; }
case 14: {
 //BA.debugLineNum = 794;BA.debugLine="Case TILE_SLIDER:			targetInstance = InstanceSli";
_targetinstance = (Object)(_instanceslider);
 break; }
case 15: {
 //BA.debugLineNum = 795;BA.debugLine="Case TILE_SPINNER:			InstanceSpinner.MinValue =";
_instancespinner._setminvalue /*float*/ (_mminvalue);
 //BA.debugLineNum = 796;BA.debugLine="InstanceSpinner.MaxValue = mMaxValue";
_instancespinner._setmaxvalue /*float*/ (_mmaxvalue);
 //BA.debugLineNum = 797;BA.debugLine="targetInstance = InstanceSpinner";
_targetinstance = (Object)(_instancespinner);
 break; }
case 16: {
 //BA.debugLineNum = 798;BA.debugLine="Case TILE_SWITCH:			targetInstance = InstanceSwi";
_targetinstance = (Object)(_instanceswitch);
 break; }
case 17: {
 //BA.debugLineNum = 799;BA.debugLine="Case TILE_TILTGAUGE:		targetInstance = InstanceT";
_targetinstance = (Object)(_instancetiltgauge);
 break; }
case 18: {
 //BA.debugLineNum = 800;BA.debugLine="Case TILE_TIMER:			targetInstance = InstanceTime";
_targetinstance = (Object)(_instancetimer);
 break; }
case 19: {
 //BA.debugLineNum = 801;BA.debugLine="Case TILE_TRENDCHART:		targetInstance = Instance";
_targetinstance = (Object)(_instancetrendchart);
 break; }
case 20: {
 //BA.debugLineNum = 802;BA.debugLine="Case TILE_VERTICALMETER:	targetInstance = Instan";
_targetinstance = (Object)(_instanceverticalmeter);
 break; }
case 21: {
 //BA.debugLineNum = 803;BA.debugLine="Case TILE_WATCHDOG:			targetInstance = InstanceW";
_targetinstance = (Object)(_instancewatchdog);
 break; }
}
;
 //BA.debugLineNum = 807;BA.debugLine="If targetInstance <> Null Then";
if (_targetinstance!= null) { 
 //BA.debugLineNum = 809;BA.debugLine="Dim res As HMITouchResult = CallSub2(targetInsta";
_res = (b4j.example.hmitilesio._hmitouchresult)(__c.CallSubNew2(ba,_targetinstance,"ProcessTouchHandler",(Object)(_touchdata)));
 //BA.debugLineNum = 810;BA.debugLine="mState = res.State";
_mstate = BA.ObjectToBoolean(_res.State /*Object*/ );
 //BA.debugLineNum = 811;BA.debugLine="mValue = res.Value";
_mvalue = _res.Value /*String*/ ;
 };
 //BA.debugLineNum = 813;BA.debugLine="End Sub";
return "";
}
public void  _setbackgroundcolor(String _value) throws Exception{
ResumableSub_setBackgroundColor rsub = new ResumableSub_setBackgroundColor(this,_value);
rsub.resume(ba, null);
}
public static class ResumableSub_setBackgroundColor extends BA.ResumableSub {
public ResumableSub_setBackgroundColor(b4j.example.hmitilesio parent,String _value) {
this.parent = parent;
this._value = _value;
}
b4j.example.hmitilesio parent;
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
 //BA.debugLineNum = 666;BA.debugLine="mBackgroundColor = value";
parent._mbackgroundcolor = _value;
 //BA.debugLineNum = 670;BA.debugLine="Dim js As String = $\"         var styleId = \"b4x-";
_js = ("\n"+"        var styleId = \"b4x-dynamic-theme\";\n"+"        var styleEl = document.getElementById(styleId);\n"+"        if (!styleEl) {\n"+"            styleEl = document.createElement(\"style\");\n"+"            styleEl.id = styleId;\n"+"            document.head.appendChild(styleEl);\n"+"        };\n"+"        styleEl.innerHTML = \"html, body { background-color: "+parent.__c.SmartStringFormatter("",(Object)(parent._mbackgroundcolor))+" !important; } svg > rect:first-of-type { fill: "+parent.__c.SmartStringFormatter("",(Object)(parent._mbackgroundcolor))+" !important; }\";\n"+"    ");
 //BA.debugLineNum = 681;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(WebViewSVG, j";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._webviewsvg,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 682;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 683;BA.debugLine="Log(\"[setBackgroundColor][E] DOM not ready yet,";
parent.__c.LogImpl("914811154","[setBackgroundColor][E] DOM not ready yet, but styling rule queued.",0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 685;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _complete(boolean _result) throws Exception{
}
public void  _setfooter(String _value) throws Exception{
ResumableSub_setFooter rsub = new ResumableSub_setFooter(this,_value);
rsub.resume(ba, null);
}
public static class ResumableSub_setFooter extends BA.ResumableSub {
public ResumableSub_setFooter(b4j.example.hmitilesio parent,String _value) {
this.parent = parent;
this._value = _value;
}
b4j.example.hmitilesio parent;
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
 //BA.debugLineNum = 608;BA.debugLine="mFooter = value.Replace(\"'\", \"\\'\")";
parent._mfooter = _value.replace("'","\\'");
 //BA.debugLineNum = 609;BA.debugLine="Dim js As String = $\"         var footer = docume";
_js = ("\n"+"        var footer = document.getElementById(\"tile-footer\");\n"+"        if(footer) { footer.textContent = \""+parent.__c.SmartStringFormatter("",(Object)(parent._mfooter))+"\"; };\n"+"    ");
 //BA.debugLineNum = 614;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(WebViewSVG, j";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._webviewsvg,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 615;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 616;BA.debugLine="Log($\"[setFooter][E] Can not set the tile footer";
parent.__c.LogImpl("914417929",("[setFooter][E] Can not set the tile footer "+parent.__c.SmartStringFormatter("",(Object)(parent._mtiletype))+""),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 618;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _setfooterfontcolor(String _value) throws Exception{
ResumableSub_setFooterFontColor rsub = new ResumableSub_setFooterFontColor(this,_value);
rsub.resume(ba, null);
}
public static class ResumableSub_setFooterFontColor extends BA.ResumableSub {
public ResumableSub_setFooterFontColor(b4j.example.hmitilesio parent,String _value) {
this.parent = parent;
this._value = _value;
}
b4j.example.hmitilesio parent;
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
 //BA.debugLineNum = 646;BA.debugLine="mFooterFontColor = value";
parent._mfooterfontcolor = _value;
 //BA.debugLineNum = 647;BA.debugLine="Dim js As String = $\"         var tilefooter = do";
_js = ("\n"+"        var tilefooter = document.getElementById(\"tile-footer\");\n"+"        if (tilefooter) { tilefooter.style.fill = \""+parent.__c.SmartStringFormatter("",(Object)(_value))+"\"; }\n"+"    ");
 //BA.debugLineNum = 652;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(WebViewSVG, j";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._webviewsvg,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 653;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 654;BA.debugLine="Log($\"[setFooterFontColor][E] Can not set color";
parent.__c.LogImpl("914680073",("[setFooterFontColor][E] Can not set color "+parent.__c.SmartStringFormatter("",(Object)(parent._mfooterfontcolor))+""),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 656;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _setfooterfontsize(int _value) throws Exception{
ResumableSub_setFooterFontSize rsub = new ResumableSub_setFooterFontSize(this,_value);
rsub.resume(ba, null);
}
public static class ResumableSub_setFooterFontSize extends BA.ResumableSub {
public ResumableSub_setFooterFontSize(b4j.example.hmitilesio parent,int _value) {
this.parent = parent;
this._value = _value;
}
b4j.example.hmitilesio parent;
int _value;
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
 //BA.debugLineNum = 628;BA.debugLine="Dim js As String = $\"         var tilefooter = do";
_js = ("\n"+"        var tilefooter = document.getElementById(\"tile-footer\");\n"+"        if (tilefooter) { tilefooter.style.fontSize = \""+parent.__c.SmartStringFormatter("",(Object)(_value))+"px\"; }\n"+"    ");
 //BA.debugLineNum = 633;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(WebViewSVG, j";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._webviewsvg,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 634;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 635;BA.debugLine="Log($\"[setFooterFontSize][E] Can not set the val";
parent.__c.LogImpl("914549001",("[setFooterFontSize][E] Can not set the value font size "+parent.__c.SmartStringFormatter("",(Object)(parent._mfooterfontsize))+""),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 637;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _setheader(String _value) throws Exception{
ResumableSub_setHeader rsub = new ResumableSub_setHeader(this,_value);
rsub.resume(ba, null);
}
public static class ResumableSub_setHeader extends BA.ResumableSub {
public ResumableSub_setHeader(b4j.example.hmitilesio parent,String _value) {
this.parent = parent;
this._value = _value;
}
b4j.example.hmitilesio parent;
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
 //BA.debugLineNum = 547;BA.debugLine="mHeader = value.Replace(\"'\", \"\\'\")";
parent._mheader = _value.replace("'","\\'");
 //BA.debugLineNum = 548;BA.debugLine="Dim js As String = $\"         var header = docume";
_js = ("\n"+"        var header = document.getElementById(\"tile-header\");\n"+"        if(header) { header.textContent = \""+parent.__c.SmartStringFormatter("",(Object)(parent._mheader))+"\"; };\n"+"    ");
 //BA.debugLineNum = 553;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(WebViewSVG, j";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._webviewsvg,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 554;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 555;BA.debugLine="Log($\"[setHeader][E] Can not set the tile header";
parent.__c.LogImpl("914024713",("[setHeader][E] Can not set the tile header "+parent.__c.SmartStringFormatter("",(Object)(parent._mtiletype))+""),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 557;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _setheaderfontcolor(String _value) throws Exception{
ResumableSub_setHeaderFontColor rsub = new ResumableSub_setHeaderFontColor(this,_value);
rsub.resume(ba, null);
}
public static class ResumableSub_setHeaderFontColor extends BA.ResumableSub {
public ResumableSub_setHeaderFontColor(b4j.example.hmitilesio parent,String _value) {
this.parent = parent;
this._value = _value;
}
b4j.example.hmitilesio parent;
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
 //BA.debugLineNum = 585;BA.debugLine="mHeaderFontColor = value";
parent._mheaderfontcolor = _value;
 //BA.debugLineNum = 586;BA.debugLine="Dim js As String = $\"         var tileheader = do";
_js = ("\n"+"        var tileheader = document.getElementById(\"tile-header\");\n"+"        if (tileheader) { tileheader.style.fill = \""+parent.__c.SmartStringFormatter("",(Object)(_value))+"\"; }\n"+"    ");
 //BA.debugLineNum = 591;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(WebViewSVG, j";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._webviewsvg,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 592;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 593;BA.debugLine="Log($\"[setHeaderFontColor][E] Can not set color";
parent.__c.LogImpl("914286857",("[setHeaderFontColor][E] Can not set color "+parent.__c.SmartStringFormatter("",(Object)(parent._mheaderfontcolor))+""),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 595;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _setheaderfontsize(int _value) throws Exception{
ResumableSub_setHeaderFontSize rsub = new ResumableSub_setHeaderFontSize(this,_value);
rsub.resume(ba, null);
}
public static class ResumableSub_setHeaderFontSize extends BA.ResumableSub {
public ResumableSub_setHeaderFontSize(b4j.example.hmitilesio parent,int _value) {
this.parent = parent;
this._value = _value;
}
b4j.example.hmitilesio parent;
int _value;
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
 //BA.debugLineNum = 566;BA.debugLine="mHeaderFontSize = value";
parent._mheaderfontsize = _value;
 //BA.debugLineNum = 567;BA.debugLine="Dim js As String = $\"         var tileheader = do";
_js = ("\n"+"        var tileheader = document.getElementById(\"tile-header\");\n"+"        if (tileheader) { tileheader.style.fontSize = \""+parent.__c.SmartStringFormatter("",(Object)(_value))+"px\"; }\n"+"    ");
 //BA.debugLineNum = 572;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(WebViewSVG, j";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._webviewsvg,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 573;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 574;BA.debugLine="Log($\"[setHeaderFontSize][E] Can not set the val";
parent.__c.LogImpl("914155785",("[setHeaderFontSize][E] Can not set the value font size "+parent.__c.SmartStringFormatter("",(Object)(parent._mheaderfontsize))+""),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 576;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public String  _setitems(anywheresoftware.b4a.objects.collections.List _value) throws Exception{
 //BA.debugLineNum = 528;BA.debugLine="Public Sub setItems(value As List)";
 //BA.debugLineNum = 529;BA.debugLine="mItems = value";
_mitems = _value;
 //BA.debugLineNum = 530;BA.debugLine="Select mTileType";
switch (BA.switchObjectToInt(_mtiletype,_tile_selector)) {
case 0: {
 //BA.debugLineNum = 532;BA.debugLine="InstanceSelector.SetItems(mItems)";
_instanceselector._setitems /*boolean*/ (_mitems);
 break; }
}
;
 //BA.debugLineNum = 534;BA.debugLine="End Sub";
return "";
}
public String  _setsegmentcolor(String _segment,String _value) throws Exception{
 //BA.debugLineNum = 694;BA.debugLine="Public Sub SetSegmentColor(segment As String, valu";
 //BA.debugLineNum = 695;BA.debugLine="Select mTileType";
switch (BA.switchObjectToInt(_mtiletype,_tile_gauge)) {
case 0: {
 //BA.debugLineNum = 697;BA.debugLine="InstanceGauge.SetSegmentColor(segment, value)";
_instancegauge._setsegmentcolor /*String*/ (_segment,_value);
 break; }
}
;
 //BA.debugLineNum = 699;BA.debugLine="End Sub";
return "";
}
public void  _setstate(boolean _state) throws Exception{
ResumableSub_setState rsub = new ResumableSub_setState(this,_state);
rsub.resume(ba, null);
}
public static class ResumableSub_setState extends BA.ResumableSub {
public ResumableSub_setState(b4j.example.hmitilesio parent,boolean _state) {
this.parent = parent;
this._state = _state;
}
b4j.example.hmitilesio parent;
boolean _state;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 375;BA.debugLine="mState = state";
parent._mstate = _state;
 //BA.debugLineNum = 377;BA.debugLine="mValue = IIf(state, 1, 0)";
parent._mvalue = BA.ObjectToString(((_state) ? ((Object)(1)) : ((Object)(0))));
 //BA.debugLineNum = 380;BA.debugLine="Select mTileType";
if (true) break;

case 1:
//select
this.state = 10;
switch (BA.switchObjectToInt(parent._mtiletype,parent._tile_button,parent._tile_ledpanel,parent._tile_switch)) {
case 0: {
this.state = 3;
if (true) break;
}
case 1: {
this.state = 5;
if (true) break;
}
case 2: {
this.state = 7;
if (true) break;
}
default: {
this.state = 9;
if (true) break;
}
}
if (true) break;

case 3:
//C
this.state = 10;
 //BA.debugLineNum = 382;BA.debugLine="InstanceButton.SetTile(mHeader, mFooter, mState";
parent._instancebutton._settile /*String*/ (parent._mheader,parent._mfooter,parent._mstate);
 if (true) break;

case 5:
//C
this.state = 10;
 //BA.debugLineNum = 384;BA.debugLine="InstanceLEDPanel.SetTile(mHeader, mFooter, mSta";
parent._instanceledpanel._settile /*String*/ (parent._mheader,parent._mfooter,parent._mstate);
 if (true) break;

case 7:
//C
this.state = 10;
 //BA.debugLineNum = 386;BA.debugLine="InstanceSwitch.SetTile(mHeader, mFooter, mState";
parent._instanceswitch._settile /*String*/ (parent._mheader,parent._mfooter,parent._mstate);
 if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 388;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 390;BA.debugLine="Sleep(1)";
parent.__c.Sleep(ba,this,(int) (1));
this.state = 11;
return;
case 11:
//C
this.state = -1;
;
 //BA.debugLineNum = 391;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public String  _settiletype(String _value) throws Exception{
 //BA.debugLineNum = 357;BA.debugLine="Public Sub setTileType(value As String)";
 //BA.debugLineNum = 358;BA.debugLine="mTileType = value.ToLowerCase";
_mtiletype = _value.toLowerCase(anywheresoftware.b4a.keywords.Common.stringLocale);
 //BA.debugLineNum = 359;BA.debugLine="DrawImage";
_drawimage();
 //BA.debugLineNum = 360;BA.debugLine="End Sub";
return "";
}
public void  _setvalue(String _value) throws Exception{
ResumableSub_setValue rsub = new ResumableSub_setValue(this,_value);
rsub.resume(ba, null);
}
public static class ResumableSub_setValue extends BA.ResumableSub {
public ResumableSub_setValue(b4j.example.hmitilesio parent,String _value) {
this.parent = parent;
this._value = _value;
}
b4j.example.hmitilesio parent;
String _value;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
        switch (state) {
            case -1:
return;

case 0:
//C
this.state = 1;
 //BA.debugLineNum = 406;BA.debugLine="mValue = value";
parent._mvalue = _value;
 //BA.debugLineNum = 410;BA.debugLine="If Not(mIsReady) Then Return";
if (true) break;

case 1:
//if
this.state = 6;
if (parent.__c.Not(parent._misready)) { 
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
 //BA.debugLineNum = 413;BA.debugLine="Select mTileType";
if (true) break;

case 7:
//select
this.state = 48;
switch (BA.switchObjectToInt(parent._mtiletype,parent._tile_battery,parent._tile_bytestatus,parent._tile_compass,parent._tile_dualreadout,parent._tile_gauge,parent._tile_iconindicator,parent._tile_iopanel,parent._tile_multistate,parent._tile_readout,parent._tile_selector,parent._tile_sevensegment,parent._tile_signal,parent._tile_slider,parent._tile_spinner,parent._tile_tiltgauge,parent._tile_timer,parent._tile_trendchart,parent._tile_verticalmeter,parent._tile_watchdog)) {
case 0: {
this.state = 9;
if (true) break;
}
case 1: {
this.state = 11;
if (true) break;
}
case 2: {
this.state = 13;
if (true) break;
}
case 3: {
this.state = 15;
if (true) break;
}
case 4: {
this.state = 17;
if (true) break;
}
case 5: {
this.state = 19;
if (true) break;
}
case 6: {
this.state = 21;
if (true) break;
}
case 7: {
this.state = 23;
if (true) break;
}
case 8: {
this.state = 25;
if (true) break;
}
case 9: {
this.state = 27;
if (true) break;
}
case 10: {
this.state = 29;
if (true) break;
}
case 11: {
this.state = 31;
if (true) break;
}
case 12: {
this.state = 33;
if (true) break;
}
case 13: {
this.state = 35;
if (true) break;
}
case 14: {
this.state = 37;
if (true) break;
}
case 15: {
this.state = 39;
if (true) break;
}
case 16: {
this.state = 41;
if (true) break;
}
case 17: {
this.state = 43;
if (true) break;
}
case 18: {
this.state = 45;
if (true) break;
}
default: {
this.state = 47;
if (true) break;
}
}
if (true) break;

case 9:
//C
this.state = 48;
 //BA.debugLineNum = 415;BA.debugLine="InstanceBattery.SetTile(mHeader, mFooter, mValu";
parent._instancebattery._settile /*String*/ (parent._mheader,parent._mfooter,parent._mvalue);
 if (true) break;

case 11:
//C
this.state = 48;
 //BA.debugLineNum = 417;BA.debugLine="InstanceByteStatus.SetTile(mHeader, mFooter, In";
parent._instancebytestatus._settile /*String*/ (parent._mheader,parent._mfooter,parent._instancebytestatus._pinsattached /*byte[]*/ ,parent._mvalue);
 if (true) break;

case 13:
//C
this.state = 48;
 //BA.debugLineNum = 419;BA.debugLine="InstanceCompass.SetTile(mHeader, mFooter, mValu";
parent._instancecompass._settile /*String*/ (parent._mheader,parent._mfooter,parent._mvalue);
 if (true) break;

case 15:
//C
this.state = 48;
 //BA.debugLineNum = 421;BA.debugLine="InstanceDualReadOut.SetTile(mHeader, mFooter, m";
parent._instancedualreadout._settile /*String*/ (parent._mheader,parent._mfooter,parent._mvalue);
 if (true) break;

case 17:
//C
this.state = 48;
 //BA.debugLineNum = 423;BA.debugLine="InstanceGauge.SetTile(mHeader, mFooter, mMinVal";
parent._instancegauge._settile /*String*/ (parent._mheader,parent._mfooter,parent._mminvalue,parent._mmaxvalue,(float) (parent._mgreenmaxpct),(float) (parent._myellowmaxpct),(float)(Double.parseDouble(parent._mvalue)));
 if (true) break;

case 19:
//C
this.state = 48;
 //BA.debugLineNum = 425;BA.debugLine="InstanceIconIndicator.SetTile(mHeader, mFooter,";
parent._instanceiconindicator._settile /*String*/ (parent._mheader,parent._mfooter,parent._mvalue);
 if (true) break;

case 21:
//C
this.state = 48;
 //BA.debugLineNum = 427;BA.debugLine="InstanceIOPanel.SetTile(mHeader, mFooter, mValu";
parent._instanceiopanel._settile /*String*/ (parent._mheader,parent._mfooter,parent._mvalue);
 if (true) break;

case 23:
//C
this.state = 48;
 //BA.debugLineNum = 429;BA.debugLine="InstanceMultiState.SetTile(mHeader, mFooter, In";
parent._instancemultistate._settile /*String*/ (parent._mheader,parent._mfooter,parent._instancemultistate._getstates /*byte[]*/ (),parent._mvalue);
 if (true) break;

case 25:
//C
this.state = 48;
 //BA.debugLineNum = 431;BA.debugLine="InstanceReadOut.SetTile(mHeader, mFooter, mValu";
parent._instancereadout._settile /*String*/ (parent._mheader,parent._mfooter,parent._mvalue);
 if (true) break;

case 27:
//C
this.state = 48;
 //BA.debugLineNum = 433;BA.debugLine="InstanceSelector.SetTile(mHeader, mFooter, mVal";
parent._instanceselector._settile /*String*/ (parent._mheader,parent._mfooter,parent._mvalue);
 if (true) break;

case 29:
//C
this.state = 48;
 //BA.debugLineNum = 435;BA.debugLine="InstanceSevenSegment.SetTile(mHeader, mFooter,";
parent._instancesevensegment._settile /*String*/ (parent._mheader,parent._mfooter,parent._mminvalue,parent._mmaxvalue,parent._mvalue);
 if (true) break;

case 31:
//C
this.state = 48;
 //BA.debugLineNum = 437;BA.debugLine="InstanceSignal.SetTile(mHeader, mFooter, mValue";
parent._instancesignal._settile /*String*/ (parent._mheader,parent._mfooter,parent._mvalue);
 if (true) break;

case 33:
//C
this.state = 48;
 //BA.debugLineNum = 439;BA.debugLine="InstanceSlider.SetTile(mHeader, mFooter, mMinVa";
parent._instanceslider._settile /*String*/ (parent._mheader,parent._mfooter,(int) (parent._mminvalue),(int) (parent._mmaxvalue),(int)(Double.parseDouble(parent._mvalue)));
 if (true) break;

case 35:
//C
this.state = 48;
 //BA.debugLineNum = 441;BA.debugLine="InstanceSpinner.SetTile(mHeader, mFooter, mMinV";
parent._instancespinner._settile /*String*/ (parent._mheader,parent._mfooter,parent._mminvalue,parent._mmaxvalue,parent._mvalue);
 if (true) break;

case 37:
//C
this.state = 48;
 //BA.debugLineNum = 443;BA.debugLine="InstanceTiltGauge.SetTile(mHeader, mFooter, mMi";
parent._instancetiltgauge._settile /*String*/ (parent._mheader,parent._mfooter,parent._mminvalue,parent._mmaxvalue,(float)(Double.parseDouble(parent._mvalue)),parent._instancetiltgauge._getinverted /*boolean*/ ());
 if (true) break;

case 39:
//C
this.state = 48;
 //BA.debugLineNum = 445;BA.debugLine="InstanceTimer.SetTile(mHeader, mFooter, mValue)";
parent._instancetimer._settile /*String*/ (parent._mheader,parent._mfooter,parent._mvalue);
 if (true) break;

case 41:
//C
this.state = 48;
 //BA.debugLineNum = 447;BA.debugLine="InstanceTrendChart.SetTile(mHeader, mFooter, mV";
parent._instancetrendchart._settile /*String*/ (parent._mheader,parent._mfooter,parent._mvalue);
 if (true) break;

case 43:
//C
this.state = 48;
 //BA.debugLineNum = 449;BA.debugLine="InstanceVerticalMeter.SetTile(mHeader, mFooter,";
parent._instanceverticalmeter._settile /*String*/ (parent._mheader,parent._mfooter,parent._instanceverticalmeter._color_track /*String*/ ,parent._mminvalue,parent._mmaxvalue,(float)(Double.parseDouble(parent._mvalue)));
 if (true) break;

case 45:
//C
this.state = 48;
 //BA.debugLineNum = 451;BA.debugLine="InstanceWatchdog.SetTile(mHeader, mFooter, mVal";
parent._instancewatchdog._settile /*String*/ (parent._mheader,parent._mfooter,parent._mvalue);
 if (true) break;

case 47:
//C
this.state = 48;
 //BA.debugLineNum = 453;BA.debugLine="Return";
if (true) return ;
 if (true) break;

case 48:
//C
this.state = -1;
;
 //BA.debugLineNum = 455;BA.debugLine="Sleep(1)";
parent.__c.Sleep(ba,this,(int) (1));
this.state = 49;
return;
case 49:
//C
this.state = -1;
;
 //BA.debugLineNum = 456;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _setvaluefontcolor(String _value) throws Exception{
ResumableSub_setValueFontColor rsub = new ResumableSub_setValueFontColor(this,_value);
rsub.resume(ba, null);
}
public static class ResumableSub_setValueFontColor extends BA.ResumableSub {
public ResumableSub_setValueFontColor(b4j.example.hmitilesio parent,String _value) {
this.parent = parent;
this._value = _value;
}
b4j.example.hmitilesio parent;
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
 //BA.debugLineNum = 498;BA.debugLine="Dim js As String";
_js = "";
 //BA.debugLineNum = 500;BA.debugLine="mValueFontColor = value";
parent._mvaluefontcolor = _value;
 //BA.debugLineNum = 502;BA.debugLine="Select mTileType";
if (true) break;

case 1:
//select
this.state = 6;
switch (BA.switchObjectToInt(parent._mtiletype,parent._tile_dualreadout)) {
case 0: {
this.state = 3;
if (true) break;
}
default: {
this.state = 5;
if (true) break;
}
}
if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 504;BA.debugLine="js = $\" 					var valueleft = document.getElemen";
_js = ("\n"+"					var valueleft = document.getElementById(\"value-left\");\n"+"					var valueright = document.getElementById(\"value-right\");\n"+"					if(valueleft) { valueleft.style.fill = \""+parent.__c.SmartStringFormatter("",(Object)(_value))+"\"; };\n"+"					if(valueright) { valueright.style.fill = \""+parent.__c.SmartStringFormatter("",(Object)(_value))+"\"; };\n"+"				");
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 511;BA.debugLine="js = $\"         			var valuedisplay = document.";
_js = ("\n"+"        			var valuedisplay = document.getElementById(\"value-display\");\n"+"			        if (valuedisplay) { valuedisplay.style.fill = \""+parent.__c.SmartStringFormatter("",(Object)(_value))+"\"; }\n"+"    			");
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 518;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(WebViewSVG, j";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._webviewsvg,_js));
this.state = 11;
return;
case 11:
//C
this.state = 7;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 519;BA.debugLine="If Not(result) Then";
if (true) break;

case 7:
//if
this.state = 10;
if (parent.__c.Not(_result)) { 
this.state = 9;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 520;BA.debugLine="Log($\"[setValueFontColor][E] Can not set color $";
parent.__c.LogImpl("913762583",("[setValueFontColor][E] Can not set color "+parent.__c.SmartStringFormatter("",(Object)(parent._mvaluefontcolor))+"; js="+parent.__c.SmartStringFormatter("",(Object)(_js))+""),0);
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 522;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _setvaluefontsize(int _value) throws Exception{
ResumableSub_setValueFontSize rsub = new ResumableSub_setValueFontSize(this,_value);
rsub.resume(ba, null);
}
public static class ResumableSub_setValueFontSize extends BA.ResumableSub {
public ResumableSub_setValueFontSize(b4j.example.hmitilesio parent,int _value) {
this.parent = parent;
this._value = _value;
}
b4j.example.hmitilesio parent;
int _value;
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
 //BA.debugLineNum = 465;BA.debugLine="Dim js As String";
_js = "";
 //BA.debugLineNum = 467;BA.debugLine="mValueFontSize = value";
parent._mvaluefontsize = _value;
 //BA.debugLineNum = 470;BA.debugLine="Select mTileType";
if (true) break;

case 1:
//select
this.state = 6;
switch (BA.switchObjectToInt(parent._mtiletype,parent._tile_dualreadout)) {
case 0: {
this.state = 3;
if (true) break;
}
default: {
this.state = 5;
if (true) break;
}
}
if (true) break;

case 3:
//C
this.state = 6;
 //BA.debugLineNum = 472;BA.debugLine="js = $\" 					var valueleft = document.getElemen";
_js = ("\n"+"					var valueleft = document.getElementById(\"value-left\");\n"+"					var valueright = document.getElementById(\"value-right\");\n"+"					if(valueleft) { valueleft.style.fontSize = \""+parent.__c.SmartStringFormatter("",(Object)(_value))+"\"; };\n"+"					if(valueright) { valueright.style.fontSize = \""+parent.__c.SmartStringFormatter("",(Object)(_value))+"\"; };\n"+"				");
 if (true) break;

case 5:
//C
this.state = 6;
 //BA.debugLineNum = 479;BA.debugLine="js = $\" 		        	var valuedisplay = document.";
_js = ("\n"+"		        	var valuedisplay = document.getElementById(\"value-display\");\n"+"		        	if (valuedisplay) { valuedisplay.style.fontSize = \""+parent.__c.SmartStringFormatter("",(Object)(_value))+"px\"; }\n"+"		    	");
 if (true) break;

case 6:
//C
this.state = 7;
;
 //BA.debugLineNum = 485;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(WebViewSVG, j";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._webviewsvg,_js));
this.state = 11;
return;
case 11:
//C
this.state = 7;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 486;BA.debugLine="If Not(result) Then";
if (true) break;

case 7:
//if
this.state = 10;
if (parent.__c.Not(_result)) { 
this.state = 9;
}if (true) break;

case 9:
//C
this.state = 10;
 //BA.debugLineNum = 487;BA.debugLine="Log($\"[setValueFontSize][E] Can not set the valu";
parent.__c.LogImpl("913631511",("[setValueFontSize][E] Can not set the value font size "+parent.__c.SmartStringFormatter("",(Object)(parent._mvaluefontsize))+""),0);
 if (true) break;

case 10:
//C
this.state = -1;
;
 //BA.debugLineNum = 489;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public boolean  _webviewsvg_locationchanged(String _url) throws Exception{
int _validx = 0;
String _rawval = "";
int _sliderpercentage = 0;
 //BA.debugLineNum = 729;BA.debugLine="Private Sub WebViewSVG_LocationChanged (url As Str";
 //BA.debugLineNum = 733;BA.debugLine="If url.StartsWith(\"http://slider\") Then";
if (_url.startsWith("http://slider")) { 
 //BA.debugLineNum = 734;BA.debugLine="Dim valIdx As Int = url.IndexOf(\"val=\")";
_validx = _url.indexOf("val=");
 //BA.debugLineNum = 735;BA.debugLine="If valIdx > -1 Then";
if (_validx>-1) { 
 //BA.debugLineNum = 736;BA.debugLine="Dim rawVal As String = url.SubString(valIdx + 4";
_rawval = _url.substring((int) (_validx+4));
 //BA.debugLineNum = 739;BA.debugLine="Dim sliderPercentage As Int = Bit.ParseInt(rawV";
_sliderpercentage = __c.Bit.ParseInt(_rawval,(int) (10));
 //BA.debugLineNum = 740;BA.debugLine="mValue = sliderPercentage";
_mvalue = BA.NumberToString(_sliderpercentage);
 //BA.debugLineNum = 743;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_Clic";
if (_xui.SubExists(ba,_mcallback,_meventname+"_Click",(int) (1))) { 
 //BA.debugLineNum = 744;BA.debugLine="CallSubDelayed3(mCallBack, mEventName & \"_Clic";
__c.CallSubDelayed3(ba,_mcallback,_meventname+"_Click",(Object)(_mstate),(Object)((_mvalue)));
 };
 };
 //BA.debugLineNum = 748;BA.debugLine="Return True";
if (true) return __c.True;
 };
 //BA.debugLineNum = 750;BA.debugLine="Return False";
if (true) return __c.False;
 //BA.debugLineNum = 751;BA.debugLine="End Sub";
return false;
}
public String  _webviewsvg_pagefinished(String _url) throws Exception{
 //BA.debugLineNum = 707;BA.debugLine="Private Sub WebViewSVG_PageFinished (Url As String";
 //BA.debugLineNum = 709;BA.debugLine="setBackgroundColor(mBackgroundColor)";
_setbackgroundcolor(_mbackgroundcolor);
 //BA.debugLineNum = 711;BA.debugLine="Select mTileType";
switch (BA.switchObjectToInt(_mtiletype,_tile_slider)) {
case 0: {
 //BA.debugLineNum = 714;BA.debugLine="InstanceSlider.Init(False)";
_instanceslider._init /*String*/ (__c.False);
 break; }
}
;
 //BA.debugLineNum = 722;BA.debugLine="mIsReady = True";
_misready = __c.True;
 //BA.debugLineNum = 724;BA.debugLine="setValue(mValue)";
_setvalue(_mvalue);
 //BA.debugLineNum = 725;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
if (BA.fastSubCompare(sub, "AFTERLOADLAYOUT"))
	return _afterloadlayout((anywheresoftware.b4a.objects.collections.Map) args[0]);
return BA.SubDelegator.SubNotFound;
}
}
