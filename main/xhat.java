/*
 * Created on 10-gen-2012
 *
 * 
 * @author  Basile Stefano
 */
package main;

import common.Environ;

import ui.MainFrame;
import java.awt.event.WindowListener; 
import java.awt.event.WindowEvent; 
import java.io.File; 


/**
 * @author  Basile Stefano
 */
public class xhat implements WindowListener{

    
    Environ  environ;
    MainFrame frame;
    
    
    private void freeTemp() {
        
        try {
              String tempDir  = environ.getTempDir();
              File   dir      = new File(tempDir);
              File   file;
              File[] list = dir.listFiles();
              if (list.length != 0);
                      for (int i = 0; i < list.length; i++) {
                           if (list[i].isFile())
                               list[i].delete();
                         }
           System.out.println(tempDir + " Empty."); 
        } catch (Exception ex) {}
    }

//    private void freeTemp() {
//        
//        try {
//              String tempDir  = environ.getTempDir();
//              System.out.println("Freeing " + tempDir + "...."); 
//              String  RmCmd   = "\\rm  "  + tempDir + "*";                                
//              String[]  cmd   = {"/bin/sh", "-c", RmCmd};
//              Runtime runtime = Runtime.getRuntime();
//              Process proc    = runtime.exec(cmd);
//              proc.waitFor();    // wait end of delete
//              System.out.println(tempDir + " Empty."); 
//        } catch (Exception ex) {}
//    }
    
    
    public xhat () {
        environ = new Environ();
        freeTemp();
        frame   = new MainFrame(environ);
        frame.addWindowListener(this);
    }
    
    public void windowActivated  (WindowEvent e) {} 
    public void windowClosed     (WindowEvent e) {System.out.println("exit."); environ.Save(); System.exit(0);}
    public void windowClosing    (WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {} 
    public void windowDeiconified(WindowEvent e) {}
    public void windowIconified  (WindowEvent e) {}
    public void windowOpened     (WindowEvent e) {}
    
    public static void main(String[] args) {
        xhat appl = new xhat();
    }    
}
