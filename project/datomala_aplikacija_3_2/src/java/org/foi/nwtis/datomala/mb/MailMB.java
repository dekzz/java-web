/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.mb;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.foi.nwtis.datomala.RecordSerialization;
import org.foi.nwtis.datomala.model.JMSMessageMail;

/**
 *
 * @author dex
 */
@MessageDriven(mappedName = "jms/NWTiS_datomala_1", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MailMB implements MessageListener {
    
    public MailMB() {
    }
    
    @Override
    public void onMessage(Message message) {
        ObjectMessage objMsg = (ObjectMessage) message;
        try {
            JMSMessageMail jmsMsgMail = (JMSMessageMail) objMsg.getObject();
            RecordSerialization.recordMail.add(jmsMsgMail);
            System.out.println("MSG received ");
            System.out.println(jmsMsgMail.getStartTime());
            System.out.println(jmsMsgMail.getStopTime());
        } catch (JMSException ex) {
            Logger.getLogger(MailMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
