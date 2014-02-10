/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;

import javax.mail.internet.InternetAddress;
import org.foi.nwtis.datomala.listener.ApplicationListener;
import org.foi.nwtis.datomala.model.Message;
import org.foi.nwtis.datomala.model.MessageAttachment;


/**
 *
 * View all received emails
 * @author dex
 */
@ManagedBean
@SessionScoped
public class ViewAllMessages implements Serializable {

    private String emailServer;
    private String username;
    private String password;
    private List<Message> msgs = new ArrayList<Message>();
    private HtmlDataTable dataTable = new HtmlDataTable();
    private String messageID;
    private Message message;
    private List<String> directories;
    private String selectedDirectory = "INBOX";
    private boolean emptyMap = true;

    /**
     * Creates a new instance of ViewAllmessages
     */
    public ViewAllMessages() {
        emailServer = ApplicationListener.config.getSettings("emailServer");
        username = ApplicationListener.config.getSettings("username");
        password = ApplicationListener.config.getSettings("password");
    }

    /**
     *
     * @return
     */
    public List<String> getDirectories() {
        directories = new ArrayList<String>();
        fetchMaps();
        return directories;
    }

    /**
     *
     * @return
     */
    public String getSelectedDirectory() {
        return selectedDirectory;
    }

    /**
     *
     * @param selectedDirectory
     */
    public void setSelectedDirectory(String selectedDirectory) {
        this.selectedDirectory = selectedDirectory;
    }

    /**
     *
     * @return
     */
    public String selectDirectory() {
        return "";
    }

    /**
     *
     * @return
     */
    public String getEmailServer() {
        return emailServer;
    }

    /**
     *
     * @param emailServer
     */
    public void setEmailServer(String emailServer) {
        this.emailServer = emailServer;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public List<Message> getMessages() {
        msgs.clear();
        loadMessages();
        Collections.reverse(msgs);
        return msgs;
    }

    /**
     *
     * @param messages
     */
    public void setMessages(List<Message> messages) {
        this.msgs = messages;
    }
    
    public void fetchMaps(){
        directories = new ArrayList<String>();
        try {
            Session session = null;
            Store store = null;
            
            session = Session.getDefaultInstance(System.getProperties(), null);
            store = session.getStore("imap");

            store.connect(emailServer, username, password);
            Folder[] f = store.getDefaultFolder().list();
            for(Folder fd : f)
                directories.add(fd.getName());
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(ViewAllMessages.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ViewAllMessages.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * View details of specific email
     * @return
     */
    public String viewMessage() {
        message = null;
        for (Message m : msgs) {
            if (m.getId().equals(messageID)) {
                message = m;
                return "OK";
            }
        }
        return "NOT_OK";
    }

    /**
     *
     * Delete selected email
     */
    public void deleteMessage() {
        Session session;
        Store store;
        Folder folder;
        javax.mail.Message message;
        javax.mail.Message[] messages;

        try {
            session = Session.getDefaultInstance(System.getProperties(), null);
            store = session.getStore("imap");
            store.connect(this.getEmailServer(), this.getUsername(), this.getPassword());
            // Get a handle on the default folder
            folder = store.getDefaultFolder();
            folder = folder.getFolder(selectedDirectory);

            // Reading the Email Index in Read / Write Mode
            folder.open(Folder.READ_WRITE);
            messages = folder.getMessages();

            // Delete message
            for (int messageNumber = 0; messageNumber < messages.length; messageNumber++) {
                message = messages[messageNumber];
                if (message.getHeader("Message-ID")[0].equals(messageID)) {
                    messages[messageNumber].setFlag(Flags.Flag.DELETED, true);
                }
            }
            folder.close(true);
            store.close();
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(ViewAllMessages.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(ViewAllMessages.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * Deletes emails from every folder
     */
    public void deleteAllMessages() {

        Session session;
        Store store;
        Folder folder;
        javax.mail.Message[] messages;
        String info;
        int papers = 0;
        int trees = 0;
        double avgSheetsPerTree = 8333.3;

        for (String dir : directories) {
            try {
                session = Session.getDefaultInstance(System.getProperties(), null);
                store = session.getStore("imap");
                store.connect(this.getEmailServer(), this.getUsername(), this.getPassword());
                // Get a handle on the default folder
                folder = store.getDefaultFolder();
                folder = folder.getFolder(dir);

                // Reading the Email Index in Read / Write Mode
                folder.open(Folder.READ_WRITE);
                messages = folder.getMessages();

                // Delete all messages
                for (int messageNumber = 0; messageNumber < messages.length; messageNumber++) {
                    messages[messageNumber].setFlag(Flags.Flag.DELETED, true);
                }
                papers += messages.length;

                folder.close(true);
                store.close();
                System.out.println("Folder " + dir + " cleared!");

            } catch (NoSuchProviderException ex) {
                Logger.getLogger(ViewAllMessages.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                Logger.getLogger(ViewAllMessages.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (papers > 0) {
            while (papers >= avgSheetsPerTree) {
                papers -= avgSheetsPerTree;
                trees++;
            }
            info = "Way to go! You've just recycled " + papers + " digital paper(s) "
                    + " and saved " + trees + " digital tree(s) and "
                    + ((papers / avgSheetsPerTree) * 100) + "% of another digital tree!";
        } else {
            info = "Nothing to recycle, no digital tree saving :(";
        }
        FacesContext.getCurrentInstance().addMessage("recycle", new FacesMessage(info));
    }

    /**
     *
     * @return
     */
    public String getMessageID() {
        return messageID;
    }

    /**
     *
     * @param messageID
     */
    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public boolean isEmptyMap() {
        return emptyMap;
    }

    public void setEmptyMap(boolean emptyMap) {
        this.emptyMap = emptyMap;
    }

    /**
     *
     * @return
     */
    public Message getMessage() {
        return message;
    }

    /**
     *
     * @param message
     */
    public void setMessage(Message message) {
        this.message = message;
    }

    // Pagination
    /**
     *
     */
    public void pageFirst() {
        dataTable.setFirst(0);
    }

    /**
     *
     */
    public void pagePrevious() {
        dataTable.setFirst(dataTable.getFirst() - dataTable.getRows());
    }

    /**
     *
     */
    public void pageNext() {
        dataTable.setFirst(dataTable.getFirst() + dataTable.getRows());
    }

    /**
     *
     */
    public void pageLast() {
        int count = dataTable.getRowCount();
        int rows = dataTable.getRows();
        dataTable.setFirst(count - ((count % rows != 0) ? count % rows : rows));
    }
    // get the current page number

    /**
     *
     * @return
     */
    public int getCurrentPage() {
        int rows = dataTable.getRows();
        int first = dataTable.getFirst();
        int count = dataTable.getRowCount();
        return (count / rows) - ((count - first) / rows) + 1;
    }
    // get the total no of pages

    /**
     *
     * @return
     */
    public int getTotalPages() {
        int rows = dataTable.getRows();
        int count = dataTable.getRowCount();
        return (count / rows) + ((count % rows != 0) ? 1 : 0);
    }

    /**
     *
     * @return
     */
    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    /**
     *
     * @param dataTable
     */
    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    /**
     * 
     * Fetch emails from server
     * @author dex
     */
    private void loadMessages() {
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

        try {
            session = Session.getDefaultInstance(System.getProperties(), null);
            store = session.getStore("imap");
            store.connect(this.getEmailServer(), this.getUsername(), this.getPassword());

            // Get a handle on the default folder
            folder = store.getDefaultFolder();
            // Retrieve the mails from selected folder
            folder = folder.getFolder(selectedDirectory);
            //Reading the Email Index in Read Mode
            folder.open(Folder.READ_ONLY);
            // Retrieve the messages
            messages = folder.getMessages();

            // Loop over all of the messages
            for (int messageNumber = 0; messageNumber < messages.length; messageNumber++) {
                // Retrieve the next message to be read
                message = messages[messageNumber];
                // Retrieve the message content
                messagecontentObject = message.getContent();
                // Determine email type
                List<MessageAttachment> messageAttachments = new ArrayList<MessageAttachment>();

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

                        String fileName = "";
                        if (contentType.toLowerCase().startsWith("text/plain")) {
                        } else {
                            // Retrieve the file name
                            fileName = part.getFileName();
                        }

                        MessageAttachment attachment = new MessageAttachment(i, contentType, part.getSize(), fileName);
                        messageAttachments.add(attachment);
                    }
                } else {
                    // Mail without attachments
                    sender = ((InternetAddress) message.getFrom()[0]).getPersonal();

                    // If the "personal" information has no entry, check the address for the sender information
                    if (sender == null) {
                        sender = ((InternetAddress) message.getFrom()[0]).getAddress();
                    }

                    // Get the subject information
                    subject = message.getSubject();
                }

                String[] headers = message.getHeader("Message-ID");
                String messID = "";
                if (headers != null && headers.length > 0) {
                    messID = headers[0];
                }

                Message msg = new Message(messID, message.getSentDate(), sender, subject,
                        message.getContent().toString(),
                        message.getContentType(), message.getSize(),
                        messageAttachments.size(), message.getFlags(),
                        messageAttachments, false, false);
                msgs.add(msg);
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
        } catch (ReadOnlyFolderException e) {
            e.printStackTrace();
        } catch (StoreClosedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
