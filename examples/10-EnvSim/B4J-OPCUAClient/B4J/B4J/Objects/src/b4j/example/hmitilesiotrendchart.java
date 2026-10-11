package b4j.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;

public class hmitilesiotrendchart extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    public static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new  anywheresoftware.b4j.objects.FxBA("b4j.example", "b4j.example.hmitilesiotrendchart", this);
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            ba.htSubs = htSubs;
             
        }
        if (BA.isShellModeRuntimeCheck(ba))
                this.getClass().getMethod("_class_globals", b4j.example.hmitilesiotrendchart.class).invoke(this, new Object[] {null});
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
public double _minvalue = 0;
public double _maxvalue = 0;
public double _firstvalue = 0;
public double _lastvalue = 0;
public b4j.example.main _main = null;
public b4j.example.b4xpages _b4xpages = null;
public b4j.example.b4xcollections _b4xcollections = null;
public b4j.example.hmitilesioutils _hmitilesioutils = null;
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 15;BA.debugLine="Private Sub Class_Globals";
 //BA.debugLineNum = 16;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 22;BA.debugLine="Private mState						As Boolean";
_mstate = false;
 //BA.debugLineNum = 23;BA.debugLine="Private mValue						As String";
_mvalue = "";
 //BA.debugLineNum = 24;BA.debugLine="Private mParentPanel 				As B4XView		'ignore Loca";
_mparentpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private mWebView					As WebView		'ignore Local We";
_mwebview = new anywheresoftware.b4j.objects.WebViewWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private mEventName 					As String";
_meventname = "";
 //BA.debugLineNum = 27;BA.debugLine="Private mCallBack 					As Object";
_mcallback = new Object();
 //BA.debugLineNum = 30;BA.debugLine="Public MinValue As Double";
_minvalue = 0;
 //BA.debugLineNum = 31;BA.debugLine="Public MaxValue As Double";
_maxvalue = 0;
 //BA.debugLineNum = 32;BA.debugLine="Public FirstValue As Double";
_firstvalue = 0;
 //BA.debugLineNum = 33;BA.debugLine="Public LastValue As Double";
_lastvalue = 0;
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _pnl,anywheresoftware.b4j.objects.WebViewWrapper _wv,String _evt,Object _cb) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 37;BA.debugLine="Public Sub Initialize(pnl As B4XView, wv As WebVie";
 //BA.debugLineNum = 38;BA.debugLine="mParentPanel = pnl";
_mparentpanel = _pnl;
 //BA.debugLineNum = 39;BA.debugLine="mWebView = wv";
_mwebview = _wv;
 //BA.debugLineNum = 40;BA.debugLine="mEventName = evt";
_meventname = _evt;
 //BA.debugLineNum = 41;BA.debugLine="mCallBack = cb";
_mcallback = _cb;
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return "";
}
public b4j.example.hmitilesio._hmitouchresult  _processtouchhandler(b4j.example.hmitilesio._hmitouchdata _data) throws Exception{
b4j.example.hmitilesio._hmitouchresult _result = null;
 //BA.debugLineNum = 152;BA.debugLine="Public Sub ProcessTouchHandler(Data As HMITouchDat";
 //BA.debugLineNum = 154;BA.debugLine="mState = Data.State";
_mstate = _data.State /*boolean*/ ;
 //BA.debugLineNum = 155;BA.debugLine="mValue = Data.Value";
_mvalue = _data.Value /*String*/ ;
 //BA.debugLineNum = 158;BA.debugLine="If Data.Action = HMITilesIOUtils.ACTION_DOWN Then";
if (_data.Action /*int*/ ==_hmitilesioutils._action_down /*int*/ ) { 
 //BA.debugLineNum = 160;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_Click";
if (_xui.SubExists(ba,_mcallback,_meventname+"_Click",(int) (1))) { 
 //BA.debugLineNum = 161;BA.debugLine="CallSubDelayed3(mCallBack, mEventName & \"_Click";
__c.CallSubDelayed3(ba,_mcallback,_meventname+"_Click",(Object)(_mstate),(Object)(_mvalue));
 };
 };
 //BA.debugLineNum = 166;BA.debugLine="Dim result As HMITouchResult";
_result = new b4j.example.hmitilesio._hmitouchresult();
 //BA.debugLineNum = 167;BA.debugLine="result.Initialize";
_result.Initialize();
 //BA.debugLineNum = 168;BA.debugLine="result.State = mState";
_result.State /*Object*/  = (Object)(_mstate);
 //BA.debugLineNum = 169;BA.debugLine="result.Value = mValue";
_result.Value /*String*/  = _mvalue;
 //BA.debugLineNum = 170;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 171;BA.debugLine="End Sub";
return null;
}
public String  _settile(String _header,String _footer,String _value) throws Exception{
String _cleanvalue = "";
String[] _items = null;
int _i = 0;
double _currentval = 0;
double _range = 0;
String _escapedheader = "";
String _escapedfooter = "";
String _js_min = "";
String _js_max = "";
String _js_range = "";
String _js_last = "";
String _js = "";
 //BA.debugLineNum = 48;BA.debugLine="Public Sub SetTile(Header As String, _";
 //BA.debugLineNum = 53;BA.debugLine="Dim CleanValue As String = Value.Replace(CRLF, \"\"";
_cleanvalue = _value.replace(__c.CRLF,"").replace(BA.ObjectToString(__c.Chr((int) (10))),"").replace(BA.ObjectToString(__c.Chr((int) (13))),"").trim();
 //BA.debugLineNum = 55;BA.debugLine="If CleanValue.Length = 0 Then";
if (_cleanvalue.length()==0) { 
 //BA.debugLineNum = 57;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 59;BA.debugLine="Dim items() As String = Regex.Split(\";\", CleanVal";
_items = __c.Regex.Split(";",_cleanvalue);
 //BA.debugLineNum = 60;BA.debugLine="If items.Length = 0 Then";
if (_items.length==0) { 
 //BA.debugLineNum = 61;BA.debugLine="Log($\"[TrendChart.SetTile[E] Missing items. Chec";
__c.LogImpl("924641549",("[TrendChart.SetTile[E] Missing items. Check value."),0);
 //BA.debugLineNum = 62;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 66;BA.debugLine="FirstValue = items(0)";
_firstvalue = (double)(Double.parseDouble(_items[(int) (0)]));
 //BA.debugLineNum = 67;BA.debugLine="LastValue = items(items.Length - 1)";
_lastvalue = (double)(Double.parseDouble(_items[(int) (_items.length-1)]));
 //BA.debugLineNum = 70;BA.debugLine="MinValue = FirstValue";
_minvalue = _firstvalue;
 //BA.debugLineNum = 71;BA.debugLine="MaxValue = FirstValue";
_maxvalue = _firstvalue;
 //BA.debugLineNum = 74;BA.debugLine="For i = 0 To items.Length - 1";
{
final int step14 = 1;
final int limit14 = (int) (_items.length-1);
_i = (int) (0) ;
for (;_i <= limit14 ;_i = _i + step14 ) {
 //BA.debugLineNum = 75;BA.debugLine="Dim CurrentVal As Double = items(i)";
_currentval = (double)(Double.parseDouble(_items[_i]));
 //BA.debugLineNum = 76;BA.debugLine="If CurrentVal < MinValue Then MinValue = Current";
if (_currentval<_minvalue) { 
_minvalue = _currentval;};
 //BA.debugLineNum = 77;BA.debugLine="If CurrentVal > MaxValue Then MaxValue = Current";
if (_currentval>_maxvalue) { 
_maxvalue = _currentval;};
 }
};
 //BA.debugLineNum = 81;BA.debugLine="Dim Range As Double = MaxValue - MinValue";
_range = _maxvalue-_minvalue;
 //BA.debugLineNum = 82;BA.debugLine="If Range = 0 Then Range = 1";
if (_range==0) { 
_range = 1;};
 //BA.debugLineNum = 84;BA.debugLine="Dim EscapedHeader As String = Header.Replace(\"'\",";
_escapedheader = _header.replace("'","\\'").replace("\"","\\\"");
 //BA.debugLineNum = 85;BA.debugLine="Dim EscapedFooter As String = Footer.Replace(\"'\",";
_escapedfooter = _footer.replace("'","\\'").replace("\"","\\\"");
 //BA.debugLineNum = 89;BA.debugLine="Dim JS_Min As String = NumberFormat2(MinValue, 1,";
_js_min = __c.NumberFormat2(_minvalue,(int) (1),(int) (1),(int) (0),__c.False).replace(",",".");
 //BA.debugLineNum = 90;BA.debugLine="Dim JS_Max As String = NumberFormat2(MaxValue, 1,";
_js_max = __c.NumberFormat2(_maxvalue,(int) (1),(int) (1),(int) (0),__c.False).replace(",",".");
 //BA.debugLineNum = 91;BA.debugLine="Dim JS_Range As String = NumberFormat2(Range, 1,";
_js_range = __c.NumberFormat2(_range,(int) (1),(int) (4),(int) (0),__c.False).replace(",",".");
 //BA.debugLineNum = 92;BA.debugLine="Dim JS_Last As String = NumberFormat2(LastValue,";
_js_last = __c.NumberFormat2(_lastvalue,(int) (1),(int) (1),(int) (0),__c.False).replace(",",".");
 //BA.debugLineNum = 95;BA.debugLine="Dim js As String = $\"         var head = docum";
_js = ("\n"+"        var head = document.getElementById('tile-header');\n"+"        var foot = document.getElementById('tile-footer');\n"+"        var valTxt = document.getElementById('current-value');\n"+"        var poly = document.getElementById('sparkline-path');\n"+"        var txtMax = document.getElementById('scale-max');\n"+"        var txtMin = document.getElementById('scale-min');\n"+"\n"+"        if (head) { head.textContent = `"+__c.SmartStringFormatter("",(Object)(_escapedheader))+"`; };\n"+"        if (foot) { foot.textContent = `"+__c.SmartStringFormatter("",(Object)(_escapedfooter))+"`; };\n"+"        if (valTxt) { valTxt.textContent = parseFloat("+__c.SmartStringFormatter("",(Object)(_js_last))+").toFixed(0); };\n"+"        if (poly) {\n"+"            var rawData = `"+__c.SmartStringFormatter("",(Object)(_cleanvalue))+"`.split(';').map(Number);\n"+"            if (rawData.length > 1) {\n"+"                if (txtMax) { txtMax.textContent = parseFloat("+__c.SmartStringFormatter("",(Object)(_js_min))+") === parseFloat("+__c.SmartStringFormatter("",(Object)(_js_max))+") ? parseFloat("+__c.SmartStringFormatter("",(Object)(_js_max))+").toFixed(0) : parseFloat("+__c.SmartStringFormatter("",(Object)(_js_max))+").toFixed(0); };\n"+"                if (txtMin) { txtMin.textContent = parseFloat("+__c.SmartStringFormatter("",(Object)(_js_min))+") === parseFloat("+__c.SmartStringFormatter("",(Object)(_js_max))+") ? '0' : parseFloat("+__c.SmartStringFormatter("",(Object)(_js_min))+").toFixed(0); };\n"+"\n"+"                var graphWidth = 88;\n"+"                var graphHeight = 50;\n"+"                var startX = 27;\n"+"                var startY = 35;\n"+"                \n"+"                var pointsArray = [];\n"+"                var xStep = graphWidth / (rawData.length - 1);\n"+"                \n"+"                for (var i = 0; i < rawData.length; i++) {\n"+"                    var x = startX + (i * xStep);\n"+"                    var y = startY + graphHeight - (((rawData[i] - "+__c.SmartStringFormatter("",(Object)(_js_min))+") / "+__c.SmartStringFormatter("",(Object)(_js_range))+") * graphHeight);\n"+"                    pointsArray.push(x.toFixed(1) + ',' + y.toFixed(1));\n"+"                }\n"+"                \n"+"                poly.setAttribute('points', pointsArray.join(' '));\n"+"            } else {\n"+"                poly.setAttribute('points', '');\n"+"                if (txtMax) { txtMax.textContent = '---'; };\n"+"                if (txtMin) { txtMin.textContent = '---'; };\n"+"            }\n"+"        }\n"+"    ");
 //BA.debugLineNum = 134;BA.debugLine="UpdateTile(js)";
_updatetile(_js);
 //BA.debugLineNum = 135;BA.debugLine="End Sub";
return "";
}
public void  _updatetile(String _js) throws Exception{
ResumableSub_UpdateTile rsub = new ResumableSub_UpdateTile(this,_js);
rsub.resume(ba, null);
}
public static class ResumableSub_UpdateTile extends BA.ResumableSub {
public ResumableSub_UpdateTile(b4j.example.hmitilesiotrendchart parent,String _js) {
this.parent = parent;
this._js = _js;
}
b4j.example.hmitilesiotrendchart parent;
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
 //BA.debugLineNum = 142;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 143;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 144;BA.debugLine="Log($\"[TrendChart.UpdateTile][E] Can not update";
parent.__c.LogImpl("924707075",("[TrendChart.UpdateTile][E] Can not update the tile."),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 146;BA.debugLine="End Sub";
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
