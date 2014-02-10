
package org.foi.nwtis.datomala.assignment_1;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.datomala.config.Configuration;
import org.foi.nwtis.datomala.config.ConfigurationAbstract;
import org.foi.nwtis.datomala.config.NoConfiguration;

/**
 * Handles user request threads worker
 * 
 * @author dex
 */
public class TimeClient {
    private int port;
    private String configFileName;
    private String ipServer;
    private String user;

    private Configuration config;
    private int nrThreads;
    private int pause;
    
    /**
     *
     * @param port - Port for communication with server
     * @param configFileName - Name of a configuration file
     * @param ipServer - Server's IP address
     * @param user - Username of an user
     */
    public TimeClient(int port, String configFileName, String ipServer, String user) {
        this.port = port;
        this.configFileName = configFileName;
        this.ipServer = ipServer;
        this.user = user;
        this.nrThreads = 0;
        this.pause = 0;
    }
    
    /**
     * Reads the configuration file and handles all the user threads.
     * Each thread (request) is started after previous one with delay
     * set in configuration file.
     * 
     */
    public void startTimeClient() {
        try {
            config = ConfigurationAbstract.loadConfig(configFileName);
            nrThreads = Integer.parseInt(config.getSettings("nrThreads"));
        } catch (NoConfiguration ex) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, ex);
            Log.getInstance().writeLog(user + " Could not load config file! " + ex);
        }        
        
        pause = Integer.parseInt(config.getSettings("pause")) * 1000;
        pause *= Math.random();
        
        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < nrThreads; i++) {
            Thread thread = new TImeClientThread(port, configFileName, ipServer, user);
            threads.add(thread);
            thread.start();
            try {
                Thread.sleep(pause);
            } catch (InterruptedException ex) {
                Logger.getLogger(TimeClient.class.getName()).log(Level.SEVERE, null, ex);
                Log.getInstance().writeLog(user + " Thread interrupted! " + ex);
            }
        }
    }
}
