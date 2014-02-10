/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.model;

/**
 * Holds email attachments
 * @author dex
 */
public class MessageAttachment {

    private int count;
    private String type;
    private int size;
    private String file;

    /**
     *
     * @param count
     * @param type
     * @param size
     * @param file
     */
    public MessageAttachment(int count, String type, int size, String file) {
        this.count = count;
        this.type = type;
        this.size = size;
        this.file = file;
    }

    /**
     *
     * @return
     */
    public int getCount() {
        return count;
    }

    /**
     *
     * @return
     */
    public String getFile() {
        return file;
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
    public String getType() {
        return type;
    }
    
}
