/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author dex
 */
public class ConfigurationTxt extends ConfigurationAbstract{

    public ConfigurationTxt(String file) {
        super(file);
    }

    @Override
    public void loadConfiguration() throws NoConfiguration {
        if (this.file == null || this.file.length() == 0) {
            throw new NoConfiguration("Invalid file name");
        }
        try {
            settings.load(new FileInputStream(this.file));
        } catch (IOException ex) {
            throw new NoConfiguration(ex.getMessage());
        }
    }

    @Override
        public void loadConfiguration(String file) throws NoConfiguration {
        if (file == null || file.length() == 0) {
            throw new NoConfiguration("Invalid file name");
        }
        try {
            settings.load(new FileInputStream(file));
        } catch (IOException ex) {
            throw new NoConfiguration(ex.getMessage());
        }
    }

    @Override
    public void saveConfiguration() throws NonvalidConfiguration {
        try {
            settings.store(new FileOutputStream(this.file), "nwtis dex");
        } catch (IOException ex) {
            throw new NonvalidConfiguration(ex.getMessage());
        }
    }

    @Override
    public void saveConfiguration(String file) throws NonvalidConfiguration {
        try {
            settings.store(new FileOutputStream(file), "nwtis dex");
        } catch (IOException ex) {
            throw new NonvalidConfiguration(ex.getMessage());
        }
    }
    
}
