/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.config;

/**
 *
 * @author dex
 */
public class NonvalidConfiguration extends Exception{

    public NonvalidConfiguration() {
    }

    public NonvalidConfiguration(String message) {
        super(message);
    }
    
}
