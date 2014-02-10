/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.web.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.foi.nwtis.datomala.web.MessageProcessing;

/**
 *
 * Holds data for email server connection
 * @author dex
 */
@ManagedBean
@SessionScoped
public class EmailConnection implements Serializable{
    private String emailServer;
    private String username;
    private String password;

    /**
     * Creates a new instance of EmailConnection
     */
    public EmailConnection() {
        this.emailServer = MessageProcessing.config.getSettings("emailServer");
        this.username = MessageProcessing.config.getSettings("username");
        this.password = MessageProcessing.config.getSettings("password");
    }
    
    /**
     *
     * @return
     */
    public String sendMessage() {
        return "OK";
    }
    
    /**
     *
     * @return
     */
    public String readMessages() {
        return "OK";
    }
    
    /**
     *
     * @return
     */
    public String getEmailServer() {
        return emailServer;
    }

    /**
     *
     * @param emailServer
     */
    public void setEmailServer(String emailServer) {
        this.emailServer = emailServer;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
}
