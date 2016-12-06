/*
 * Created on Dec 18, 2011
 *
 *
 * @author  Basile Stefano
 */
//
//
//  <1><10df>: Abbrev Number: 21 (DW_TAG_enumeration_type)
//      DW_AT_sibling     : <1a19>
//      DW_AT_name        : (indirect string, offset: 0xf7fd): LIST_OF_ERROR
//      DW_AT_byte_size   : 4
//      DW_AT_decl_file   : 10
//      DW_AT_decl_line   : 97
//  <2><10eb>: Abbrev Number: 22 (DW_TAG_enumerator)
//      DW_AT_name        : (indirect string, offset: 0xa4e1): COMMAND_FULL_INIT_REQUIRED
//      DW_AT_const_value : 1
//  <2><10f1>: Abbrev Number: 22 (DW_TAG_enumerator)
//      DW_AT_name        : (indirect string, offset: 0x94de): DB_BACKUP_FAILED
//      DW_AT_const_value : 2
//
package dwarf;

/**
 * @author   Basile Stefano
 */

  public class DW_TAG_enumerator extends DWARFnode{
    
      int    DW_AT_decl_file   = -1;
      int    DW_AT_decl_line   = -1;
      String DW_AT_name        = null;
      int    DW_AT_const_value = -1;
      int    DW_AT_sibling     = -1;

      //#########    CONSTRUCTOR     ###########
      DW_TAG_enumerator ()
      {
        super (DWARFdef.DW_TAG_enumerator);
      }
      DW_TAG_enumerator (int type)
      {
        super (DWARFdef.DW_TAG_enumerator);
      }

//    //#########    toString ###########
//    public String toString()  {
//           String s = (DW_AT_name == null) ? "?" : DW_AT_name;
//           return  DW_AT_const_value+" ("+s+")";
//    }

  public  void set_AT_string ( int AT, String str) {
     switch(AT) {
        case DWARFdef.DW_AT_name         :  DW_AT_name  = str; break;
        default : break;
     }
  }
  public  String get_AT_string (int AT) {
     switch(AT) {
        case DWARFdef.DW_AT_name         :  return DW_AT_name;
        default : return null;
     }
  }
  public  void set_AT_int ( int AT, int val) {
     switch(AT) {
        case DWARFdef.DW_AT_decl_file    :  DW_AT_decl_file   = val; break;
        case DWARFdef.DW_AT_decl_line    :  DW_AT_decl_line   = val; break;
        case DWARFdef.DW_AT_const_value  :  DW_AT_const_value = val; break;
        case DWARFdef.DW_AT_sibling      :  DW_AT_sibling     = val; break;
        default : break;
     }
  }
  public  int get_AT_int ( int AT) {
     switch(AT) {
        case DWARFdef.DW_AT_decl_file    :  return DW_AT_decl_file   ;
        case DWARFdef.DW_AT_decl_line    :  return DW_AT_decl_line   ;
        case DWARFdef.DW_AT_const_value  :  return DW_AT_const_value ;
        case DWARFdef.DW_AT_sibling      :  return DW_AT_sibling     ;
        default : return -1;
     }
  }
/*

      private String  getDW_AT_name        () {return DW_AT_name;}
      private int     getDW_AT_decl_file   () {return DW_AT_decl_file;}
      private int     getDW_AT_decl_line   () {return DW_AT_decl_line;}
      private int     getDW_AT_const_value () {return DW_AT_const_value;}
      private int     getDW_AT_sibling     () {return DW_AT_sibling;}

      private void setDW_AT_name        (String name) {DW_AT_name        = name;}
      private void setDW_AT_decl_file   (int val)     {DW_AT_decl_file   = val;}
      private void setDW_AT_decl_line   (int val)     {DW_AT_decl_line   = val;}
      private void setDW_AT_const_value (int val)     {DW_AT_const_value = val;}
      private void setDW_AT_sibling     (int val)     {DW_AT_sibling     = val;}
*/
  }


