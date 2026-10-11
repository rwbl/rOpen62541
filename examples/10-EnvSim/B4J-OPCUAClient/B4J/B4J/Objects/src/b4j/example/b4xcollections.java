package b4j.example;


import anywheresoftware.b4a.BA;

public class b4xcollections extends Object{
public static b4xcollections mostCurrent = new b4xcollections();

public static BA ba;
static {
		ba = new  anywheresoftware.b4j.objects.FxBA("b4j.example", "b4j.example.b4xcollections", null);
		ba.loadHtSubs(b4xcollections.class);
        if (ba.getClass().getName().endsWith("ShellBA")) {
			
			ba.raiseEvent2(null, true, "SHELL", false);
			ba.raiseEvent2(null, true, "CREATE", true, "b4j.example.b4xcollections", ba);
		}
	}
    public static Class<?> getObject() {
		return b4xcollections.class;
	}

 public static anywheresoftware.b4a.keywords.Common __c = null;
public static anywheresoftware.b4a.objects.collections.Map _memptymap = null;
public static anywheresoftware.b4a.objects.collections.List _memptylist = null;
public static b4j.example.main _main = null;
public static b4j.example.b4xpages _b4xpages = null;
public static b4j.example.hmitilesioutils _hmitilesioutils = null;
public static b4j.example.b4xbitset  _createbitset(int _size) throws Exception{
b4j.example.b4xbitset _s = null;
 //BA.debugLineNum = 53;BA.debugLine="Public Sub CreateBitSet (Size As Int) As B4XBitSet";
 //BA.debugLineNum = 54;BA.debugLine="Dim s As B4XBitSet";
_s = new b4j.example.b4xbitset();
 //BA.debugLineNum = 55;BA.debugLine="s.Initialize(Size)";
_s._initialize /*String*/ (ba,_size);
 //BA.debugLineNum = 56;BA.debugLine="Return s";
if (true) return _s;
 //BA.debugLineNum = 57;BA.debugLine="End Sub";
return null;
}
public static anywheresoftware.b4a.objects.collections.List  _createlist(anywheresoftware.b4a.objects.collections.List _items) throws Exception{
anywheresoftware.b4a.objects.collections.List _res = null;
 //BA.debugLineNum = 104;BA.debugLine="Public Sub CreateList (Items As List = Null) As Li";
 //BA.debugLineNum = 105;BA.debugLine="Dim res As List";
_res = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 106;BA.debugLine="res.Initialize";
_res.Initialize();
 //BA.debugLineNum = 107;BA.debugLine="If Initialized(Items) Then res.AddAll(Items)";
if (anywheresoftware.b4a.keywords.Common.Initialized((Object)(_items))) { 
_res.AddAll(_items);};
 //BA.debugLineNum = 108;BA.debugLine="Return res";
if (true) return _res;
 //BA.debugLineNum = 109;BA.debugLine="End Sub";
return null;
}
public static b4j.example.b4xorderedmap  _createorderedmap() throws Exception{
 //BA.debugLineNum = 31;BA.debugLine="Public Sub CreateOrderedMap As B4XOrderedMap";
 //BA.debugLineNum = 32;BA.debugLine="Return CreateOrderedMap2(Null, Null)";
if (true) return _createorderedmap2((anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(anywheresoftware.b4a.keywords.Common.Null)),(anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(anywheresoftware.b4a.keywords.Common.Null)));
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return null;
}
public static b4j.example.b4xorderedmap  _createorderedmap2(anywheresoftware.b4a.objects.collections.List _keys,anywheresoftware.b4a.objects.collections.List _values) throws Exception{
b4j.example.b4xorderedmap _m = null;
int _i = 0;
 //BA.debugLineNum = 39;BA.debugLine="Public Sub CreateOrderedMap2 (Keys As List, Values";
 //BA.debugLineNum = 40;BA.debugLine="Dim m As B4XOrderedMap";
_m = new b4j.example.b4xorderedmap();
 //BA.debugLineNum = 41;BA.debugLine="m.Initialize";
_m._initialize /*String*/ (ba);
 //BA.debugLineNum = 42;BA.debugLine="If Keys <> Null And Values <> Null And Keys.IsIni";
if (_keys!= null && _values!= null && _keys.IsInitialized() && _values.IsInitialized()) { 
 //BA.debugLineNum = 43;BA.debugLine="For i = 0 To Keys.Size - 1";
{
final int step4 = 1;
final int limit4 = (int) (_keys.getSize()-1);
_i = (int) (0) ;
for (;_i <= limit4 ;_i = _i + step4 ) {
 //BA.debugLineNum = 44;BA.debugLine="m.Put(Keys.Get(i), Values.Get(i))";
_m._put /*String*/ (_keys.Get(_i),_values.Get(_i));
 }
};
 };
 //BA.debugLineNum = 47;BA.debugLine="Return m";
if (true) return _m;
 //BA.debugLineNum = 48;BA.debugLine="End Sub";
return null;
}
public static b4j.example.b4xset  _createset() throws Exception{
 //BA.debugLineNum = 10;BA.debugLine="Public Sub CreateSet As B4XSet";
 //BA.debugLineNum = 11;BA.debugLine="Return CreateSet2(Null)";
if (true) return _createset2((anywheresoftware.b4a.objects.collections.List) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.List(), (java.util.List)(anywheresoftware.b4a.keywords.Common.Null)));
 //BA.debugLineNum = 12;BA.debugLine="End Sub";
return null;
}
public static b4j.example.b4xset  _createset2(anywheresoftware.b4a.objects.collections.List _values) throws Exception{
b4j.example.b4xset _s = null;
Object _v = null;
 //BA.debugLineNum = 17;BA.debugLine="Public Sub CreateSet2 (Values As List) As B4XSet";
 //BA.debugLineNum = 18;BA.debugLine="Dim s As B4XSet";
_s = new b4j.example.b4xset();
 //BA.debugLineNum = 19;BA.debugLine="s.Initialize";
_s._initialize /*String*/ (ba);
 //BA.debugLineNum = 20;BA.debugLine="If Values <> Null And Values.IsInitialized Then";
if (_values!= null && _values.IsInitialized()) { 
 //BA.debugLineNum = 21;BA.debugLine="For Each v As Object In Values";
{
final anywheresoftware.b4a.BA.IterableList group4 = _values;
final int groupLen4 = group4.getSize()
;int index4 = 0;
;
for (; index4 < groupLen4;index4++){
_v = group4.Get(index4);
 //BA.debugLineNum = 22;BA.debugLine="s.Add(v)";
_s._add /*String*/ (_v);
 }
};
 };
 //BA.debugLineNum = 25;BA.debugLine="Return s";
if (true) return _s;
 //BA.debugLineNum = 26;BA.debugLine="End Sub";
return null;
}
public static anywheresoftware.b4a.objects.collections.List  _getemptylist() throws Exception{
 //BA.debugLineNum = 69;BA.debugLine="Public Sub GetEmptyList As List";
 //BA.debugLineNum = 70;BA.debugLine="If mEmptyList.IsInitialized = False Or mEmptyList";
if (_memptylist.IsInitialized()==anywheresoftware.b4a.keywords.Common.False || _memptylist.getSize()>0) { 
 //BA.debugLineNum = 71;BA.debugLine="Dim mEmptyList As List";
_memptylist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 72;BA.debugLine="mEmptyList.Initialize";
_memptylist.Initialize();
 };
 //BA.debugLineNum = 74;BA.debugLine="Return mEmptyList";
if (true) return _memptylist;
 //BA.debugLineNum = 75;BA.debugLine="End Sub";
return null;
}
public static anywheresoftware.b4a.objects.collections.Map  _getemptymap() throws Exception{
 //BA.debugLineNum = 60;BA.debugLine="Public Sub GetEmptyMap As Map";
 //BA.debugLineNum = 61;BA.debugLine="If mEmptyMap.IsInitialized = False Or mEmptyMap.S";
if (_memptymap.IsInitialized()==anywheresoftware.b4a.keywords.Common.False || _memptymap.getSize()>0) { 
 //BA.debugLineNum = 62;BA.debugLine="Dim mEmptyMap As Map";
_memptymap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 63;BA.debugLine="mEmptyMap.Initialize";
_memptymap.Initialize();
 };
 //BA.debugLineNum = 65;BA.debugLine="Return mEmptyMap";
if (true) return _memptymap;
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return null;
}
public static anywheresoftware.b4a.objects.collections.List  _mergelists(anywheresoftware.b4a.objects.collections.List _list1,anywheresoftware.b4a.objects.collections.List _list2) throws Exception{
anywheresoftware.b4a.objects.collections.List _res = null;
 //BA.debugLineNum = 95;BA.debugLine="Public Sub MergeLists (List1 As List, List2 As Lis";
 //BA.debugLineNum = 96;BA.debugLine="Dim res As List";
_res = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 97;BA.debugLine="res.Initialize";
_res.Initialize();
 //BA.debugLineNum = 98;BA.debugLine="If Initialized(List1) Then res.AddAll(List1)";
if (anywheresoftware.b4a.keywords.Common.Initialized((Object)(_list1))) { 
_res.AddAll(_list1);};
 //BA.debugLineNum = 99;BA.debugLine="If Initialized(List2) Then res.AddAll(List2)";
if (anywheresoftware.b4a.keywords.Common.Initialized((Object)(_list2))) { 
_res.AddAll(_list2);};
 //BA.debugLineNum = 100;BA.debugLine="Return res";
if (true) return _res;
 //BA.debugLineNum = 101;BA.debugLine="End Sub";
return null;
}
public static anywheresoftware.b4a.objects.collections.Map  _mergemaps(anywheresoftware.b4a.objects.collections.Map _map1,anywheresoftware.b4a.objects.collections.Map _map2) throws Exception{
anywheresoftware.b4a.objects.collections.Map _res = null;
Object _key = null;
 //BA.debugLineNum = 78;BA.debugLine="Public Sub MergeMaps (Map1 As Map, Map2 As Map) As";
 //BA.debugLineNum = 79;BA.debugLine="Dim res As Map";
_res = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 80;BA.debugLine="res.Initialize";
_res.Initialize();
 //BA.debugLineNum = 81;BA.debugLine="If Initialized(Map1) Then";
if (anywheresoftware.b4a.keywords.Common.Initialized((Object)(_map1))) { 
 //BA.debugLineNum = 82;BA.debugLine="For Each key As Object In Map1.Keys";
{
final anywheresoftware.b4a.BA.IterableList group4 = _map1.Keys();
final int groupLen4 = group4.getSize()
;int index4 = 0;
;
for (; index4 < groupLen4;index4++){
_key = group4.Get(index4);
 //BA.debugLineNum = 83;BA.debugLine="res.Put(key, Map1.Get(key))";
_res.Put(_key,_map1.Get(_key));
 }
};
 };
 //BA.debugLineNum = 86;BA.debugLine="If Initialized(Map2) Then";
if (anywheresoftware.b4a.keywords.Common.Initialized((Object)(_map2))) { 
 //BA.debugLineNum = 87;BA.debugLine="For Each key As Object In Map2.Keys";
{
final anywheresoftware.b4a.BA.IterableList group9 = _map2.Keys();
final int groupLen9 = group9.getSize()
;int index9 = 0;
;
for (; index9 < groupLen9;index9++){
_key = group9.Get(index9);
 //BA.debugLineNum = 88;BA.debugLine="res.Put(key, Map2.Get(key))";
_res.Put(_key,_map2.Get(_key));
 }
};
 };
 //BA.debugLineNum = 91;BA.debugLine="Return res";
if (true) return _res;
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
return null;
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 2;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 3;BA.debugLine="Private mEmptyMap As Map";
_memptymap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 4;BA.debugLine="Private mEmptyList As List";
_memptylist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 5;BA.debugLine="End Sub";
return "";
}
public static String  _shufflelist(anywheresoftware.b4a.objects.collections.List _items) throws Exception{
int _n = 0;
int _i = 0;
int _j = 0;
Object _o = null;
 //BA.debugLineNum = 112;BA.debugLine="Public Sub ShuffleList (Items As List)";
 //BA.debugLineNum = 113;BA.debugLine="Dim n As Int = Items.Size";
_n = _items.getSize();
 //BA.debugLineNum = 114;BA.debugLine="For i = 0 To n - 2";
{
final int step2 = 1;
final int limit2 = (int) (_n-2);
_i = (int) (0) ;
for (;_i <= limit2 ;_i = _i + step2 ) {
 //BA.debugLineNum = 115;BA.debugLine="Dim j As Int = Rnd(i, n)";
_j = anywheresoftware.b4a.keywords.Common.Rnd(_i,_n);
 //BA.debugLineNum = 116;BA.debugLine="Dim o As Object = Items.Get(i)";
_o = _items.Get(_i);
 //BA.debugLineNum = 117;BA.debugLine="Items.Set(i, Items.Get(j))";
_items.Set(_i,_items.Get(_j));
 //BA.debugLineNum = 118;BA.debugLine="Items.Set(j, o)";
_items.Set(_j,_o);
 }
};
 //BA.debugLineNum = 120;BA.debugLine="End Sub";
return "";
}
public static anywheresoftware.b4a.objects.collections.List  _sublist(anywheresoftware.b4a.objects.collections.List _items,int _startindex,int _endindex) throws Exception{
anywheresoftware.b4a.objects.collections.List _res = null;
int _i = 0;
 //BA.debugLineNum = 124;BA.debugLine="Public Sub SubList (Items As List, StartIndex As I";
 //BA.debugLineNum = 125;BA.debugLine="Dim res As List";
_res = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 126;BA.debugLine="res.Initialize";
_res.Initialize();
 //BA.debugLineNum = 127;BA.debugLine="For i = StartIndex To EndIndex - 1";
{
final int step3 = 1;
final int limit3 = (int) (_endindex-1);
_i = _startindex ;
for (;_i <= limit3 ;_i = _i + step3 ) {
 //BA.debugLineNum = 128;BA.debugLine="res.Add(Items.Get(i))";
_res.Add(_items.Get(_i));
 }
};
 //BA.debugLineNum = 130;BA.debugLine="Return res";
if (true) return _res;
 //BA.debugLineNum = 131;BA.debugLine="End Sub";
return null;
}
}
