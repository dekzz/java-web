
package org.foi.nwtis.datomala.assignment_1;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Prints out the recorded objects from serialized file
 * 
 * @author dex
 */
public class PrintRecords {

    private String fileName;
    
    /**
     * Gets serialized file name via constructor
     * 
     * @param fileName - name of serialized file containing record objects
     */
    public PrintRecords(String fileName) {
        this.fileName = fileName;
    }
    
    /**
     * Opens up serialized file and deserialize object into array,
     * afterwards prints out the data and closes the file
     * 
     */
    public void print() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        List<Records> records = new ArrayList<>();
        try {
            fis = new FileInputStream(fileName);
            ois = new ObjectInputStream(fis);
            records = (ArrayList<Records>)ois.readObject();
            ois.close();
            fis.close();
            System.out.println("server" + " Records loaded!");
            Log.getInstance().writeLog("server" + " Records loaded!");
            
            for (Records rec:records) {
                System.out.println(rec.getUser() + " " + rec.getCommand() + " " + rec.getTime());
            }
            
        } catch (ClassNotFoundException | IOException | NullPointerException ex) {
            Logger.getLogger(PrintRecords.class.getName()).log(Level.SEVERE, null, ex);
            Log.getInstance().writeLog("print" + " Could not read from " + fileName);
        } finally {
            try {                
                ois.close();
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(PrintRecords.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
