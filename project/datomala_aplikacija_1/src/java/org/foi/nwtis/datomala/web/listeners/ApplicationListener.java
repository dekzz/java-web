/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.web.listeners;

import java.io.File;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.foi.nwtis.datomala.config.Configuration;
import org.foi.nwtis.datomala.config.ConfigurationAbstract;
import org.foi.nwtis.datomala.config.NoConfiguration;
import org.foi.nwtis.datomala.configurations.db.DB_Configuration;
import org.foi.nwtis.datomala.db.DBManager;
import org.foi.nwtis.datomala.server.CommandServer;
import org.foi.nwtis.datomala.web.WeatherDataFetcher;

/**
 * Web application lifecycle listener.
 *
 * @author dex
 */
public class ApplicationListener implements ServletContextListener {

    //public static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private WeatherDataFetcher weatherDataFetcher;
    private CommandServer cmdServer;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        
        weatherDataFetcher = new WeatherDataFetcher();
        String path = sce.getServletContext().getRealPath("WEB-INF");
        
        String DBfile = sce.getServletContext().getInitParameter("DBconfiguration");
        DB_Configuration dbConf = new DB_Configuration(path + File.separator + DBfile);
        sce.getServletContext().setAttribute("DB_Configuration", dbConf);
        weatherDataFetcher.setDbConf(dbConf);
        DBManager.setDbConf(dbConf);
        System.out.println("Database Configuration loaded!");
        
        String file = sce.getServletContext().getInitParameter("configuration");
        try {
            Configuration config = ConfigurationAbstract.loadConfig(path + File.separator + file);
            sce.getServletContext().setAttribute("Configuration", config);
            cmdServer = new CommandServer();
            cmdServer.setConfig(config);
            weatherDataFetcher.setConfig(config);
            System.out.println("Application Configuration loaded!");
        } catch (NoConfiguration ex) {
            System.out.println("ERROR: " + ex);
        }
        
        weatherDataFetcher.start();
        cmdServer.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (weatherDataFetcher != null && weatherDataFetcher.isAlive()) {
            weatherDataFetcher.interrupt();
        }
        if (cmdServer != null && cmdServer.isAlive()) {
            cmdServer.interrupt();
        }
    }
}
