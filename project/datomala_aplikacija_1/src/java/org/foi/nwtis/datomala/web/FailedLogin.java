/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.web;

/**
 *
 * @author dex
 */
public class FailedLogin extends Exception{

    public FailedLogin(String message) {
        super(message);
    }
    
}
