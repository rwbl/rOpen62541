package b4j.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;

public class hmitilesiotimer extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    public static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new  anywheresoftware.b4j.objects.FxBA("b4j.example", "b4j.example.hmitilesiotimer", this);
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            ba.htSubs = htSubs;
             
        }
        if (BA.isShellModeRuntimeCheck(ba))
                this.getClass().getMethod("_class_globals", b4j.example.hmitilesiotimer.class).invoke(this, new Object[] {null});
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
public long _currenttime = 0L;
public long _totaltime = 0L;
public String _date_format = "";
public anywheresoftware.b4a.objects.Timer _timerclock = null;
public long _timerclock_interval = 0L;
public b4j.example.main _main = null;
public b4j.example.b4xpages _b4xpages = null;
public b4j.example.b4xcollections _b4xcollections = null;
public b4j.example.hmitilesioutils _hmitilesioutils = null;
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 19;BA.debugLine="Private Sub Class_Globals";
 //BA.debugLineNum = 20;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
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
 //BA.debugLineNum = 36;BA.debugLine="Public CurrentTime As Long";
_currenttime = 0L;
 //BA.debugLineNum = 37;BA.debugLine="Public TotalTime As Long";
_totaltime = 0L;
 //BA.debugLineNum = 41;BA.debugLine="Private DATE_FORMAT As String = \"dd.MM.yyyy\"";
_date_format = "dd.MM.yyyy";
 //BA.debugLineNum = 43;BA.debugLine="Private TimerClock As Timer";
_timerclock = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 44;BA.debugLine="Private TIMERCLOCK_INTERVAL As Long = 1000";
_timerclock_interval = (long) (1000);
 //BA.debugLineNum = 45;BA.debugLine="End Sub";
return "";
}
public String  _getdate() throws Exception{
 //BA.debugLineNum = 180;BA.debugLine="Public Sub GetDate As String";
 //BA.debugLineNum = 181;BA.debugLine="DateTime.DateFormat = DATE_FORMAT";
__c.DateTime.setDateFormat(_date_format);
 //BA.debugLineNum = 182;BA.debugLine="Return DateTime.Date(DateTime.Now)";
if (true) return __c.DateTime.Date(__c.DateTime.getNow());
 //BA.debugLineNum = 183;BA.debugLine="End Sub";
return "";
}
public String  _gettime() throws Exception{
 //BA.debugLineNum = 176;BA.debugLine="Public Sub GetTime As String";
 //BA.debugLineNum = 177;BA.debugLine="Return DateTime.Time(DateTime.Now)";
if (true) return __c.DateTime.Time(__c.DateTime.getNow());
 //BA.debugLineNum = 178;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _pnl,anywheresoftware.b4j.objects.WebViewWrapper _wv,String _evt,Object _cb) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 48;BA.debugLine="Public Sub Initialize(pnl As B4XView, wv As WebVie";
 //BA.debugLineNum = 49;BA.debugLine="mParentPanel = pnl";
_mparentpanel = _pnl;
 //BA.debugLineNum = 50;BA.debugLine="mWebView = wv";
_mwebview = _wv;
 //BA.debugLineNum = 51;BA.debugLine="mEventName = evt";
_meventname = _evt;
 //BA.debugLineNum = 52;BA.debugLine="mCallBack = cb";
_mcallback = _cb;
 //BA.debugLineNum = 55;BA.debugLine="TimerClock.Initialize(\"TimerClock\", TIMERCLOCK_IN";
_timerclock.Initialize(ba,"TimerClock",_timerclock_interval);
 //BA.debugLineNum = 56;BA.debugLine="TimerClock.Enabled = False";
_timerclock.setEnabled(__c.False);
 //BA.debugLineNum = 57;BA.debugLine="End Sub";
return "";
}
public b4j.example.hmitilesio._hmitouchresult  _processtouchhandler(b4j.example.hmitilesio._hmitouchdata _data) throws Exception{
b4j.example.hmitilesio._hmitouchresult _result = null;
 //BA.debugLineNum = 229;BA.debugLine="Public Sub ProcessTouchHandler(Data As HMITouchDat";
 //BA.debugLineNum = 231;BA.debugLine="mState = Data.State";
_mstate = _data.State /*boolean*/ ;
 //BA.debugLineNum = 232;BA.debugLine="mValue = Data.Value";
_mvalue = _data.Value /*String*/ ;
 //BA.debugLineNum = 235;BA.debugLine="If Data.Action = HMITilesIOUtils.ACTION_DOWN Then";
if (_data.Action /*int*/ ==_hmitilesioutils._action_down /*int*/ ) { 
 //BA.debugLineNum = 237;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_Click";
if (_xui.SubExists(ba,_mcallback,_meventname+"_Click",(int) (1))) { 
 //BA.debugLineNum = 238;BA.debugLine="CallSubDelayed3(mCallBack, mEventName & \"_Click";
__c.CallSubDelayed3(ba,_mcallback,_meventname+"_Click",(Object)(_mstate),(Object)(_mvalue));
 };
 };
 //BA.debugLineNum = 243;BA.debugLine="Dim result As HMITouchResult";
_result = new b4j.example.hmitilesio._hmitouchresult();
 //BA.debugLineNum = 244;BA.debugLine="result.Initialize";
_result.Initialize();
 //BA.debugLineNum = 245;BA.debugLine="result.State = mState";
_result.State /*Object*/  = (Object)(_mstate);
 //BA.debugLineNum = 246;BA.debugLine="result.Value = mValue";
_result.Value /*String*/  = _mvalue;
 //BA.debugLineNum = 247;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 248;BA.debugLine="End Sub";
return null;
}
public String  _settile(String _header,String _footer,String _value) throws Exception{
String _cleanvalue = "";
String _displaytext = "";
double _progresspct = 0;
boolean _showring = false;
String[] _parts = null;
int _hours = 0;
int _minutes = 0;
int _seconds = 0;
String _escapedheader = "";
String _escapedfooter = "";
String _js_pct = "";
String _js_showring = "";
String _js = "";
 //BA.debugLineNum = 65;BA.debugLine="Public Sub SetTile(Header As String, _";
 //BA.debugLineNum = 69;BA.debugLine="Dim CleanValue As String = Value.Replace(CRLF, \"\"";
_cleanvalue = _value.replace(__c.CRLF,"").replace(BA.ObjectToString(__c.Chr((int) (10))),"").replace(BA.ObjectToString(__c.Chr((int) (13))),"").trim();
 //BA.debugLineNum = 71;BA.debugLine="If CleanValue.Length = 0 Then";
if (_cleanvalue.length()==0) { 
 //BA.debugLineNum = 72;BA.debugLine="Log($\"[IOTimer.SetTile[E] Missing items. Check v";
__c.LogImpl("923920647",("[IOTimer.SetTile[E] Missing items. Check value."),0);
 //BA.debugLineNum = 73;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 76;BA.debugLine="Dim DisplayText As String = CleanValue";
_displaytext = _cleanvalue;
 //BA.debugLineNum = 77;BA.debugLine="Dim ProgressPct As Double = 0";
_progresspct = 0;
 //BA.debugLineNum = 78;BA.debugLine="Dim ShowRing As Boolean = False";
_showring = __c.False;
 //BA.debugLineNum = 80;BA.debugLine="CurrentTime = 0";
_currenttime = (long) (0);
 //BA.debugLineNum = 81;BA.debugLine="TotalTime = 0";
_totaltime = (long) (0);
 //BA.debugLineNum = 83;BA.debugLine="If CleanValue.Contains(\";\") Then";
if (_cleanvalue.contains(";")) { 
 //BA.debugLineNum = 85;BA.debugLine="ShowRing = True";
_showring = __c.True;
 //BA.debugLineNum = 86;BA.debugLine="Dim Parts() As String = Regex.Split(\";\", CleanVa";
_parts = __c.Regex.Split(";",_cleanvalue);
 //BA.debugLineNum = 87;BA.debugLine="If Parts.Length >= 2 Then";
if (_parts.length>=2) { 
 //BA.debugLineNum = 88;BA.debugLine="Try";
try { //BA.debugLineNum = 89;BA.debugLine="CurrentTime = Parts(0)";
_currenttime = (long)(Double.parseDouble(_parts[(int) (0)]));
 //BA.debugLineNum = 90;BA.debugLine="TotalTime = Parts(1)";
_totaltime = (long)(Double.parseDouble(_parts[(int) (1)]));
 } 
       catch (Exception e19) {
			ba.setLastException(e19); //BA.debugLineNum = 92;BA.debugLine="Log(\"[HMITilesIO.Timer] Error parsing numbers:";
__c.LogImpl("923920667","[HMITilesIO.Timer] Error parsing numbers: "+_cleanvalue,0);
 };
 //BA.debugLineNum = 95;BA.debugLine="If TotalTime > 0 Then";
if (_totaltime>0) { 
 //BA.debugLineNum = 96;BA.debugLine="ProgressPct = (CurrentTime / TotalTime) * 100";
_progresspct = (_currenttime/(double)_totaltime)*100;
 };
 //BA.debugLineNum = 100;BA.debugLine="If CurrentTime < 86400 And TotalTime > 120 Then";
if (_currenttime<86400 && _totaltime>120) { 
 //BA.debugLineNum = 101;BA.debugLine="Dim Hours As Int = CurrentTime / 3600";
_hours = (int) (_currenttime/(double)3600);
 //BA.debugLineNum = 102;BA.debugLine="Dim Minutes As Int = (CurrentTime Mod 3600) /";
_minutes = (int) ((_currenttime%3600)/(double)60);
 //BA.debugLineNum = 103;BA.debugLine="Dim Seconds As Int = CurrentTime Mod 60";
_seconds = (int) (_currenttime%60);
 //BA.debugLineNum = 104;BA.debugLine="DisplayText = NumberFormat(Hours, 2, 0) & \":\"";
_displaytext = __c.NumberFormat(_hours,(int) (2),(int) (0))+":"+__c.NumberFormat(_minutes,(int) (2),(int) (0))+":"+__c.NumberFormat(_seconds,(int) (2),(int) (0));
 }else {
 //BA.debugLineNum = 106;BA.debugLine="DisplayText = Parts(0)";
_displaytext = _parts[(int) (0)];
 };
 };
 }else {
 //BA.debugLineNum = 111;BA.debugLine="ShowRing = False";
_showring = __c.False;
 };
 //BA.debugLineNum = 114;BA.debugLine="If ProgressPct < 0 Then ProgressPct = 0";
if (_progresspct<0) { 
_progresspct = 0;};
 //BA.debugLineNum = 115;BA.debugLine="If ProgressPct > 100 Then ProgressPct = 100";
if (_progresspct>100) { 
_progresspct = 100;};
 //BA.debugLineNum = 117;BA.debugLine="Dim EscapedHeader As String = Header.Replace(\"'\",";
_escapedheader = _header.replace("'","\\'").replace("\"","\\\"");
 //BA.debugLineNum = 118;BA.debugLine="Dim EscapedFooter As String = Footer.Replace(\"'\",";
_escapedfooter = _footer.replace("'","\\'").replace("\"","\\\"");
 //BA.debugLineNum = 119;BA.debugLine="Dim JS_Pct As String = NumberFormat2(ProgressPct,";
_js_pct = __c.NumberFormat2(_progresspct,(int) (1),(int) (1),(int) (0),__c.False).replace(",",".");
 //BA.debugLineNum = 120;BA.debugLine="Dim JS_ShowRing As String = \"none\"";
_js_showring = "none";
 //BA.debugLineNum = 121;BA.debugLine="If ShowRing Then JS_ShowRing = \"inline\"";
if (_showring) { 
_js_showring = "inline";};
 //BA.debugLineNum = 124;BA.debugLine="Dim js As String = $\"         var head = document";
_js = ("\n"+"        var head = document.getElementById('tile-header');\n"+"        var foot = document.getElementById('tile-footer');\n"+"        var valTxt = document.getElementById('current-value');\n"+"        var ring = document.getElementById('timer-ring');\n"+"        var track = ring ? ring.previousElementSibling : null;\n"+"\n"+"        if (head) { head.textContent = `"+__c.SmartStringFormatter("",(Object)(_escapedheader))+"`; };\n"+"        if (foot) { foot.textContent = `"+__c.SmartStringFormatter("",(Object)(_escapedfooter))+"`; };\n"+"        if (valTxt) { \n"+"            valTxt.textContent = `"+__c.SmartStringFormatter("",(Object)(_displaytext))+"`; \n"+"            if (`"+__c.SmartStringFormatter("",(Object)(_displaytext))+"`.length > 3) {\n"+"                valTxt.setAttribute('font-size', '24'); \n"+"            } else {\n"+"                valTxt.setAttribute('font-size', '24'); \n"+"            }\n"+"        };\n"+"\n"+"        if (ring) {\n"+"            ring.style.display = `"+__c.SmartStringFormatter("",(Object)(_js_showring))+"`;\n"+"            if (track) { track.style.display = `"+__c.SmartStringFormatter("",(Object)(_js_showring))+"`; }\n"+"            \n"+"            var pct = parseFloat(`"+__c.SmartStringFormatter("",(Object)(_js_pct))+"`); \n"+"            var circumference = 188.5; \n"+"            var offset = circumference - ((pct / 100) * circumference);\n"+"            \n"+"            ring.setAttribute('stroke-dashoffset', offset.toFixed(1));\n"+"            \n"+"            if (pct >= 90) {\n"+"                ring.setAttribute('stroke', '#ef4444');\n"+"            } else if (pct >= 75) {\n"+"                ring.setAttribute('stroke', '#f59e0b');\n"+"            } else {\n"+"                ring.setAttribute('stroke', '#3b82f6');\n"+"            }\n"+"        }\n"+"    ");
 //BA.debugLineNum = 162;BA.debugLine="UpdateTile(js)";
_updatetile(_js);
 //BA.debugLineNum = 163;BA.debugLine="End Sub";
return "";
}
public String  _startclock() throws Exception{
 //BA.debugLineNum = 186;BA.debugLine="Public Sub StartClock";
 //BA.debugLineNum = 187;BA.debugLine="TimerClock.Enabled = True";
_timerclock.setEnabled(__c.True);
 //BA.debugLineNum = 188;BA.debugLine="End Sub";
return "";
}
public String  _stopclock() throws Exception{
 //BA.debugLineNum = 190;BA.debugLine="Public Sub StopClock";
 //BA.debugLineNum = 191;BA.debugLine="TimerClock.Enabled = False";
_timerclock.setEnabled(__c.False);
 //BA.debugLineNum = 192;BA.debugLine="End Sub";
return "";
}
public String  _timerclock_tick() throws Exception{
String _value = "";
String _footer = "";
String _js = "";
 //BA.debugLineNum = 194;BA.debugLine="Private Sub TimerClock_Tick";
 //BA.debugLineNum = 195;BA.debugLine="Dim value As String = GetTime";
_value = _gettime();
 //BA.debugLineNum = 196;BA.debugLine="Dim footer As String = GetDate";
_footer = _getdate();
 //BA.debugLineNum = 198;BA.debugLine="Dim js As String = $\"         var foot = document";
_js = ("\n"+"        var foot = document.getElementById('tile-footer');\n"+"        var currentvalue = document.getElementById('current-value');\n"+"\n"+"        if (foot) { foot.textContent = `"+__c.SmartStringFormatter("",(Object)(_footer))+"`; };\n"+"        if (currentvalue) { \n"+"            currentvalue.textContent = `"+__c.SmartStringFormatter("",(Object)(_value))+"`; \n"+"            currentvalue.setAttribute('font-size', '24'); \n"+"        };\n"+"    ");
 //BA.debugLineNum = 209;BA.debugLine="UpdateClock(js)";
_updateclock(_js);
 //BA.debugLineNum = 210;BA.debugLine="End Sub";
return "";
}
public void  _updateclock(String _js) throws Exception{
ResumableSub_UpdateClock rsub = new ResumableSub_UpdateClock(this,_js);
rsub.resume(ba, null);
}
public static class ResumableSub_UpdateClock extends BA.ResumableSub {
public ResumableSub_UpdateClock(b4j.example.hmitilesiotimer parent,String _js) {
this.parent = parent;
this._js = _js;
}
b4j.example.hmitilesiotimer parent;
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
 //BA.debugLineNum = 217;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 218;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 219;BA.debugLine="Log($\"[Timer.UpdateClock][E] Can not update the";
parent.__c.LogImpl("924379395",("[Timer.UpdateClock][E] Can not update the tile."),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 221;BA.debugLine="End Sub";
if (true) break;

            }
        }
    }
}
public void  _complete(boolean _result) throws Exception{
}
public void  _updatetile(String _js) throws Exception{
ResumableSub_UpdateTile rsub = new ResumableSub_UpdateTile(this,_js);
rsub.resume(ba, null);
}
public static class ResumableSub_UpdateTile extends BA.ResumableSub {
public ResumableSub_UpdateTile(b4j.example.hmitilesiotimer parent,String _js) {
this.parent = parent;
this._js = _js;
}
b4j.example.hmitilesiotimer parent;
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
 //BA.debugLineNum = 170;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 171;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 172;BA.debugLine="Log($\"[Timer.UpdateTile][E] Can not update the t";
parent.__c.LogImpl("923986179",("[Timer.UpdateTile][E] Can not update the tile."),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 174;BA.debugLine="End Sub";
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
