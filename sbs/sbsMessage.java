/*
 * Created on Dec 18, 2011
 *
 * 
 * @author  Basile Stefano
 */
package  sbs;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.io.*;
/**
 * @author   Basile Stefano
 */

public class sbsMessage extends sbs_generic {
    
    private  sbsTask  task1;
    private  sbsTask  task2;
    
    private int  arrowHigh = 8;
    private int  textHigh  = 20;
    
    String  user_name;
    String  CTypeName;
    int     CTypeSize;
   //####################
   public sbsMessage( sbsTest test, int num, sbsTask t1, sbsTask t2, int y){
        super(test, num, sbs_generic.MESSAGE, y);
        user_name = "msg ???";
        CTypeName = null;
        CTypeSize = 0;
        super.setIntVar(V_INT_YUPRSV,  arrowHigh + textHigh);
        super.setIntVar(V_INT_YDOWNRSV,arrowHigh);
        task1 = t1;
        task2 = t2;
    }
   //####################
   public sbsMessage(sbsTest test, int num){
        super(test, num, sbs_generic.MESSAGE, 0);
        user_name = null;
        CTypeName = null;
        CTypeSize = 0;
        task1     = null;
        task2     = null;
        super.setIntVar(V_INT_YUPRSV,  arrowHigh + textHigh);
        super.setIntVar(V_INT_YDOWNRSV,arrowHigh);
     }
    //####################
    public sbsMessage (sbsMessage ori, int num, int y) {
         super(ori, num, y);
         user_name      = ori.getStringVar(sbs_generic.V_STR_USERNAME);
         CTypeName      = ori.getStringVar(sbs_generic.V_STR_CTYPENAME);
         CTypeSize      = ori.getIntVar(sbs_generic.V_INT_CTYPESIZE);         
         task1          = ori.getTask1();  
         task2          = ori.getTask2();  
    }
    //#####################################
    public void setIntVar (int varType, int val) {
           switch(varType) {
               case V_INT_TEXT_H:     textHigh  = val;   break;
               case V_INT_CTYPESIZE:  CTypeSize = val;   break;
               default : super.setIntVar(varType, val);
           }
    }
    //#####################################
    public int getIntVar (int varType) {
           switch(varType) {
               case V_INT_TEXT_H:     return textHigh; 
               case V_INT_CTYPESIZE:  return CTypeSize;
               default :              return super.getIntVar(varType);
           }
    }
    //#####################################
    public void setStringVar (int varType, String val) {
           switch(varType) {
               case V_STR_USERNAME:      user_name  = val;    break;
               case V_STR_CTYPENAME:     CTypeName  = val;    break;
               default :  super.setStringVar(varType, val);   break;
           }
    }
    //#####################################
    public String getStringVar (int varType) {
           switch(varType) {
               case V_STR_USERNAME:      return user_name;
               case V_STR_CTYPENAME:     return CTypeName;
               default :                 return super.getStringVar(varType);
           }
    }
    //####################
    public void  setTask1 (sbsTask task1){
        this.task1 = task1;
    }
    //####################
    public void  setTask2 (sbsTask task2){
        this.task2 = task2;
    }
    //####################
    public sbsTask  getTask1 (){
        return task1;
    }
    //####################
    public sbsTask  getTask2 (){
        return task2;
    }
    //####################
    public boolean isaSend () {
           return  task2.isMainTask();
    }
    //####################
    public boolean concernTask (sbsTask task){
        return (task1 == task || task2 == task);
    }
    //####################
    public void exportInTestFile (FileWriter out) {
       try {
           super.export(out);
           out.write (ParseTestFile.T_STR_ITMUNAME  + user_name + "\n");
           out.write (ParseTestFile.T_STR_ITMCNAME  + CTypeName + "\n");
//           out.write (ParseTestFile.T_STR_ITMDSCKEY + DBDesckey + "\n");
           out.write (ParseTestFile.T_STR_ITMDATKEY + DBDatakey + "\n");
           out.write (ParseTestFile.T_STR_ITMTSK1   + test.getTaskPosition(task1) + "\n");
           out.write (ParseTestFile.T_STR_ITMTSK2   + test.getTaskPosition(task2) + "\n");
           out.write (ParseTestFile.T_STR_ITMSIZE   + CTypeSize + "\n");
           out.write (ParseTestFile.T_STR_ITMEND    + "\n\n");
        } catch (Exception e) {System.out.println("Unable to export Message "+ user_name);}  
    }       

//    ####################
//    public void export2Lis (FileWriter out) {
//       try {
//           String line = isaSend() ?  "\nsend\n" : "\nreceive\n";  
//           line += getIntVar(sbs_generic.V_INT_CTYPESIZE) + "\n";
//           out.write(line);
//        } catch (Exception e) {System.out.println("Unable to export to Lis Message "+ user_name);}  
//    }       
//
    /*
     * 
     *          +----------------------------      ----------------+
     * textHigh |     M S G _ N A M E                              |
     *          +------------------------------------+             | Y_UpReserved
     *                                         \     | arrowHigh   |
     *      Y   +------------------------------->   -+  -----------+
     *                                         /     | arrowHigh   | Y_DownReserved
     *          +------------------------------------+          ---+
     * 
     */
    //####################
   public void paintItem (Graphics g) {

	 int x1 = task1.getLineX();
	 int x2 = task2.getLineX();
     int m  = (x1 < x2) ? -arrowHigh : arrowHigh;
     int index; 
	
	 int arrowXPoints[] = {x2, x2+m, x2+m,x2};    // points for
 	 int arrowYPoints[] = {Y,   Y-m, Y+m, Y};     // arrow-triangle

  	 Graphics2D g2 = (Graphics2D) g;
     Color oldColor = g2.getColor();
	 FontRenderContext frc = g2.getFontRenderContext();
     TextLayout textL = new TextLayout(user_name, font, frc);
     g2.setColor(color);
	 textL.draw(g2, x1+5, Y - arrowHigh);
     Line2D.Double line = new Line2D.Double(x1, Y, x2, Y);
     g2.draw(line);
    

     GeneralPath filledPolygon = new  GeneralPath(GeneralPath.WIND_EVEN_ODD,
                                                  arrowXPoints.length);
     filledPolygon.moveTo(arrowXPoints[0],
     					  arrowYPoints[0]);
    
     for (index = 1;
    	  index < arrowXPoints.length;
    	  index++) 	{
    	  filledPolygon.lineTo(arrowXPoints[index], arrowYPoints[index]);
     };
    
     filledPolygon.closePath();
     g2.setPaint(Color.red);
     g2.fill(filledPolygon);

     g2.setColor(oldColor);   // restore Graphics
    }
}
