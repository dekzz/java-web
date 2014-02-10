/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.web.controls;

import java.util.Date;
import java.util.List;
import javax.mail.Flags;

/**
 * Holds data for email
 * @author dex
 */
public class Message {
    private String id;
    private Date time;
    private String sender;
    private String subject;
    private String content;
    private String type;
    private int size;    
    private int nrAttachments;
    private Flags flags;
    private List<MessageAttachment> messageAttachments;
    private boolean delete;
    private boolean read;

    /**
     *
     * @param id
     * @param time
     * @param sender
     * @param subject
     * @param content
     * @param type
     * @param size
     * @param nrAttachments
     * @param flags
     * @param messageAttachments
     * @param delete
     * @param read
     */
    public Message(String id, Date time, String sender, String subject, String content, String type, int size, int nrAttachments, Flags flags, List<MessageAttachment> messageAttachments, boolean delete, boolean read) {
        this.id = id;
        this.time = time;
        this.sender = sender;
        this.subject = subject;
        this.content = content;
        this.type = type;
        this.size = size;
        this.nrAttachments = nrAttachments;
        this.flags = flags;
        this.messageAttachments = messageAttachments;
        this.delete = delete;
        this.read = read;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @return
     */
    public boolean isDelete() {
        return delete;
    }

    /**
     *
     * @param delete
     */
    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    /**
     *
     * @return
     */
    public int getNrAttachments() {
        return nrAttachments;
    }

    /**
     *
     * @return
     */
    public Flags getFlags() {
        return flags;
    }

    /**
     *
     * @return
     */
    public Date getTime() {
        return time;
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
     * @return
     */
    public List<MessageAttachment> getMessageAttachments() {
        return messageAttachments;
    }

    /**
     *
     * @return
     */
    public boolean isRead() {
        return read;
    }

    /**
     *
     * @param read
     */
    public void setRead(boolean read) {
        this.read = read;
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
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     *
     * @return
     */
    public String getContent() {
        return content;
    }
    
}
