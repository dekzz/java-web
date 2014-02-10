/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.listeners;

import java.io.File;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.foi.nwtis.datomala.configurations.db.DB_Configuration;

/**
 * Web application lifecycle listener.
 *
 * @author dex
 */
@WebListener()
public class ApplicationListener implements ServletContextListener {

    /**
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String path = sce.getServletContext().getRealPath("WEB-INF");
        String file = sce.getServletContext().getInitParameter("configuration");
        
        DB_Configuration dbConf = new DB_Configuration(path + File.separator + file);
        
        sce.getServletContext().setAttribute("DB_Configuration", dbConf);
        
        System.out.println(sce.getServletContext().getContextPath() + ": Configuration loaded!");
    }

    /**
     *
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
}
