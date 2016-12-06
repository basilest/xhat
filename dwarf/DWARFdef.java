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
public  class DWARFdef {
    
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
    public final static int DW_TAG_LAST                     = 18;


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
    public final static int DW_AT_LAST                  = 38;


    public final static int DW_OP                       =  0;
    public final static int DW_OP_plus_uconst           =  1;

    public final static String STR_TAG_                       = "DW_TAG_";
    public final static String STR_TAG_base_type              = "base_type";
    public final static String STR_TAG_subrange_type          = "subrange_type";
    public final static String STR_TAG_array_type             = "array_type";
    public final static String STR_TAG_const_type             = "const_type";
    public final static String STR_TAG_pointer_type           = "pointer_type";
    public final static String STR_TAG_volatile_type          = "volatile_type";
    public final static String STR_TAG_constant               = "constant";
    public final static String STR_TAG_formal_parameter       = "formal_parameter";
    public final static String STR_TAG_variable               = "variable";
    public final static String STR_TAG_enumeration_type       = "enumeration_type";
    public final static String STR_TAG_enumerator             = "enumerator";
    public final static String STR_TAG_structure_type         = "structure_type";
    public final static String STR_TAG_union_type             = "union_type";
    public final static String STR_TAG_member                 = "member";
    public final static String STR_TAG_typedef                = "typedef";
    public final static String STR_TAG_subprogram             = "subprogram";
    public final static String STR_TAG_subroutine_type        = "subroutine_type";
    public final static String STR_TAG_unspecified_parameters = "unspecified_parameters";

    public final static String STR_AT_                        = "DW_AT_";
    public final static String STR_AT_type                    = "type";
    public final static String STR_AT_name                    = "name";
    public final static String STR_AT_sibling                 = "sibling";
    public final static String STR_AT_decl_file               = "decl_file";
    public final static String STR_AT_decl_line               = "decl_line";
    public final static String STR_AT_start_scope             = "start_scope";
    public final static String STR_AT_bit_offset              = "bit_offset";
    public final static String STR_AT_bit_size                = "bit_size";
    public final static String STR_AT_byte_size               = "byte_size";
    public final static String STR_AT_inline                  = "inline";
    public final static String STR_AT_high_pc                 = "high_pc";
    public final static String STR_AT_low_pc                  = "low_pc";
    public final static String STR_AT_location                = "location";
    public final static String STR_AT_segment                 = "segment";
    public final static String STR_AT_address_class           = "address_class";
    public final static String STR_AT_data_member_location    = "data_member_location";
    public final static String STR_AT_artificial              = "artificial";
    public final static String STR_AT_calling_convention      = "calling_convention";
    public final static String STR_AT_const_value             = "const_value";
    public final static String STR_AT_constant_value          = "constant_value";
    public final static String STR_AT_declaration             = "declaration";
    public final static String STR_AT_default_value           = "default_value";
    public final static String STR_AT_encoding                = "encoding";
    public final static String STR_AT_external                = "external";
    public final static String STR_AT_frame_base              = "frame_base";
    public final static String STR_AT_count                   = "count";
    public final static String STR_AT_upper_bound             = "upper_bound";
    public final static String STR_AT_lower_bound             = "lower_bound";
    public final static String STR_AT_ordering                = "ordering";
    public final static String STR_AT_prototyped              = "prototyped";
    public final static String STR_AT_return_addr             = "return_addr";
    public final static String STR_AT_variable_parameter      = "variable_parameter";
    public final static String STR_AT_specification           = "specification";
    public final static String STR_AT_static_link             = "static_link";
    public final static String STR_AT_stride_size             = "stride_size";
    public final static String STR_AT_virtuality              = "virtuality";
    public final static String STR_AT_visibility              = "visibility";
    public final static String STR_AT_vtable_elem_location    = "vtable_elem_location";

    public final static String STR_OP_                        = "DW_OP_";
    public final static String STR_OP_plus_uconst             = "plus_uconst";

    private static  final Hashtable DW_TAG_hashT = new Hashtable(DW_TAG_LAST);
    private static  final Hashtable DW_AT_hashT  = new Hashtable(DW_AT_LAST);

   static {
       
       DW_TAG_hashT.put( STR_TAG_base_type              , new Integer(DW_TAG_base_type));
       DW_TAG_hashT.put( STR_TAG_subrange_type          , new Integer(DW_TAG_subrange_type));
       DW_TAG_hashT.put( STR_TAG_array_type             , new Integer(DW_TAG_array_type));
       DW_TAG_hashT.put( STR_TAG_const_type             , new Integer(DW_TAG_const_type));
       DW_TAG_hashT.put( STR_TAG_pointer_type           , new Integer(DW_TAG_pointer_type));
       DW_TAG_hashT.put( STR_TAG_volatile_type          , new Integer(DW_TAG_volatile_type));
       DW_TAG_hashT.put( STR_TAG_constant               , new Integer(DW_TAG_constant));
       DW_TAG_hashT.put( STR_TAG_formal_parameter       , new Integer(DW_TAG_formal_parameter));
       DW_TAG_hashT.put( STR_TAG_variable               , new Integer(DW_TAG_variable));
       DW_TAG_hashT.put( STR_TAG_enumeration_type       , new Integer(DW_TAG_enumeration_type));
       DW_TAG_hashT.put( STR_TAG_enumerator             , new Integer(DW_TAG_enumerator));
       DW_TAG_hashT.put( STR_TAG_structure_type         , new Integer(DW_TAG_structure_type));
       DW_TAG_hashT.put( STR_TAG_union_type             , new Integer(DW_TAG_union_type));
       DW_TAG_hashT.put( STR_TAG_member                 , new Integer(DW_TAG_member));
       DW_TAG_hashT.put( STR_TAG_typedef                , new Integer(DW_TAG_typedef));
       DW_TAG_hashT.put( STR_TAG_subprogram             , new Integer(DW_TAG_subprogram));
       DW_TAG_hashT.put( STR_TAG_subroutine_type        , new Integer(DW_TAG_subroutine_type));
       DW_TAG_hashT.put( STR_TAG_unspecified_parameters , new Integer(DW_TAG_unspecified_parameters));

       DW_AT_hashT.put ( STR_AT_type                    , new Integer(DW_AT_type));
       DW_AT_hashT.put ( STR_AT_name                    , new Integer(DW_AT_name));
       DW_AT_hashT.put ( STR_AT_sibling                 , new Integer(DW_AT_sibling));
       DW_AT_hashT.put ( STR_AT_decl_file               , new Integer(DW_AT_decl_file));
       DW_AT_hashT.put ( STR_AT_decl_line               , new Integer(DW_AT_decl_line));
       DW_AT_hashT.put ( STR_AT_start_scope             , new Integer(DW_AT_start_scope));
       DW_AT_hashT.put ( STR_AT_bit_offset              , new Integer(DW_AT_bit_offset));
       DW_AT_hashT.put ( STR_AT_bit_size                , new Integer(DW_AT_bit_size));
       DW_AT_hashT.put ( STR_AT_byte_size               , new Integer(DW_AT_byte_size));
       DW_AT_hashT.put ( STR_AT_inline                  , new Integer(DW_AT_inline));
       DW_AT_hashT.put ( STR_AT_high_pc                 , new Integer(DW_AT_high_pc));
       DW_AT_hashT.put ( STR_AT_low_pc                  , new Integer(DW_AT_low_pc));
       DW_AT_hashT.put ( STR_AT_location                , new Integer(DW_AT_location));
       DW_AT_hashT.put ( STR_AT_segment                 , new Integer(DW_AT_segment));
       DW_AT_hashT.put ( STR_AT_address_class           , new Integer(DW_AT_address_class));
       DW_AT_hashT.put ( STR_AT_data_member_location    , new Integer(DW_AT_data_member_location));
       DW_AT_hashT.put ( STR_AT_artificial              , new Integer(DW_AT_artificial));
       DW_AT_hashT.put ( STR_AT_calling_convention      , new Integer(DW_AT_calling_convention));
       DW_AT_hashT.put ( STR_AT_const_value             , new Integer(DW_AT_const_value));
       DW_AT_hashT.put ( STR_AT_constant_value          , new Integer(DW_AT_constant_value));
       DW_AT_hashT.put ( STR_AT_declaration             , new Integer(DW_AT_declaration));
       DW_AT_hashT.put ( STR_AT_default_value           , new Integer(DW_AT_default_value));
       DW_AT_hashT.put ( STR_AT_encoding                , new Integer(DW_AT_encoding));
       DW_AT_hashT.put ( STR_AT_external                , new Integer(DW_AT_external));
       DW_AT_hashT.put ( STR_AT_frame_base              , new Integer(DW_AT_frame_base));
       DW_AT_hashT.put ( STR_AT_count                   , new Integer(DW_AT_count));
       DW_AT_hashT.put ( STR_AT_upper_bound             , new Integer(DW_AT_upper_bound));
       DW_AT_hashT.put ( STR_AT_lower_bound             , new Integer(DW_AT_lower_bound));
       DW_AT_hashT.put ( STR_AT_ordering                , new Integer(DW_AT_ordering));
       DW_AT_hashT.put ( STR_AT_prototyped              , new Integer(DW_AT_prototyped));
       DW_AT_hashT.put ( STR_AT_return_addr             , new Integer(DW_AT_return_addr));
       DW_AT_hashT.put ( STR_AT_variable_parameter      , new Integer(DW_AT_variable_parameter));
       DW_AT_hashT.put ( STR_AT_specification           , new Integer(DW_AT_specification));
       DW_AT_hashT.put ( STR_AT_static_link             , new Integer(DW_AT_static_link));
       DW_AT_hashT.put ( STR_AT_stride_size             , new Integer(DW_AT_stride_size));
       DW_AT_hashT.put ( STR_AT_virtuality              , new Integer(DW_AT_virtuality));
       DW_AT_hashT.put ( STR_AT_visibility              , new Integer(DW_AT_visibility));
       DW_AT_hashT.put ( STR_AT_vtable_elem_location    , new Integer(DW_AT_vtable_elem_location));
    }

    //###########   get TAG id    ################
    public static  int TAGid (String TAGname)
    {
        Integer id = (Integer)DW_TAG_hashT.get(TAGname);
        return (id == null) ? -1 : id.intValue();
    }

    //###########   get AT id     ################
    public static  int ATid ( String ATname)
    {
        Integer id = (Integer)DW_AT_hashT.get(ATname);
        return (id == null) ? -1 : id.intValue();
    }

    //###########   get Op id     ################
    public static  int OPid (String OPname)
    {
        return (STR_OP_plus_uconst.equals(OPname)) ? DW_OP_plus_uconst : -1;
    }
  }




