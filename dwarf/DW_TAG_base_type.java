/*
 * Created on Dec 18, 2011
 *
 *
 * @author  Basile Stefano
 */
//             <1><aebb>: Abbrev Number: 7 (DW_TAG_base_type)
//                 DW_AT_name        : (indirect string, offset: 0xd6a): long long int
//                 DW_AT_byte_size   : 8
//                 DW_AT_encoding    : 5      (signed)
//
//   ------------------------
//   DW_TAG_base_type
//       DW_AT_name
//       DW_AT_bit_offset
//       DW_AT_bit_size
//       DW_AT_byte_size
//       DW_AT_encoding
//       DW_AT_sibling

package dwarf;

/**
 * @author   Basile Stefano
 */
  public class   DW_TAG_base_type extends DWARFnode{

//       int    VariableValue    = -1;

       String DW_AT_name       = null;
       int    DW_AT_bit_offset = -1;
       int    DW_AT_bit_size   = -1;
       int    DW_AT_byte_size  = -1;
       int    DW_AT_encoding   = -1;
       int    DW_AT_sibling    = -1;


      //#########    CONSTRUCTOR     ###########
      DW_TAG_base_type ()
      {
        super (DWARFdef.DW_TAG_base_type);
      }
      DW_TAG_base_type (int type)
      {
        super (DWARFdef.DW_TAG_base_type);
      }


      //#########             ###########
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
        case DWARFdef.DW_AT_bit_offset   :  DW_AT_bit_offset  = val; break;
        case DWARFdef.DW_AT_bit_size     :  DW_AT_bit_size    = val; break;
        case DWARFdef.DW_AT_byte_size    :  DW_AT_byte_size   = val; break;
        case DWARFdef.DW_AT_encoding     :  DW_AT_encoding    = val; break;
        case DWARFdef.DW_AT_sibling      :  DW_AT_sibling     = val; break;

        default : break;
     }
  }
  public  int get_AT_int ( int AT) {
     switch(AT) {
        case DWARFdef.DW_AT_bit_offset   :  return DW_AT_bit_offset;
        case DWARFdef.DW_AT_bit_size     :  return DW_AT_bit_size  ;
        case DWARFdef.DW_AT_byte_size    :  return DW_AT_byte_size ;
        case DWARFdef.DW_AT_encoding     :  return DW_AT_encoding  ;
        case DWARFdef.DW_AT_sibling      :  return DW_AT_sibling   ;
        default : return -1;
     }
  }
/*
       private String  getDW_AT_name       (){return DW_AT_name;}
       private int     getDW_AT_bit_offset (){return DW_AT_bit_offset;}
       private int     getDW_AT_bit_size   (){return DW_AT_bit_size;}
       private int     getDW_AT_byte_size  (){return DW_AT_byte_size;}
       private int     getDW_AT_encoding   (){return DW_AT_encoding;}
       private int     getDW_AT_sibling    (){return DW_AT_sibling;}

       private void setDW_AT_name       (String name) {DW_AT_name       = name;}
       private void setDW_AT_bit_offset (int val)     {DW_AT_bit_offset = val;}
       private void setDW_AT_bit_size   (int val)     {DW_AT_bit_size   = val;}
       private void setDW_AT_byte_size  (int val)     {DW_AT_byte_size  = val;}
       private void setDW_AT_encoding   (int val)     {DW_AT_encoding   = val;}
       private void setDW_AT_sibling    (int val)     {DW_AT_sibling    = val;}
*/
  }
