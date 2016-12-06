/*
 * Created on Dec 18, 2011
 *
 *
 * @author  Basile Stefano
 */
//  <1><b1bb>: Abbrev Number: 39 (DW_TAG_subroutine_type)
//      DW_AT_sibling     : <b1c7>
//      DW_AT_prototyped  : 1
//  <1><b1f0>: Abbrev Number: 36 (DW_TAG_subroutine_type)
//      DW_AT_sibling     : <b223>
//      DW_AT_prototyped  : 1
//      DW_AT_type        : <7a4>
//  <2><b1fa>: Abbrev Number: 37 (DW_TAG_formal_parameter)
//      DW_AT_type        : <afd4>
//  <2><b1ff>: Abbrev Number: 37 (DW_TAG_formal_parameter)
//      DW_AT_type        : <b0e5>
package dwarf;

/**
 * @author   Basile Stefano
 */
  public class DW_TAG_formal_parameter  extends DWARFnode{
    
//      int    VariableValue    = -1;

      DWARFnode DW_AT_type_ref       = null;

      int       DW_AT_decl_file          = -1;
      int       DW_AT_decl_line          = -1;
      String    DW_AT_name               = null;
      int       DW_AT_type               = -1;
      int       DW_AT_artificial         = -1;
      int       DW_AT_default_value      = 0;  // if  != 0  refer to default value
      int       DW_AT_location           = -1;
      int       DW_AT_segment            = -1;
      int       DW_AT_sibling            = -1;
      int       DW_AT_variable_parameter = -1;  // if present means that the called
                                               // can modify parameter in the caller

      //#########    CONSTRUCTOR     ###########
      DW_TAG_formal_parameter ()
      {
        super (DWARFdef.DW_TAG_formal_parameter);
      }
      DW_TAG_formal_parameter (int type)
      {
        super (DWARFdef.DW_TAG_formal_parameter);
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

//    //#########    toString ###########
//    public String toString()  {
//          return (DW_AT_name == null) ? "" : DW_AT_name;
//    }


  public DWARFnode getDW_AT_type_ref   ()               {return DW_AT_type_ref;}
  public  void     setDW_AT_type_ref   (DWARFnode ref)  {DW_AT_type_ref = ref;}




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
        case DWARFdef.DW_AT_decl_line          : DW_AT_decl_line          = val; break;
        case DWARFdef.DW_AT_type               : DW_AT_type               = val; break;
        case DWARFdef.DW_AT_artificial         : DW_AT_artificial         = val; break;
        case DWARFdef.DW_AT_default_value      : DW_AT_default_value      = val; break;
        case DWARFdef.DW_AT_location           : DW_AT_location           = val; break;
        case DWARFdef.DW_AT_segment            : DW_AT_segment            = val; break;
        case DWARFdef.DW_AT_sibling            : DW_AT_sibling            = val; break;
        case DWARFdef.DW_AT_variable_parameter : DW_AT_variable_parameter = val; break;
        default : break;
     }
  }
  public  int get_AT_int ( int AT) {
     switch(AT) {
        case DWARFdef.DW_AT_decl_file          : return DW_AT_decl_file   ;
        case DWARFdef.DW_AT_decl_line          : return DW_AT_decl_line          ;
        case DWARFdef.DW_AT_type               : return DW_AT_type               ;
        case DWARFdef.DW_AT_artificial         : return DW_AT_artificial         ;
        case DWARFdef.DW_AT_default_value      : return DW_AT_default_value      ;
        case DWARFdef.DW_AT_location           : return DW_AT_location           ;
        case DWARFdef.DW_AT_segment            : return DW_AT_segment            ;
        case DWARFdef.DW_AT_sibling            : return DW_AT_sibling            ;
        case DWARFdef.DW_AT_variable_parameter : return DW_AT_variable_parameter ;
        default : return -1;
     }
  }
/*

      public DWARFnode getDW_AT_type_ref      (){return DW_AT_type_ref;}

      private String    getDW_AT_name               () {return DW_AT_name;}
      private int       getDW_AT_type               () {return DW_AT_type;}
      private int       getDW_AT_decl_file          () {return DW_AT_decl_file;}
      private int       getDW_AT_decl_line          () {return DW_AT_decl_line;}
      private int       getDW_AT_artificial         () {return DW_AT_artificial;}
      private int       getDW_AT_default_value      () {return DW_AT_default_value;}
      private int       getDW_AT_location           () {return DW_AT_location;}
      private int       getDW_AT_segment            () {return DW_AT_segment;}
      private int       getDW_AT_sibling            () {return DW_AT_sibling;}
      private int       getDW_AT_variable_parameter () {return DW_AT_variable_parameter;}

      public  void setDW_AT_type_ref     (DWARFnode ref)  {DW_AT_type_ref = ref;}

      private void setDW_AT_name               (String name)    {DW_AT_name = name;}
      private void setDW_AT_type               (int val)        {DW_AT_type               = val;}
      private void setDW_AT_decl_file          (int val)        {DW_AT_decl_file          = val;}
      private void setDW_AT_decl_line          (int val)        {DW_AT_decl_line          = val;}
      private void setDW_AT_artificial         (int val)        {DW_AT_artificial         = val;}
      private void setDW_AT_default_value      (int val)        {DW_AT_default_value      = val;}
      private void setDW_AT_location           (int val)        {DW_AT_location           = val;}
      private void setDW_AT_segment            (int val)        {DW_AT_segment            = val;}
      private void setDW_AT_sibling            (int val)        {DW_AT_sibling            = val;}
      private void setDW_AT_variable_parameter (int val)        {DW_AT_variable_parameter = val;}
*/
  }

