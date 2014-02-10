
package org.foi.nwtis.datomala.assignment_1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Saves the record of each action taken on the server
 * which can be serialized and recovered later
 * 
 * @author dex
 */
public class Records implements Serializable{
    
    public static List<Records> records = new ArrayList<>();
    private String user;
    private String command;
    private Date time;
    //private int status;
    //private String statusDescription;

    /**
     * Information needed for each record, obtained via constructor
     * 
     * @param user - username of user which made an action
     * @param command - command called by the 'user'
     * @param time - timestamp of recorded action
     */
    public Records(String user, String command, Date time) {
        this.user = user;
        this.command = command;
        this.time = time;
        //this.status = status;
        //this.statusDescription = statusDescription;
    }
    
    /**
     * Adds the passed record to the record list,
     * which will later be serialized
     * 
     * @param record - Record object containing action data
     */
    public static synchronized void add (Records record) {
        records.add(record);
    }

    /**
     * Getter for username
     * 
     * @return Username of an user from record
     */
    public String getUser() {
        return user;
    }

    /**
     * Getter for command
     * 
     * @return - Command called by the user
     */
    public String getCommand() {
        return command;
    }

    /**
     * Getter for time
     * 
     * @return - Timestamp of recorded action
     */
    public Date getTime() {
        return time;
    }
    /*
    public int getStatus() {
        return status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }
    */
}
