/*
 * Created on Dec 18, 2011
 *
 *
 * @author  Basile Stefano
 */
package dwarf;

import java.util.*;
/**
 * @author   Basile Stefano
 */
public  class Old_DWARFdef {
    
    public final static int DW_TAG                          =  0;
	public final static int DW_TAG_base_type                =  1;
	public final static int DW_TAG_subrange_type            =  2;
	public final static int DW_TAG_array_type               =  3;
	public final static int DW_TAG_const_type               =  4;
	public final static int DW_TAG_pointer_type             =  5;
	public final static int DW_TAG_volatile_type            =  6;
	public final static int DW_TAG_constant                 =  7;
	public final static int DW_TAG_formal_parameter         =  8;
	public final static int DW_TAG_variable                 =  9;
	public final static int DW_TAG_enumeration_type         = 10;
	public final static int DW_TAG_enumerator               = 11;
	public final static int DW_TAG_structure_type           = 12;
	public final static int DW_TAG_union_type               = 13;
	public final static int DW_TAG_member                   = 14;
	public final static int DW_TAG_typedef                  = 15;
	public final static int DW_TAG_subprogram               = 16;
	public final static int DW_TAG_subroutine_type          = 17;
	public final static int DW_TAG_unspecified_parameters   = 18;


    static int[] DW_TAG_order;


    final static String[] DW_TAG_names = {
                                             "DW_TAG_",
                                             "base_type",
                                             "subrange_type",
                                             "array_type",
                                             "const_type",
                                             "pointer_type",
                                             "volatile_type",
                                             "constant",
                                             "formal_parameter",
                                             "variable",
                                             "enumeration_type",
                                             "enumerator",
                                             "structure_type",
                                             "union_type",
                                             "member",
                                             "typedef",
                                             "subprogram",
                                             "subroutine_type",
                                             "unspecified_parameters"
                                           };

	public final static int DW_AT                       =  0;
	public final static int DW_AT_type                  =  1;
	public final static int DW_AT_name                  =  2;
	public final static int DW_AT_sibling               =  3;
	public final static int DW_AT_decl_file             =  4;
	public final static int DW_AT_decl_line             =  5;
	public final static int DW_AT_start_scope           =  6;
	public final static int DW_AT_bit_offset            =  7;
	public final static int DW_AT_bit_size              =  8;
	public final static int DW_AT_byte_size             =  9;
	public final static int DW_AT_inline                = 10;
	public final static int DW_AT_high_pc               = 11;
	public final static int DW_AT_low_pc                = 12;
	public final static int DW_AT_location              = 13;
	public final static int DW_AT_segment               = 14;
	public final static int DW_AT_address_class         = 15;
	public final static int DW_AT_data_member_location  = 16;
	public final static int DW_AT_artificial            = 17;
	public final static int DW_AT_calling_convention    = 18;
	public final static int DW_AT_const_value           = 19;
	public final static int DW_AT_constant_value        = 20;
	public final static int DW_AT_declaration           = 21;
	public final static int DW_AT_default_value         = 22;
	public final static int DW_AT_encoding              = 23;
	public final static int DW_AT_external              = 24;
	public final static int DW_AT_frame_base            = 25;
	public final static int DW_AT_count                 = 26;
	public final static int DW_AT_upper_bound           = 27;
	public final static int DW_AT_lower_bound           = 28;
	public final static int DW_AT_ordering              = 29;
	public final static int DW_AT_prototyped            = 30;
	public final static int DW_AT_return_addr           = 31;
	public final static int DW_AT_variable_parameter    = 32;
	public final static int DW_AT_specification         = 33;
	public final static int DW_AT_static_link           = 34;
	public final static int DW_AT_stride_size           = 35;
	public final static int DW_AT_virtuality            = 36;
	public final static int DW_AT_visibility            = 37;
	public final static int DW_AT_vtable_elem_location  = 38;

     static int[]  DW_AT_order;

     final static String[] DW_AT_names = {
                                            "DW_AT_",
                                            "type",
                                            "name",
                                            "sibling",
                                            "decl_file",
                                            "decl_line",
                                            "start_scope",
                                            "bit_offset",
                                            "bit_size",
                                            "byte_size",
                                            "inline",
                                            "high_pc",
                                            "low_pc",
                                            "location",
                                            "segment",
                                            "address_class",
                                            "data_member_location",
                                            "artificial",
                                            "calling_convention",
                                            "const_value",
                                            "constant_value",
                                            "declaration",
                                            "default_value",
                                            "encoding",
                                            "external",
                                            "frame_base",
                                            "count",
                                            "upper_bound",
                                            "lower_bound",
                                            "ordering",
                                            "prototyped",
                                            "return_addr",
                                            "variable_parameter",
                                            "specification",
                                            "static_link",
                                            "stride_size",
                                            "virtuality",
                                            "visibility",
                                            "vtable_elem_location"
                                          };

    public final static int DW_OP                       =  0;
    public final static int DW_OP_plus_uconst           =  1;
    
    static int[]  DW_OP_order;

    final static String[] DW_OP_names = {
                                           "DW_OP_",
                                           "plus_uconst"
                                        };

   static {
      Arrays.sort(DW_TAG_names);
      Arrays.sort(DW_AT_names);
      Arrays.sort(DW_OP_names);

      DW_TAG_order = new int[DW_TAG_names.length];
      DW_AT_order  = new int[DW_AT_names.length];

      DW_OP_order  = new int[DW_OP_names.length];

      DW_OP_order[ Arrays.binarySearch(DW_TAG_names, "DW_OP_")]                  = DW_OP                       ;
      DW_OP_order[ Arrays.binarySearch(DW_TAG_names, "plus_uconst")]             = DW_OP_plus_uconst           ;

      DW_TAG_order[ Arrays.binarySearch(DW_TAG_names, "DW_TAG_")]               = DW_TAG                       ;
      DW_TAG_order[ Arrays.binarySearch(DW_TAG_names, "base_type")]             = DW_TAG_base_type             ;
      DW_TAG_order[ Arrays.binarySearch(DW_TAG_names, "subrange_type")]         = DW_TAG_subrange_type         ;
      DW_TAG_order[ Arrays.binarySearch(DW_TAG_names, "array_type")]            = DW_TAG_array_type            ;
      DW_TAG_order[ Arrays.binarySearch(DW_TAG_names, "const_type")]            = DW_TAG_const_type            ;
      DW_TAG_order[ Arrays.binarySearch(DW_TAG_names, "pointer_type")]          = DW_TAG_pointer_type          ;
      DW_TAG_order[ Arrays.binarySearch(DW_TAG_names, "volatile_type")]         = DW_TAG_volatile_type         ;
      DW_TAG_order[ Arrays.binarySearch(DW_TAG_names, "constant")]              = DW_TAG_constant              ;
      DW_TAG_order[ Arrays.binarySearch(DW_TAG_names, "formal_parameter")]      = DW_TAG_formal_parameter      ;
      DW_TAG_order[ Arrays.binarySearch(DW_TAG_names, "variable")]              = DW_TAG_variable              ;
      DW_TAG_order[ Arrays.binarySearch(DW_TAG_names, "enumeration_type")]      = DW_TAG_enumeration_type      ;
      DW_TAG_order[ Arrays.binarySearch(DW_TAG_names, "enumerator")]            = DW_TAG_enumerator            ;
      DW_TAG_order[ Arrays.binarySearch(DW_TAG_names, "structure_type")]        = DW_TAG_structure_type        ;
      DW_TAG_order[ Arrays.binarySearch(DW_TAG_names, "union_type")]            = DW_TAG_union_type            ;
      DW_TAG_order[ Arrays.binarySearch(DW_TAG_names, "member")]                = DW_TAG_member                ;
      DW_TAG_order[ Arrays.binarySearch(DW_TAG_names, "typedef")]               = DW_TAG_typedef               ;
      DW_TAG_order[ Arrays.binarySearch(DW_TAG_names, "subprogram")]            = DW_TAG_subprogram            ;
      DW_TAG_order[ Arrays.binarySearch(DW_TAG_names, "subroutine_type")]       = DW_TAG_subroutine_type       ;
      DW_TAG_order[ Arrays.binarySearch(DW_TAG_names, "unspecified_parameters")]= DW_TAG_unspecified_parameters;

      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "DW_AT_")]                  = DW_AT                     ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "type")]                    = DW_AT_type                ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "name")]                    = DW_AT_name                ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "sibling")]                 = DW_AT_sibling             ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "decl_file")]               = DW_AT_decl_file           ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "decl_line")]               = DW_AT_decl_line           ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "start_scope")]             = DW_AT_start_scope         ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "bit_offset")]              = DW_AT_bit_offset          ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "bit_size")]                = DW_AT_bit_size            ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "byte_size")]               = DW_AT_byte_size           ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "inline")]                  = DW_AT_inline              ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "high_pc")]                 = DW_AT_high_pc             ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "low_pc")]                  = DW_AT_low_pc              ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "location")]                = DW_AT_location            ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "segment")]                 = DW_AT_segment             ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "address_class")]           = DW_AT_address_class       ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "data_member_location")]    = DW_AT_data_member_location;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "artificial")]              = DW_AT_artificial          ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "calling_convention")]      = DW_AT_calling_convention  ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "const_value")]             = DW_AT_const_value         ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "constant_value")]          = DW_AT_constant_value      ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "declaration")]             = DW_AT_declaration         ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "default_value")]           = DW_AT_default_value       ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "encoding")]                = DW_AT_encoding            ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "external")]                = DW_AT_external            ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "frame_base")]              = DW_AT_frame_base          ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "count")]                   = DW_AT_count               ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "upper_bound")]             = DW_AT_upper_bound         ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "lower_bound")]             = DW_AT_lower_bound         ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "ordering")]                = DW_AT_ordering            ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "prototyped")]              = DW_AT_prototyped          ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "return_addr")]             = DW_AT_return_addr         ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "variable_parameter")]      = DW_AT_variable_parameter  ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "specification")]           = DW_AT_specification       ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "static_link")]             = DW_AT_static_link         ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "stride_size")]             = DW_AT_stride_size         ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "virtuality")]              = DW_AT_virtuality          ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "visibility")]              = DW_AT_visibility          ;
      DW_AT_order[ Arrays.binarySearch(DW_AT_names, "vtable_elem_location")]    = DW_AT_vtable_elem_location;
    }


        //###########   get TAG name   ################
        public static  String TAGname ( int TAGid)
        {
            int i;
            for ( i=0; i < DW_TAG_order.length; i++ )
            if (TAGid == DW_TAG_order[i])
                return DW_TAG_names[i];
            return null;
        }

        //###########   get AT name   ################
        public static  String ATname ( int ATid)
        {
            int i;
            for ( i=0; i < DW_AT_names.length; i++ )
            if (ATid == DW_AT_order[i])
                return DW_AT_names[i];
            return null;
        }

        //###########   get TAG id    ################
        public static  int TAGid ( String TAGname)
        {
            int i = Arrays.binarySearch(DW_TAG_names, TAGname);
            if ( i >= 0)
                return DW_TAG_order[i];
            return -1;
        }

        //###########   get AT id     ################
        public static  int ATid ( String ATname)
        {
            int i = Arrays.binarySearch(DW_AT_names, ATname);
            if ( i >= 0)
                return DW_AT_order[i];
            return i;

        }

        //###########   get TAG cardinality   ################
        public static  int  MaxTAG ( )
        {
            return DW_TAG_names.length;
        }

        //###########   get AT cardinality    ################
        public static  int  MaxAT ( )
        {
            return DW_AT_names.length;
        }
  }




