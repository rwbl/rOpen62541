package b4j.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;

public class copyonwritelist extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    public static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new  anywheresoftware.b4j.objects.FxBA("b4j.example", "b4j.example.copyonwritelist", this);
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            ba.htSubs = htSubs;
             
        }
        if (BA.isShellModeRuntimeCheck(ba))
                this.getClass().getMethod("_class_globals", b4j.example.copyonwritelist.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.collections.List _internallist = null;
public b4j.example.main _main = null;
public b4j.example.b4xpages _b4xpages = null;
public b4j.example.b4xcollections _b4xcollections = null;
public b4j.example.hmitilesioutils _hmitilesioutils = null;
public String  _add(Object _item) throws Exception{
 //BA.debugLineNum = 16;BA.debugLine="Public Sub Add (Item As Object)";
 //BA.debugLineNum = 17;BA.debugLine="MakeCopy";
_makecopy();
 //BA.debugLineNum = 18;BA.debugLine="InternalList.Add(Item)";
_internallist.Add(_item);
 //BA.debugLineNum = 19;BA.debugLine="End Sub";
return "";
}
public String  _addall(anywheresoftware.b4a.objects.collections.List _items) throws Exception{
 //BA.debugLineNum = 49;BA.debugLine="Public Sub AddAll (Items As List)";
 //BA.debugLineNum = 50;BA.debugLine="MakeCopy";
_makecopy();
 //BA.debugLineNum = 51;BA.debugLine="InternalList.AddAll(Items)";
_internallist.AddAll(_items);
 //BA.debugLineNum = 52;BA.debugLine="End Sub";
return "";
}
public String  _addallat(int _index,anywheresoftware.b4a.objects.collections.List _items) throws Exception{
 //BA.debugLineNum = 54;BA.debugLine="Public Sub AddAllAt (Index As Int, Items As List)";
 //BA.debugLineNum = 55;BA.debugLine="MakeCopy";
_makecopy();
 //BA.debugLineNum = 56;BA.debugLine="InternalList.AddAllAt(Index, Items)";
_internallist.AddAllAt(_index,_items);
 //BA.debugLineNum = 57;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 4;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 5;BA.debugLine="Private InternalList As List";
_internallist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 6;BA.debugLine="End Sub";
return "";
}
public String  _clear() throws Exception{
 //BA.debugLineNum = 26;BA.debugLine="Public Sub Clear";
 //BA.debugLineNum = 27;BA.debugLine="Dim InternalList As List";
_internallist = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 28;BA.debugLine="InternalList.Initialize";
_internallist.Initialize();
 //BA.debugLineNum = 29;BA.debugLine="End Sub";
return "";
}
public boolean  _get(int _index) throws Exception{
 //BA.debugLineNum = 31;BA.debugLine="Public Sub Get (Index As Int) As Boolean";
 //BA.debugLineNum = 32;BA.debugLine="Return InternalList.Get(Index)";
if (true) return BA.ObjectToBoolean(_internallist.Get(_index));
 //BA.debugLineNum = 33;BA.debugLine="End Sub";
return false;
}
public anywheresoftware.b4a.objects.collections.List  _getlist() throws Exception{
 //BA.debugLineNum = 84;BA.debugLine="Public Sub GetList As List";
 //BA.debugLineNum = 85;BA.debugLine="Return InternalList";
if (true) return _internallist;
 //BA.debugLineNum = 86;BA.debugLine="End Sub";
return null;
}
public int  _getsize() throws Exception{
 //BA.debugLineNum = 40;BA.debugLine="Public Sub getSize As Int";
 //BA.debugLineNum = 41;BA.debugLine="Return InternalList.Size";
if (true) return _internallist.getSize();
 //BA.debugLineNum = 42;BA.debugLine="End Sub";
return 0;
}
public int  _indexof(Object _item) throws Exception{
 //BA.debugLineNum = 59;BA.debugLine="Public Sub IndexOf (Item As Object) As Int";
 //BA.debugLineNum = 60;BA.debugLine="Return InternalList.IndexOf(Item)";
if (true) return _internallist.IndexOf(_item);
 //BA.debugLineNum = 61;BA.debugLine="End Sub";
return 0;
}
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.collections.List _initialitems) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 8;BA.debugLine="Public Sub Initialize (InitialItems As List)";
 //BA.debugLineNum = 9;BA.debugLine="InternalList = B4XCollections.CreateList(InitialI";
_internallist = _b4xcollections._createlist /*anywheresoftware.b4a.objects.collections.List*/ (_initialitems);
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public String  _insertat(int _index,Object _item) throws Exception{
 //BA.debugLineNum = 44;BA.debugLine="Public Sub InsertAt (Index As Int, Item As Object)";
 //BA.debugLineNum = 45;BA.debugLine="MakeCopy";
_makecopy();
 //BA.debugLineNum = 46;BA.debugLine="InternalList.InsertAt(Index, Item)";
_internallist.InsertAt(_index,_item);
 //BA.debugLineNum = 47;BA.debugLine="End Sub";
return "";
}
public String  _makecopy() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Private Sub MakeCopy";
 //BA.debugLineNum = 13;BA.debugLine="InternalList = B4XCollections.CreateList(Internal";
_internallist = _b4xcollections._createlist /*anywheresoftware.b4a.objects.collections.List*/ (_internallist);
 //BA.debugLineNum = 14;BA.debugLine="End Sub";
return "";
}
public String  _removeat(int _index) throws Exception{
 //BA.debugLineNum = 21;BA.debugLine="Public Sub RemoveAt (Index As Int)";
 //BA.debugLineNum = 22;BA.debugLine="MakeCopy";
_makecopy();
 //BA.debugLineNum = 23;BA.debugLine="InternalList.RemoveAt(Index)";
_internallist.RemoveAt(_index);
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public String  _set(int _index,Object _item) throws Exception{
 //BA.debugLineNum = 35;BA.debugLine="Public Sub Set (Index As Int, Item As Object)";
 //BA.debugLineNum = 36;BA.debugLine="MakeCopy";
_makecopy();
 //BA.debugLineNum = 37;BA.debugLine="InternalList.Set(Index, Item)";
_internallist.Set(_index,_item);
 //BA.debugLineNum = 38;BA.debugLine="End Sub";
return "";
}
public String  _sort(boolean _ascending) throws Exception{
 //BA.debugLineNum = 63;BA.debugLine="Public Sub Sort (Ascending As Boolean)";
 //BA.debugLineNum = 64;BA.debugLine="MakeCopy";
_makecopy();
 //BA.debugLineNum = 65;BA.debugLine="InternalList.Sort(Ascending)";
_internallist.Sort(_ascending);
 //BA.debugLineNum = 66;BA.debugLine="End Sub";
return "";
}
public String  _sortcaseinsensitive(boolean _ascending) throws Exception{
 //BA.debugLineNum = 68;BA.debugLine="Public Sub SortCaseInsensitive (Ascending As Boole";
 //BA.debugLineNum = 69;BA.debugLine="MakeCopy";
_makecopy();
 //BA.debugLineNum = 70;BA.debugLine="InternalList.SortCaseInsensitive(Ascending)";
_internallist.SortCaseInsensitive(_ascending);
 //BA.debugLineNum = 71;BA.debugLine="End Sub";
return "";
}
public String  _sorttype(String _fieldname,boolean _ascending) throws Exception{
 //BA.debugLineNum = 73;BA.debugLine="Public Sub SortType (FieldName As String, Ascendin";
 //BA.debugLineNum = 74;BA.debugLine="MakeCopy";
_makecopy();
 //BA.debugLineNum = 75;BA.debugLine="InternalList.SortType(FieldName, Ascending)";
_internallist.SortType(_fieldname,_ascending);
 //BA.debugLineNum = 76;BA.debugLine="End Sub";
return "";
}
public String  _sorttypecaseinsensitive(String _fieldname,boolean _ascending) throws Exception{
 //BA.debugLineNum = 78;BA.debugLine="Public Sub SortTypeCaseInsensitive (FieldName As S";
 //BA.debugLineNum = 79;BA.debugLine="MakeCopy";
_makecopy();
 //BA.debugLineNum = 80;BA.debugLine="InternalList.SortTypeCaseInsensitive(FieldName, A";
_internallist.SortTypeCaseInsensitive(_fieldname,_ascending);
 //BA.debugLineNum = 81;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
