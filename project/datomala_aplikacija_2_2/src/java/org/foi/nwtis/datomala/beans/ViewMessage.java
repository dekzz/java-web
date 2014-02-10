/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.beans;

import java.io.Serializable;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.foi.nwtis.datomala.model.Message;


/**
 *
 * View details of specific email
 * @author dex
 */
@Named(value = "viewMessage")
@SessionScoped
public class ViewMessage implements Serializable{
    
    private Message message;
    private boolean multipart;

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
        ViewAllMessages vam = (ViewAllMessages) FacesContext.getCurrentInstance()
                                                .getExternalContext()
                                                .getSessionMap()
                                                .get("viewAllMessages");
        message = vam.getMessage();
        if (message.getNrAttachments() > 0) {
            setMultipart(true);
        } else {
            setMultipart(false);
        }
        return message;
    }
    
    /**
     *
     * @param message
     */
    public void setMessage(Message message) {
        this.message = message;
    }

    public boolean isMultipart() {
        return multipart;
    }

    public void setMultipart(boolean multipart) {
        this.multipart = multipart;
    }
    
    /**
     *
     * @return
     */
    public String backViewAllMessages() {
        return "OK";
    }
    
}
