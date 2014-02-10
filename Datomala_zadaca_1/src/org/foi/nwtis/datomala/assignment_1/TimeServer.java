
package org.foi.nwtis.datomala.assignment_1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.datomala.config.Configuration;
import org.foi.nwtis.datomala.config.ConfigurationAbstract;
import org.foi.nwtis.datomala.config.NoConfiguration;

/**
 * Acts as a server which works with, and responds to various
 * user requests
 * 
 * @author dex
 */
public class TimeServer {
    
    private int port;
    private String configFileName;
    private boolean loadNeeded;
    private String serializationFileName;
    private Configuration config;
    
    private static boolean end = false;
    private static boolean pause = false;

    /**
     *
     * @param port - Port on which will server respond
     * @param configFileName - Server configuration file name
     * @param loadNeeded - If it is needed to load serialized record objects
     * @param serializationFileName - Name of a serialization file
     */
    public TimeServer(int port, String configFileName, boolean loadNeeded, String serializationFileName) {
        this.port = port;
        this.configFileName = configFileName;
        this.loadNeeded = loadNeeded;
        this.serializationFileName = serializationFileName;
        
        try {
            config = ConfigurationAbstract.loadConfig(configFileName);
        } catch (NoConfiguration ex) {
            Logger.getLogger(TimeServer.class.getName()).log(Level.SEVERE, null, ex);
            Log.getInstance().writeLog("server " + " Could not load config file! " + ex);
        }
    }
    
    /**
     * Handles user requests and dedicates a work thread to each of them.
     * If needed, loads serialized objects from file.
     * On shutdown serializes all record objects.
     * 
     * @throws InterruptedException
     */
    public void startTimeServer() throws InterruptedException {
        try {
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(1000);
            
            if (loadNeeded) {
                try {
                    FileInputStream fis = new FileInputStream(serializationFileName);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    Records.records = (ArrayList<Records>)ois.readObject();
                    ois.close();
                    fis.close();
                    System.out.println("server" + " Records loaded!");
                    Log.getInstance().writeLog("server" + " Records loaded!");
                } catch (IOException e) {
                    Logger.getLogger(TimeServer.class.getName()).log(Level.SEVERE, null, e);
                    Log.getInstance().writeLog("server" + " Could not open file: " + serializationFileName);
                }
            }
            
            while (!end) {
                try {
                Socket client = server.accept();
                System.out.println("Client spotted!");
                Log.getInstance().writeLog("server" + " Client spotted!");
                TimeServerThread thread = new TimeServerThread
                                    (client, serializationFileName, config);
                thread.start();
                } catch (SocketTimeoutException e) {
                    //continue
                }
            }
            
            if (end) {
                try {
                    FileOutputStream fos = new FileOutputStream(serializationFileName);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(Records.records); 
                    oos.close();
                    fos.close();
                    System.out.println("server" + " Record serialized!");
                    Log.getInstance().writeLog("server" + " Record serialized!");
                } catch (IOException e) {
                    Logger.getLogger(TimeServer.class.getName()).log(Level.SEVERE, null, e);
                    Log.getInstance().writeLog("server" + " Could not open file: " + serializationFileName);
                }
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TimeServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TimeServer.class.getName()).log(Level.SEVERE, null, ex);
            Log.getInstance().writeLog("server " + ex);
        }
    }

    /**
     * Used for check if server is stopped
     * 
     * @return true if stopped. false otherwise
     */
    public static boolean isEnd() {
        return end;
    }

    /**
     * Used to set server online/offline status
     * 
     * @param end - if true server is working, otherwise shuts down the server
     */
    public static void setEnd(boolean end) {
        TimeServer.end = end;
    }

    /**
     * Used to check if server is in pause mode
     * 
     * @return true if paused, false otherwise
     */
    public static boolean isPause() {
        return pause;
    }

    /**
     * used to set server working status
     * 
     * @param pause - if true server is paused, otherwise not
     */
    public static void setPause(boolean pause) {
        TimeServer.pause = pause;
    }
    
}
