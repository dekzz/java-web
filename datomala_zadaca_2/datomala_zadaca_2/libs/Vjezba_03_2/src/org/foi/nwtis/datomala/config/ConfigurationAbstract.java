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
public abstract class ConfigurationAbstract implements Configuration{

    protected String file;
    protected Properties settings;

    public ConfigurationAbstract(String file) {
        this.file = file;
        this.settings = new Properties();
    }

    @Override
    public void addConfiguration(Properties settings) {
        this.settings.putAll(settings);
    }

    @Override
    public void addConfiguration(Configuration config) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void copyConfiguration(Properties settings) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void copyConfiguration(Configuration config) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Properties getAllSettings() {
        return settings;
    }

    @Override
    public Enumeration getSettings() {
        return settings.propertyNames();
    }

    @Override
    public boolean deleteAllSettings() {
        if (settings.isEmpty()) {
            return false;
        }
        else{
            settings.clear();
            return true;
        }
    }

    @Override
    public String getSettings(String key) {
        return settings.getProperty(key);
    }

    @Override
    public boolean saveSettings(String key, String value) {
        if (!existSettings(key)) {
            settings.setProperty(key, value);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateSettings(String key, String value) {
        if (existSettings(key)) {
            settings.setProperty(key, value);
            return true;
        }
        return false;
    }

    @Override
    public boolean existSettings(String key) {
        return settings.containsKey(key);
    }

    @Override
    public boolean deleteSettings(String key) {
        if (existSettings(key)){
            settings.remove(key);
            return true;
        }
        return false;
    }
    
    public static Configuration createConfig(String file) throws NonvalidConfiguration {
        Configuration config = null;
        if (file.endsWith(".txt")) {
            config = new ConfigurationTxt(file);
            config.saveConfiguration();
        }
        else if (file.endsWith(".xml")) {
            config = new ConfigurationXML(file);
            config.saveConfiguration();
        }
        else {
            throw new NonvalidConfiguration("Unknown file type");
        }
        
        return config;
    }
    
    public static Configuration loadConfig(String file) throws NoConfiguration {
        Configuration config = null;
        if (file.endsWith(".txt")) {
            config = new ConfigurationTxt(file);
            config.loadConfiguration();
        }
        else if (file.endsWith(".xml")) {
            config = new ConfigurationXML(file);
            config.loadConfiguration();
        }
        else {
            throw new NoConfiguration("Unknown file type");
        }
        
        return config;
    }
    
}
