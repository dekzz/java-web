/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.web.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.foi.nwtis.datomala.web.controls.Message;

/**
 *
 * View details of specific email
 * @author dex
 */
@ManagedBean
@SessionScoped
public class ViewMessage implements Serializable{
    
    private Message message;

    /**
     * Creates a new instance of ViewMessage
     */
    public ViewMessage() {
    }
    
    /**
     *
     * @return
     */
    public Message getMessage() {
        ViewAllMessages vam = (ViewAllMessages)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("viewAllMessages");
        message = vam.getMessage();
        return message;
    }
    
    /**
     *
     * @param message
     */
    public void setMessage(Message message) {
        this.message = message;
    }
    
    /**
     *
     * @return
     */
    public String backViewAllMessages() {
        return "OK";
    }
    
}
