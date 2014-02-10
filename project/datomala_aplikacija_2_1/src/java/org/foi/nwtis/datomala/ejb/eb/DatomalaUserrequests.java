/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ejb.eb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dex
 */
@Entity
@Table(name = "DATOMALA_USERREQUESTS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DatomalaUserrequests.findAll", query = "SELECT d FROM DatomalaUserrequests d"),
    @NamedQuery(name = "DatomalaUserrequests.findById", query = "SELECT d FROM DatomalaUserrequests d WHERE d.id = :id"),
    @NamedQuery(name = "DatomalaUserrequests.findByUsername", query = "SELECT d FROM DatomalaUserrequests d WHERE d.username = :username"),
    @NamedQuery(name = "DatomalaUserrequests.findByRequest", query = "SELECT d FROM DatomalaUserrequests d WHERE d.request = :request"),
    @NamedQuery(name = "DatomalaUserrequests.findByDuration", query = "SELECT d FROM DatomalaUserrequests d WHERE d.duration = :duration")})
public class DatomalaUserrequests implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "REQUEST")
    private String request;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DURATION")
    private String duration;

    public DatomalaUserrequests() {
    }

    public DatomalaUserrequests(Integer id) {
        this.id = id;
    }

    public DatomalaUserrequests(Integer id, String username, String request, String duration) {
        this.id = id;
        this.username = username;
        this.request = request;
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DatomalaUserrequests)) {
            return false;
        }
        DatomalaUserrequests other = (DatomalaUserrequests) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.datomala.ejb.eb.DatomalaUserrequests[ id=" + id + " ]";
    }
    
}
