/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.web.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.foi.nwtis.datomala.web.MessageProcessing;

/**
 *
 * Send email message to specific server
 * @author dex
 */
@ManagedBean
@SessionScoped
public class SendMessage implements Serializable{
    
    private String emailServer;
    private String username;
    private String password;
    private String receiver;
    private String sender;
    private String subject;
    private String content;

    /**
     * Creates a new instance of SendMessage
     */
    public SendMessage() {
        this.emailServer = MessageProcessing.config.getSettings("emailServer");
        this.username = MessageProcessing.config.getSettings("username");
        this.password = MessageProcessing.config.getSettings("password");
        this.subject = MessageProcessing.config.getSettings("emailSubject");
    }
    
    /**
     *
     * @return
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     *
     * @param receiver
     */
    public void setReceiver(String receiver) {
        this.receiver = username;
        this.receiver = receiver;
    }

    /**
     *
     * @return
     */
    public String getSender() {
        return sender;
    }

    /**
     *
     * @param sender
     */
    public void setSender(String sender) {
        this.sender = username;
        this.sender = sender;
    }

    /**
     *
     * @return
     */
    public String getSubject() {
        return subject;
    }

    /**
     *
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     *
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    /**
     *
     * Send email with specific data
     * @return
     */
    public String sendMessage() {
        String status = "";
        try {
            int randID = new Random().nextInt(Integer.MAX_VALUE) + 1;
            
            // Create the JavaMail session
            java.util.Properties properties = System.getProperties();
            properties.put("mail.smtp.host", this.emailServer);

            Session session = Session.getInstance(properties, null);

            // Construct the message
            MimeMessage message = new MimeMessage(session);

            // Message ID
            message.setHeader("Message-ID", Integer.toString(randID));
            if (sender.equals("system@nwtis.nastava.foi.hr")) {
                message.setHeader("Content-Type", "text/html");
            } else {
                message.setHeader("Content-Type", "text/plain");
            }
            
            // Set the from address
            Address fromAddress = new InternetAddress(this.sender);
            message.setFrom(fromAddress);

            // Parse and set the recipient addresses
            Address[] toAddresses = InternetAddress.parse(this.receiver);
            message.setRecipients(Message.RecipientType.TO, toAddresses);

            // Set the subject and text
            message.setSubject(this.subject);
            message.setText(this.content);
            
            message.setSentDate(new Date());

            Transport.send(message);
            
            status = "Mail successfully sent!";
            FacesContext.getCurrentInstance().addMessage("status", new FacesMessage(status));
            
            if (sender.equals("system@nwtis.nastava.foi.hr")) {
                // get Session object's store
                Store store = session.getStore("imap");
                // connect to store
                store.connect(emailServer, username, password);
                // obtain reference to "Sent" folder
                Folder f = store.getFolder(MessageProcessing.config.getSettings("reportSaveDirectory"));
                // create "Sent" folder if it does not exist
                if (!f.exists()) f.create(Folder.HOLDS_MESSAGES);
                // add message to "Sent" folder
                f.appendMessages(new Message[] {message});
            }

        } catch (AddressException e) {
            e.printStackTrace();
            status = "Mail send failed!";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(status));
        } catch (SendFailedException e) {
            e.printStackTrace();
            status = "Mail send failed!";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(status));
        } catch (MessagingException e) {
            e.printStackTrace();
            status = "Mail send failed!";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(status));
        }
        return null;
    }
    
}
