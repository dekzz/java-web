/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import org.foi.nwtis.datomala.RecordSerialization;
import org.foi.nwtis.datomala.model.JMSMessageMail;
import org.foi.nwtis.datomala.model.JMSMessageZip;

/**
 *
 * @author dex
 */
@Named(value = "dataJMS")
@SessionScoped
public class DataJMS implements Serializable {

    List<JMSMessageMail> dataMail;
    List<JMSMessageZip> dataZip;
    JMSMessageZip selectedZip;
    JMSMessageMail selectedMail;
    
    public DataJMS() {
    }
    
    public String deleteZip(){
        if (dataZip.contains(selectedZip)){
            dataZip.remove(selectedZip);
            RecordSerialization.recordZip.remove(selectedZip);
        }
        return "";
    }
    
    public String deleteMail(){
        if (dataMail.contains(selectedMail)){
            dataMail.remove(selectedMail);
            RecordSerialization.recordMail.remove(selectedMail);
        }
        return "";
    }
    
    public String deleteAllZips(){
        RecordSerialization.recordZip.clear();
        return "";
    }
    
    public String deleteAllMails(){
        RecordSerialization.recordMail.clear();
        return "";
    }

    public List<JMSMessageMail> getDataMail() {
        dataMail = RecordSerialization.recordMail;
        return dataMail;
    }

    public void setDataMail(List<JMSMessageMail> podaciMail) {
        this.dataMail = podaciMail;
    }

    public List<JMSMessageZip> getDataZip() {
        dataZip = RecordSerialization.recordZip;
        return dataZip;
    }

    public void setDataZip(List<JMSMessageZip> podaciZip) {
        this.dataZip = podaciZip;
    }

    public JMSMessageZip getSelectedZip() {
        return selectedZip;
    }

    public void setSelectedZip(JMSMessageZip odabraniZip) {
        this.selectedZip = odabraniZip;
    }

    public JMSMessageMail getSelectedMail() {
        return selectedMail;
    }

    public void setSelectedMail(JMSMessageMail odabraniMail) {
        this.selectedMail = odabraniMail;
    }
}
