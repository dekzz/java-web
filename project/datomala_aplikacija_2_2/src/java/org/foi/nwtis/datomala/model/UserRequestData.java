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
public class UserRequestData implements Serializable{
    
    private int id;
    private String username;
    private String request;
    private String duration;

    public UserRequestData() {
    }

    public UserRequestData(int id, String username, String request, String duration) {
        this.id = id;
        this.username = username;
        this.request = request;
        this.duration = duration;
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

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    
}
