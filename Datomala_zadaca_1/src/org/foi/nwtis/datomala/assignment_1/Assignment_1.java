
package org.foi.nwtis.datomala.assignment_1;

import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.nwtis.datomala.config.Configuration;
import org.foi.nwtis.datomala.config.ConfigurationAbstract;
import org.foi.nwtis.datomala.config.NoConfiguration;

/**
 * Main class for handling commands
 * 
 * @author dex
 */
public class Assignment_1 {
    
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    
    /**
     *
     * @param command
     */
    public static void main(String[] args) {    
        
        StringBuilder sb;
        String p;
        Pattern pattern;
        Matcher m;
        boolean status;
                
        String ipServer;
        int port;
        String configFileName;
        String user;
        String adminCommand;
        
        switch (args[0]) {
            case "-server":
                // -server -port port -konf datoteka[.txt | .xml] [-load] -s datoteka
                String syntaxServer = "^-server -port ([8-9]\\d{3}) -konf ([^\\s]+\\.(?i)(txt|xml))( +-load)? -s ([^\\s]+\\.[^\\s]+) *$";
                sb = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    sb.append(args[i]).append(" ");
                }
                p = sb.toString().trim();
                pattern = Pattern.compile(syntaxServer);
                m = pattern.matcher(p);
                status = m.matches();
                
                if (status) {
                    port = Integer.parseInt(m.group(1));
                    configFileName = m.group(2);
                    boolean loadNeeded = (m.group(4) != null);
                    String serializationFileName = m.group(5);

                    try {
                        Configuration config = ConfigurationAbstract.loadConfig(configFileName);
                    } catch (NoConfiguration ex) {
                        Logger.getLogger(Assignment_1.class.getName()).log(Level.SEVERE, null, ex);
                        Log.getInstance().writeLog("server " + p + " Could not load config file! " + ex);
                    }

                    TimeServer ts = new TimeServer
                                        (port, configFileName, 
                                        loadNeeded, serializationFileName);
                    try {
                        ts.startTimeServer();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Assignment_1.class.getName()).log(Level.SEVERE, null, ex);
                        Log.getInstance().writeLog("server" + " Could not start! " + ex);
                    }
                }
                break;
                
            case "-user":
                String syntaxUser = "^-user -ts (\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}) -port ([8-9]\\d{3}) -u ([a-zA-Z]+) -konf ([a-zA-Z0-9_]+\\.(txt|xml))";
                sb = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    sb.append(args[i]).append(" ");
                }
                p = sb.toString().trim();
                pattern = Pattern.compile(syntaxUser);
                m = pattern.matcher(p);
                status = m.matches();
                
                if (status) {
                    ipServer = m.group(1);
                    port = Integer.parseInt(m.group(2));
                    user = m.group(3);
                    configFileName = m.group(4);

                    try {
                        Configuration config = ConfigurationAbstract.loadConfig(configFileName);
                    } catch (NoConfiguration ex) {
                        Logger.getLogger(Assignment_1.class.getName()).log(Level.SEVERE, null, ex);
                        Log.getInstance().writeLog(user + " " + p + " Could not load config file! " + ex);
                    }

                    TimeClient tc = new TimeClient(port, configFileName, ipServer, user);
                    tc.startTimeClient();
                }
                break;
                
            case "-admin":
                String syntaxAdmin = "^-admin -ts (\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}) -port ([8-9]\\d{3}) -u ([a-zA-Z]+) -p ([a-zA-Z0-9_]+) -konf ([a-zA-Z0-9_]+\\.(txt|xml)) (-t (\\d\\d.\\d\\d.\\d\\d\\d\\d \\d\\d:\\d\\d:\\d\\d)|PAUSE|START|STOP|CLEAN)";
                sb = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    sb.append(args[i]).append(" ");
                }
                p = sb.toString().trim();
                pattern = Pattern.compile(syntaxAdmin);
                m = pattern.matcher(p);
                status = m.matches();
                
                if (status) {
                    ipServer = m.group(1);
                    port = Integer.parseInt(m.group(2));
                    user = m.group(3);
                    String password = m.group(4);
                    configFileName = m.group(5);
                    if (m.group(7).startsWith("-t")) {
                        adminCommand = "SETTIME";
                    } else {
                        adminCommand = m.group(6);
                    }
                    String time = m.group(7);
                    
                    Configuration config = null;
                    try {
                        config = ConfigurationAbstract.loadConfig(configFileName);
                    } catch (NoConfiguration ex) {
                        Logger.getLogger(Assignment_1.class.getName()).log(Level.SEVERE, null, ex);
                        Log.getInstance().writeLog(user + " " + p + " Could not load config file! " + ex);
                    }
                    
                    if (user.equals(config.getSettings("admin")) && password.equals(config.getSettings("password"))) {
                        TimeAdministrator admin = new TimeAdministrator(port, configFileName, ipServer, user, password, adminCommand, time);
                        admin.startTimeAdministrator();
                    } else {
                        System.out.println("ERROR: Authentication failed!");
                        Log.getInstance().writeLog(user + " " + p + " ERROR: Authentication failed!");
                    }
                }
                break;
                
            case "-show":
                String syntaxShow = "^-show -s ([a-zA-Z0-9_]+\\.bin)";
                sb = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    sb.append(args[i]).append(" ");
                }
                p = sb.toString().trim();
                pattern = Pattern.compile(syntaxShow);
                m = pattern.matcher(p);
                status = m.matches();
                
                if (status) {
                    configFileName = m.group(1);
                    
                    PrintRecords printRecords = new PrintRecords(configFileName);
                    printRecords.print();
                }
                break;
                
            default:
                System.out.println("No response!");
                Log.getInstance().writeLog("server " + " No response!");
                break;
        }
    }
}
