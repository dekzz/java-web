package org.foi.nwtis.datomala.server;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.foi.nwtis.datomala.config.Configuration;
import org.foi.nwtis.datomala.db.DBManager;
import org.foi.nwtis.datomala.model.MeteoData;

/**
 * Handles user requests
 *
 * @author dex
 */
public class CommandServerThread extends Thread {

    private Socket client;
    private Configuration config;
    private long startTime;
    private long executionTime;
    private long lastStateDuration;
    private int nrCommandsReceived;
    private int nrCommandsInvalid;
    private int nrCommandsExecuted;
    private String content;
    
    private Pattern pattern;
    private Matcher match;
    private String userRegex = "^USER ([a-zA-Z0-9_]+)\\; GET ZIP (\\d\\d\\d\\d\\d)\\; *$"; 
    private String adminRegex = "^USER ([a-zA-Z0-9_]+)\\; PASSWD ([a-zA-Z0-9_]+)\\; (PAUSE\\;|START\\;|STOP\\;|TEST ZIP (\\d\\d\\d\\d\\d)\\;|GET ZIP (\\d\\d\\d\\d\\d)\\;|ADD ZIP (\\d\\d\\d\\d\\d);)\\ *$";

    public CommandServerThread(Socket client, Configuration config) {
        this.client = client;
        this.config = config;
    }


    @Override
    public synchronized void start() {
        super.start(); //To change body of generated methods, choose Tools | Templates.
        System.out.println("Command thread started...");
    }


    @Override
    public void run() {

        if (CommandServer.getStopTime() != 0) {
            lastStateDuration = System.currentTimeMillis() - CommandServer.getStopTime();
        }

        startTime = System.currentTimeMillis();
        InputStream is = null;
        OutputStream os = null;

        String username = "unknown";
        String password = "";
        String action = "";
        MeteoData result;
        boolean validUser = false;
        content = "";

        nrCommandsReceived = 0;
        nrCommandsInvalid = 0;
        nrCommandsExecuted = 0;
        //FIXME won't recognize command!!1
        try {
            is = client.getInputStream();
            os = client.getOutputStream();
            String response = "";

            StringBuilder cmd = new StringBuilder();
            int letter;
            //while ((letter = is.read()) != -1 || is.available() > 0) {
            //    cmd.append((char) letter);
            //}
            while (true){
                letter = is.read();
                if (letter == -1 )
                    break;
                cmd.append((char) letter);
            }
            String command = cmd.toString().trim();
            nrCommandsReceived++;

            if (matchRegex(command, adminRegex)) {

                username = match.group(1);
                password = match.group(2);
                action = match.group(3);
                System.out.println("action " + action);

                validUser = DBManager.authenticateUser(username, password);
                if (validUser) {
                    if (action.equals("PAUSE")) {
                        if (!CommandServer.isPause()) {
                            CommandServer.setPause(true);
                            response = "OK 10 Pausing server";
                        } else {
                            response = "OK 40 Server already in pause";
                        }
                    } else if (action.equals("START")) {
                        if (CommandServer.isPause()) {
                            CommandServer.setPause(false);
                            response = "OK 10 Starting server";
                        } else {
                            response = "OK 41 Server not paused";
                        }
                    } else if (action.equals("STOP")) {
                        if (!CommandServer.isEnd()) {
                            CommandServer.setEnd(true);
                            response = "OK 10 Stopping server";
                            interrupt();
                        } else {
                            response = "OK 42 Stopping in progress";
                        }
                    } else if (action.equals("ADD ZIP")) {
                        String zipcode = match.group(6);
                        if (!DBManager.existsInActiveZipCodes(zipcode)) {
                            DBManager.insertIntoActiveZipCodes(zipcode, username);
                            System.out.println("ADDED ZIP TO DB");
                            response = "OK 10 Adding zip code";
                        } else {
                            response = "OK 42 Zip code already exists";
                        }
                    } else if (action.equals("TEST ZIP")) {
                        String zipcode = match.group(4);
                        if (DBManager.existsInActiveZipCodes(zipcode)) {
                            response = "OK 10 Zip code is active";
                        } else {
                            response = "OK 44 Zip code is inactive";
                        }
                    }
                    nrCommandsExecuted++;
                }

            } else if (matchRegex(command, userRegex)) {
                username = match.group(1);
                String zipcode = match.group(2);
                if (DBManager.existsInActiveZipCodes(zipcode)) {
                    /*
                    result = DBManager.selectLatestDataForZipCode(zipcode);
                    response = "OK 10 TEMPERATURE " + result.getTemperature()
                            + "HUMIDITY " + result.getHumidity()
                            + "PRESSURE " + result.getPressure()
                            + "LATITUDE " + result.getLatitude()
                            + "LONGITUDE " + result.getLongitude();
                            */
                    response = "OK 10 " + DBManager.selectLatestDataString(zipcode);
                } else {
                    response = "OK 43 Zip code is not active";
                }
                nrCommandsExecuted++;
            } else {
                response = "ERROR: Unknown command!";
                nrCommandsInvalid++;
            }

            CommandServer.setStopTime(System.currentTimeMillis());
            executionTime = startTime - CommandServer.getStopTime();

            if (validUser) {
                sendMessage();
            }

            DBManager.insertIntoCommandLog(username, command, response);
            
            os.write(response.getBytes());
            os.flush();

        } catch (IOException ex) {
            Logger.getLogger(CommandServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                is.close();
                os.close();
                client.close();

            } catch (IOException ex) {
                Logger.getLogger(CommandServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean matchRegex(String command, String regex) {
        pattern = Pattern.compile(regex);
        match = pattern.matcher(command);
        if (match.matches()) {
            return true;
        }
        return false;
    }

    private boolean sendMessage() {
        try {
            int randID = new Random().nextInt(Integer.MAX_VALUE) + 1;

            // Create the JavaMail session
            java.util.Properties properties = System.getProperties();
            properties.put("mail.smtp.host", config.getSettings("emailServer"));

            Session session = Session.getInstance(properties, null);

            // Construct the message
            MimeMessage message = new MimeMessage(session);

            // Message ID
            message.setHeader("Message-ID", Integer.toString(randID));
            message.setHeader("Content-Type", "text/plain");

            // Set the from address
            Address fromAddress = new InternetAddress("CMD-server-report@nwtis.foi.hr");
            message.setFrom(fromAddress);

            // Parse and set the recipient addresses
            Address[] toAddresses = InternetAddress.parse(config.getSettings("reportEmail"));
            message.setRecipients(Message.RecipientType.TO, toAddresses);

            // Set the subject and text
            message.setSubject(config.getSettings("reportSubject"));
            content = "Execution time: " + executionTime + "; "
                    + "Last state duration: " + lastStateDuration + "; "
                    + "Commands Received: " + nrCommandsReceived + "; "
                    + "Commands Invalid: " + nrCommandsInvalid + "; "
                    + "Commands Executed: " + nrCommandsExecuted + "; ";
            message.setText(this.content);

            message.setSentDate(new Date());

            Transport.send(message);

            return true;

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (SendFailedException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     */
    @Override
    public void interrupt() {
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
    }
}
