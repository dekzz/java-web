/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.config;

import java.util.Enumeration;
import java.util.Properties;

/**
 *
 * @author dex
 */
public interface Configuration {
    
    public void loadConfiguration() throws NoConfiguration;
    public void loadConfiguration(String file) throws NoConfiguration;
    public void saveConfiguration() throws NonvalidConfiguration;
    public void saveConfiguration(String file) throws NonvalidConfiguration;
    public void addConfiguration(Properties settings);
    public void addConfiguration(Configuration config);
    public void copyConfiguration(Properties settings);
    public void copyConfiguration(Configuration config);
    public Properties getAllSettings();
    public Enumeration getSettings();
    public boolean deleteAllSettings();
    public String getSettings(String key);
    public boolean saveSettings(String key, String value);
    public boolean updateSettings(String key, String value);
    public boolean existSettings(String key);
    public boolean deleteSettings(String key);
    
}
