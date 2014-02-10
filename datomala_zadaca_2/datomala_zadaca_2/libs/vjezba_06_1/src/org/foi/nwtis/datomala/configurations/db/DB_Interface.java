/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.configurations.db;

import java.util.Properties;

/**
 *
 * @author dex
 */
public interface DB_Interface {
    String getAdmin_database();
    String getAdmin_password();
    String getAdmin_username();
    String getDriver_database();
    String getDriver_database(String bp_url);
    Properties getDrivers_database();
    String getServer_database();
    String getUser_database();
    String getUser_password();
    String getUser_username();    
}
