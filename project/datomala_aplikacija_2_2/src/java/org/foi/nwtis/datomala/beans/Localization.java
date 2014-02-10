/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.beans;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.faces.context.FacesContext;

/**
 *
 * @author dex
 */
@Named(value = "localization")
@SessionScoped
public class Localization implements Serializable {

    private Map<String, Object> languages;
    private String selectedLanguage;
    private Locale selectedLocale;


    public Localization() {
        languages = new HashMap<String, Object>();
        languages.put("English", Locale.ENGLISH);
        languages.put("Hrvatski", new Locale("hr"));
    }
    
    public Object selectLanguage() throws IOException {
        for (Map.Entry<String, Object> entry : languages.entrySet()) {
            if (entry.getValue().toString().equals(selectedLanguage)) {
                setSelectedLocale((Locale) entry.getValue());
                FacesContext.getCurrentInstance()
                        .getViewRoot().setLocale((Locale) entry.getValue());
            }
        }
        return "OK";
    }

    public Map<String, Object> getLanguages() {
        return languages;
    }

    public void setLanguages(Map<String, Object> languages) {
        this.languages = languages;
    }
    

    public String getSelectedLanguage() {
        return selectedLanguage;
    }

    public void setSelectedLanguage(String selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
    }

    public Locale getSelectedLocale() {
        return selectedLocale;
    }

    public void setSelectedLocale(Locale selectedLocale) {
        this.selectedLocale = selectedLocale;
    }
}
