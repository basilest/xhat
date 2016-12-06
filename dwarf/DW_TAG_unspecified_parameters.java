/*
 * Created on Dec 18, 2011
 *
 *
 * @author  Basile Stefano
 */
package dwarf;
/**
 * @author   Basile Stefano
 */
  public class DW_TAG_unspecified_parameters extends DWARFnode{
                                                                                  
//      int    VariableValue    = -1;

      int    DW_AT_decl_file  = -1;
      int    DW_AT_decl_line  = -1;
      int    DW_AT_artificial = -1;
      int    DW_AT_sibling    = -1;

      //#########    CONSTRUCTOR     ###########
      DW_TAG_unspecified_parameters ()
      {
        super (DWARFdef.DW_TAG_unspecified_parameters);
      }
      DW_TAG_unspecified_parameters (int type)
      {
        super (DWARFdef.DW_TAG_unspecified_parameters);
      }

//      //#########    VariableValue  set/get ###########
//      public void setVariableValue (int val)
//      {
//        VariableValue = val;
//      }
//      public int getVariableValue()
//      {
//        return VariableValue;
//      }
      //#########    toString ###########
//    public String toString()  {
//          return "";
//    }

  public  void set_AT_string ( int AT, String str) {  }

  public  String get_AT_string (int AT) { return null; }

  public  void set_AT_int ( int AT, int val) {
     switch(AT) {
        case DWARFdef.DW_AT_decl_file    :  DW_AT_decl_file   = val; break;
        case DWARFdef.DW_AT_decl_line    :  DW_AT_decl_line   = val; break;
        case DWARFdef.DW_AT_artificial   :  DW_AT_artificial  = val; break;
        case DWARFdef.DW_AT_sibling      :  DW_AT_sibling     = val; break;
        default : break;
     }
  }
  public  int get_AT_int ( int AT) {
     switch(AT) {
        case DWARFdef.DW_AT_decl_file    :  return DW_AT_decl_file ;
        case DWARFdef.DW_AT_decl_line    :  return DW_AT_decl_line ;
        case DWARFdef.DW_AT_artificial   :  return DW_AT_artificial;
        case DWARFdef.DW_AT_sibling      :  return DW_AT_sibling   ;
        default : return -1;
     }
  }
/*
      private int  getDW_AT_decl_file  () {return DW_AT_decl_file;}
      private int  getDW_AT_decl_line  () {return DW_AT_decl_line;}
      private int  getDW_AT_artificial () {return DW_AT_artificial;}
      private int  getDW_AT_sibling    () {return DW_AT_sibling;}

      private void setDW_AT_decl_file  (int val)  {DW_AT_decl_file  = val;}
      private void setDW_AT_decl_line  (int val)  {DW_AT_decl_line  = val;}
      private void setDW_AT_artificial (int val)  {DW_AT_artificial = val;}
      private void setDW_AT_sibling    (int val)  {DW_AT_sibling    = val;}
*/
  }
