/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.listener;

import java.io.File;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.foi.nwtis.datomala.RecordSerialization;
import org.foi.nwtis.datomala.config.Configuration;
import org.foi.nwtis.datomala.config.ConfigurationAbstract;
import org.foi.nwtis.datomala.config.NoConfiguration;
import org.foi.nwtis.datomala.configurations.db.DB_Configuration;
import org.foi.nwtis.datomala.mb.ZipMB;


/**
 * Web application lifecycle listener.
 *
 * @author dex
 */
public class ApplicationListener implements ServletContextListener {

    public static Configuration config;
    public static boolean stopped = false;
    public static boolean paused = false;
    public static String path = "";
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        String path = sce.getServletContext().getRealPath("WEB-INF");
        
        String DBfile = sce.getServletContext().getInitParameter("DBconfiguration");
        DB_Configuration dbConf = new DB_Configuration(path + File.separator + DBfile);
        sce.getServletContext().setAttribute("DB_Configuration", dbConf);
        System.out.println("Database Configuration loaded!");
        
        String file = sce.getServletContext().getInitParameter("configuration");
        try {
            config = ConfigurationAbstract.loadConfig(path + File.separator + file);
            sce.getServletContext().setAttribute("Configuration", config);
            ZipMB.setConfig(config);
            System.out.println("Application Configuration loaded!");
        } catch (NoConfiguration ex) {
            System.out.println("ERROR: " + ex);
        }
        
        RecordSerialization.deserializeJMSMail(config.getSettings("ser_dat_mail"));
        RecordSerialization.deserializeJMSZip(config.getSettings("ser_dat_zip"));

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        RecordSerialization.serializeToFileMail(config.getSettings("ser_dat_mail"), RecordSerialization.recordMail);
        RecordSerialization.serializeToFileZip(config.getSettings("ser_dat_zip"), RecordSerialization.recordZip);
    }
}
