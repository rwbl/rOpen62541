package b4j.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;

public class hmitilesiotiltgauge extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    public static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new  anywheresoftware.b4j.objects.FxBA("b4j.example", "b4j.example.hmitilesiotiltgauge", this);
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            ba.htSubs = htSubs;
             
        }
        if (BA.isShellModeRuntimeCheck(ba))
                this.getClass().getMethod("_class_globals", b4j.example.hmitilesiotiltgauge.class).invoke(this, new Object[] {null});
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
public boolean _minverted = false;
public b4j.example.main _main = null;
public b4j.example.b4xpages _b4xpages = null;
public b4j.example.b4xcollections _b4xcollections = null;
public b4j.example.hmitilesioutils _hmitilesioutils = null;
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 28;BA.debugLine="Private Sub Class_Globals";
 //BA.debugLineNum = 29;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 32;BA.debugLine="Private mState						As Boolean";
_mstate = false;
 //BA.debugLineNum = 33;BA.debugLine="Private mValue						As String";
_mvalue = "";
 //BA.debugLineNum = 34;BA.debugLine="Private mParentPanel 				As B4XView		'ignore Loca";
_mparentpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private mWebView					As WebView		'ignore Local We";
_mwebview = new anywheresoftware.b4j.objects.WebViewWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private mEventName 					As String";
_meventname = "";
 //BA.debugLineNum = 37;BA.debugLine="Private mCallBack 					As Object";
_mcallback = new Object();
 //BA.debugLineNum = 40;BA.debugLine="Private mInverted 					As Boolean = False";
_minverted = __c.False;
 //BA.debugLineNum = 41;BA.debugLine="End Sub";
return "";
}
public boolean  _getinverted() throws Exception{
 //BA.debugLineNum = 165;BA.debugLine="Public Sub getInverted As Boolean";
 //BA.debugLineNum = 166;BA.debugLine="Return mInverted";
if (true) return _minverted;
 //BA.debugLineNum = 167;BA.debugLine="End Sub";
return false;
}
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _pnl,anywheresoftware.b4j.objects.WebViewWrapper _wv,String _evt,Object _cb) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 44;BA.debugLine="Public Sub Initialize(pnl As B4XView, wv As WebVie";
 //BA.debugLineNum = 45;BA.debugLine="mParentPanel = pnl";
_mparentpanel = _pnl;
 //BA.debugLineNum = 46;BA.debugLine="mWebView = wv";
_mwebview = _wv;
 //BA.debugLineNum = 47;BA.debugLine="mEventName = evt";
_meventname = _evt;
 //BA.debugLineNum = 48;BA.debugLine="mCallBack = cb";
_mcallback = _cb;
 //BA.debugLineNum = 49;BA.debugLine="End Sub";
return "";
}
public b4j.example.hmitilesio._hmitouchresult  _processtouchhandler(b4j.example.hmitilesio._hmitouchdata _data) throws Exception{
b4j.example.hmitilesio._hmitouchresult _result = null;
 //BA.debugLineNum = 173;BA.debugLine="Public Sub ProcessTouchHandler(Data As HMITouchDat";
 //BA.debugLineNum = 175;BA.debugLine="mState = Data.State";
_mstate = _data.State /*boolean*/ ;
 //BA.debugLineNum = 176;BA.debugLine="mValue = Data.Value";
_mvalue = _data.Value /*String*/ ;
 //BA.debugLineNum = 179;BA.debugLine="If Data.Action = HMITilesIOUtils.ACTION_DOWN Then";
if (_data.Action /*int*/ ==_hmitilesioutils._action_down /*int*/ ) { 
 //BA.debugLineNum = 181;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_Click";
if (_xui.SubExists(ba,_mcallback,_meventname+"_Click",(int) (1))) { 
 //BA.debugLineNum = 182;BA.debugLine="CallSubDelayed3(mCallBack, mEventName & \"_Click";
__c.CallSubDelayed3(ba,_mcallback,_meventname+"_Click",(Object)(_mstate),(Object)(_mvalue));
 };
 };
 //BA.debugLineNum = 187;BA.debugLine="Dim result As HMITouchResult";
_result = new b4j.example.hmitilesio._hmitouchresult();
 //BA.debugLineNum = 188;BA.debugLine="result.Initialize";
_result.Initialize();
 //BA.debugLineNum = 189;BA.debugLine="result.State = mState";
_result.State /*Object*/  = (Object)(_mstate);
 //BA.debugLineNum = 190;BA.debugLine="result.Value = mValue";
_result.Value /*String*/  = _mvalue;
 //BA.debugLineNum = 191;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 192;BA.debugLine="End Sub";
return null;
}
public String  _setinverted(boolean _value) throws Exception{
 //BA.debugLineNum = 162;BA.debugLine="Public Sub setInverted(value As Boolean)";
 //BA.debugLineNum = 163;BA.debugLine="mInverted = value";
_minverted = _value;
 //BA.debugLineNum = 164;BA.debugLine="End Sub";
return "";
}
public String  _setpositionsegmentcolor(String _value) throws Exception{
String _segment = "";
String _js = "";
 //BA.debugLineNum = 141;BA.debugLine="Public Sub SetPositionSegmentColor(value As String";
 //BA.debugLineNum = 142;BA.debugLine="Dim segment As String = $\"arc-position\"$";
_segment = ("arc-position");
 //BA.debugLineNum = 143;BA.debugLine="If Not(value.StartsWith(\"#\")) Then value = $\"#${v";
if (__c.Not(_value.startsWith("#"))) { 
_value = ("#"+__c.SmartStringFormatter("",(Object)(_value))+"");};
 //BA.debugLineNum = 144;BA.debugLine="Dim js As String = $\"         var segment = docum";
_js = ("\n"+"        var segment = document.getElementById(\""+__c.SmartStringFormatter("",(Object)(_segment))+"\");\n"+"        if(segment) { segment.setAttribute(\"stroke\", \""+__c.SmartStringFormatter("",(Object)(_value))+"\"); };\n"+"    ");
 //BA.debugLineNum = 148;BA.debugLine="UpdateSegment(js)";
_updatesegment(_js);
 //BA.debugLineNum = 149;BA.debugLine="End Sub";
return "";
}
public String  _settile(String _header,String _footer,float _minvalue,float _maxvalue,float _value,boolean _inverted) throws Exception{
float _totalrange = 0f;
float _pct = 0f;
float _targetdegrees = 0f;
float _positionoffset = 0f;
String _js = "";
 //BA.debugLineNum = 63;BA.debugLine="Public Sub SetTile(Header As String, _";
 //BA.debugLineNum = 70;BA.debugLine="mInverted = Inverted";
_minverted = _inverted;
 //BA.debugLineNum = 73;BA.debugLine="Value = Max(MinValue, Min(MaxValue, Value))";
_value = (float) (__c.Max(_minvalue,__c.Min(_maxvalue,_value)));
 //BA.debugLineNum = 74;BA.debugLine="mValue = Value";
_mvalue = BA.NumberToString(_value);
 //BA.debugLineNum = 77;BA.debugLine="Dim totalRange As Float = MaxValue - MinValue";
_totalrange = (float) (_maxvalue-_minvalue);
 //BA.debugLineNum = 78;BA.debugLine="Dim pct As Float = 0";
_pct = (float) (0);
 //BA.debugLineNum = 79;BA.debugLine="If totalRange > 0 Then pct = (Value - MinValue) /";
if (_totalrange>0) { 
_pct = (float) ((_value-_minvalue)/(double)_totalrange);};
 //BA.debugLineNum = 82;BA.debugLine="If Inverted Then";
if (_inverted) { 
 //BA.debugLineNum = 83;BA.debugLine="pct = 1.0 - pct";
_pct = (float) (1.0-_pct);
 };
 //BA.debugLineNum = 89;BA.debugLine="Dim targetDegrees As Float = pct * 90.0";
_targetdegrees = (float) (_pct*90.0);
 //BA.debugLineNum = 92;BA.debugLine="Header = Header.Replace(\"'\", \"\\'\")";
_header = _header.replace("'","\\'");
 //BA.debugLineNum = 93;BA.debugLine="Footer = Footer.Replace(\"'\", \"\\'\")";
_footer = _footer.replace("'","\\'");
 //BA.debugLineNum = 96;BA.debugLine="Dim positionOffset As Float = 56.55 - (56.55 * pc";
_positionoffset = (float) (56.55-(56.55*_pct));
 //BA.debugLineNum = 99;BA.debugLine="Dim js As String = $\"         var head = document";
_js = ("\n"+"        var head = document.getElementById(\"tile-header\");\n"+"        var foot = document.getElementById(\"tile-footer\");\n"+"        var arcPos = document.getElementById(\"arc-position\");\n"+"        \n"+"        if(head) { head.textContent = \""+__c.SmartStringFormatter("",(Object)(_header))+"\"; };\n"+"        if(foot) { foot.textContent = \""+__c.SmartStringFormatter("",(Object)(_footer))+"\"; };\n"+"        if(arcPos) { arcPos.setAttribute(\"stroke-dashoffset\", \""+__c.SmartStringFormatter("",(Object)(_positionoffset))+"\"); };\n"+"        \n"+"        function applyTransform() {\n"+"            var needle = document.getElementById(\"gauge-needle\");\n"+"            if (needle) {\n"+"                needle.style.transform = \"\";\n"+"                needle.style.transformOrigin = \"\";\n"+"                needle.style.transition = \"\";\n"+"                var deg = "+__c.SmartStringFormatter("",(Object)(_targetdegrees))+";\n"+"                needle.setAttribute(\"transform\", \"rotate(\" + deg + \", 42, 78)\");\n"+"            } else {\n"+"                setTimeout(applyTransform, 20);\n"+"            }\n"+"        }\n"+"        applyTransform();\n"+"    ");
 //BA.debugLineNum = 123;BA.debugLine="UpdateTile(js)";
_updatetile(_js);
 //BA.debugLineNum = 124;BA.debugLine="End Sub";
return "";
}
public void  _updatesegment(String _js) throws Exception{
ResumableSub_UpdateSegment rsub = new ResumableSub_UpdateSegment(this,_js);
rsub.resume(ba, null);
}
public static class ResumableSub_UpdateSegment extends BA.ResumableSub {
public ResumableSub_UpdateSegment(b4j.example.hmitilesiotiltgauge parent,String _js) {
this.parent = parent;
this._js = _js;
}
b4j.example.hmitilesiotiltgauge parent;
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
 //BA.debugLineNum = 156;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 157;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 158;BA.debugLine="Log($\"[TiltGauge.UpdateSegment][E] Can not updat";
parent.__c.LogImpl("925165827",("[TiltGauge.UpdateSegment][E] Can not update the tile."),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 160;BA.debugLine="End Sub";
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
public ResumableSub_UpdateTile(b4j.example.hmitilesiotiltgauge parent,String _js) {
this.parent = parent;
this._js = _js;
}
b4j.example.hmitilesiotiltgauge parent;
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
 //BA.debugLineNum = 131;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 132;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 133;BA.debugLine="Log($\"[TiltGauge.UpdateTile][E] Can not update t";
parent.__c.LogImpl("925034755",("[TiltGauge.UpdateTile][E] Can not update the tile."),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 135;BA.debugLine="End Sub";
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
