/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.datomala.listener.ApplicationListener;
import org.foi.nwtis.datomala.model.JMSMessageMail;
import org.foi.nwtis.datomala.model.JMSMessageZip;

/**
 *
 * @author dex
 */
public class RecordSerialization {
    public static List<JMSMessageMail> recordMail = new ArrayList<JMSMessageMail>();
    public static List<JMSMessageZip> recordZip = new ArrayList<JMSMessageZip>();


    public static void serializeToFileMail(String filename, List<JMSMessageMail> list){
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        
        try {
            fileOutputStream = new FileOutputStream(ApplicationListener.path + File.separator + filename);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(list);
        } catch (IOException ex) {
            System.out.println("Failed to serialize.");
            return;
        }
        finally{
            try {
                if (objectOutputStream != null)
                    objectOutputStream.close();
                if (fileOutputStream != null)
                    fileOutputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(RecordSerialization.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Serialized.");
        System.out.println(ApplicationListener.path + File.separator + filename);
    }
    

    public static void serializeToFileZip(String filename, List<JMSMessageZip> list){
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        
        try {
            fileOutputStream = new FileOutputStream(ApplicationListener.path + File.separator + filename);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(list);
        } catch (IOException ex) {
            System.out.println("Failed to serialize.");
        }
        finally{
            try {
                if (objectOutputStream != null)
                    objectOutputStream.close();
                if (fileOutputStream != null)
                    fileOutputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(RecordSerialization.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void deserializeJMSMail(String filename){
        List<JMSMessageMail> records = new ArrayList<JMSMessageMail>();
        FileInputStream fileInputStream = null;
        ObjectInputStream in = null;
        try {
            fileInputStream = new FileInputStream(ApplicationListener.path + File.separator + filename);
            in = new ObjectInputStream(fileInputStream);
            records =  (List<JMSMessageMail>) in.readObject();
        } catch (IOException ex){
            
        } catch(ClassNotFoundException ex2){
            
        } catch (ClassCastException ex3){
            
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
                if (in != null)
                    in.close();
            } catch (IOException ex) {
                Logger.getLogger(RecordSerialization.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (records != null)
            RecordSerialization.recordMail = records;
        else{
            System.out.println("File is empty.");
        }
        System.out.println(ApplicationListener.path + File.separator + filename);
    }
    

    public static void deserializeJMSZip(String filename){
        List<JMSMessageZip> records = new ArrayList<JMSMessageZip>();
        FileInputStream fileInputStream = null;
        ObjectInputStream in = null;
        try {
            fileInputStream = new FileInputStream(ApplicationListener.path + File.separator + filename);
            in = new ObjectInputStream(fileInputStream);
            records =  (List<JMSMessageZip>) in.readObject();
        } catch (IOException ex){
            
        } catch(ClassNotFoundException ex2){
            
        } catch (ClassCastException ex3){
            
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
                if (in != null)
                    in.close();
            } catch (IOException ex) {
                Logger.getLogger(RecordSerialization.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (records != null)
            RecordSerialization.recordZip = records;
        else{
            System.out.println("File is empty.");
        }
    }
}
