/*
 * Created on Dec 18, 2011
 *
 * 
 * @author  Basile Stefano
 */
package  ui;

import common.Environ;

import java.io.*;
import javax.swing.*;

/**
 * @author   Basile Stefano
 */

/*
 * ElfFileChooser.java
 */
//####################
//####################
public class ElfFileChooser  {

    JFileChooser        fc;
    ExtensionFileFilter Filter;
    File                ElfFile;
    Environ             environ;
    
    //####################
    public ElfFileChooser(Environ environ, String extensions[], String description) {

        this.environ = environ;
        
        fc = new JFileChooser("./");
        //fc.setFileSelectionMode(JFileChooser.FILES_ONLY);             // the default mode
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        if ( extensions != null && extensions.length != 0 )
            Filter =  new ExtensionFileFilter (extensions, description);

        fc.setFileFilter(Filter);
    }
    
    //####################
    public void displayDialog () {
        int returnVal = fc.showOpenDialog(environ.getMainFrame());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            ElfFile = fc.getSelectedFile();
        } else {
            ElfFile = null;
        }
    }
    //####################
    public String  getFileName () {
           return  (( ElfFile != null ) ? ElfFile.getName() : null);
    }
    //####################
    public String  getFullFileName () {
           return  (( ElfFile != null ) ? ElfFile.getPath() : null);
    }
} //  ElfFileChooser
