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
public class JMSMessageZip implements Serializable{
    
    private String zip;

    public JMSMessageZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
    
}
