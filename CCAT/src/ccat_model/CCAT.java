
package ccat_model;

import ccat.FileLoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CCAT {
    
    public static void main(String args[]) throws FileNotFoundException {
        
        FileReader filereader = null;
        try {
            
            filereader = new FileReader(new File("questions.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CCAT.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FileLoader template = new FileLoader(filereader);
        template.loadTemplate();
        template.traverseMap();
        
    }
}
