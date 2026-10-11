package b4j.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;

public class hmitilesioverticalmeter extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    public static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new  anywheresoftware.b4j.objects.FxBA("b4j.example", "b4j.example.hmitilesioverticalmeter", this);
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            ba.htSubs = htSubs;
             
        }
        if (BA.isShellModeRuntimeCheck(ba))
                this.getClass().getMethod("_class_globals", b4j.example.hmitilesioverticalmeter.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.B4XViewWrapper.XUI _xui = null;
public String _text_color = "";
public int _text_size = 0;
public String _color_track = "";
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
 //BA.debugLineNum = 12;BA.debugLine="Private Sub Class_Globals";
 //BA.debugLineNum = 13;BA.debugLine="Private xui As XUI";
_xui = new anywheresoftware.b4a.objects.B4XViewWrapper.XUI();
 //BA.debugLineNum = 15;BA.debugLine="Public TEXT_COLOR As String = \"#0f172a\"";
_text_color = "#0f172a";
 //BA.debugLineNum = 16;BA.debugLine="Public TEXT_SIZE As Int = 24";
_text_size = (int) (24);
 //BA.debugLineNum = 17;BA.debugLine="Public COLOR_TRACK As String = \"#808080\"";
_color_track = "#808080";
 //BA.debugLineNum = 19;BA.debugLine="Private mState						As Boolean";
_mstate = false;
 //BA.debugLineNum = 20;BA.debugLine="Private mValue						As String";
_mvalue = "";
 //BA.debugLineNum = 21;BA.debugLine="Private mParentPanel 				As B4XView		'ignore Loca";
_mparentpanel = new anywheresoftware.b4a.objects.B4XViewWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private mWebView					As WebView		'ignore Local We";
_mwebview = new anywheresoftware.b4j.objects.WebViewWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private mEventName 					As String";
_meventname = "";
 //BA.debugLineNum = 24;BA.debugLine="Private mCallBack 					As Object";
_mcallback = new Object();
 //BA.debugLineNum = 26;BA.debugLine="Private mMinValue					As Float		'ignore";
_mminvalue = 0f;
 //BA.debugLineNum = 27;BA.debugLine="Private mMaxValue					As Float		'ignore";
_mmaxvalue = 0f;
 //BA.debugLineNum = 28;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.B4XViewWrapper _pnl,anywheresoftware.b4j.objects.WebViewWrapper _wv,String _evt,Object _cb) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 31;BA.debugLine="Public Sub Initialize(pnl As B4XView, wv As WebVie";
 //BA.debugLineNum = 32;BA.debugLine="mParentPanel = pnl";
_mparentpanel = _pnl;
 //BA.debugLineNum = 33;BA.debugLine="mWebView = wv";
_mwebview = _wv;
 //BA.debugLineNum = 34;BA.debugLine="mEventName = evt";
_meventname = _evt;
 //BA.debugLineNum = 35;BA.debugLine="mCallBack = cb";
_mcallback = _cb;
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return "";
}
public b4j.example.hmitilesio._hmitouchresult  _processtouchhandler(b4j.example.hmitilesio._hmitouchdata _data) throws Exception{
b4j.example.hmitilesio._hmitouchresult _result = null;
 //BA.debugLineNum = 118;BA.debugLine="Public Sub ProcessTouchHandler(Data As HMITouchDat";
 //BA.debugLineNum = 120;BA.debugLine="mState = Data.State";
_mstate = _data.State /*boolean*/ ;
 //BA.debugLineNum = 121;BA.debugLine="mValue = Data.Value";
_mvalue = _data.Value /*String*/ ;
 //BA.debugLineNum = 124;BA.debugLine="If Data.Action = HMITilesIOUtils.ACTION_DOWN Then";
if (_data.Action /*int*/ ==_hmitilesioutils._action_down /*int*/ ) { 
 //BA.debugLineNum = 126;BA.debugLine="If xui.SubExists(mCallBack, mEventName & \"_Click";
if (_xui.SubExists(ba,_mcallback,_meventname+"_Click",(int) (1))) { 
 //BA.debugLineNum = 127;BA.debugLine="CallSubDelayed3(mCallBack, mEventName & \"_Click";
__c.CallSubDelayed3(ba,_mcallback,_meventname+"_Click",(Object)(_mstate),(Object)(_mvalue));
 };
 };
 //BA.debugLineNum = 132;BA.debugLine="Dim result As HMITouchResult";
_result = new b4j.example.hmitilesio._hmitouchresult();
 //BA.debugLineNum = 133;BA.debugLine="result.Initialize";
_result.Initialize();
 //BA.debugLineNum = 134;BA.debugLine="result.State = mState";
_result.State /*Object*/  = (Object)(_mstate);
 //BA.debugLineNum = 135;BA.debugLine="result.Value = mValue";
_result.Value /*String*/  = _mvalue;
 //BA.debugLineNum = 136;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 137;BA.debugLine="End Sub";
return null;
}
public String  _settile(String _header,String _footer,String _trackcolor,float _minvalue,float _maxvalue,float _value) throws Exception{
float _totalrange = 0f;
float _pct = 0f;
float _barheight = 0f;
float _bary = 0f;
float _arrowshifty = 0f;
String _js = "";
 //BA.debugLineNum = 46;BA.debugLine="Public Sub SetTile(Header As String, _ 				   Foot";
 //BA.debugLineNum = 54;BA.debugLine="Value = Max(MinValue, Min(MaxValue, Value))";
_value = (float) (__c.Max(_minvalue,__c.Min(_maxvalue,_value)));
 //BA.debugLineNum = 56;BA.debugLine="mMinValue = MinValue";
_mminvalue = _minvalue;
 //BA.debugLineNum = 57;BA.debugLine="mMaxValue = MaxValue";
_mmaxvalue = _maxvalue;
 //BA.debugLineNum = 58;BA.debugLine="mValue = Value";
_mvalue = BA.NumberToString(_value);
 //BA.debugLineNum = 61;BA.debugLine="Dim totalRange As Float = MaxValue - MinValue";
_totalrange = (float) (_maxvalue-_minvalue);
 //BA.debugLineNum = 62;BA.debugLine="Dim pct As Float = 0";
_pct = (float) (0);
 //BA.debugLineNum = 63;BA.debugLine="If totalRange > 0 Then pct = (Value - MinValue) /";
if (_totalrange>0) { 
_pct = (float) ((_value-_minvalue)/(double)_totalrange);};
 //BA.debugLineNum = 66;BA.debugLine="Dim barHeight As Float = pct * 64.0";
_barheight = (float) (_pct*64.0);
 //BA.debugLineNum = 67;BA.debugLine="Dim barY As Float = 94.0 - barHeight";
_bary = (float) (94.0-_barheight);
 //BA.debugLineNum = 70;BA.debugLine="Dim arrowShiftY As Float = -barHeight";
_arrowshifty = (float) (-_barheight);
 //BA.debugLineNum = 73;BA.debugLine="Header = Header.Replace(\"'\", \"\\'\")";
_header = _header.replace("'","\\'");
 //BA.debugLineNum = 74;BA.debugLine="Footer = Footer.Replace(\"'\", \"\\'\")";
_footer = _footer.replace("'","\\'");
 //BA.debugLineNum = 75;BA.debugLine="TrackColor = TrackColor.Replace(\"'\", \"\\'\")";
_trackcolor = _trackcolor.replace("'","\\'");
 //BA.debugLineNum = 78;BA.debugLine="Dim js As String = $\"         var head = document";
_js = ("\n"+"        var head = document.getElementById(\"tile-header\");\n"+"        var foot = document.getElementById(\"tile-footer\");\n"+"        var vtxt = document.getElementById(\"meter-val\");\n"+"        var level = document.getElementById(\"meter-level\");\n"+"        var arrow = document.getElementById(\"meter-arrow\");\n"+"        \n"+"        if(head) { head.textContent = \""+__c.SmartStringFormatter("",(Object)(_header))+"\"; };\n"+"        if(foot) { foot.textContent = \""+__c.SmartStringFormatter("",(Object)(_footer))+"\"; };\n"+"        if(vtxt) { vtxt.textContent = \""+__c.SmartStringFormatter("",(Object)(((int) (_value))))+"\"; };\n"+"        \n"+"        if(level) { \n"+"            level.setAttribute(\"y\", \""+__c.SmartStringFormatter("",(Object)(_bary))+"\");\n"+"            level.setAttribute(\"height\", \""+__c.SmartStringFormatter("",(Object)(_barheight))+"\");\n"+"            level.setAttribute(\"fill\", \""+__c.SmartStringFormatter("",(Object)(_trackcolor))+"\");\n"+"        };\n"+"        \n"+"        if(arrow) {\n"+"            arrow.setAttribute(\"fill\", \""+__c.SmartStringFormatter("",(Object)(_trackcolor))+"\");\n"+"            arrow.setAttribute(\"transform\", \"translate(0, \" + "+__c.SmartStringFormatter("",(Object)(_arrowshifty))+" + \")\");\n"+"        };\n"+"    ");
 //BA.debugLineNum = 100;BA.debugLine="UpdateTile(js)";
_updatetile(_js);
 //BA.debugLineNum = 101;BA.debugLine="End Sub";
return "";
}
public void  _updatetile(String _js) throws Exception{
ResumableSub_UpdateTile rsub = new ResumableSub_UpdateTile(this,_js);
rsub.resume(ba, null);
}
public static class ResumableSub_UpdateTile extends BA.ResumableSub {
public ResumableSub_UpdateTile(b4j.example.hmitilesioverticalmeter parent,String _js) {
this.parent = parent;
this._js = _js;
}
b4j.example.hmitilesioverticalmeter parent;
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
 //BA.debugLineNum = 108;BA.debugLine="Wait for (HMITilesIOUtils.ExecuteJS(mWebView, js)";
parent.__c.WaitFor("complete", ba, this, parent._hmitilesioutils._executejs /*anywheresoftware.b4a.keywords.Common.ResumableSubWrapper*/ (parent._mwebview,_js));
this.state = 5;
return;
case 5:
//C
this.state = 1;
_result = (boolean) result[0];
;
 //BA.debugLineNum = 109;BA.debugLine="If Not(result) Then";
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
 //BA.debugLineNum = 110;BA.debugLine="Log($\"[VerticalMeter.UpdateTile][E] Can not upda";
parent.__c.LogImpl("917694723",("[VerticalMeter.UpdateTile][E] Can not update the tile."),0);
 if (true) break;

case 4:
//C
this.state = -1;
;
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
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
