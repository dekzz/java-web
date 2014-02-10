
package org.foi.nwtis.datomala.assignment_1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handle administrative requests on server
 * 
 * @author dex
 */
public class TimeAdministrator {
    private int port;
    private String configFileName;
    private String ipServer;
    private String user;
    
    private String password;
    private String adminCommand;
    private String time;

    /**
     * Required data for administration
     * 
     * @param port - Port for communication with server
     * @param configFileName - Name of configuration file
     * @param ipServer - Server's IP address
     * @param user - Administrative username
     * @param password - Administrative password
     * @param adminCommand - Administrative command for server
     * @param time - Server time
     */
    public TimeAdministrator(int port, String configFileName, String ipServer, String user, String password, String adminCommand, String time) {
        this.port = port;
        this.configFileName = configFileName;
        this.ipServer = ipServer;
        this.user = user;
        this.password = password;
        this.adminCommand = adminCommand;
        this.time = time;
    }
    
    /**
     * Opens up a communication with server, sends an admin command
     * and waits for server response
     * 
     */
    public void startTimeAdministrator() {
        try {
            Socket server = new Socket(ipServer, port);
            
            InputStream is = server.getInputStream();
            OutputStream os = server.getOutputStream();
            
            String command = "ADMIN " + user + "; PASSWD " + password + "; " + adminCommand + ";";
            if (time != null) {
                command = "ADMIN " + user + "; PASSWD " + password + "; " + adminCommand + " " + time + ";";
            }
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
            System.out.println("Response: " + response);
            Log.getInstance().writeLog(user + " server responded: " + response);
            
            is.close();
            os.close();
            server.close();            
        } catch (UnknownHostException ex) {
            Logger.getLogger(TimeAdministrator.class.getName()).log(Level.SEVERE, null, ex);
            Log.getInstance().writeLog(user + " " + ex);
        } catch (IOException ex) {
            Logger.getLogger(TimeAdministrator.class.getName()).log(Level.SEVERE, null, ex);
            Log.getInstance().writeLog(user + " " + ex);
        }
    }
}
