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
  public class DW_TAG_constant  extends DWARFnode{

      DWARFnode DW_AT_type_ref       = null;

      int       DW_AT_decl_file      = -1;
      int       DW_AT_decl_line      = -1;
      String    DW_AT_name           = null;
      int       DW_AT_type           = -1;
      int       DW_AT_constant_value = -1;
      int       DW_AT_declaration    = -1;
      int       DW_AT_external       = -1;
      int       DW_AT_sibling        = -1;
      int       DW_AT_start_scope    = -1;
      int       DW_AT_visibility     = -1;

      //#########    CONSTRUCTOR     ###########
      DW_TAG_constant ()
      {
        super (DWARFdef.DW_TAG_constant);
      }
      DW_TAG_constant (int type)
      {
        super (DWARFdef.DW_TAG_constant);
      }

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
        default : return "";
     }
  }
  public  void set_AT_int ( int AT, int val) {
     switch(AT) {
        case DWARFdef.DW_AT_decl_file      : DW_AT_decl_file      = val; break;
        case DWARFdef.DW_AT_decl_line      : DW_AT_decl_line      = val; break;
        case DWARFdef.DW_AT_type           : DW_AT_type           = val; break;
        case DWARFdef.DW_AT_constant_value : DW_AT_constant_value = val; break;
        case DWARFdef.DW_AT_declaration    : DW_AT_declaration    = val; break;
        case DWARFdef.DW_AT_external       : DW_AT_external       = val; break;
        case DWARFdef.DW_AT_sibling        : DW_AT_sibling        = val; break;
        case DWARFdef.DW_AT_start_scope    : DW_AT_start_scope    = val; break;
        case DWARFdef.DW_AT_visibility     : DW_AT_visibility     = val; break;
        default : break;
     }
  }
  public  int get_AT_int ( int AT) {
     switch(AT) {
        case DWARFdef.DW_AT_decl_file      : return DW_AT_decl_file   ;
        case DWARFdef.DW_AT_decl_line      : return DW_AT_decl_line     ;
        case DWARFdef.DW_AT_type           : return DW_AT_type          ;
        case DWARFdef.DW_AT_constant_value : return DW_AT_constant_value;
        case DWARFdef.DW_AT_declaration    : return DW_AT_declaration   ;
        case DWARFdef.DW_AT_external       : return DW_AT_external      ;
        case DWARFdef.DW_AT_sibling        : return DW_AT_sibling       ;
        case DWARFdef.DW_AT_start_scope    : return DW_AT_start_scope   ;
        case DWARFdef.DW_AT_visibility     : return DW_AT_visibility    ;
        default : return -1;
     }
  }
/*

      public DWARFnode getDW_AT_type_ref        (){return DW_AT_type_ref;}

      private String    getDW_AT_name           () {return DW_AT_name;}
      private int       getDW_AT_type           () {return DW_AT_type;}
      private int       getDW_AT_decl_file      () {return DW_AT_decl_file;}
      private int       getDW_AT_decl_line      () {return DW_AT_decl_line;}
      private int       getDW_AT_constant_value () {return DW_AT_constant_value;}
      private int       getDW_AT_declaration    () {return DW_AT_declaration;}
      private int       getDW_AT_external       () {return DW_AT_external;}
      private int       getDW_AT_sibling        () {return DW_AT_sibling;}
      private int       getDW_AT_start_scope    () {return DW_AT_start_scope;}
      private int       getDW_AT_visibility     () {return DW_AT_visibility;}

      public  void setDW_AT_type_ref     (DWARFnode ref)  {DW_AT_type_ref = ref;}

      private void setDW_AT_name           (String name)    {DW_AT_name           = name;}
      private void setDW_AT_type           (int val)        {DW_AT_type           = val;}
      private void setDW_AT_decl_file      (int val)        {DW_AT_decl_file      = val;}
      private void setDW_AT_decl_line      (int val)        {DW_AT_decl_line      = val;}
      private void setDW_AT_constant_value (int val)        {DW_AT_constant_value = val;}
      private void setDW_AT_declaration    (int val)        {DW_AT_declaration    = val;}
      private void setDW_AT_external       (int val)        {DW_AT_external       = val;}
      private void setDW_AT_sibling        (int val)        {DW_AT_sibling        = val;}
      private void setDW_AT_start_scope    (int val)        {DW_AT_start_scope    = val;}
      private void setDW_AT_visibility     (int val)        {DW_AT_visibility     = val;}
*/
  }
