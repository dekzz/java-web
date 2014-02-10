/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.listener;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Web application lifecycle listener.
 *
 * @author dex
 */
@WebListener()
public class SessionListener implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if (event.getName().compareTo("username") == 0){
            String username = (String) event.getValue();
            List<String> activeUsers = (List<String>) event.getSession().getServletContext().getAttribute("activeUsers");
            if (activeUsers == null){
                activeUsers = new ArrayList<String>();
            }
            activeUsers.add(username);
            event.getSession().getServletContext().setAttribute("activeUsers", activeUsers);
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if (event.getName().compareTo("username") == 0){
            String username = (String) event.getValue();
            List<String> activeUsers = (List<String>) event.getSession().getServletContext().getAttribute("activeUsers");
            activeUsers.remove(username);
            event.getSession().getServletContext().setAttribute("activeUsers", activeUsers);
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
