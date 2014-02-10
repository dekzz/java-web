/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Locale;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.naming.NamingException;
import org.foi.nwtis.datomala.ejb.sb.DatomalaUsersFacade;

/**
 *
 * @author dex
 */
@Named(value = "registration")
@SessionScoped
public class Registration implements Serializable {
    @EJB
    private DatomalaUsersFacade datomalaUsersFacade;
    
    private String username;
    private String password;
    private boolean valid = true;
    
    /**
     * Creates a new instance of Registration
     */
    public Registration() {
    }
    
    public String registration() throws NamingException{
        if (datomalaUsersFacade.checkUser(username, password)){
            valid = false;
            return "NOT OK";
        }
        else{
            datomalaUsersFacade.addUser(username, password);
            return "OK";
        }
    }
    
    public void checkEmpty(FacesContext context, UIComponent component, Object value) {
        Locale l = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        String box = (String) value;
        if (box.equals("")) {
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            if (l.equals(Locale.ENGLISH)) {
                message.setSummary("Field can not be empty.");
                message.setDetail("Field can not be empty.");
            } else {
                message.setSummary("Polje ne smije biti prazno.");
                message.setDetail("Polje ne smije biti prazno.");
            }
            context.addMessage(null, message);
            throw new ValidatorException(message);
        }
    }
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
    
}
