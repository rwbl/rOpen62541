package b4j.example;


import anywheresoftware.b4a.BA;

public class hmitilesioutils extends Object{
public static hmitilesioutils mostCurrent = new hmitilesioutils();

public static BA ba;
static {
		ba = new  anywheresoftware.b4j.objects.FxBA("b4j.example", "b4j.example.hmitilesioutils", null);
		ba.loadHtSubs(hmitilesioutils.class);
        if (ba.getClass().getName().endsWith("ShellBA")) {
			
			ba.raiseEvent2(null, true, "SHELL", false);
			ba.raiseEvent2(null, true, "CREATE", true, "b4j.example.hmitilesioutils", ba);
		}
	}
    public static Class<?> getObject() {
		return hmitilesioutils.class;
	}

 public static anywheresoftware.b4a.keywords.Common __c = null;
public static int _action_down = 0;
public static int _action_up = 0;
public static int _action_move = 0;
public static String _display_style_block = "";
public static String _display_style_none = "";
public static b4j.example.main _main = null;
public static b4j.example.b4xpages _b4xpages = null;
public static b4j.example.b4xcollections _b4xcollections = null;
public static String  _bytetobin(byte _b) throws Exception{
anywheresoftware.b4a.keywords.StringBuilderWrapper _sb = null;
int _i = 0;
 //BA.debugLineNum = 145;BA.debugLine="Public Sub ByteToBin(b As Byte) As String";
 //BA.debugLineNum = 146;BA.debugLine="Dim sb As StringBuilder";
_sb = new anywheresoftware.b4a.keywords.StringBuilderWrapper();
 //BA.debugLineNum = 147;BA.debugLine="sb.Initialize";
_sb.Initialize();
 //BA.debugLineNum = 148;BA.debugLine="For i = 7 To 0 Step -1";
{
final int step3 = -1;
final int limit3 = (int) (0);
_i = (int) (7) ;
for (;_i >= limit3 ;_i = _i + step3 ) {
 //BA.debugLineNum = 149;BA.debugLine="sb.Append(IIf(GetBit(b, i), \"1\", \"0\"))";
_sb.Append(BA.ObjectToString(((_getbit(_b,_i)) ? ((Object)("1")) : ((Object)("0")))));
 }
};
 //BA.debugLineNum = 151;BA.debugLine="Return sb.ToString";
if (true) return _sb.ToString();
 //BA.debugLineNum = 152;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.keywords.Common.ResumableSubWrapper  _executejs(anywheresoftware.b4j.objects.WebViewWrapper _wv,String _js) throws Exception{
ResumableSub_ExecuteJS rsub = new ResumableSub_ExecuteJS(null,_wv,_js);
rsub.resume(ba, null);
return (anywheresoftware.b4a.keywords.Common.ResumableSubWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.keywords.Common.ResumableSubWrapper(), rsub);
}
public static class ResumableSub_ExecuteJS extends BA.ResumableSub {
public ResumableSub_ExecuteJS(b4j.example.hmitilesioutils parent,anywheresoftware.b4j.objects.WebViewWrapper _wv,String _js) {
this.parent = parent;
this._wv = _wv;
this._js = _js;
}
b4j.example.hmitilesioutils parent;
anywheresoftware.b4j.objects.WebViewWrapper _wv;
String _js;
anywheresoftware.b4j.object.JavaObject _jowebview = null;
anywheresoftware.b4j.object.JavaObject _engine = null;

@Override
public void resume(BA ba, Object[] result) throws Exception{

    while (true) {
try {

        switch (state) {
            case -1:
{
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,null);return;}
case 0:
//C
this.state = 1;
 //BA.debugLineNum = 35;BA.debugLine="If js.Length == 0 Then";
if (true) break;

case 1:
//if
this.state = 4;
if (_js.length()==0) { 
this.state = 3;
}if (true) break;

case 3:
//C
this.state = 4;
 //BA.debugLineNum = 36;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;

case 4:
//C
this.state = 5;
;
 //BA.debugLineNum = 40;BA.debugLine="Dim js As String = $\" 		(() => { 			const runScri";
_js = ("\n"+"		(() => {\n"+"			const runScript = () => {\n"+"				try {\n"+"					"+anywheresoftware.b4a.keywords.Common.SmartStringFormatter("",(Object)(_js))+"\n"+"				} catch(e) {\n"+"					console.error('HMITile DOM Error:', e);\n"+"				}\n"+"			};\n"+"			if (document && document.readyState === 'complete') {\n"+"				runScript();\n"+"			} else {\n"+"				window.addEventListener('load', runScript);\n"+"			}\n"+"		})();\n"+"	");
 //BA.debugLineNum = 58;BA.debugLine="js = js.Replace(CRLF, \" \").Replace(Chr(10), \" \").";
_js = _js.replace(anywheresoftware.b4a.keywords.Common.CRLF," ").replace(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (10)))," ").replace(BA.ObjectToString(anywheresoftware.b4a.keywords.Common.Chr((int) (13)))," ");
 //BA.debugLineNum = 62;BA.debugLine="Sleep(50)";
anywheresoftware.b4a.keywords.Common.Sleep(ba,this,(int) (50));
this.state = 11;
return;
case 11:
//C
this.state = 5;
;
 //BA.debugLineNum = 65;BA.debugLine="Dim joWebView As JavaObject = wv";
_jowebview = new anywheresoftware.b4j.object.JavaObject();
_jowebview = (anywheresoftware.b4j.object.JavaObject) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4j.object.JavaObject(), (java.lang.Object)(_wv.getObject()));
 //BA.debugLineNum = 68;BA.debugLine="Try";
if (true) break;

case 5:
//try
this.state = 10;
this.catchState = 9;
this.state = 7;
if (true) break;

case 7:
//C
this.state = 10;
this.catchState = 9;
 //BA.debugLineNum = 77;BA.debugLine="Dim engine As JavaObject = joWebView.RunMethodJO";
_engine = new anywheresoftware.b4j.object.JavaObject();
_engine = _jowebview.RunMethodJO("getEngine",(Object[])(anywheresoftware.b4a.keywords.Common.Null));
 //BA.debugLineNum = 78;BA.debugLine="engine.RunMethod(\"executeScript\", Array(js))";
_engine.RunMethod("executeScript",new Object[]{(Object)(_js)});
 //BA.debugLineNum = 81;BA.debugLine="Return True";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.True));return;};
 if (true) break;

case 9:
//C
this.state = 10;
this.catchState = 0;
 //BA.debugLineNum = 83;BA.debugLine="Log(\"[Utils.ExecuteJS][E] \" & LastException.Mess";
anywheresoftware.b4a.keywords.Common.LogImpl("921692466","[Utils.ExecuteJS][E] "+anywheresoftware.b4a.keywords.Common.LastException(ba).getMessage(),0);
 //BA.debugLineNum = 84;BA.debugLine="Return False";
if (true) {
anywheresoftware.b4a.keywords.Common.ReturnFromResumableSub(this,(Object)(anywheresoftware.b4a.keywords.Common.False));return;};
 if (true) break;
if (true) break;

case 10:
//C
this.state = -1;
this.catchState = 0;
;
 //BA.debugLineNum = 86;BA.debugLine="End Sub";
if (true) break;
}} 
       catch (Exception e0) {
			
if (catchState == 0)
    throw e0;
else {
    state = catchState;
ba.setLastException(e0);}
            }
        }
    }
}
public static boolean  _getbit(byte _b,int _bitpos) throws Exception{
boolean _result = false;
 //BA.debugLineNum = 114;BA.debugLine="Public Sub GetBit(b As Byte, bitpos As Int) As Boo";
 //BA.debugLineNum = 115;BA.debugLine="Dim Result As Boolean = False";
_result = anywheresoftware.b4a.keywords.Common.False;
 //BA.debugLineNum = 116;BA.debugLine="Select bitpos";
switch (_bitpos) {
case 0: {
 //BA.debugLineNum = 118;BA.debugLine="Result = Bit.And(b, 1) = 1";
_result = anywheresoftware.b4a.keywords.Common.Bit.And((int) (_b),(int) (1))==1;
 break; }
case 1: {
 //BA.debugLineNum = 120;BA.debugLine="Result = Bit.And(b, 2) = 2";
_result = anywheresoftware.b4a.keywords.Common.Bit.And((int) (_b),(int) (2))==2;
 break; }
case 2: {
 //BA.debugLineNum = 122;BA.debugLine="Result = Bit.And(b, 4) = 4";
_result = anywheresoftware.b4a.keywords.Common.Bit.And((int) (_b),(int) (4))==4;
 break; }
case 3: {
 //BA.debugLineNum = 124;BA.debugLine="Result = Bit.And(b, 8) = 8";
_result = anywheresoftware.b4a.keywords.Common.Bit.And((int) (_b),(int) (8))==8;
 break; }
case 4: {
 //BA.debugLineNum = 126;BA.debugLine="Result = Bit.And(b, 18) = 16";
_result = anywheresoftware.b4a.keywords.Common.Bit.And((int) (_b),(int) (18))==16;
 break; }
case 5: {
 //BA.debugLineNum = 128;BA.debugLine="Result = Bit.And(b, 32) = 32";
_result = anywheresoftware.b4a.keywords.Common.Bit.And((int) (_b),(int) (32))==32;
 break; }
case 6: {
 //BA.debugLineNum = 130;BA.debugLine="Result = Bit.And(b, 64) = 64";
_result = anywheresoftware.b4a.keywords.Common.Bit.And((int) (_b),(int) (64))==64;
 break; }
case 7: {
 //BA.debugLineNum = 132;BA.debugLine="Result = Bit.And(b, 128) = 128";
_result = anywheresoftware.b4a.keywords.Common.Bit.And((int) (_b),(int) (128))==128;
 break; }
}
;
 //BA.debugLineNum = 134;BA.debugLine="Return Result";
if (true) return _result;
 //BA.debugLineNum = 135;BA.debugLine="End Sub";
return false;
}
public static boolean[]  _getbitarray(byte _b) throws Exception{
boolean[] _result = null;
int _i = 0;
 //BA.debugLineNum = 98;BA.debugLine="Public Sub GetBitArray(b As Byte) As Boolean()	'ig";
 //BA.debugLineNum = 99;BA.debugLine="Dim result(8) As Boolean";
_result = new boolean[(int) (8)];
;
 //BA.debugLineNum = 100;BA.debugLine="For i = 0 To 7";
{
final int step2 = 1;
final int limit2 = (int) (7);
_i = (int) (0) ;
for (;_i <= limit2 ;_i = _i + step2 ) {
 //BA.debugLineNum = 101;BA.debugLine="result(i) = GetBit(b, i)";
_result[_i] = _getbit(_b,_i);
 }
};
 //BA.debugLineNum = 103;BA.debugLine="Return result";
if (true) return _result;
 //BA.debugLineNum = 104;BA.debugLine="End Sub";
return null;
}
public static String  _getlimitcolor(float _currentvalue,float _warninglimit,float _alarmlimit) throws Exception{
 //BA.debugLineNum = 168;BA.debugLine="Public Sub GetLimitColor(CurrentValue As Float, Wa";
 //BA.debugLineNum = 169;BA.debugLine="If CurrentValue >= AlarmLimit Then";
if (_currentvalue>=_alarmlimit) { 
 //BA.debugLineNum = 170;BA.debugLine="Return \"#dc2626\" ' Level 4: Critical Alarm Red";
if (true) return "#dc2626";
 }else if(_currentvalue>=_warninglimit) { 
 //BA.debugLineNum = 172;BA.debugLine="Return \"#d97706\" ' Level 3: Warning Amber";
if (true) return "#d97706";
 }else {
 //BA.debugLineNum = 174;BA.debugLine="Return \"#0f172a\" ' Level 2: Normal Black/Dark Sl";
if (true) return "#0f172a";
 };
 //BA.debugLineNum = 176;BA.debugLine="End Sub";
return "";
}
public static String  _getstatecolor(int _state) throws Exception{
 //BA.debugLineNum = 159;BA.debugLine="Public Sub GetStateColor(State As Int) As String	'";
 //BA.debugLineNum = 160;BA.debugLine="Select State";
switch (_state) {
case 1: {
 //BA.debugLineNum = 161;BA.debugLine="Case 1: 	Return \"#20bf6b\" 	' Muted Green (Runnin";
if (true) return "#20bf6b";
 break; }
case 2: {
 //BA.debugLineNum = 162;BA.debugLine="Case 2: 	Return \"#eb3b5a\" 	' Muted Red (Alarm)";
if (true) return "#eb3b5a";
 break; }
default: {
 //BA.debugLineNum = 163;BA.debugLine="Case Else: Return \"#4b6584\"		' Muted Slate (Off)";
if (true) return "#4b6584";
 break; }
}
;
 //BA.debugLineNum = 165;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 13;BA.debugLine="Public ACTION_DOWN			As Int = 0";
_action_down = (int) (0);
 //BA.debugLineNum = 14;BA.debugLine="Public ACTION_UP 			As Int = 1";
_action_up = (int) (1);
 //BA.debugLineNum = 15;BA.debugLine="Public ACTION_MOVE 			As Int = 2";
_action_move = (int) (2);
 //BA.debugLineNum = 18;BA.debugLine="Public DISPLAY_STYLE_BLOCK	As String = \"block\"";
_display_style_block = "block";
 //BA.debugLineNum = 19;BA.debugLine="Public DISPLAY_STYLE_NONE 	As String = \"none\"";
_display_style_none = "none";
 //BA.debugLineNum = 22;BA.debugLine="End Sub";
return "";
}
public static byte  _setbit(byte _b,int _index,boolean _value) throws Exception{
 //BA.debugLineNum = 106;BA.debugLine="Public Sub SetBit(b As Byte, index As Int, value A";
 //BA.debugLineNum = 107;BA.debugLine="If value Then";
if (_value) { 
 //BA.debugLineNum = 108;BA.debugLine="Return Bit.Or(b, Bit.ShiftLeft(1, index))";
if (true) return (byte) (anywheresoftware.b4a.keywords.Common.Bit.Or((int) (_b),anywheresoftware.b4a.keywords.Common.Bit.ShiftLeft((int) (1),_index)));
 }else {
 //BA.debugLineNum = 110;BA.debugLine="Return Bit.And(b, Bit.Not(Bit.ShiftLeft(1, index";
if (true) return (byte) (anywheresoftware.b4a.keywords.Common.Bit.And((int) (_b),anywheresoftware.b4a.keywords.Common.Bit.Not(anywheresoftware.b4a.keywords.Common.Bit.ShiftLeft((int) (1),_index))));
 };
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
return (byte)0;
}
}
