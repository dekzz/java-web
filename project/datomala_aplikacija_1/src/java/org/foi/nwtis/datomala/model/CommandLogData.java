/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.model;

import java.io.Serializable;

/**
 *
 * @author dex
 */
public class CommandLogData implements Serializable{
    private int id;
    private String username;
    private String command;
    private String status;
    private String receivetime;

    public CommandLogData() {
    }

    public CommandLogData(int id, String username, String command, String status, String receivetime) {
        this.id = id;
        this.username = username;
        this.command = command;
        this.status = status;
        this.receivetime = receivetime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReceivetime() {
        return receivetime;
    }

    public void setReceivetime(String receivetime) {
        this.receivetime = receivetime;
    }
    
}
