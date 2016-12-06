/*
 * Created on Dec 18, 2011
 *
 *
 * @author  Basile Stefano
 */
//    MODIFICATORS : const, pointer, volatile


//    <1><b16e>: Abbrev Number: 38 (DW_TAG_const_type)
//        DW_AT_type        : <85>
//
//
//    <1><b173>: Abbrev Number: 12 (DW_TAG_pointer_type)
//        DW_AT_byte_size   : 4
//        DW_AT_type        : <b168>
//
//    <1><101c8>: Abbrev Number: 55 (DW_TAG_volatile_type)
//        DW_AT_type        : <4f3>
//
package dwarf;
/**
 * @author   Basile Stefano
 */
  public class DW_TAG_volatile_type extends DWARFnode{

      DWARFnode DW_AT_type_ref       = null;

     int       DW_AT_type      = -1;
     int       DW_AT_sibling   = -1;

//    //#########    toString ###########
//    public String toString()  {
//           return "volatile";
//    }

      //#########    CONSTRUCTOR     ###########
      DW_TAG_volatile_type ()
      {
        super (DWARFdef.DW_TAG_volatile_type);
      }
      DW_TAG_volatile_type (int type)
      {
        super (DWARFdef.DW_TAG_volatile_type);
      }




  public DWARFnode getDW_AT_type_ref   ()               {return DW_AT_type_ref;}
  public  void     setDW_AT_type_ref   (DWARFnode ref)  {DW_AT_type_ref = ref;}


  public  void set_AT_string ( int AT, String str) { }
  public  String get_AT_string (int AT) { return null;  }


  public  void set_AT_int ( int AT, int val) {
     switch(AT) {
        case DWARFdef.DW_AT_type    :  DW_AT_type    = val; break;
        case DWARFdef.DW_AT_sibling :  DW_AT_sibling = val; break;
        default : break;
     }
  }
  public  int get_AT_int ( int AT) {
     switch(AT) {
        case DWARFdef.DW_AT_type    :  return DW_AT_type    ;
        case DWARFdef.DW_AT_sibling :  return DW_AT_sibling ;
        default : return -1;
     }
  }

/*
     public DWARFnode getDW_AT_type_ref      (){return DW_AT_type_ref;}

     private int      getDW_AT_type         () {return DW_AT_type;}
     private int       getDW_AT_sibling   () {return DW_AT_sibling;}

     public  void setDW_AT_type_ref     (DWARFnode ref)  {DW_AT_type_ref = ref;}

     private void setDW_AT_type         (int val)        {DW_AT_type     = val;}
     private void setDW_AT_sibling      (int val)        {DW_AT_sibling  = val;}
*/
  }




