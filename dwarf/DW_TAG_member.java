/*
 * Created on Dec 18, 2011
 *
 *
 * @author  Basile Stefano
 */
//  <1><1e1c>: Abbrev Number: 15 (DW_TAG_structure_type)
//      DW_AT_sibling     : <1e66>
//      DW_AT_name        : (indirect string, offset: 0xd72e): l3_hdr
//      DW_AT_byte_size   : 4
//      DW_AT_decl_file   : 12
//      DW_AT_decl_line   : 617
//  <2><1e29>: Abbrev Number: 14 (DW_TAG_member)
//      DW_AT_name        : (indirect string, offset: 0x8582): message_discriminator
//      DW_AT_decl_file   : 12
//      DW_AT_decl_line   : 618
//      DW_AT_type        : <29>
//      DW_AT_data_member_location: 2 byte block: 23 0     (DW_OP_plus_uconst: 0; )
package dwarf;
/**
 * @author   Basile Stefano
 */
  public class DW_TAG_member  extends DWARFnode{
                      
      DWARFnode DW_AT_type_ref       = null;

      int       DW_AT_decl_file            = -1;
      int       DW_AT_decl_line            = -1;
      String    DW_AT_name                 = null;
      int       DW_AT_type                 = -1;
      int       DW_AT_byte_size            = -1; // x bit-field : dim in bytes (padding included)
      int       DW_AT_bit_offset           = -1; // x bit-field : dim padding
      int       DW_AT_bit_size             = -1; // x bit-field : dim total in bit
      int       DW_AT_data_member_location = -1;
      int       DW_AT_declaration          = -1;
      int       DW_AT_sibling              = -1;
      int       DW_AT_visibility           = -1;

      //#########    CONSTRUCTOR     ###########
      DW_TAG_member ()
      {
        super (DWARFdef.DW_TAG_member);
      }
      DW_TAG_member (int type)
      {
        super (DWARFdef.DW_TAG_member);
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
        case DWARFdef.DW_AT_decl_file           :  DW_AT_decl_file            = val; break;
        case DWARFdef.DW_AT_decl_line            : DW_AT_decl_line            = val; break;
        case DWARFdef.DW_AT_type                 : DW_AT_type                 = val; break;
        case DWARFdef.DW_AT_byte_size            : DW_AT_byte_size            = val; break;
        case DWARFdef.DW_AT_bit_offset           : DW_AT_bit_offset           = val; break;
        case DWARFdef.DW_AT_bit_size             : DW_AT_bit_size             = val; break;
        case DWARFdef.DW_AT_data_member_location : DW_AT_data_member_location = val; break;
        case DWARFdef.DW_AT_declaration          : DW_AT_declaration          = val; break;
        case DWARFdef.DW_AT_sibling              : DW_AT_sibling              = val; break;
        case DWARFdef.DW_AT_visibility           : DW_AT_visibility           = val; break;
        default : break;
     }
  }
  public  int get_AT_int ( int AT) {
     switch(AT) {
        case DWARFdef.DW_AT_decl_file            : return DW_AT_decl_file   ;
        case DWARFdef.DW_AT_decl_line            : return DW_AT_decl_line           ;
        case DWARFdef.DW_AT_type                 : return DW_AT_type                ;
        case DWARFdef.DW_AT_byte_size            : return DW_AT_byte_size           ;
        case DWARFdef.DW_AT_bit_offset           : return DW_AT_bit_offset          ;
        case DWARFdef.DW_AT_bit_size             : return DW_AT_bit_size            ;
        case DWARFdef.DW_AT_data_member_location : return DW_AT_data_member_location;
        case DWARFdef.DW_AT_declaration          : return DW_AT_declaration         ;
        case DWARFdef.DW_AT_sibling              : return DW_AT_sibling             ;
        case DWARFdef.DW_AT_visibility           : return DW_AT_visibility          ;
        default : return -1;
     }
  }
/*
      public DWARFnode getDW_AT_type_ref      (){return DW_AT_type_ref;}

      private String    getDW_AT_name                  () {return DW_AT_name;}
      private int       getDW_AT_type                  () {return DW_AT_type;}
      private int       getDW_AT_decl_file             () {return DW_AT_decl_file;}
      private int       getDW_AT_decl_line             () {return DW_AT_decl_line;}
      private int       getDW_AT_byte_size             () {return DW_AT_byte_size;}
      private int       getDW_AT_bit_offset            () {return DW_AT_bit_offset;}
      private int       getDW_AT_bit_size              () {return DW_AT_bit_size;}
      private int       getDW_AT_data_member_location  () {return DW_AT_data_member_location;}
      private int       getDW_AT_declaration           () {return DW_AT_declaration;}
      private int       getDW_AT_sibling               () {return DW_AT_sibling;}
      private int       getDW_AT_visibility            () {return DW_AT_visibility;}

      public  void setDW_AT_type_ref     (DWARFnode ref)  {DW_AT_type_ref = ref;}

      private void setDW_AT_name                  (String name)    {DW_AT_name = name;}
      private void setDW_AT_type                  (int val)        {DW_AT_type = val;}
      private void setDW_AT_decl_file             (int val)        {DW_AT_decl_file  = val;}
      private void setDW_AT_decl_line             (int val)        {DW_AT_decl_line  = val;}
      private void setDW_AT_byte_size             (int val)        {DW_AT_byte_size  = val;}
      private void setDW_AT_bit_offset            (int val)        {DW_AT_bit_offset = val;}
      private void setDW_AT_bit_size              (int val)        {DW_AT_bit_size   = val;}
      private void setDW_AT_data_member_location  (int val)        {DW_AT_data_member_location = val;}
      private void setDW_AT_declaration           (int val)        {DW_AT_declaration = val;}
      private void setDW_AT_sibling               (int val)        {DW_AT_sibling     = val;}
      private void setDW_AT_visibility            (int val)        {DW_AT_visibility  = val;}
*/
  }





































