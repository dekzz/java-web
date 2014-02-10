
package org.foi.nwtis.datomala.assignment_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.datomala.config.Configuration;
import org.foi.nwtis.datomala.config.ConfigurationAbstract;
import org.foi.nwtis.datomala.config.NoConfiguration;

/**
 * Represents user interaction with server
 * 
 * @author dex
 */
public class TImeClientThread extends Thread {
    
    private Configuration config;
    private int port;
    private String configFileName;
    private String ipServer;
    private String user;
    
    private static int nrThreads = 0;
    
    private int nrTry;
    private int maxNrTry;

    /**
     *
     * @param port - Port for communication with server 
     * @param configFileName - Name of a configuration file
     * @param ipServer - Server's IP address
     * @param user - Username of an interactive user
     */
    public TImeClientThread(int port, String configFileName, String ipServer, String user) {
        this.port = port;
        this.configFileName = configFileName;
        this.ipServer = ipServer;
        this.user = user;

        setName("TCT " + nrThreads++);
        
        try {
            this.config = ConfigurationAbstract.loadConfig(configFileName);
            this.maxNrTry = Integer.parseInt(config.getSettings("maxNrTry"));
        } catch (NoConfiguration ex) {
            Logger.getLogger(TImeClientThread.class.getName()).log(Level.SEVERE, null, ex);
            Log.getInstance().writeLog(user + " Could not load config file! " + ex);
        }      
    }

    /**
     *
     */
    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
        System.out.println("Thread " + getName() + " started!");
        Log.getInstance().writeLog(user + " Thread " + getName() + " started!");
    }

    /**
     * Opens communication with server, sends user request and
     * waits for server response. Requests are sent on interval
     * and are stopped after number of failed tries, 
     * which are set in configuration file.
     * 
     */
    @Override
    public void run() {
        while (true) {
            long start = System.currentTimeMillis();
            try {
                Socket server = new Socket(ipServer, port);

                InputStream is = server.getInputStream();
                OutputStream os = server.getOutputStream();

                String command = "USER " + user + "; GETTIME;";
                Log.getInstance().writeLog(user + " " + command);
                try {
                    Records record = new Records(user, command.toString(), Assignment_1.sdf.parse(Assignment_1.sdf.format(new Date())));
                    Records.records.add(record);
                } catch (ParseException ex) {
                    Logger.getLogger(TimeServerThread.class.getName()).log(Level.SEVERE, null, ex);
                    Log.getInstance().writeLog("server" + " Could not make record!");
                }
                
                os.write(command.getBytes());
                os.flush();
                server.shutdownOutput();

                StringBuilder response = new StringBuilder();
                int letter;
                while ((letter = is.read()) != -1 || is.available() > 0) {                
                    response.append((char) letter);
                }
                System.out.println("Thread '" + getName() + "' response: " + response);
                Log.getInstance().writeLog(user + " Thread " + getName() + " time: " + Assignment_1.sdf.format(new Date())
                                                + " | Server time: " + response);

                is.close();
                os.close();
                server.close();
            } catch (IOException ex) {
                nrTry++;
            }
            
            if (nrTry >= maxNrTry) {
                    System.out.println("Thread '" + getName() + "' interrupted after " + nrTry + " tries!");
                    Log.getInstance().writeLog(user + " Thread " + getName() + " interrupted after " + nrTry + " tries!");
                    break;
                }
            
            long work = System.currentTimeMillis() - start;
            try {
                sleep((Integer.parseInt(config.getSettings("interval")) * 1000) - work);
            } catch (InterruptedException ex) {
                Logger.getLogger(TImeClientThread.class.getName()).log(Level.SEVERE, null, ex);
                Log.getInstance().writeLog(user + " Thread interrupted! " + ex);
            }            
        }
    }

    /**
     *
     */
    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
