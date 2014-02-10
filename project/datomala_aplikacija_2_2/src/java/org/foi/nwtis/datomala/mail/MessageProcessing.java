/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.mail;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import org.foi.nwtis.datomala.config.Configuration;
import org.foi.nwtis.datomala.ejb.jms.MailJMS;

/**
 * 
 * Process emails fetched from mail server
 * @author dex
 */
@Stateless
public class MessageProcessing extends Thread {
    
    @EJB
    private MailJMS mailJMS;

    public Configuration config;

    private String emailServer;
    private int emailPort;
    private String emailUsername;
    private String emailPassword;
    private String emailSubject;
    private int interval;
    private String nameNWTiSDirectory;
    private String nameOtherDirectory;
    
    private int nrOverallMessages;
    private int nrNWTiSMessages;

    /**
     *
     */
    public MessageProcessing() {
    }
    
    /**
     *
     * @return
     */
    public Configuration getConfig() {
        return config;
    }

    /**
     *
     * @param config
     */
    public void setConfig(Configuration config) {
        this.config = config;
    }

    /**
     *
     */
    @Override
    public synchronized void start() {

        emailServer = config.getSettings("emailServer");
        emailPort = Integer.parseInt(config.getSettings("emailPort"));
        emailUsername = config.getSettings("username");
        emailPassword = config.getSettings("password");
        emailSubject = config.getSettings("emailSubject");
        interval = Integer.parseInt(config.getSettings("interval"));
        nameNWTiSDirectory = config.getSettings("nameValidDirectory");
        nameOtherDirectory = config.getSettings("nameOtherDirectory");

        super.start();
    }

    /**
 * 
 * Move mail to specific directory
 * @param newDirectoryName Name of directory that will hold the mail
 * @param store Mail storage
 * @param message Email message which will be moved
 * @author dex
 */
    private void moveMessage(String newDirectoryName, Store store, Message message, Folder folder) {
        try {
            Folder newFolder = store.getFolder(newDirectoryName);
            if (!newFolder.exists()) {
                newFolder.create(Folder.HOLDS_MESSAGES);
            }
            newFolder.open(Folder.READ_WRITE);
            //Message[] forCopy = new Message[1];
            //forCopy[0] = message;
            //folder.copyMessages(forCopy, newFolder);
            newFolder.appendMessages(new Message[]{message});
            newFolder.close(false);
        } catch (MessagingException ex) {
            Logger.getLogger(MessageProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * Handles mail processing, moving to directories, syntax validation, etc
     */
    @Override
    public void run() {
        System.out.println("Message Processing STARTED");
        Session session = null;
        Store store = null;
        Folder folder = null;
        Message message = null;
        Message[] messages = null;
        Object messagecontentObject = null;
        String contentType = null;
        long start, duration = 0;
        while (true) {
                try {
                    nrOverallMessages = 0;
                    nrNWTiSMessages = 0;
                    start = System.currentTimeMillis();
                    session = Session.getDefaultInstance(System.getProperties(), null);
                    store = session.getStore("imap");
                    store.connect(emailServer, emailPort, emailUsername, emailPassword);
                    
                    folder = store.getDefaultFolder();
                    folder = folder.getFolder("INBOX");
                    folder.open(Folder.READ_WRITE);
                    messages = folder.getMessages();
                    nrOverallMessages = messages.length;
                    System.out.println("Number of msgs read: " + nrOverallMessages);
                    for (int messageNumber = 0; messageNumber < messages.length; messageNumber++) {
                        
                        message = messages[messageNumber];
                        messagecontentObject = message.getContent();
                        
                        if (message.getSubject().startsWith(emailSubject)){
                            contentType = message.getContentType();
                            
                            if (contentType.startsWith("text/plain") || contentType.startsWith("TEXT/PLAIN")){
                                nrNWTiSMessages++;
                                Folder f = store.getFolder(nameNWTiSDirectory);
                                if (!f.exists()){ 
                                    f.create(Folder.HOLDS_MESSAGES);
                                }
                                f.appendMessages(new Message[] {message});
                                if (f.isOpen())
                                    f.close(true);
                                message.setFlag(Flags.Flag.DELETED, true);
                                continue;
                            }
                           
                        } else {
                            Folder f = store.getFolder(nameOtherDirectory);
                            if (!f.exists()){ 
                                f.create(Folder.HOLDS_MESSAGES);
                            }
                            f.appendMessages(new Message[] {message});
                            if (f.isOpen())
                                f.close(true);
                            message.setFlag(Flags.Flag.DELETED, true);
                            continue;
                        }
                    }  
                    duration = System.currentTimeMillis() - start;
                    
                    mailJMS = new MailJMS();
                    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss");
                    mailJMS.sendJMSMessageToNWTiS_datomala_1(df.format(new Date(start)),
                                                             df.format(new Date(start + duration)),
                                                             nrOverallMessages,
                                                             nrNWTiSMessages);

                    try {
                        long sleepTime;
                        sleepTime = ((int) (interval * 1000) - duration);
                        if (sleepTime > 0) {
                            sleep(sleepTime);
                        }
                        else {
                            sleep(0);
                        }
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                        Logger.getLogger(MessageProcessing.class.getName()).log(Level.SEVERE, null, ex);
                    }
            
            } catch (NoSuchProviderException ex) {
                    Logger.getLogger(MessageProcessing.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                Logger.getLogger(MessageProcessing.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MessageProcessing.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     */
    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }
    
}
