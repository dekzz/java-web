/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.AuthenticationFailedException;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.ReadOnlyFolderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.StoreClosedException;
import javax.mail.internet.InternetAddress;
import org.foi.nwtis.datomala.config.Configuration;
import org.foi.nwtis.datomala.configurations.db.DB_Configuration;
import org.foi.nwtis.datomala.web.beans.SendMessage;

/**
 * 
 * Process emails fetched from mail server
 * @author dex
 */
public class MessageProcessing extends Thread {

    private DB_Configuration dbConf;
    /**
     *
     */
    static public Configuration config;

    private String emailServer;
    private int emailPort;
    private String emailUsername;
    private String emailPassword;
    private String emailSubject;
    private int interval;
    private String nameValidDirectory;
    private String nameInvalidDirectory;
    private String nameOtherDirectory;
    
    private long start;
    private long stop;
    private long work;
    
    private int nrOverallMessages;
    private int nrAllMessages;
    private int nrValidMessages;
    private int nrInvalidMessages;
    private int nrOtherMessages;
    private int nrFiles;
    
    private String[] attributes = {};
    private String[] syntax = {"^USER ([a-zA-Z_]+)*$", "^PASSWORD ([a-zA-Z_]+)*$", "^GALLERY ([a-zA-Z_]+)*$"};

    /**
     *
     */
    public MessageProcessing() {
        super();
    }

    /**
     *
     * @return
     */
    public DB_Configuration getDbConf() {
        return dbConf;
    }

    /**
     *
     * @param dbConf
     */
    public void setDbConf(DB_Configuration dbConf) {
        this.dbConf = dbConf;
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
        nameValidDirectory = config.getSettings("nameValidDirectory");
        nameInvalidDirectory = config.getSettings("nameInvalidDirectory");
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
        start = System.currentTimeMillis();
        Session session = null;
        Store store = null;
        Folder folder = null;
        javax.mail.Message message = null;
        javax.mail.Message[] messages = null;
        Object messagecontentObject = null;
        String sender = null;
        String subject = null;
        Multipart multipart = null;
        Part part = null;
        String contentType = null;
        nrOverallMessages = 0;
        nrAllMessages = 0;
        nrValidMessages = 0;
        nrInvalidMessages = 0;
        nrOtherMessages = 0;
        nrFiles = 0;

        while (true) {
            nrAllMessages = 0;
            nrValidMessages = 0;
            nrInvalidMessages = 0;
            nrOtherMessages = 0;
            try {
                session = Session.getDefaultInstance(System.getProperties(), null);

                store = session.getStore("imap");

                store.connect(emailServer, emailPort, emailUsername, emailPassword);

                // Get a handle on the default folder
                folder = store.getDefaultFolder();

                // Retrieve the "Inbox"
                folder = folder.getFolder("inbox");

                //Reading the Email Index in Read / Write Mode
                folder.open(Folder.READ_WRITE);

                // Retrieve the messages
                messages = folder.getMessages();

                // Loop over all of the messages
                for (int messageNumber = 0; messageNumber < messages.length; messageNumber++) {
                    nrOverallMessages++;
                    nrAllMessages++;
                    // Retrieve the next message to be read
                    message = messages[messageNumber];

                    // Retrieve the message content
                    messagecontentObject = message.getContent();

                    if (message.getSubject().equals(config.getSettings("emailSubject"))) {
                        
                        // Determine email type
                        if (messagecontentObject instanceof Multipart) {

                            sender = ((InternetAddress) message.getFrom()[0]).getPersonal();

                            // If the "personal" information has no entry, check the address for the sender information
                            if (sender == null) {
                                sender = ((InternetAddress) message.getFrom()[0]).getAddress();
                            }

                            // Get the subject information
                            subject = message.getSubject();

                            // Retrieve the Multipart object from the message
                            multipart = (Multipart) message.getContent();

                            // Loop over the parts of the email
                            for (int i = 0; i < multipart.getCount(); i++) {
                                // Retrieve the next part
                                part = multipart.getBodyPart(i);

                                // Get the content type
                                contentType = part.getContentType();

                                if (contentType.toLowerCase().startsWith("text/plain")) {

                                    if (validSyntax(part.getContent().toString())) {
                                        nrValidMessages++;         
                                        moveMessage(nameValidDirectory, store, message, folder);
                                        message.setFlag(Flags.Flag.DELETED, true);
                                    } else {
                                        nrInvalidMessages++;
                                        moveMessage(nameInvalidDirectory, store, message, folder);
                                        message.setFlag(Flags.Flag.DELETED, true);
                                    }
                                } else if (contentType.toLowerCase().contains("image/*") || contentType.toLowerCase().contains("application/octet-stream")) {
                                    //TODO save part to attachment list / image to gallery file named in mail content
                                    nrFiles++;
                                } else {
                                    // Retrieve the file name
                                    String fileName = part.getFileName();
                                    //TODO save part to attachment list
                                }
                            }
                        } else {
                            // Mail without attachment
                            sender = ((InternetAddress) message.getFrom()[0]).getPersonal();

                            // If the "personal" information has no entry, check the address for the sender information
                            if (sender == null) {
                                sender = ((InternetAddress) message.getFrom()[0]).getAddress();
                            }

                            // Get the subject information
                            subject = message.getSubject();

                            moveMessage(nameOtherDirectory, store, message, folder);
                            message.setFlag(Flags.Flag.DELETED, true);
                        }
                    } else {
                        // Invalid subject
                        sender = ((InternetAddress) message.getFrom()[0]).getPersonal();

                        // If the "personal" information has no entry, check the address for the sender information
                        if (sender == null) {
                            sender = ((InternetAddress) message.getFrom()[0]).getAddress();
                        }

                        // Get the subject information
                        subject = message.getSubject();

                        moveMessage(nameOtherDirectory, store, message, folder);
                        message.setFlag(Flags.Flag.DELETED, true);
                    }
                }

                // Close the folder
                folder.close(true);

                // Close the message store
                store.close();

            } catch (AuthenticationFailedException e) {
                e.printStackTrace();
            } catch (FolderClosedException e) {
                e.printStackTrace();
            } catch (FolderNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            } catch (ReadOnlyFolderException e) {
                e.printStackTrace();
            } catch (StoreClosedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            stop = System.currentTimeMillis();
            work = stop - start;
            
            sendReport();
            
            try {
                sleep((interval * 1000) - work);
            } catch (InterruptedException ex) {
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

    /**
    * 
    * Check mail content syntax
    * @para content Email content 
    * @author dex
    */
    private boolean validSyntax(String content) {
        BufferedReader br = new BufferedReader(new StringReader(content));
        String line = "";
        Pattern pattern;
        Matcher m;
        int arg = 0;
        
        try {
            while (((line = br.readLine()) != null) && (arg < 3)) {
                pattern = Pattern.compile(syntax[arg]);
                m = pattern.matcher(line);
                if (m.matches()) {
                    attributes[arg] = m.group(1);
                    arg++;
                } else {
                    return false;
                }
            }
            //TODO check email content in db
        } catch (IOException ex) {
            Logger.getLogger(MessageProcessing.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    private boolean verifyUser(String username, String password){
        String url = dbConf.getServer_database() + dbConf.getUser_database();
        String DBusername = dbConf.getUser_username();
        String DBpassword = dbConf.getUser_password();
        String query = "SELECT kor_ime, lozinka FROM polaznici WHERE kor_ime = '" + DBusername + "' AND lozinka = '" + DBpassword + "';";
        Connection connection = null;
        Statement instr = null;
        ResultSet rs = null;
        
        try{
            connection = DriverManager.getConnection(url, DBusername, DBpassword);
            instr = connection.createStatement();
            rs = instr.executeQuery(query);
        
            if (rs != null) {
                return  true;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
    /**
    * 
    * Systematic report for processed mails
    * @author dex
    */
    private void sendReport() {
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh.mm.ss.zzz");
        Date startDate = new Date(start);
        String startText = sdf.format(startDate);
        Date stopDate = new Date(stop);
        String stopText = sdf.format(stopDate);
        
        SendMessage mail = new SendMessage();
        mail.setSender("system@nwtis.nastava.foi.hr");
        mail.setReceiver(config.getSettings("reportEmail"));
        mail.setSubject(config.getSettings("reportSubject"));
        mail.setContent("Processing started: " + startText + "\n" + 
                        "Processing ended: " + stopText + "\n" + 
                        "Processing time (ms): " + work + "\n" + 
                        "Overall messages: " + nrOverallMessages + "\n" + 
                        "All messages: " + nrAllMessages + "\n" +
                        "Valid messages: " + nrValidMessages + "\n" +
                        "Invalid messages: " + nrInvalidMessages + "\n" +
                        "Other messages: " + nrOtherMessages + "\n" + 
                        "Files: " + nrFiles);
        mail.sendMessage();
    }
    
}
