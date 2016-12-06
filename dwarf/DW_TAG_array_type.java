/*
 * Created on Dec 18, 2011
 *
 *
 * @author  Basile Stefano
 */
//     <1><7ab>: Abbrev Number: 5 (DW_TAG_array_type)
//         DW_AT_sibling     : <7bb>
//         DW_AT_type        : <29>
//     <2><7b4>: Abbrev Number: 6 (DW_TAG_subrange_type)
//         DW_AT_type        : <7e>
//         DW_AT_upper_bound : 31
//
//  DW_AT_declaration (column /rows) :  DW_ORD_col_major
//                                      DW_ORD_row_major
package dwarf;


import java.util.*;

/**
 * @author   Basile Stefano
 */
  public class DW_TAG_array_type  extends DWARFnode {
    
      DWARFnode DW_AT_type_ref       = null;

      int       DW_AT_decl_file    = -1;
      int       DW_AT_decl_line    = -1;
      String    DW_AT_name         = null;
      int       DW_AT_type         = -1;
      int       DW_AT_byte_size    = -1; // dim in bytes 1 entry
      int       DW_AT_declaration  = -1;
      int       DW_AT_ordering     = -1; // major for column or row
      int       DW_AT_sibling      = -1;
      int       DW_AT_start_scope  = -1;
      int       DW_AT_stride_size  = -1; // 1 entry-bits-dim if the entry occupies more than as defined in its type declaration
      int       DW_AT_visibility   = -1;

      private  ArrayList SubrangeList;

      //#########    CONSTRUCTOR     ###########
      DW_TAG_array_type ()
      {
        super (DWARFdef.DW_TAG_array_type);
        SubrangeList = new ArrayList(2);

      }
      DW_TAG_array_type (int type)
      {
        super (DWARFdef.DW_TAG_array_type);
        SubrangeList = new ArrayList(2);
      }


      public void addSubrange ( DW_TAG_subrange_type s) {
    	if (s != null)
    	    SubrangeList.add (s);
      }

      public int NumSubranges () {
      	return SubrangeList.size();
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
        case DWARFdef.DW_AT_ordering     :  DW_AT_ordering    = val; break;
        case DWARFdef.DW_AT_sibling      :  DW_AT_sibling     = val; break;
        case DWARFdef.DW_AT_start_scope  :  DW_AT_start_scope = val; break;
        case DWARFdef.DW_AT_stride_size  :  DW_AT_stride_size = val; break;
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
        case DWARFdef.DW_AT_ordering     :  return DW_AT_ordering    ;
        case DWARFdef.DW_AT_sibling      :  return DW_AT_sibling     ;
        case DWARFdef.DW_AT_start_scope  :  return DW_AT_start_scope ;
        case DWARFdef.DW_AT_stride_size  :  return DW_AT_stride_size ;
        case DWARFdef.DW_AT_visibility   :  return DW_AT_visibility  ;
        default : return -1;
     }
  }

/*

      public DWARFnode getDW_AT_type_ref      (){return DW_AT_type_ref;}

      private String    getDW_AT_name         () {return DW_AT_name;}
      private int       getDW_AT_type         () {return DW_AT_type;}
      private int       getDW_AT_decl_file    () {return DW_AT_decl_file;}
      private int       getDW_AT_decl_line    () {return DW_AT_decl_line;}
      private int       getDW_AT_byte_size    () {return DW_AT_byte_size;}
      private int       getDW_AT_declaration  () {return DW_AT_declaration;}
      private int       getDW_AT_ordering     () {return DW_AT_ordering;}
      private int       getDW_AT_sibling      () {return DW_AT_sibling;}
      private int       DW_AT_start_scope     () {return DW_AT_start_scope;}
      private int       DW_AT_stride_size     () {return DW_AT_stride_size;}
      private int       getDW_AT_visibility   () {return DW_AT_visibility;}


      public  void setDW_AT_type_ref     (DWARFnode ref)  {DW_AT_type_ref = ref;}

      private void setDW_AT_name         (String name)    {DW_AT_name = name;}
      private void setDW_AT_type         (int val)        {DW_AT_type = val;}
      private void setDW_AT_decl_file    (int val)        {DW_AT_decl_file   = val;}
      private void setDW_AT_decl_line    (int val)        {DW_AT_decl_line   = val;}
      private void setDW_AT_byte_size    (int val)        {DW_AT_byte_size   = val;}
      private void setDW_AT_declaration  (int val)        {DW_AT_declaration = val;}
      private void setDW_AT_ordering     (int val)        {DW_AT_ordering    = val;}
      private void setDW_AT_sibling      (int val)        {DW_AT_sibling     = val;}
      private void setDW_AT_start_scope  (int val)        {DW_AT_start_scope = val;}
      private void setDW_AT_stride_size  (int val)        {DW_AT_stride_size = val;}
      private void setDW_AT_visibility   (int val)        {DW_AT_visibility  = val;}
*/
  }
