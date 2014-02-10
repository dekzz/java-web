/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.mb;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.foi.nwtis.datomala.RecordSerialization;
import org.foi.nwtis.datomala.config.Configuration;
import org.foi.nwtis.datomala.config.ConfigurationAbstract;
import org.foi.nwtis.datomala.config.NoConfiguration;
import org.foi.nwtis.datomala.listener.ApplicationListener;
import org.foi.nwtis.datomala.model.JMSMessageZip;

/**
 *
 * @author dex
 */
@MessageDriven(mappedName = "jms/NWTiS_datomala_2", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class ZipMB implements MessageListener {
    
    private static Configuration config;

    public static void setConfig(Configuration config) {
        ZipMB.config = config;
    }
    
    public ZipMB() {
    }
    
    @Override
    public void onMessage(Message message) {
        ObjectMessage objMsg = (ObjectMessage) message;
        try {
            JMSMessageZip jmsMsgZip = (JMSMessageZip) objMsg.getObject();
            RecordSerialization.recordZip.add(jmsMsgZip);
            String zip = jmsMsgZip.getZip();
            System.out.println("JMS for zip: " + jmsMsgZip.getZip());
            String s = sendRequest("USER " + config.getSettings("adminUsername") + 
                                   "; PASSWD" + config.getSettings("adminPassword") + 
                                   "admin; TEST ZIP " + zip + ";");
            if (s.trim().contains("OK 44")){
                sendRequest("USER " + config.getSettings("adminUsername") + 
                            "; PASSWD" + config.getSettings("adminPassword") + 
                            "admin; ADD ZIP " + zip + ";");
            }
        } catch (JMSException ex) {
            Logger.getLogger(MailMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String sendRequest(String request){
        StringBuilder response = new StringBuilder();
        try {
            InputStream is = null;
            OutputStream os = null;
            Socket server = null;
            int character;
            System.out.println("Request: " + request);
            String serverIP = ConfigurationAbstract.loadConfig(ApplicationListener.path + File.separator + "NWTiS.app.config.xml").getSettings("socketIpAddress");
            int port = Integer.parseInt(ConfigurationAbstract.loadConfig(ApplicationListener.path + File.separator + "NWTiS.app.config.xml").getSettings("socketPort"));
                try{
                    server = new Socket(serverIP, port);
                    os = server.getOutputStream();
                    is = server.getInputStream();
                    os.write(request.getBytes());
                    os.flush();
                    server.shutdownOutput();
                    
                    while ((character = is.read()) != -1){
                        response.append((char) character);
                    }
                }
                catch (IOException ex) {
                    System.out.println("ERROR: server is not responding.");
                    ex.printStackTrace();
                }
                finally{
                    try{
                        if (os != null)
                            os.close();
                        if (is != null)
                            is.close();
                        if (server != null)
                            server.close();
                    }
                    catch (IOException ex){
                    }
                } 
        } catch (NoConfiguration ex) {
            ex.printStackTrace();
        }
        System.out.println("REQUEST: " + request);
        System.out.println("RESPONSE: " + response);
        return response.toString();
    }
}
