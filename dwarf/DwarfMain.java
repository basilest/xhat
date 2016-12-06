/*
 * Created on Dec 18, 2011
 *
 *
 * @author  Basile Stefano
 */
package dwarf;

import javax.swing.tree.*;

import java.util.*;

import java.io.*;
import java.util.regex.*;
import common.*;
import ui.ElfFileChooser;
import treetable.*;
/**
 * @author   Basile Stefano
 */
public class DwarfMain {

    static final int SEARCH_BY_ID   = 1;
    static final int SEARCH_BY_NAME = 2;

    static final int LINE_OTHER = 0;
    static final int LINE_TAG   = 1;
    static final int LINE_AT    = 2;

    Vector      NodeArray;

    BufferedReader   readElfBuffFile;

    int         num_line;
    String      main_line;
    String      main_tail;
    byte        main_levNode;
    int         main_DWidx;
    int         main_DW_TAG;
    int         main_DW_AT;

    Pattern     pattNode;
    Pattern     pattTAG;
    Pattern     pattAT ;
    Pattern     pattOPuconst ;
    Pattern     pat_DW_AT_int16;
    Pattern     pat_DW_AT_int10;
    Pattern     pat_DW_AT_string;
    Pattern     patNumber;
    Matcher     m;

    Environ     environ;

	//######################################################
	public DwarfMain ( Environ environ) {

      this.environ = environ;
      NodeArray = new Vector (9000);
	}
    //######################################################
     public void GenerateFromObj () {
     ElfFileChooser elfChooser = environ.getElfChooser();
     elfChooser.displayDialog();

    String ObjectFileName = elfChooser.getFullFileName();
    if ( ObjectFileName != null) {
         try {
               System.out.println("reading FILE:" + ObjectFileName);
               String ElfFileName = environ.getTempFullFileName(elfChooser.getFileName()) + ".wi";
               String readElfCmd = "readelf -wi " + ObjectFileName + "> " +
                                    ElfFileName;

               String[] cmd = {"/bin/sh", "-c", readElfCmd};

               Runtime runtime = Runtime.getRuntime();
               Process proc    = runtime.exec(cmd);
               proc.waitFor();    // wait the process-end before opening the generated file
               InitDwarf(ElfFileName);
            } catch (Exception ex) {}
    }
  }
    //######################################################
     public void InitDwarf (String ElfFile) throws IOException  {
        initPattern();
        OpenElfFile(ElfFile);
        CreateNodeArray();
        readElfBuffFile.close();
    }
    //######################################################
    private void initPattern()
    {
//        sPattTAG         = DWARFdef.TAGname(DWARFdef.DW_TAG)+"(\\w+)";
//        sPattAT          = "^\\s*"+DWARFdef.ATname(DWARFdef.DW_AT)+"(\\w+)(.+)";
       String sPattTAG      = DWARFdef.STR_TAG_+"(\\w+)";
       String sPattAT       = "^\\s*"+DWARFdef.STR_AT_+"(\\w+)(.+)";
       String sPattOPuconst = DWARFdef.STR_OP_ + DWARFdef.STR_OP_plus_uconst + "[^\\d]*(\\d+)";

       pattNode         = Pattern.compile("^\\s*<(\\d+)><([0-9a-f]+)(.+)");
       pattTAG          = Pattern.compile(sPattTAG);
       pattAT           = Pattern.compile(sPattAT);
       pattOPuconst     = Pattern.compile(sPattOPuconst);
       patNumber        = Pattern.compile("(-?\\d+)");
       pat_DW_AT_int16  = Pattern.compile("<([^<]+)>[^>]*$");
       pat_DW_AT_int10  = Pattern.compile(":[^:\\d]*([\\d]+)[^\\d]*$");
       pat_DW_AT_string = Pattern.compile(":\\s*([^:]+[^\\s])\\s*$");

     //pat_DW_AT_string = Pattern.compile("[\\s:]([^\\s:]+)\\s*$");
    }
    //######################################################
    private void OpenElfFile( String fileName)
    {
        try {
              System.out.println("Opening:["+fileName+"]");

              readElfBuffFile  = new BufferedReader(new FileReader(new File(fileName)));
              num_line = 0;

        } catch (FileNotFoundException e) {
                 System.out.println("Unable to Open Elf File " + fileName);
          }
    }
    //######################################################
    private int testLine ()   {

         int lineType = LINE_OTHER;

         m = pattAT.matcher(main_line);
         if ( m.find())      // found 'DW_AT_'....
         {
              main_DW_AT = DWARFdef.ATid(m.group(1));
              main_tail  = m.group(2);
              lineType   = LINE_AT;
         }
         else
         {
           m = pattNode.matcher(main_line);
           if (m.find())      // found '<1><4fe5>'....
           {
                main_levNode = (byte) Integer.parseInt(m.group(1));       //<1>
                main_DWidx   = Integer.parseInt(m.group(2),16);    //<4fe5>
                main_tail    = m.group(3);
                m       = pattTAG.matcher(main_tail);
                if (m.find())  // found 'DW_TAG_'....
                {
                  main_DW_TAG = DWARFdef.TAGid(m.group(1));
                  lineType = LINE_TAG;
                }
           }
         }
         return lineType;
      }
    //######################################################
    private void  CreateNodeArray ( ) throws IOException
    {

       DWARFnode N = null;
       int lineType ;
       int num_line = 0;

       NodeArray.clear();       // clear from previous usage.

      try {
           while ((main_line = readElfBuffFile.readLine()) != null) {

//               System.out.println("line:"+num_line+" "+main_line);
               num_line++;
               lineType = testLine();

               switch(lineType) {
                 case LINE_TAG:
                      if (N != null) {
                          NodeArray.add(N);
                      }
                      N = createDW_Node(main_DW_TAG);
                      N.setDWidx(main_DWidx);
                      N.setLevel(main_levNode);
                      break;

                 case LINE_AT:
                      add_Node_AT (N, main_DW_AT, main_tail);
                      break;

                 default :
                     break;
               }
          }
          if (N != null)        //add last Node
              NodeArray.add(N);

          System.out.println(" Symbol Loaded (exit line="+num_line+
                             " Table Entries="+NodeArray.size()+")\n");
   } catch (IOException e) {
            System.out.println("Unable to parse Elf File at line "+num_line);
            }
 }
  //######################################################
  private void add_Node_AT (DWARFnode N, int AT, String tail)  {
    int val =1;
    if (N != null) {
      switch(AT) {
        case DWARFdef.DW_AT_name:
            m = pat_DW_AT_string.matcher(tail);
            if (m.find()) {
                add_Node_AT_String(N, AT, m.group(1));
            }
            break;

        case DWARFdef.DW_AT_type:
        case DWARFdef.DW_AT_sibling:
             m = pat_DW_AT_int16.matcher(tail);
             if (m.find()) {
                 val = Integer.parseInt(m.group(1),16);
                 add_Node_AT_int(N, AT, val);
             }
             break;

        case DWARFdef.DW_AT_data_member_location:
             m = pattOPuconst.matcher(tail);
             if (m.find()) {
                 val = Integer.parseInt(m.group(1),10);
                 add_Node_AT_int(N, AT, val);
             }
             break;

        default:
             m = pat_DW_AT_int10.matcher(tail);
             if (m.find()) {
                 val = Integer.parseInt(m.group(1));
                 add_Node_AT_int(N, AT, val);
             }
             break;
      }
    }
  }
  //######################################################
  private void add_Node_AT_int (DWARFnode N, int AT, int val)
  {
    switch(N.getType())
    {
      case DWARFdef.DW_TAG_base_type:
           ((DW_TAG_base_type             ) N).set_AT_int(AT, val);
           break;
      case DWARFdef.DW_TAG_subrange_type:
           ((DW_TAG_subrange_type         ) N).set_AT_int(AT, val);
           break;
      case DWARFdef.DW_TAG_array_type:
           ((DW_TAG_array_type            ) N).set_AT_int(AT, val);
           break;
      case DWARFdef.DW_TAG_const_type:
           ((DW_TAG_const_type            ) N).set_AT_int(AT, val);
           break;
      case DWARFdef.DW_TAG_pointer_type:
           ((DW_TAG_pointer_type          ) N).set_AT_int(AT, val);
           break;
      case DWARFdef.DW_TAG_volatile_type:
           ((DW_TAG_volatile_type         ) N).set_AT_int(AT, val);
           break;
      case DWARFdef.DW_TAG_constant:
           ((DW_TAG_constant              ) N).set_AT_int(AT, val);
           break;
      case DWARFdef.DW_TAG_formal_parameter:
           ((DW_TAG_formal_parameter      ) N).set_AT_int(AT, val);
           break;
      case DWARFdef.DW_TAG_variable:
           ((DW_TAG_variable              ) N).set_AT_int(AT, val);
           break;
      case DWARFdef.DW_TAG_enumeration_type:
           ((DW_TAG_enumeration_type      ) N).set_AT_int(AT, val);
           break;
      case DWARFdef.DW_TAG_enumerator:
           ((DW_TAG_enumerator            ) N).set_AT_int(AT, val);
           break;
      case DWARFdef.DW_TAG_structure_type:
           ((DW_TAG_structure_type        ) N).set_AT_int(AT, val);
           break;
      case DWARFdef.DW_TAG_union_type:
           ((DW_TAG_union_type            ) N).set_AT_int(AT, val);
           break;
      case DWARFdef.DW_TAG_member:
           ((DW_TAG_member                ) N).set_AT_int(AT, val);
           break;
      case DWARFdef.DW_TAG_typedef:
           ((DW_TAG_typedef               ) N).set_AT_int(AT, val);
           break;
      case DWARFdef.DW_TAG_subprogram:
           ((DW_TAG_subprogram            ) N).set_AT_int(AT, val);
           break;
      case DWARFdef.DW_TAG_subroutine_type:
           ((DW_TAG_subroutine_type       ) N).set_AT_int(AT, val);
           break;
      case DWARFdef.DW_TAG_unspecified_parameters:
           ((DW_TAG_unspecified_parameters) N).set_AT_int(AT, val);
           break;
      default : break;
    }
 }

 //######################################################
 private void add_Node_AT_String (DWARFnode N, int AT, String str)
 {
    switch(N.getType())
    {
      case DWARFdef.DW_TAG_base_type        :((DW_TAG_base_type       ) N).set_AT_string(AT, str); break;
      case DWARFdef.DW_TAG_subrange_type    :((DW_TAG_subrange_type   ) N).set_AT_string(AT, str); break;
      case DWARFdef.DW_TAG_array_type       :((DW_TAG_array_type      ) N).set_AT_string(AT, str); break;
      case DWARFdef.DW_TAG_constant         :((DW_TAG_constant        ) N).set_AT_string(AT, str); break;
      case DWARFdef.DW_TAG_formal_parameter :((DW_TAG_formal_parameter) N).set_AT_string(AT, str); break;
      case DWARFdef.DW_TAG_variable         :((DW_TAG_variable        ) N).set_AT_string(AT, str); break;
      case DWARFdef.DW_TAG_enumeration_type :((DW_TAG_enumeration_type) N).set_AT_string(AT, str); break;
      case DWARFdef.DW_TAG_enumerator       :((DW_TAG_enumerator      ) N).set_AT_string(AT, str); break;
      case DWARFdef.DW_TAG_structure_type   :((DW_TAG_structure_type  ) N).set_AT_string(AT, str); break;
      case DWARFdef.DW_TAG_union_type       :((DW_TAG_union_type      ) N).set_AT_string(AT, str); break;
      case DWARFdef.DW_TAG_member           :((DW_TAG_member          ) N).set_AT_string(AT, str); break;
      case DWARFdef.DW_TAG_typedef          :((DW_TAG_typedef         ) N).set_AT_string(AT, str); break;
      case DWARFdef.DW_TAG_subprogram       :((DW_TAG_subprogram      ) N).set_AT_string(AT, str); break;
      case DWARFdef.DW_TAG_subroutine_type  :((DW_TAG_subroutine_type ) N).set_AT_string(AT, str); break;
      default : break;
   }
 }
  //######################################################
  private boolean testNode ( DWARFnode N, int searchMode, int Nodeid, String Nodename) {

    boolean found = false;
    if ( N != null) {
       switch (searchMode) {
         case SEARCH_BY_ID:
              if ( Nodeid == main_DWidx)
                   found = true;
              break;
         case SEARCH_BY_NAME:
              String name;
              switch(N.getType()) {
                 case DWARFdef.DW_TAG_base_type        : name =((DW_TAG_base_type       )N).get_AT_string(DWARFdef.DW_AT_name); break;
                 case DWARFdef.DW_TAG_subrange_type    : name =((DW_TAG_subrange_type   )N).get_AT_string(DWARFdef.DW_AT_name); break;
                 case DWARFdef.DW_TAG_array_type       : name =((DW_TAG_array_type      )N).get_AT_string(DWARFdef.DW_AT_name); break;
                 case DWARFdef.DW_TAG_constant         : name =((DW_TAG_constant        )N).get_AT_string(DWARFdef.DW_AT_name); break;
                 case DWARFdef.DW_TAG_formal_parameter : name =((DW_TAG_formal_parameter)N).get_AT_string(DWARFdef.DW_AT_name); break;
                 case DWARFdef.DW_TAG_variable         : name =((DW_TAG_variable        )N).get_AT_string(DWARFdef.DW_AT_name); break;
                 case DWARFdef.DW_TAG_enumeration_type : name =((DW_TAG_enumeration_type)N).get_AT_string(DWARFdef.DW_AT_name); break;
                 case DWARFdef.DW_TAG_enumerator       : name =((DW_TAG_enumerator      )N).get_AT_string(DWARFdef.DW_AT_name); break;
                 case DWARFdef.DW_TAG_structure_type   : name =((DW_TAG_structure_type  )N).get_AT_string(DWARFdef.DW_AT_name); break;
                 case DWARFdef.DW_TAG_union_type       : name =((DW_TAG_union_type      )N).get_AT_string(DWARFdef.DW_AT_name); break;
                 case DWARFdef.DW_TAG_member           : name =((DW_TAG_member          )N).get_AT_string(DWARFdef.DW_AT_name); break;
                 case DWARFdef.DW_TAG_typedef          : name =((DW_TAG_typedef         )N).get_AT_string(DWARFdef.DW_AT_name); break;
                 case DWARFdef.DW_TAG_subprogram       : name =((DW_TAG_subprogram      )N).get_AT_string(DWARFdef.DW_AT_name); break;
                 case DWARFdef.DW_TAG_subroutine_type  : name =((DW_TAG_subroutine_type )N).get_AT_string(DWARFdef.DW_AT_name); break;
                 default : name = null; break;
              }
              if ( name != null &&
                 ( name.equals(Nodename)))
                   found = true;
              break;
         default:
              break;
       }
    }
    return found;
  }
  //######################################################
  private  int FindNodeByType (int NodeDWidx) {
        int i = 0;
        DWARFnode N;
        for (; i < NodeArray.size(); i++)
        {
              N = (DWARFnode)NodeArray.get(i);
              if (N.getDWidx() == NodeDWidx )
                  break;
        }
        return i;
    }
  //######################################################
  private  int FindNodeByName (String NodeName) {
        int i = 0;
        DWARFnode N;
        String name;
        for (; i < NodeArray.size(); i++)
        {
           N = (DWARFnode)NodeArray.get(i);
           switch(N.getType()) {
              case DWARFdef.DW_TAG_base_type        : name =((DW_TAG_base_type       )N).get_AT_string(DWARFdef.DW_AT_name); break;
              case DWARFdef.DW_TAG_subrange_type    : name =((DW_TAG_subrange_type   )N).get_AT_string(DWARFdef.DW_AT_name); break;
              case DWARFdef.DW_TAG_array_type       : name =((DW_TAG_array_type      )N).get_AT_string(DWARFdef.DW_AT_name); break;
              case DWARFdef.DW_TAG_constant         : name =((DW_TAG_constant        )N).get_AT_string(DWARFdef.DW_AT_name); break;
              case DWARFdef.DW_TAG_formal_parameter : name =((DW_TAG_formal_parameter)N).get_AT_string(DWARFdef.DW_AT_name); break;
              case DWARFdef.DW_TAG_variable         : name =((DW_TAG_variable        )N).get_AT_string(DWARFdef.DW_AT_name); break;
              case DWARFdef.DW_TAG_enumeration_type : name =((DW_TAG_enumeration_type)N).get_AT_string(DWARFdef.DW_AT_name); break;
              case DWARFdef.DW_TAG_enumerator       : name =((DW_TAG_enumerator      )N).get_AT_string(DWARFdef.DW_AT_name); break;
              case DWARFdef.DW_TAG_structure_type   : name =((DW_TAG_structure_type  )N).get_AT_string(DWARFdef.DW_AT_name); break;
              case DWARFdef.DW_TAG_union_type       : name =((DW_TAG_union_type      )N).get_AT_string(DWARFdef.DW_AT_name); break;
              case DWARFdef.DW_TAG_member           : name =((DW_TAG_member          )N).get_AT_string(DWARFdef.DW_AT_name); break;
              case DWARFdef.DW_TAG_typedef          : name =((DW_TAG_typedef         )N).get_AT_string(DWARFdef.DW_AT_name); break;
              case DWARFdef.DW_TAG_subprogram       : name =((DW_TAG_subprogram      )N).get_AT_string(DWARFdef.DW_AT_name); break;
              case DWARFdef.DW_TAG_subroutine_type  : name =((DW_TAG_subroutine_type )N).get_AT_string(DWARFdef.DW_AT_name); break;
              default : name = null; break;
           }
           if ( name != null &&
              ( name.equals(NodeName)))
                break;
        }
        return i;
    }
  //######################################################
  private DWARFnode createDW_Node (int TAG)
  {
      switch(TAG) {
        case DWARFdef.DW_TAG_base_type:             return  new DW_TAG_base_type();
        case DWARFdef.DW_TAG_subrange_type:         return  new DW_TAG_subrange_type();
        case DWARFdef.DW_TAG_array_type:            return  new DW_TAG_array_type();
        case DWARFdef.DW_TAG_const_type:            return  new DW_TAG_const_type();
        case DWARFdef.DW_TAG_pointer_type:          return  new DW_TAG_pointer_type();
        case DWARFdef.DW_TAG_volatile_type:         return  new DW_TAG_volatile_type();
        case DWARFdef.DW_TAG_constant:              return  new DW_TAG_constant();
        case DWARFdef.DW_TAG_formal_parameter:      return  new DW_TAG_formal_parameter();
        case DWARFdef.DW_TAG_variable:              return  new DW_TAG_variable();
        case DWARFdef.DW_TAG_enumeration_type:      return  new DW_TAG_enumeration_type();
        case DWARFdef.DW_TAG_enumerator:            return  new DW_TAG_enumerator();
        case DWARFdef.DW_TAG_structure_type:        return  new DW_TAG_structure_type();
        case DWARFdef.DW_TAG_union_type:            return  new DW_TAG_union_type();
        case DWARFdef.DW_TAG_member:                return  new DW_TAG_member();
        case DWARFdef.DW_TAG_typedef:               return  new DW_TAG_typedef();
        case DWARFdef.DW_TAG_subprogram:            return  new DW_TAG_subprogram();
        case DWARFdef.DW_TAG_subroutine_type:       return  new DW_TAG_subroutine_type();
        case DWARFdef.DW_TAG_unspecified_parameters:return  new DW_TAG_unspecified_parameters();
        default:   return new DWARFnode();
     }
  }

  /**
   *
   *
   *   S T A R T I N G        P U B L I C       U T I L I T I E S
   *
   *
   **/

//    //######################################################
//    private byte  ArrayLevels ( int arrayIdx) {
//        int  i;
//        byte lev = 0;
//        DWARFnode subrange;
//        for (i = arrayIdx + 1; i < NodeArray.size(); i++) {
//             subrange = (DWARFnode) NodeArray.get(i);
//             if ( subrange.getType() == DWARFdef.DW_TAG_subrange_type)
//                  lev++;
//             else
//                  break;
//        }
//        return lev;
//    }
    //######################################################
    private int   AddFunction( int                    idx,
                               DefaultMutableTreeNode father,
                               int                    Offset) {
        DWARFnode param;
        int       MemberSize;
        int       Size = 0;

        while (++idx < NodeArray.size())
        {
          param = (DWARFnode)NodeArray.get(idx);
          if (param.getType() == DWARFdef.DW_TAG_formal_parameter)
          {
             MemberSize = CreateTree(param, father, Offset);
             Size   += MemberSize;
             Offset += MemberSize;
          }
          else if (param.getLevel() != 2)
               break;
       }
       System.out.println("AddFunction "+Size);
       return Size;
    }
    //######################################################
    private int   AddTreeStruct( int                    idx,
                                 DefaultMutableTreeNode father,
                                 int                    Offset,
                                 boolean                aUnion) {
        DWARFnode member;
        int       MemberSize = 0;
        int       Size = 0;
        int       GivenOffset;
        int       Base = Offset;

        while (++idx < NodeArray.size())
        {
          member = (DWARFnode)NodeArray.get(idx);
          if (member.getType() == DWARFdef.DW_TAG_member)
          {
             if (aUnion) {
                 if (MemberSize > Size)
                     Size  = MemberSize;
             }
             else  {// a Struct
                Size   += MemberSize;
                GivenOffset    = ((DW_TAG_member)member).get_AT_int(DWARFdef.DW_AT_data_member_location);
                if (GivenOffset < 0 ) 
                    Offset += MemberSize;
                else
                    Offset  =  Base + GivenOffset;
             }

             MemberSize = CreateTree(member, father, Offset);
          }
          else if (member.getLevel() != 2)
               break;
       }
       return Size;
    }
    //######################################################
    private int CreateTree (DWARFnode N, DefaultMutableTreeNode father, int Offset) {

     DefaultMutableTreeNode  NewNode;
     TreeNodeInfo            FatherInfoNode  = (TreeNodeInfo) father.getUserObject();
     TreeNodeInfo            NewInfoNode;
     int                     idx = FindNodeByType(N.getDWidx());
     int                     Type;
     int                     TypeIdx;
     int                     GivenSize;
     int                     Size = 0;
     int                     ArrayElem;
     int                     i;

     int                     ArrayTOTElements;
     int                     Cardinality;
     DWARFnode               subrange;


     if ( idx < NodeArray.size())
     {
        switch(N.getType()) {

         /**  BASE TYPE  -- start */
         case DWARFdef.DW_TAG_pointer_type:
              Size     = ((DW_TAG_pointer_type)N).get_AT_int(DWARFdef.DW_AT_byte_size);
              FatherInfoNode.setNodeType(TreeNodeInfo.C_TYPE_POINTER);
              FatherInfoNode.CatTypeName("*");
              break;

         case DWARFdef.DW_TAG_base_type:
              Size     = ((DW_TAG_base_type)N).get_AT_int(DWARFdef.DW_AT_byte_size);
              FatherInfoNode.setNodeType(TreeNodeInfo.C_TYPE_BASE);
              FatherInfoNode.CatTypeName(((DW_TAG_base_type)N).get_AT_string(DWARFdef.DW_AT_name));
              break;

         case DWARFdef.DW_TAG_enumeration_type:
              Size     = ((DW_TAG_enumeration_type)N).get_AT_int(DWARFdef.DW_AT_byte_size);
              FatherInfoNode.setNodeType(TreeNodeInfo.C_TYPE_ENUM);
              FatherInfoNode.CatTypeName("enum");
              break;
         /**  BASE TYPE  -- end */

         /**  Only Forward -- start */
         case DWARFdef.DW_TAG_const_type:
              Type    = ((DW_TAG_const_type)N).get_AT_int(DWARFdef.DW_AT_type);
              TypeIdx = FindNodeByType (Type);
              FatherInfoNode.CatTypeName("const");
              Size    = CreateTree((DWARFnode)NodeArray.get(TypeIdx), father, Offset);
              break;

         case DWARFdef.DW_TAG_volatile_type:
              Type    = ((DW_TAG_volatile_type)N).get_AT_int(DWARFdef.DW_AT_type);
              TypeIdx = FindNodeByType (Type);
              FatherInfoNode.CatTypeName("volatile");
              Size    = CreateTree((DWARFnode)NodeArray.get(TypeIdx), father, Offset);
              break;

         case DWARFdef.DW_TAG_variable:
              Type     = ((DW_TAG_variable)N).get_AT_int(DWARFdef.DW_AT_type);
              TypeIdx  = FindNodeByType (Type);
              Size     = CreateTree((DWARFnode)NodeArray.get(TypeIdx), father, Offset);
              FatherInfoNode = (TreeNodeInfo) father.getUserObject();
              FatherInfoNode.setSize(Size);
              break;

         case DWARFdef.DW_TAG_typedef:
              Type     = ((DW_TAG_typedef)N).get_AT_int(DWARFdef.DW_AT_type);
              TypeIdx  = FindNodeByType (Type);
              Size     = CreateTree((DWARFnode)NodeArray.get(TypeIdx), father, Offset);
              FatherInfoNode = (TreeNodeInfo) father.getUserObject();
              FatherInfoNode.setSize(Size);
              break;
         /**  Only Forward -- end */

         case DWARFdef.DW_TAG_array_type:
              GivenSize = ((DW_TAG_array_type)N).get_AT_int(DWARFdef.DW_AT_byte_size);
              Type      = ((DW_TAG_array_type)N).get_AT_int(DWARFdef.DW_AT_type);
              TypeIdx   = FindNodeByType (Type);
              FatherInfoNode.setNodeType(TreeNodeInfo.C_TYPE_ARRAY);
              ArrayTOTElements = 1;
              for ( i=idx+1; i < NodeArray.size(); i++)
              {
                 subrange = (DWARFnode)NodeArray.get(i);
                 if ( subrange.getType() == DWARFdef.DW_TAG_subrange_type)
                 {
                    Cardinality = ((DW_TAG_subrange_type)subrange).get_cardinality();
                    ArrayTOTElements *= Cardinality;
                    FatherInfoNode.AddArraySubrange(Cardinality);
                    FatherInfoNode.CatTypeName("["+Cardinality+"]");
                 }
                 else break;
              }
//              treeTable.setArrayLevels(i-idx-1);

              NewInfoNode = new TreeNodeInfo(environ, TreeNodeInfo.C_TYPE_UNKNOWN);
              NewNode     = new DefaultMutableTreeNode (NewInfoNode);
              environ.getTreeTableModel().insertNodeInto(NewNode, father, father.getChildCount());
              Size = CreateTree((DWARFnode)NodeArray.get(TypeIdx), NewNode, Offset);
              if (GivenSize > 0)
                  Size = GivenSize;
              NewInfoNode.setSize  (Size);
              NewInfoNode.setOffset(Offset);

              Size *= ArrayTOTElements;
              FatherInfoNode.setSize  (Size);
              FatherInfoNode.setOffset(Offset);
              break;

         case DWARFdef.DW_TAG_member:
              NewInfoNode = new TreeNodeInfo(environ, TreeNodeInfo.C_TYPE_UNKNOWN);
              NewNode     = new DefaultMutableTreeNode (NewInfoNode);
              environ.getTreeTableModel().insertNodeInto(NewNode, father, father.getChildCount());
              NewInfoNode.setFieldName(((DW_TAG_member)N).get_AT_string(DWARFdef.DW_AT_name));
              GivenSize      = ((DW_TAG_member)N).get_AT_int(DWARFdef.DW_AT_byte_size);
              Type           = ((DW_TAG_member)N).get_AT_int(DWARFdef.DW_AT_type);
              TypeIdx        = FindNodeByType (Type);
              Size           = CreateTree((DWARFnode)NodeArray.get(TypeIdx), NewNode, Offset);
              if (GivenSize > 0)
                  Size = GivenSize;
              NewInfoNode.setSize(Size);
              NewInfoNode.setOffset(Offset);
              break;

         case DWARFdef.DW_TAG_formal_parameter:
              NewInfoNode = new TreeNodeInfo(environ, TreeNodeInfo.C_TYPE_UNKNOWN);
              NewNode     = new DefaultMutableTreeNode (NewInfoNode);
              environ.getTreeTableModel().insertNodeInto(NewNode, father, father.getChildCount());
              NewInfoNode.setFieldName(((DW_TAG_formal_parameter)N).get_AT_string(DWARFdef.DW_AT_name));
              Type           = ((DW_TAG_formal_parameter)N).get_AT_int(DWARFdef.DW_AT_type);
              TypeIdx        = FindNodeByType (Type);
              Size           = CreateTree((DWARFnode)NodeArray.get(TypeIdx), NewNode, Offset);
              NewInfoNode.setSize(Size);
              NewInfoNode.setOffset(Offset);
              break;

         case DWARFdef.DW_TAG_structure_type:
              FatherInfoNode.setNodeType(TreeNodeInfo.C_TYPE_STRUCT);
              FatherInfoNode.CatTypeName("struct {..}");
              GivenSize = ((DW_TAG_structure_type)N).get_AT_int(DWARFdef.DW_AT_byte_size);
              Size = AddTreeStruct(idx, father, Offset, false);
              if (GivenSize > 0)
                  Size = GivenSize;
              break;

         case DWARFdef.DW_TAG_union_type:
              FatherInfoNode.setNodeType(TreeNodeInfo.C_TYPE_UNION);
              FatherInfoNode.CatTypeName("union {..}");
              GivenSize = ((DW_TAG_union_type)N).get_AT_int(DWARFdef.DW_AT_byte_size);
              Size = AddTreeStruct(idx, father, Offset, true);
              if (GivenSize > 0)
                  Size = GivenSize;
              break;

         case DWARFdef.DW_TAG_subprogram:
              FatherInfoNode.setNodeType(TreeNodeInfo.C_TYPE_FUN);
              FatherInfoNode.CatTypeName("(..)");
              Size = AddFunction(idx, father, Offset);
              FatherInfoNode.setSize(Size);
              System.out.println("subprogram "+Size);
              break;

      // case DWARFdef.DW_TAG_subrange_type:
      // case DWARFdef.DW_TAG_constant:
      // case DWARFdef.DW_TAG_enumerator:
      // case DWARFdef.DW_TAG_subroutine_type:
      // case DWARFdef.DW_TAG_unspecified_parameters:
         default :
              break;
        }
      }
      return Size;
    }
 //######################################################
 public void PopulateVector (String regx, int VecorType, Vector JBoxNamesVector) {

   int       i;
   DWARFnode N;
   int       type;
   String    name;
   Pattern   pat;


   if (regx == null)
       regx = "^[^ ]";

   pat = Pattern.compile (regx);

   for (i=0; i < NodeArray.size(); i++)
   {
     N = (DWARFnode)NodeArray.get(i);
     type = N.getType();
     if ( type == VecorType) {
       switch(type) {
          case DWARFdef.DW_TAG_variable:
          name = ((DW_TAG_variable)N).get_AT_string(DWARFdef.DW_AT_name);
          break;

          case DWARFdef.DW_TAG_typedef:
          name = ((DW_TAG_typedef )N).get_AT_string(DWARFdef.DW_AT_name);
          break;

          case DWARFdef.DW_TAG_subprogram:
          name = ((DW_TAG_subprogram )N).get_AT_string(DWARFdef.DW_AT_name);
          break;

          case DWARFdef.DW_TAG_enumeration_type:
          default : name = null;
          break;
       }
       if (name != null) {
           m = pat.matcher(name);
           if(m.find())
               if (JBoxNamesVector.contains(name) == false) {
                   JBoxNamesVector.add(name);
               }
       }
     }
   }
}
 //######################################################
 public void  ChangeJtree (String VarName) {

     int  TreeSize;
     int  idx = FindNodeByName(VarName);

     if ( idx < NodeArray.size()) {
//         TreeSize = CreateTree((DWARFnode)NodeArray.get(idx), environ.getSymbolTree().getVarRoot(), 0);

         environ.getSymbolTree().resetTree();
         DefaultMutableTreeNode  root  = environ.getSymbolTree().getTreeRoot();
         TreeNodeInfo            rinfo = (TreeNodeInfo) root.getUserObject();
         rinfo.setFieldName(VarName);
         TreeSize = CreateTree((DWARFnode)NodeArray.get(idx), root, 0);
         System.out.println("ChangeJTree "+TreeSize);

         //        String VarName = item.getCTypeName();
//        int  TreeSize;
//        int  idx = FindNodeByName(VarName);
//        TreeNodeInfo  infoNode;
//
//        if ( idx < NodeArray.size()) {
//            treeTable.reInitTable(VarName, idx);
//            TreeSize = CreateTree((DWARFnode)NodeArray.get(idx), treeTable.getTreeVarRoot(), 0);
//            item.changeFile(TreeSize);
//            treeTable.UpdateTable(item);
      }
    }
 //######################################################
// public  int getTreeSize() {
//         return treeTable.getTreeSize();
//    }
//
// //######################################################
// public  JScrollPane createTreeScrollPane () {
//         return treeTable.createScrollPane();
//    }

 //######################################################
//  private void   DumpToDataBase( DefaultMutableTreeNode treenode, int level) {
//
//      TreeNodeInfo             infonode;
//      int                      Size;
//      int                      Offset;
//      int                      TypeInTable;
//      int                      ArrayType;
//      int                      idx;
//      int                      i;
//      DWARFnode                DWnode;
//      DWARFnode                subrange;
//      DefaultMutableTreeNode   son;
//
//      try {
//        if (treenode != null)  {
//
//        infonode = (TreeNodeInfo) treenode.getUserObject();
//        Size     = infonode.getSize();
//        Offset   = infonode.getOffset();
//
//        if (treenode.isLeaf() == true) {
//
//            idx      =  infonode.getBaseTypeIdx();
//            if ( idx > 0 && idx < NodeArray.size())
//            {
//              DWnode   = (DWARFnode)NodeArray.get(idx);
//
//              switch (DWnode.getType()) {
//
//              case DWARFdef.DW_TAG_enumeration_type:
//              case DWARFdef.DW_TAG_base_type:
//                   DataBaseconnection.PopulateTableTemplate(TemplateTableName,
//                                                            level, Defines.C_TYPE_BASE, Size, Offset);
//                   break;
//              case DWARFdef.DW_TAG_pointer_type:
//                   DataBaseconnection.PopulateTableTemplate(TemplateTableName,
//                                                            level, Defines.C_TYPE_POINTER, Size, Offset);
//                   break;
//
//              default:
//                   break;
//              }
//            }
//          }
//          else {
//          if (infonode.isUnion())
//               DataBaseconnection.PopulateTableTemplate(TemplateTableName,
//                                                        level, Defines.C_TYPE_UNION, Size, Offset);
//          else {
//            idx  =  infonode.getIdx();
//            if ( idx > 0 && idx < NodeArray.size())
//            {
//              if ( infonode.getLevel() == 0) {  //* an ARRAY
//                   DataBaseconnection.PopulateTableTemplate(TemplateTableName,
//                                                            level, Defines.C_TYPE_ARRAY, Size, Offset);
//                   for (i = idx+1,  son = treenode;
//                        i > 0 && i < NodeArray.size();
//                        i++, son = (DefaultMutableTreeNode) son.getFirstChild()) {
//
//                        subrange = (DWARFnode) NodeArray.get(i);
//
//                        if ( subrange.getType() == DWARFdef.DW_TAG_subrange_type) {
//                             Size = ((DW_TAG_subrange_type)subrange).get_cardinality();
//                             DataBaseconnection.PopulateTableTemplate(TemplateTableName,
//                                                                      level+1,
//                                                                      Defines.C_TYPE_RANGE, Size, Offset);
//                        }
//                        else break;
//
//                        DumpToDataBase(son, level+1); // add Type (for the array)
//                   }
//              }
//              else {
//                   DataBaseconnection.PopulateTableTemplate(TemplateTableName,
//                                                            level, Defines.C_TYPE_STRUCT, Size, Offset);
//
//                   for( son  = (DefaultMutableTreeNode) treenode.getFirstChild();
//                        son != null;
//                        son  = (DefaultMutableTreeNode) treenode.getChildAfter(son))
//                   {
//                        DumpToDataBase(son, level+1); // add each member
//                   }
//                }
//              }
//            } // Not a Union
//          }   // Not a Leaf
//      }
//    } catch (Exception e) {e.printStackTrace();}
//  }


}





