/*
 * Created on Dec 18, 2011
 *
 *
 * @author  Basile Stefano
 */
//   <1><7ab>: Abbrev Number: 5 (DW_TAG_array_type)
//       DW_AT_sibling     : <7bb>
//       DW_AT_type        : <29>
//   <2><7b4>: Abbrev Number: 6 (DW_TAG_subrange_type)
//       DW_AT_type        : <7e>
//       DW_AT_upper_bound : 31
//
package dwarf;
/**
 * @author   Basile Stefano
 */
  public class DW_TAG_subrange_type  extends DWARFnode {
                                                                                  
      DWARFnode DW_AT_type_ref       = null;

      int       DW_AT_decl_file   = -1;
      int       DW_AT_decl_line   = -1;
      String    DW_AT_name        = null;
      int       DW_AT_type        = -1;
      int       DW_AT_byte_size   = -1;
      int       DW_AT_declaration = -1;
      int       DW_AT_count       = -1;
      int       DW_AT_lower_bound =  0;   // default 0.
      int       DW_AT_upper_bound = -1;
      int       DW_AT_sibling     = -1;
      int       DW_AT_visibility  = -1;

      //#########    CONSTRUCTOR     ###########
      DW_TAG_subrange_type ()
      {
        super (DWARFdef.DW_TAG_subrange_type);
      }
      DW_TAG_subrange_type (int type)
      {
        super (DWARFdef.DW_TAG_subrange_type);
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
        case DWARFdef.DW_AT_decl_file    :  DW_AT_decl_file   = val; break;
        case DWARFdef.DW_AT_decl_line    :  DW_AT_decl_line   = val; break;
        case DWARFdef.DW_AT_type         :  DW_AT_type        = val; break;
        case DWARFdef.DW_AT_byte_size    :  DW_AT_byte_size   = val; break;
        case DWARFdef.DW_AT_declaration  :  DW_AT_declaration = val; break;
        case DWARFdef.DW_AT_count        :  DW_AT_count       = val; break;
        case DWARFdef.DW_AT_lower_bound  :  DW_AT_lower_bound = val; break;
        case DWARFdef.DW_AT_upper_bound  :  DW_AT_upper_bound = val; break;
        case DWARFdef.DW_AT_sibling      :  DW_AT_sibling     = val; break;
        case DWARFdef.DW_AT_visibility   :  DW_AT_visibility  = val; break;
        default : break;
     }
  }
  public  int get_AT_int ( int AT) {
     switch(AT) {
        case DWARFdef.DW_AT_decl_file    :  return DW_AT_decl_file   ;
        case DWARFdef.DW_AT_decl_line    :  return DW_AT_decl_line   ;
        case DWARFdef.DW_AT_type         :  return DW_AT_type        ;
        case DWARFdef.DW_AT_byte_size    :  return DW_AT_byte_size   ;
        case DWARFdef.DW_AT_declaration  :  return DW_AT_declaration ;
        case DWARFdef.DW_AT_count        :  return DW_AT_count       ;
        case DWARFdef.DW_AT_lower_bound  :  return DW_AT_lower_bound ;
        case DWARFdef.DW_AT_upper_bound  :  return DW_AT_upper_bound ;
        case DWARFdef.DW_AT_sibling      :  return DW_AT_sibling     ;
        case DWARFdef.DW_AT_visibility   :  return DW_AT_visibility  ;
        default : return -1;
     }
  }

  public  int get_cardinality () {

     if ( DW_AT_count > 0 )
          return DW_AT_count;
     else
        return (DW_AT_upper_bound - DW_AT_lower_bound + 1);
  }
/*

      public DWARFnode getDW_AT_type_ref      (){return DW_AT_type_ref;}

      private String    getDW_AT_name        () {return DW_AT_name;}
      private int       getDW_AT_type        () {return DW_AT_type;}
      private int       getDW_AT_decl_file   () {return DW_AT_decl_file;}
      private int       getDW_AT_decl_line   () {return DW_AT_decl_line;}
      private int       getDW_AT_byte_size   () {return DW_AT_byte_size;}
      private int       getDW_AT_declaration () {return DW_AT_declaration;}
      private int       getDW_AT_count       () {return DW_AT_count;}
      private int       getDW_AT_lower_bound () {return DW_AT_lower_bound;}
      private int       getDW_AT_upper_bound () {return DW_AT_upper_bound;}
      private int       getDW_AT_sibling     () {return DW_AT_sibling;}
      private int       getDW_AT_visibility  () {return DW_AT_visibility;}

      public  void setDW_AT_type_ref     (DWARFnode ref)  {DW_AT_type_ref = ref;}

      private void setDW_AT_name        (String name)    {DW_AT_name = name;}
      private void setDW_AT_type        (int val)        {DW_AT_type = val;}
      private void setDW_AT_decl_file   (int val)        {DW_AT_decl_file   = val;}
      private void setDW_AT_decl_line   (int val)        {DW_AT_decl_line   = val;}
      private void setDW_AT_byte_size   (int val)        {DW_AT_byte_size   = val;}
      private void setDW_AT_declaration (int val)        {DW_AT_declaration = val;}
      private void setDW_AT_count       (int val)        {DW_AT_count       = val;}
      private void setDW_AT_lower_bound (int val)        {DW_AT_lower_bound = val;}
      private void setDW_AT_upper_bound (int val)        {DW_AT_upper_bound = val;}
      private void setDW_AT_sibling     (int val)        {DW_AT_sibling     = val;}
      private void setDW_AT_visibility  (int val)        {DW_AT_visibility  = val;}
*/
  }
