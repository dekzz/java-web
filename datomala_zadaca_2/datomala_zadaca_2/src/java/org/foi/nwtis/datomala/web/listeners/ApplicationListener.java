/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.web.listeners;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.foi.nwtis.datomala.config.Configuration;
import org.foi.nwtis.datomala.config.ConfigurationAbstract;
import org.foi.nwtis.datomala.config.NoConfiguration;
import org.foi.nwtis.datomala.configurations.db.DB_Configuration;
import org.foi.nwtis.datomala.web.MessageProcessing;

/**
 * Web application lifecycle listener.
 * Loads config files and listens emails for processing
 * @author dex
 */
@WebListener()
public class ApplicationListener implements ServletContextListener {
    
    private MessageProcessing messageProcessing;

    /**
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        
        messageProcessing = new MessageProcessing();
        String path = sce.getServletContext().getRealPath("WEB-INF");
        
        String DBfile = sce.getServletContext().getInitParameter("DBconfiguration");
        DB_Configuration dbConf = new DB_Configuration(path + File.separator + DBfile);
        sce.getServletContext().setAttribute("DB_Configuration", dbConf);
        messageProcessing.setDbConf(dbConf);
        System.out.println("DB Configuration loaded!");
        
        String file = sce.getServletContext().getInitParameter("configuration");
        try {
            Configuration config = ConfigurationAbstract.loadConfig(path + File.separator + file);
            sce.getServletContext().setAttribute("Configuration", config);
            messageProcessing.setConfig(config);
            System.out.println("Configuration loaded!");
        } catch (NoConfiguration ex) {
            Logger.getLogger(ApplicationListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        messageProcessing.start();
    }

    /**
     *
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (messageProcessing != null && messageProcessing.isAlive()) {
            messageProcessing.interrupt();
        }
    }
}
