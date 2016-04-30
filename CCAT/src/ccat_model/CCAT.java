/**
 * Copyright (C) John Mulvany-Robbins, Elliott Sobek, Zac Batog.
 * Github profiles:
 * John Mulvany-Robbins (https://github.com/reboss),
 * Elliott Sobek (https://github.com/ElliottSobek),
 * Zac Batog (https://github.com/batogz) 
 */

package ccat_model;

import ccat.FileLoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author John
 */
public class CCAT {

    /**
     *
     * @param args
     * @throws FileNotFoundException
     */
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
