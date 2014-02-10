/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ejb.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.foi.nwtis.datomala.model.JMSMessageZip;

/**
 *
 * @author dex
 */
@Singleton
@LocalBean
public class ZipJMS {

    public void sendJMSMessageToNWTiS_datomala_2(String zip) throws JMSException {
        Connection connection = null;
        Session session = null;
        try {
            InitialContext ic = new InitialContext();
            ConnectionFactory cf = (ConnectionFactory)ic.lookup("jms/NWTiS_QF_lurajcevi_2");
            Queue orderQueue = (Queue)ic.lookup("jms/NWTiS_lurajcevi_2");
            connection = cf.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(orderQueue);
            
            connection.start();
            ObjectMessage message = session.createObjectMessage();
            JMSMessageZip jmsMsgZip = new JMSMessageZip(zip);
            message.setObject(jmsMsgZip);
            producer.send(message);
            producer.close();
            connection.close();
        } catch (NamingException ex) {
            Logger.getLogger(ZipJMS.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Cannot close session", e);
                }
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

}
