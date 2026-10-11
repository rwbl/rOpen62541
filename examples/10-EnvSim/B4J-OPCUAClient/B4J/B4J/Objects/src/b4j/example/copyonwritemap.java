package b4j.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;

public class copyonwritemap extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    public static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new  anywheresoftware.b4j.objects.FxBA("b4j.example", "b4j.example.copyonwritemap", this);
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            ba.htSubs = htSubs;
             
        }
        if (BA.isShellModeRuntimeCheck(ba))
                this.getClass().getMethod("_class_globals", b4j.example.copyonwritemap.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.collections.Map _internalmap = null;
public b4j.example.main _main = null;
public b4j.example.b4xpages _b4xpages = null;
public b4j.example.b4xcollections _b4xcollections = null;
public b4j.example.hmitilesioutils _hmitilesioutils = null;
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 9;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 10;BA.debugLine="Private InternalMap As Map";
_internalmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 11;BA.debugLine="End Sub";
return "";
}
public String  _clear() throws Exception{
 //BA.debugLineNum = 40;BA.debugLine="Public Sub Clear";
 //BA.debugLineNum = 41;BA.debugLine="Dim InternalMap As Map";
_internalmap = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 42;BA.debugLine="InternalMap.Initialize";
_internalmap.Initialize();
 //BA.debugLineNum = 43;BA.debugLine="End Sub";
return "";
}
public boolean  _containskey(Object _key) throws Exception{
 //BA.debugLineNum = 49;BA.debugLine="Public Sub ContainsKey (Key As Object) As Boolean";
 //BA.debugLineNum = 50;BA.debugLine="Return InternalMap.ContainsKey(Key)";
if (true) return _internalmap.ContainsKey(_key);
 //BA.debugLineNum = 51;BA.debugLine="End Sub";
return false;
}
public String  _copymap() throws Exception{
 //BA.debugLineNum = 18;BA.debugLine="Private Sub CopyMap";
 //BA.debugLineNum = 19;BA.debugLine="InternalMap = B4XCollections.MergeMaps(InternalMa";
_internalmap = _b4xcollections._mergemaps /*anywheresoftware.b4a.objects.collections.Map*/ (_internalmap,(anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(__c.Null)));
 //BA.debugLineNum = 20;BA.debugLine="End Sub";
return "";
}
public Object  _get(Object _key) throws Exception{
 //BA.debugLineNum = 22;BA.debugLine="Public Sub Get (Key As Object) As Object";
 //BA.debugLineNum = 23;BA.debugLine="Return InternalMap.Get(Key)";
if (true) return _internalmap.Get(_key);
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return null;
}
public Object  _getdefault(Object _key,Object _default) throws Exception{
 //BA.debugLineNum = 26;BA.debugLine="Public Sub GetDefault (Key As Object, Default As O";
 //BA.debugLineNum = 27;BA.debugLine="Return InternalMap.GetDefault(Key, Default)";
if (true) return _internalmap.GetDefault(_key,_default);
 //BA.debugLineNum = 28;BA.debugLine="End Sub";
return null;
}
public anywheresoftware.b4a.objects.collections.Map  _getmap() throws Exception{
 //BA.debugLineNum = 54;BA.debugLine="Public Sub GetMap As Map";
 //BA.debugLineNum = 55;BA.debugLine="Return InternalMap";
if (true) return _internalmap;
 //BA.debugLineNum = 56;BA.debugLine="End Sub";
return null;
}
public int  _getsize() throws Exception{
 //BA.debugLineNum = 45;BA.debugLine="Public Sub getSize As Int";
 //BA.debugLineNum = 46;BA.debugLine="Return InternalMap.Size";
if (true) return _internalmap.getSize();
 //BA.debugLineNum = 47;BA.debugLine="End Sub";
return 0;
}
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.collections.Map _initialitems) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 14;BA.debugLine="Public Sub Initialize (InitialItems As Map)";
 //BA.debugLineNum = 15;BA.debugLine="InternalMap = B4XCollections.MergeMaps(InitialIte";
_internalmap = _b4xcollections._mergemaps /*anywheresoftware.b4a.objects.collections.Map*/ (_initialitems,(anywheresoftware.b4a.objects.collections.Map) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.collections.Map(), (java.util.Map)(__c.Null)));
 //BA.debugLineNum = 16;BA.debugLine="End Sub";
return "";
}
public String  _put(Object _key,Object _value) throws Exception{
 //BA.debugLineNum = 30;BA.debugLine="Public Sub Put (Key As Object, Value As Object)";
 //BA.debugLineNum = 31;BA.debugLine="CopyMap";
_copymap();
 //BA.debugLineNum = 32;BA.debugLine="InternalMap.Put(Key, Value)";
_internalmap.Put(_key,_value);
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return "";
}
public String  _remove(Object _key) throws Exception{
 //BA.debugLineNum = 35;BA.debugLine="Public Sub Remove (Key As Object)";
 //BA.debugLineNum = 36;BA.debugLine="CopyMap";
_copymap();
 //BA.debugLineNum = 37;BA.debugLine="InternalMap.Remove(Key)";
_internalmap.Remove(_key);
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
