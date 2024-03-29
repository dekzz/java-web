/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ejb.eb;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dex
 */
@Entity
@Table(name = "COUNTIES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Counties.findAll", query = "SELECT c FROM Counties c"),
    @NamedQuery(name = "Counties.findByState", query = "SELECT c FROM Counties c WHERE c.countiesPK.state = :state"),
    @NamedQuery(name = "Counties.findByCounty", query = "SELECT c FROM Counties c WHERE c.countiesPK.county = :county"),
    @NamedQuery(name = "Counties.findByName", query = "SELECT c FROM Counties c WHERE c.name = :name")})
public class Counties implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CountiesPK countiesPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NAME")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "counties")
    private List<Cities> citiesList;
    @JoinColumn(name = "STATE", referencedColumnName = "STATE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private States states;

    public Counties() {
    }

    public Counties(CountiesPK countiesPK) {
        this.countiesPK = countiesPK;
    }

    public Counties(CountiesPK countiesPK, String name) {
        this.countiesPK = countiesPK;
        this.name = name;
    }

    public Counties(String state, String county) {
        this.countiesPK = new CountiesPK(state, county);
    }

    public CountiesPK getCountiesPK() {
        return countiesPK;
    }

    public void setCountiesPK(CountiesPK countiesPK) {
        this.countiesPK = countiesPK;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<Cities> getCitiesList() {
        return citiesList;
    }

    public void setCitiesList(List<Cities> citiesList) {
        this.citiesList = citiesList;
    }

    public States getStates() {
        return states;
    }

    public void setStates(States states) {
        this.states = states;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (countiesPK != null ? countiesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Counties)) {
            return false;
        }
        Counties other = (Counties) object;
        if ((this.countiesPK == null && other.countiesPK != null) || (this.countiesPK != null && !this.countiesPK.equals(other.countiesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.datomala.ejb.eb.Counties[ countiesPK=" + countiesPK + " ]";
    }
    
}
