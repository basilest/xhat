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
public class DWARFnode {

     private int  Nodetype = -1;  // tag node as unusable;
     private int  DWidx    = -1;
     private byte Level    =  1;

/*
     private DWARFnode child   = null;
     private DWARFnode sibling = null;
*/

    //#########    CONSTRUCTOR     ###########
     DWARFnode(){
     }

     DWARFnode(int type){
        if ( type >= 0 && (type < DWARFdef.DW_TAG_LAST))
            Nodetype  = type;
     }

    //#########    GET Node TYPE     ###########
     public int getType (){
        return Nodetype;
     }

     public int getDWidx (){
        return DWidx;
     }

     public void setDWidx (int idx){
         DWidx = idx;
     }

     public byte getLevel (){
        return Level;
     }

     public void setLevel (byte lev){
         Level = lev;
     }
/*
    //#########    GET CHILD     ###########
     public DWARFnode getChild ()
     {
        return child;
     }

    //#########    SET CHILD     ###########
     public void setChild (DWARFnode child)
     {
        this.child = child;
     }

    //#########    GET SIBLING     ###########
     public DWARFnode getSibling ()
     {
        return sibling;
     }

    //#########    SET SIBLING     ###########
     public void setSibling (DWARFnode sibling)
     {
        this.sibling = sibling;
     }


    //#########    GET  SPECIFIC DWARF AT     ###########
     public DWARFnode getDW_AT_type_ref                 (){return null;}

     private int      getDW_AT_type                     (){return -1;}
     private String   getDW_AT_name                     (){return null;}
     private int      getDW_AT_sibling                  (){return -1;}
     private int      getDW_AT_decl_file                (){return -1;}
     private int      getDW_AT_decl_line                (){return -1;}
     private int      getDW_AT_start_scope              (){return -1;}
     private int      getDW_AT_bit_offset               (){return -1;}
     private int      getDW_AT_bit_size                 (){return -1;}
     private int      getDW_AT_byte_size                (){return -1;}
     private int      getDW_AT_inline                   (){return -1;}
     private int      getDW_AT_high_pc                  (){return -1;}
     private int      getDW_AT_low_pc                   (){return -1;}
     private int      getDW_AT_location                 (){return -1;}
     private int      getDW_AT_segment                  (){return -1;}
     private int      getDW_AT_address_class            (){return -1;}
     private int      getDW_AT_data_member_location     (){return -1;}
     private int      getDW_AT_artificial               (){return -1;}
     private int      getDW_AT_calling_convention       (){return -1;}
     private int      getDW_AT_const_value              (){return -1;}
     private int      getDW_AT_constant_value           (){return -1;}
     private int      getDW_AT_declaration              (){return -1;}
     private int      getDW_AT_default_value            (){return -1;}
     private int      getDW_AT_encoding                 (){return -1;}
     private int      getDW_AT_external                 (){return -1;}
     private int      getDW_AT_frame_base               (){return -1;}
     private int      getDW_AT_count                    (){return -1;}
     private int      getDW_AT_upper_bound              (){return -1;}
     private int      getDW_AT_lower_bound              (){return -1;}
     private int      getDW_AT_ordering                 (){return -1;}
     private int      getDW_AT_prototyped               (){return -1;}
     private int      getDW_AT_return_addr              (){return -1;}
     private int      getDW_AT_variable_parameter       (){return -1;}
     private int      getDW_AT_specification            (){return -1;}
     private int      getDW_AT_static_link              (){return -1;}
     private int      getDW_AT_stride_size              (){return -1;}
     private int      getDW_AT_virtuality               (){return -1;}
     private int      getDW_AT_visibility               (){return -1;}
     private int      getDW_AT_vtable_elem_location     (){return -1;}


    //#########    SET SPECIFIC DWARF AT     ###########
     public  void setDW_AT_type_ref                 (DWARFnode type) {}

     private void setDW_AT_type                     (int val){}
     private void setDW_AT_name                     (String name){
                              System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA\n");

    }
     private void setDW_AT_sibling                  (int val){}
     private void setDW_AT_decl_file                (int val){}
     private void setDW_AT_decl_line                (int val){}
     private void setDW_AT_start_scope              (int val){}
     private void setDW_AT_bit_offset               (int val){}
     private void setDW_AT_bit_size                 (int val){}
     private void setDW_AT_byte_size                (int val){}
     private void setDW_AT_inline                   (int val){}
     private void setDW_AT_high_pc                  (int val){}
     private void setDW_AT_low_pc                   (int val){}
     private void setDW_AT_location                 (int val){}
     private void setDW_AT_segment                  (int val){}
     private void setDW_AT_address_class            (int val){}
     private void setDW_AT_data_member_location     (int val){}
     private void setDW_AT_artificial               (int val){}
     private void setDW_AT_calling_convention       (int val){}
     private void setDW_AT_const_value              (int val){}
     private void setDW_AT_constant_value           (int val){}
     private void setDW_AT_declaration              (int val){}
     private void setDW_AT_default_value            (int val){}
     private void setDW_AT_encoding                 (int val){}
     private void setDW_AT_external                 (int val){}
     private void setDW_AT_frame_base               (int val){}
     private void setDW_AT_count                    (int val){}
     private void setDW_AT_upper_bound              (int val){}
     private void setDW_AT_lower_bound              (int val){}
     private void setDW_AT_ordering                 (int val){}
     private void setDW_AT_prototyped               (int val){}
     private void setDW_AT_return_addr              (int val){}
     private void setDW_AT_variable_parameter       (int val){}
     private void setDW_AT_specification            (int val){}
     private void setDW_AT_static_link              (int val){}
     private void setDW_AT_stride_size              (int val){}
     private void setDW_AT_virtuality               (int val){}
     private void setDW_AT_visibility               (int val){}
     private void setDW_AT_vtable_elem_location     (int val){}

    //#########    GET GENERIC DWARF AT  (TYPE STRING)   ###########
     public String getString(int type){
        if (type == DWARFdef.DW_AT_name)
            return getDW_AT_name();
        return null;
     }

    //#########    SET GENERIC DWARF AT  (TYPE STRING)   ###########
     public void setString(int type, String s){
        if (type == DWARFdef.DW_AT_name)
            setDW_AT_name(s);
     }

    //#########    GET GENERIC DWARF AT  (TYPE INT)   ###########
     public int getInt(int type) {

       switch (type)  {
         case DWARFdef.DW_AT_type                : return getDW_AT_type                  ();

         case DWARFdef.DW_AT_sibling             : return getDW_AT_sibling               ();
         case DWARFdef.DW_AT_decl_file           : return getDW_AT_decl_file             ();
         case DWARFdef.DW_AT_decl_line           : return getDW_AT_decl_line             ();
         case DWARFdef.DW_AT_start_scope         : return getDW_AT_start_scope           ();
         case DWARFdef.DW_AT_bit_offset          : return getDW_AT_bit_offset            ();
         case DWARFdef.DW_AT_bit_size            : return getDW_AT_bit_size              ();
         case DWARFdef.DW_AT_byte_size           : return getDW_AT_byte_size             ();
         case DWARFdef.DW_AT_inline              : return getDW_AT_inline                ();
         case DWARFdef.DW_AT_high_pc             : return getDW_AT_high_pc               ();
         case DWARFdef.DW_AT_low_pc              : return getDW_AT_low_pc                ();
         case DWARFdef.DW_AT_location            : return getDW_AT_location              ();
         case DWARFdef.DW_AT_segment             : return getDW_AT_segment               ();
         case DWARFdef.DW_AT_address_class       : return getDW_AT_address_class         ();
         case DWARFdef.DW_AT_data_member_location: return getDW_AT_data_member_location  ();
         case DWARFdef.DW_AT_artificial          : return getDW_AT_artificial            ();
         case DWARFdef.DW_AT_calling_convention  : return getDW_AT_calling_convention    ();
         case DWARFdef.DW_AT_const_value         : return getDW_AT_const_value           ();
         case DWARFdef.DW_AT_constant_value      : return getDW_AT_constant_value        ();
         case DWARFdef.DW_AT_declaration         : return getDW_AT_declaration           ();
         case DWARFdef.DW_AT_default_value       : return getDW_AT_default_value         ();
         case DWARFdef.DW_AT_encoding            : return getDW_AT_encoding              ();
         case DWARFdef.DW_AT_external            : return getDW_AT_external              ();
         case DWARFdef.DW_AT_frame_base          : return getDW_AT_frame_base            ();
         case DWARFdef.DW_AT_count               : return getDW_AT_count                 ();
         case DWARFdef.DW_AT_upper_bound         : return getDW_AT_upper_bound           ();
         case DWARFdef.DW_AT_lower_bound         : return getDW_AT_lower_bound           ();
         case DWARFdef.DW_AT_ordering            : return getDW_AT_ordering              ();
         case DWARFdef.DW_AT_prototyped          : return getDW_AT_prototyped            ();
         case DWARFdef.DW_AT_return_addr         : return getDW_AT_return_addr           ();
         case DWARFdef.DW_AT_variable_parameter  : return getDW_AT_variable_parameter    ();
         case DWARFdef.DW_AT_specification       : return getDW_AT_specification         ();
         case DWARFdef.DW_AT_static_link         : return getDW_AT_static_link           ();
         case DWARFdef.DW_AT_stride_size         : return getDW_AT_stride_size           ();
         case DWARFdef.DW_AT_virtuality          : return getDW_AT_virtuality            ();
         case DWARFdef.DW_AT_visibility          : return getDW_AT_visibility            ();
         case DWARFdef.DW_AT_vtable_elem_location: return getDW_AT_vtable_elem_location  ();

         default : return -1;
       }
     }

    //#########    SET GENERIC DWARF AT  (TYPE INT)   ###########
     public void setInt(int type, int val){
        switch (type) {
           case DWARFdef.DW_AT_type                : {setDW_AT_type                  (val); break;}
           case DWARFdef.DW_AT_sibling             : {setDW_AT_sibling               (val); break;}
           case DWARFdef.DW_AT_decl_file           : {setDW_AT_decl_file             (val); break;}
           case DWARFdef.DW_AT_decl_line           : {setDW_AT_decl_line             (val); break;}
           case DWARFdef.DW_AT_start_scope         : {setDW_AT_start_scope           (val); break;}
           case DWARFdef.DW_AT_bit_offset          : {setDW_AT_bit_offset            (val); break;}
           case DWARFdef.DW_AT_bit_size            : {setDW_AT_bit_size              (val); break;}
           case DWARFdef.DW_AT_byte_size           : {setDW_AT_byte_size             (val); break;}
           case DWARFdef.DW_AT_inline              : {setDW_AT_inline                (val); break;}
           case DWARFdef.DW_AT_high_pc             : {setDW_AT_high_pc               (val); break;}
           case DWARFdef.DW_AT_low_pc              : {setDW_AT_low_pc                (val); break;}
           case DWARFdef.DW_AT_location            : {setDW_AT_location              (val); break;}
           case DWARFdef.DW_AT_segment             : {setDW_AT_segment               (val); break;}
           case DWARFdef.DW_AT_address_class       : {setDW_AT_address_class         (val); break;}
           case DWARFdef.DW_AT_data_member_location: {setDW_AT_data_member_location  (val); break;}
           case DWARFdef.DW_AT_artificial          : {setDW_AT_artificial            (val); break;}
           case DWARFdef.DW_AT_calling_convention  : {setDW_AT_calling_convention    (val); break;}
           case DWARFdef.DW_AT_const_value         : {setDW_AT_const_value           (val); break;}
           case DWARFdef.DW_AT_constant_value      : {setDW_AT_constant_value        (val); break;}
           case DWARFdef.DW_AT_declaration         : {setDW_AT_declaration           (val); break;}
           case DWARFdef.DW_AT_default_value       : {setDW_AT_default_value         (val); break;}
           case DWARFdef.DW_AT_encoding            : {setDW_AT_encoding              (val); break;}
           case DWARFdef.DW_AT_external            : {setDW_AT_external              (val); break;}
           case DWARFdef.DW_AT_frame_base          : {setDW_AT_frame_base            (val); break;}
           case DWARFdef.DW_AT_count               : {setDW_AT_count                 (val); break;}
           case DWARFdef.DW_AT_upper_bound         : {setDW_AT_upper_bound           (val); break;}
           case DWARFdef.DW_AT_lower_bound         : {setDW_AT_lower_bound           (val); break;}
           case DWARFdef.DW_AT_ordering            : {setDW_AT_ordering              (val); break;}
           case DWARFdef.DW_AT_prototyped          : {setDW_AT_prototyped            (val); break;}
           case DWARFdef.DW_AT_return_addr         : {setDW_AT_return_addr           (val); break;}
           case DWARFdef.DW_AT_variable_parameter  : {setDW_AT_variable_parameter    (val); break;}
           case DWARFdef.DW_AT_specification       : {setDW_AT_specification         (val); break;}
           case DWARFdef.DW_AT_static_link         : {setDW_AT_static_link           (val); break;}
           case DWARFdef.DW_AT_stride_size         : {setDW_AT_stride_size           (val); break;}
           case DWARFdef.DW_AT_virtuality          : {setDW_AT_virtuality            (val); break;}
           case DWARFdef.DW_AT_visibility          : {setDW_AT_visibility            (val); break;}
           case DWARFdef.DW_AT_vtable_elem_location: {setDW_AT_vtable_elem_location  (val); break;}
           default : break;
       }
  }
  */
}

