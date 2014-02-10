/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ejb.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.InitialContext;
import org.foi.nwtis.datomala.model.JMSMessageMail;

/**
 *
 * @author dex
 */
@Stateless
public class MailJMS {

    public void sendJMSMessageToNWTiS_datomala_1(String param1, String param2, int param3, int param4) {
        Session session = null;
        Connection connection = null;
        try {
            InitialContext ic = new InitialContext();
            ConnectionFactory cf = (ConnectionFactory)ic.lookup("jms/NWTiS_QF_datomala_1");
            Queue orderQueue = (Queue)ic.lookup("jms/NWTiS_datomala_1");
            connection = cf.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(orderQueue);
            
            connection.start();
            ObjectMessage message = session.createObjectMessage();
            JMSMessageMail jmsMsgMail = new JMSMessageMail(param2, param2, param3+"", param4+"");
            message.setObject(jmsMsgMail);
            producer.send(message);
            producer.close();
            connection.close();

        } catch(Exception e){
            e.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot close session", e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException ex) {
                    Logger.getLogger(MailJMS.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
