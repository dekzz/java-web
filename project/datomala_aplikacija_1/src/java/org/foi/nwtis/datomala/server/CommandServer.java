package org.foi.nwtis.datomala.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.datomala.config.Configuration;
import org.foi.nwtis.datomala.web.WeatherDataFetcher;

/**
 * Acts as a server which works with, and responds to various user requests
 *
 * @author dex
 */
public class CommandServer extends Thread{

    private Configuration config;
    private int port;
    private static boolean end;
    private static boolean pause;
    private static long stopTime = 0;

    public CommandServer() {
    }

    public Configuration getConfig() {
        return config;
    }

    public void setConfig(Configuration config) {
        this.config = config;
    }

    public static long getStopTime() {
        return stopTime;
    }

    public static void setStopTime(long stop) {
        stopTime = stop;
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
        CommandServer.pause = pause;
        WeatherDataFetcher.setRunning(!pause);
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
        CommandServer.end = end;
        WeatherDataFetcher.setEnd(end);
    }

    @Override
    public synchronized void start() {
        setPause(false);
        setEnd(false);
        this.port = Integer.parseInt(config.getSettings("socketPort"));
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(1000);

            while (!isEnd()) {
                try {
                    Socket client = server.accept();
                    System.out.println("Command recieved!");
                    CommandServerThread csThread = new CommandServerThread(client, config);
                    csThread.start();
                } catch (SocketTimeoutException e) {
                    //continue;
                }
            }
   
        } catch (IOException ex) {
            Logger.getLogger(CommandServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }
}
