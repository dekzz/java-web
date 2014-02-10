/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.configurations.db;

import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.datomala.config.Configuration;
import org.foi.nwtis.datomala.config.ConfigurationAbstract;
import org.foi.nwtis.datomala.config.NoConfiguration;

/**
 *
 * @author dex
 */
public class DB_Configuration implements DB_Interface {

    private Configuration config;
    
    private String server_database;
    private String admin_username;
    private String admin_password;
    private String admin_database;
    private String user_username;
    private String user_password;
    private String user_database;
    private String driver_database;
    
    public DB_Configuration(String file) {
        try {
            config = ConfigurationAbstract.loadConfig(file);
            server_database = config.getSettings("server.database");
            
            admin_username = config.getSettings("admin.username");
            admin_password = config.getSettings("admin.password");
            admin_database = config.getSettings("admin.database");
            
            user_username = config.getSettings("user.username");
            user_password = config.getSettings("user.password");
            user_database = config.getSettings("user.database");
            
            driver_database = getDriver_database(server_database);
        } catch (NoConfiguration ex) {
            Logger.getLogger(DB_Configuration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getAdmin_database() {
        return admin_database;
    }

    @Override
    public String getAdmin_password() {
        return admin_password;
    }

    @Override
    public String getAdmin_username() {
        return admin_username;
    }

    @Override
    public String getDriver_database() {
        return driver_database;
    }

    @Override
    public String getDriver_database(String db_url) {
        String[] data = db_url.split(":");
        //TODO check number of elements
        String driverType = data[1];
        String selectedDriver = null;
        Properties p = getDrivers_database();
        for(Enumeration e = p.keys(); e.hasMoreElements();) {
            String key = (String)e.nextElement();
            String[] drivers = key.split("\\.");
            if (drivers[2].compareTo(driverType) == 0) {
                selectedDriver = p.getProperty(key);
            }
        }
        
        return selectedDriver;
    }

    @Override
    public Properties getDrivers_database() {
        Properties drivers = new Properties();
        for (Enumeration<String> e = config.getSettings(); e.hasMoreElements();) {
            String key = e.nextElement();
            if(key.startsWith("driver.database.")) {
                drivers.setProperty(key, config.getSettings(key));
            }
        }
        return drivers;
    }

    @Override
    public String getServer_database() {
        return server_database;
    }

    @Override
    public String getUser_database() {
        return user_database;
    }

    @Override
    public String getUser_password() {
        return user_password;
    }

    @Override
    public String getUser_username() {
        return user_username;
    }
    
}
