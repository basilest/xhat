/*
 * Created on Dec 18, 2011
 *
 *
 * @author  Basile Stefano
 */
// <1><1e1c>: Abbrev Number: 15 (DW_TAG_structure_type)
//     DW_AT_sibling     : <1e66>
//     DW_AT_name        : (indirect string, offset: 0xd72e): l3_hdr
//     DW_AT_byte_size   : 4
//     DW_AT_decl_file   : 12
//     DW_AT_decl_line   : 617
// <2><1e29>: Abbrev Number: 14 (DW_TAG_member)
//     DW_AT_name        : (indirect string, offset: 0x8582): message_discriminator
//     DW_AT_decl_file   : 12
//     DW_AT_decl_line   : 618
//     DW_AT_type        : <29>
//     DW_AT_data_member_location: 2 byte block: 23 0     (DW_OP_plus_uconst: 0; )
package dwarf;

import java.util.*;
/**
 * @author   Basile Stefano
 */
  public class DW_TAG_structure_type  extends DWARFnode{
                                                                                  
      int    DW_AT_decl_file   = -1;
      int    DW_AT_decl_line   = -1;
      String DW_AT_name        = null;
      int    DW_AT_byte_size   = -1;   //  1 struct total dim in bytes
      int    DW_AT_declaration = -1;
      int    DW_AT_sibling     = -1;
      int    DW_AT_start_scope = -1;
      int    DW_AT_visibility  = -1;


      private  ArrayList MemberList;


      //#########    CONSTRUCTOR     ###########
      DW_TAG_structure_type ()      {
        super (DWARFdef.DW_TAG_structure_type);
        MemberList = new ArrayList(15);

      }
      DW_TAG_structure_type (int type)      {
        super (DWARFdef.DW_TAG_structure_type);
        MemberList = new ArrayList(15);
      }

      public void addMember ( DWARFnode m) {
    	if (m != null)
    	    MemberList.add (m);
      }

      public int NumMembers () {
      	return MemberList.size();
      }



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
        case DWARFdef.DW_AT_byte_size    :  DW_AT_byte_size   = val; break;
        case DWARFdef.DW_AT_declaration  :  DW_AT_declaration = val; break;
        case DWARFdef.DW_AT_sibling      :  DW_AT_sibling     = val; break;
        case DWARFdef.DW_AT_start_scope  :  DW_AT_start_scope = val; break;
        case DWARFdef.DW_AT_visibility   :  DW_AT_visibility  = val; break;
        default : break;
     }
  }
  public  int get_AT_int ( int AT) {
     switch(AT) {
        case DWARFdef.DW_AT_decl_file    :  return DW_AT_decl_file   ;
        case DWARFdef.DW_AT_decl_line    :  return DW_AT_decl_line   ;
        case DWARFdef.DW_AT_byte_size    :  return DW_AT_byte_size   ;
        case DWARFdef.DW_AT_declaration  :  return DW_AT_declaration ;
        case DWARFdef.DW_AT_sibling      :  return DW_AT_sibling     ;
        case DWARFdef.DW_AT_start_scope  :  return DW_AT_start_scope ;
        case DWARFdef.DW_AT_visibility   :  return DW_AT_visibility  ;
        default : return -1;
     }
  }
}
