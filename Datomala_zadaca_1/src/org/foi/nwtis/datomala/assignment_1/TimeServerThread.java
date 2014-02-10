
package org.foi.nwtis.datomala.assignment_1;

import java.io.*;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.datomala.config.Configuration;

/**
 * Handles user requests
 * 
 * @author dex
 */
public class TimeServerThread extends Thread{
    
    private Socket client;
    private String serializationFileName;
    private Configuration config;
    
    static Date newTime;
    static Date serverTime;
    static long deltaTime;

    /**
     *
     * @param client - Socket for communication with client
     * @param serializationFileName - Name of serialization file
     * @param config - Name of configuration file
     */
    public TimeServerThread(Socket client, String serializationFileName, Configuration config) {
        this.client = client;
        this.serializationFileName = serializationFileName;
        this.config = config;
    }

    /**
     *
     */
    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
        System.out.println("Client thread started...");
        Log.getInstance().writeLog("server" + " Client thread started!");
    }

    /**
     * Receives user/admin command, processes it, do the requested and responds with status to client.
     * Works with commands like: GETTIME, SETTIME, START, STOP, PAUSE, CLEAN.
     * 
     */
    @Override
    public void run() {
        InputStream is = null;
        OutputStream os = null;
        
        try {
            is = client.getInputStream();
            os = client.getOutputStream();
            
            StringBuilder command = new StringBuilder();
            int letter;
            while ((letter = is.read()) != -1 || is.available() > 0) {
                command.append((char) letter);
            }
            System.out.println("Command: " + command);
            Log.getInstance().writeLog("server" + " received: " + command);
            try {
                Records record = new Records("server", command.toString(), Assignment_1.sdf.parse(Assignment_1.sdf.format(new Date())));
                Records.add(record);
            } catch (ParseException | NullPointerException ex) {
                Logger.getLogger(TimeServerThread.class.getName()).log(Level.SEVERE, null, ex);
                Log.getInstance().writeLog("server" + " Could not make record!");
            }
            
            String response = "";
            if (command.indexOf("GETTIME") != -1) {
                if (!TimeServer.isPause()) {
                    serverTime = new Date((System.currentTimeMillis() - deltaTime));
                    response = "OK " + serverTime;
                    Log.getInstance().writeLog("server" + " answers: " + response);
                } else {
                    response = "Server paused!";
                    Log.getInstance().writeLog("server" + " answers: " + response);
                }
            } else if (command.indexOf("SETTIME") != -1) {
                try {
                    String [] requestedTime = command.toString().split("-t ");
                    newTime = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(requestedTime[1].replace(";", ""));
                    deltaTime = System.currentTimeMillis() - newTime.getTime();
                    response = "OK Setting time to: " + newTime.toString();
                } catch (ParseException ex) {
                    Logger.getLogger(TimeServerThread.class.getName()).log(Level.SEVERE, null, ex);
                    response = "ERROR: Could not set new time!";
                }
                Log.getInstance().writeLog("server" + " answers: " + response);
            } else if (command.indexOf("STOP") != -1) {
                if(!TimeServer.isEnd()) {
                    TimeServer.setEnd(true);
                    response = "OK Stopping server";
                    Log.getInstance().writeLog("server" + " answers: " + response);
                    interrupt();
                } else {
                    response = "ERROR: Server already stopped!";
                    Log.getInstance().writeLog("server" + " answers: " + response);
                }
            } else if (command.indexOf("PAUSE") != -1) {
                if (TimeServer.isPause()) {
                    response = "ERROR: Server already paused!";
                    Log.getInstance().writeLog("server" + " answers: " + response);
                } else {
                    response = "OK Pausing server";
                    Log.getInstance().writeLog("server" + " answers: " + response);
                    TimeServer.setPause(true);
                }
            } else if (command.indexOf("START") != -1) {
                if (TimeServer.isPause()) {
                    TimeServer.setPause(false);
                    response = "OK Resuming server";
                    Log.getInstance().writeLog("server" + " answers: " + response);
                } else {
                    response = "ERROR: Server not paused!";
                    Log.getInstance().writeLog("server" + " answers: " + response);
                }
            } else if (command.indexOf("CLEAN") != -1) {
                try {
                    FileOutputStream fos = new FileOutputStream(serializationFileName);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(null); 
                    oos.close();
                    System.out.println("server" + " Serialization file cleaned!");
                    Log.getInstance().writeLog("server" + " Serialization file cleaned!");
                } catch (IOException e) {
                    Logger.getLogger(TimeServer.class.getName()).log(Level.SEVERE, null, e);
                    Log.getInstance().writeLog("server" + " Could not open file: " + serializationFileName);
                }
                response = "OK Cleaning records";
                Log.getInstance().writeLog("server" + " answers: " + response);
            } else {
                response = "ERROR: unknown command!";
                Log.getInstance().writeLog("server" + " answers: " + response);
            }
            
            os.write(response.getBytes());
            os.flush();
            
            is.close();
            os.close();
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(TimeServerThread.class.getName()).log(Level.SEVERE, null, ex);
            Log.getInstance().writeLog("server " + ex);
        } finally {
            try {
                is.close();
                os.close();
                client.close();
            } catch (IOException ex) {
                Logger.getLogger(TimeServerThread.class.getName()).log(Level.SEVERE, null, ex);
                Log.getInstance().writeLog("server " + ex);
            }
        }
    }

    /**
     *
     */
    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
        Log.getInstance().writeLog("server" + " interrupted!");
    }
    
}
