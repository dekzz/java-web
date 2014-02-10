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
public class JMSMessageMail implements Serializable{
    
    private String startTime;
    private String stopTime;
    private String nrAllMessages;
    private String nrNWTiSMessages;

    public JMSMessageMail(String startTime, String stopTime, String nrAllMessages, String nrNWTiSMessages) {
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.nrAllMessages = nrAllMessages;
        this.nrNWTiSMessages = nrNWTiSMessages;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getNrAllMessages() {
        return nrAllMessages;
    }

    public void setNrAllMessages(String nrAllMessages) {
        this.nrAllMessages = nrAllMessages;
    }

    public String getNrNWTiSMessages() {
        return nrNWTiSMessages;
    }

    public void setNrNWTiSMessages(String nrNWTiSMessages) {
        this.nrNWTiSMessages = nrNWTiSMessages;
    }
    
}
