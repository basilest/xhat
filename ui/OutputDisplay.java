/*
 * Created on Dec 18, 2011
 *
 * 
 * @author  Basile Stefano
 */
package  ui;
import java.io.*;

import javax.swing.*;
import java.awt.*;

import common.*;
/**
 * @author   Basile Stefano
 */

/*
 * MainFrame.java application that requires:
 *   sbsTest.java
 */
public class OutputDisplay {

    JFrame       OutputFrame;
    JPanel       contentPane;
    
    JTextArea    tArea;
    JScrollPane  tScrollPane;

     Environ      environ;

     int          frameX;
     int          frameY;
     int          frameWidth;
     int          frameHeight;

    //######################################################
    public OutputDisplay(Environ environ) {
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenSizeWidth      = screenSize.width; 
        int screenSizeHeight     = screenSize.height;

        frameX        = screenSizeWidth/3 * 2 - 15;
        frameY        = screenSizeHeight/3;
        frameWidth    = screenSizeWidth/3 - 2;
        frameHeight   = screenSizeHeight/3 * 2 - 80;

        contentPane  = new JPanel(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(frameWidth, frameHeight));

        tArea = new JTextArea();
        tArea.setEditable(false);
        tArea.setMargin(new Insets(3,5,3,4));
        tArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
//        tArea.setFont(new Font("Courier", Font.PLAIN, 10));
        tScrollPane = new JScrollPane(tArea);
             
        tScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        tScrollPane.setPreferredSize(new Dimension(150, 150));
        tScrollPane.setBorder(BorderFactory.createLoweredBevelBorder());
    
        contentPane.add(tScrollPane, BorderLayout.CENTER);
 }
    //######################################################
      public void DisplayFrame (String FrameTitle, String FileName) {
        OutputFrame = new JFrame (FrameTitle);
        OutputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        try {
             FileReader in = new FileReader(FileName);
             tArea.read(in, null);
             in.close();
        } catch (IOException e) { }
        
        OutputFrame.setContentPane(contentPane);
        OutputFrame.setBounds(frameX, frameY, frameWidth, frameHeight);
        OutputFrame.pack();
        OutputFrame.setVisible(true);
      }

} // end class OutputDisplay
