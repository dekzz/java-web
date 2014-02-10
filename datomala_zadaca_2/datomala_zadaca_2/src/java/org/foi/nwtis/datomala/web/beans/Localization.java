/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.web.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * Localization, language specification 
 * @author dex
 */
//FIXME localization does't working
@ManagedBean(name = "localization")
@SessionScoped
public class Localization implements Serializable{

    private Map<String, Object> languages;
    private String selectedLanguage;
    private Locale selectedLocale;
    
    /**
     * Creates a new instance of Localization
     */
    public Localization() {
        selectedLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        
        languages = new HashMap<String, Object>();
        languages.put("Hrvatski", new Locale("hr"));
        languages.put("English", Locale.ENGLISH);
        languages.put("Deutsch", Locale.GERMAN);
    }
        
    /**
     *
     * Choose language
     * @return
     */
    public Object selectLanguage() {
        for (Map.Entry<String, Object> entry : languages.entrySet()) {
            if (entry.getValue().toString().equals(selectedLanguage)) {
                FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale) entry.getValue());
                setSelectedLocale((Locale) entry.getValue());
                //selectedLocale = (Locale) entry.getValue();
            }
        }
        return "OK";
    }

    /**
     *
     * @return
     */
    public Map<String, Object> getLanguages() {
        return languages;
    }

    /**
     *
     * @param languages
     */
    public void setLanguages(Map<String, Object> languages) {
        this.languages = languages;
    }

    /**
     *
     * @return
     */
    public String getSelectedLanguage() {
        return selectedLanguage;
    }

    /**
     *
     * @param selectedLanguage
     */
    public void setSelectedLanguage(String selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
    }

    /**
     *
     * @return
     */
    public Locale getSelectedLocale() {
        return selectedLocale;
    }

    /**
     *
     * @param selectedLocale
     */
    public void setSelectedLocale(Locale selectedLocale) {
        this.selectedLocale = selectedLocale;
    }
    
}
