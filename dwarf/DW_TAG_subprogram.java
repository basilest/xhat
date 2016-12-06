/*
 * Created on Dec 18, 2011
 *
 *
 * @author  Basile Stefano
 */
//  <1><f3d5>: Abbrev Number: 46 (DW_TAG_subprogram)
//      DW_AT_sibling     : <f44c>
//      DW_AT_external    : 1
//      DW_AT_name        : (indirect string, offset: 0xb7d): fdhbf_Lv3Fred
//      DW_AT_decl_file   : 1
//      DW_AT_decl_line   : 825
//      DW_AT_prototyped  : 1
//      DW_AT_type        : <7a4>
//      DW_AT_low_pc      : 0 0
//      DW_AT_high_pc     : 0x108 264
//      DW_AT_frame_base  : 1 byte block: 55 	(DW_OP_reg5; )
//  <2><f3f1>: Abbrev Number: 47 (DW_TAG_formal_parameter)
//      DW_AT_name        : (indirect string, offset: 0x3d3): nbts
//      DW_AT_decl_file   : 1
//      DW_AT_decl_line   : 824
//      DW_AT_type        : <4d0>
//      DW_AT_location    : 2 byte block: 91 8 	(DW_OP_fbreg: 8; )
//  <2><f400>: Abbrev Number: 47 (DW_TAG_formal_parameter)
//      DW_AT_name        : (indirect string, offset: 0xc10b): strTmV
//      DW_AT_decl_file   : 1
//      DW_AT_decl_line   : 824
//      DW_AT_type        : <56>
//      DW_AT_location    : 2 byte block: 91 7e 	(DW_OP_fbreg: -2; )
//
//
// DW_AT_calling_convention    :  DW_CC_normal : obey     "standard" calling conventions target architecture
//                                DW_CC_nocall : not obey "standard" calling conventions target architecture

//-------------A global or static subroutine or function.
package dwarf;
/**
 * @author   Basile Stefano
 */
  public class DW_TAG_subprogram  extends DWARFnode {
                                                                                  
      DWARFnode DW_AT_type_ref       = null;

      int       DW_AT_decl_file             = -1;
      int       DW_AT_decl_line             = -1;
      String    DW_AT_name                  = null;
      int       DW_AT_type                  = -1;
      int       DW_AT_address_class         = -1;
      int       DW_AT_artificial            = -1;
      int       DW_AT_calling_convention    = -1;
      int       DW_AT_declaration           = -1;
      int       DW_AT_external              = -1;
      int       DW_AT_frame_base            = -1;
      int       DW_AT_high_pc               = -1;
      int       DW_AT_inline                = -1;
      int       DW_AT_low_pc                = -1;
      int       DW_AT_prototyped            = -1;
      int       DW_AT_return_addr           = -1;
      int       DW_AT_segment               = -1;
      int       DW_AT_sibling               = -1;
      int       DW_AT_specification         = -1;
      int       DW_AT_start_scope           = -1;
      int       DW_AT_static_link           = -1;
      int       DW_AT_visibility            = -1;
      int       DW_AT_virtuality            = -1;
      int       DW_AT_vtable_elem_location  = -1;

      //#########    CONSTRUCTOR     ###########
      DW_TAG_subprogram ()
      {
        super (DWARFdef.DW_TAG_subprogram);
      }
      DW_TAG_subprogram (int type)
      {
        super (DWARFdef.DW_TAG_subprogram);
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
        default : return null;
     }
  }
  public  void set_AT_int ( int AT, int val) {
     switch(AT) {
        case DWARFdef.DW_AT_decl_file             : DW_AT_decl_file             = val; break;
        case DWARFdef.DW_AT_decl_line             : DW_AT_decl_line             = val; break;
        case DWARFdef.DW_AT_type                  : DW_AT_type                  = val; break;
        case DWARFdef.DW_AT_address_class         : DW_AT_address_class         = val; break;
        case DWARFdef.DW_AT_artificial            : DW_AT_artificial            = val; break;
        case DWARFdef.DW_AT_calling_convention    : DW_AT_calling_convention    = val; break;
        case DWARFdef.DW_AT_declaration           : DW_AT_declaration           = val; break;
        case DWARFdef.DW_AT_external              : DW_AT_external              = val; break;
        case DWARFdef.DW_AT_frame_base            : DW_AT_frame_base            = val; break;
        case DWARFdef.DW_AT_high_pc               : DW_AT_high_pc               = val; break;
        case DWARFdef.DW_AT_inline                : DW_AT_inline                = val; break;
        case DWARFdef.DW_AT_low_pc                : DW_AT_low_pc                = val; break;
        case DWARFdef.DW_AT_prototyped            : DW_AT_prototyped            = val; break;
        case DWARFdef.DW_AT_return_addr           : DW_AT_return_addr           = val; break;
        case DWARFdef.DW_AT_segment               : DW_AT_segment               = val; break;
        case DWARFdef.DW_AT_sibling               : DW_AT_sibling               = val; break;
        case DWARFdef.DW_AT_specification         : DW_AT_specification         = val; break;
        case DWARFdef.DW_AT_start_scope           : DW_AT_start_scope           = val; break;
        case DWARFdef.DW_AT_static_link           : DW_AT_static_link           = val; break;
        case DWARFdef.DW_AT_visibility            : DW_AT_visibility            = val; break;
        case DWARFdef.DW_AT_virtuality            : DW_AT_virtuality            = val; break;
        case DWARFdef.DW_AT_vtable_elem_location  : DW_AT_vtable_elem_location  = val; break;
        default : break;
     }
  }
  public  int get_AT_int ( int AT) {
     switch(AT) {
        case DWARFdef.DW_AT_decl_file             : return DW_AT_decl_file            ;
        case DWARFdef.DW_AT_decl_line             : return DW_AT_decl_line            ;
        case DWARFdef.DW_AT_type                  : return DW_AT_type                 ;
        case DWARFdef.DW_AT_address_class         : return DW_AT_address_class        ;
        case DWARFdef.DW_AT_artificial            : return DW_AT_artificial           ;
        case DWARFdef.DW_AT_calling_convention    : return DW_AT_calling_convention   ;
        case DWARFdef.DW_AT_declaration           : return DW_AT_declaration          ;
        case DWARFdef.DW_AT_external              : return DW_AT_external             ;
        case DWARFdef.DW_AT_frame_base            : return DW_AT_frame_base           ;
        case DWARFdef.DW_AT_high_pc               : return DW_AT_high_pc              ;
        case DWARFdef.DW_AT_inline                : return DW_AT_inline               ;
        case DWARFdef.DW_AT_low_pc                : return DW_AT_low_pc               ;
        case DWARFdef.DW_AT_prototyped            : return DW_AT_prototyped           ;
        case DWARFdef.DW_AT_return_addr           : return DW_AT_return_addr          ;
        case DWARFdef.DW_AT_segment               : return DW_AT_segment              ;
        case DWARFdef.DW_AT_sibling               : return DW_AT_sibling              ;
        case DWARFdef.DW_AT_specification         : return DW_AT_specification        ;
        case DWARFdef.DW_AT_start_scope           : return DW_AT_start_scope          ;
        case DWARFdef.DW_AT_static_link           : return DW_AT_static_link          ;
        case DWARFdef.DW_AT_visibility            : return DW_AT_visibility           ;
        case DWARFdef.DW_AT_virtuality            : return DW_AT_virtuality           ;
        case DWARFdef.DW_AT_vtable_elem_location  : return DW_AT_vtable_elem_location ;
        default : return -1;
     }
  }
/*
      public DWARFnode getDW_AT_type_ref      (){return DW_AT_type_ref;}

      private String    getDW_AT_name                  () {return DW_AT_name;}
      private int       getDW_AT_type                  () {return DW_AT_type;}
      private int       getDW_AT_decl_file             () {return DW_AT_decl_file;}
      private int       getDW_AT_decl_line             () {return DW_AT_decl_line;}
      private int       getDW_AT_address_class         () {return DW_AT_address_class;}
      private int       getDW_AT_artificial            () {return DW_AT_artificial;}
      private int       getDW_AT_calling_convention    () {return DW_AT_calling_convention;}
      private int       getDW_AT_declaration           () {return DW_AT_declaration;}
      private int       getDW_AT_external              () {return DW_AT_external;}
      private int       getDW_AT_frame_base            () {return DW_AT_frame_base;}
      private int       getDW_AT_high_pc               () {return DW_AT_high_pc;}
      private int       getDW_AT_inline                () {return DW_AT_inline;}
      private int       getDW_AT_low_pc                () {return DW_AT_low_pc;}
      private int       getDW_AT_prototyped            () {return DW_AT_prototyped;}
      private int       getDW_AT_return_addr           () {return DW_AT_return_addr;}
      private int       getDW_AT_segment               () {return DW_AT_segment;}
      private int       getDW_AT_sibling               () {return DW_AT_sibling;}
      private int       getDW_AT_specification         () {return DW_AT_specification;}
      private int       getDW_AT_start_scope           () {return DW_AT_start_scope;}
      private int       getDW_AT_static_link           () {return DW_AT_static_link;}
      private int       getDW_AT_visibility            () {return DW_AT_visibility;}
      private int       getDW_AT_virtuality            () {return DW_AT_virtuality;}
      private int       getDW_AT_vtable_elem_location  () {return DW_AT_vtable_elem_location;}

      public  void setDW_AT_type_ref     (DWARFnode ref)  {DW_AT_type_ref = ref;}

      private void setDW_AT_name                  (String name)    {DW_AT_name = name;}
      private void setDW_AT_type                  (int val)        {DW_AT_type = val;}
      private void setDW_AT_decl_file             (int val)        {DW_AT_decl_file     = val;}
      private void setDW_AT_decl_line             (int val)        {DW_AT_decl_line     = val;}
      private void setDW_AT_address_class         (int val)        {DW_AT_address_class = val;}
      private void setDW_AT_artificial            (int val)        {DW_AT_artificial    = val;}
      private void setDW_AT_calling_convention    (int val)        {DW_AT_calling_convention = val;}
      private void setDW_AT_declaration           (int val)        {DW_AT_declaration   = val;}
      private void setDW_AT_external              (int val)        {DW_AT_external      = val;}
      private void setDW_AT_frame_base            (int val)        {DW_AT_frame_base    = val;}
      private void setDW_AT_high_pc               (int val)        {DW_AT_high_pc       = val;}
      private void setDW_AT_inline                (int val)        {DW_AT_inline        = val;}
      private void setDW_AT_low_pc                (int val)        {DW_AT_low_pc        = val;}
      private void setDW_AT_prototyped            (int val)        {DW_AT_prototyped    = val;}
      private void setDW_AT_return_addr           (int val)        {DW_AT_return_addr   = val;}
      private void setDW_AT_segment               (int val)        {DW_AT_segment       = val;}
      private void setDW_AT_sibling               (int val)        {DW_AT_sibling       = val;}
      private void setDW_AT_specification         (int val)        {DW_AT_specification = val;}
      private void setDW_AT_start_scope           (int val)        {DW_AT_start_scope   = val;}
      private void setDW_AT_static_link           (int val)        {DW_AT_static_link   = val;}
      private void setDW_AT_visibility            (int val)        {DW_AT_visibility    = val;}
      private void setDW_AT_virtuality            (int val)        {DW_AT_virtuality    = val;}
      private void setDW_AT_vtable_elem_location  (int val)        {DW_AT_vtable_elem_location = val;}
*/
  }



