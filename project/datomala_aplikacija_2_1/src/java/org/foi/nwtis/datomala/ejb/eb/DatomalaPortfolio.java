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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "DATOMALA_PORTFOLIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DatomalaPortfolio.findAll", query = "SELECT d FROM DatomalaPortfolio d"),
    @NamedQuery(name = "DatomalaPortfolio.findById", query = "SELECT d FROM DatomalaPortfolio d WHERE d.id = :id"),
    @NamedQuery(name = "DatomalaPortfolio.findByName", query = "SELECT d FROM DatomalaPortfolio d WHERE d.name = :name")})
public class DatomalaPortfolio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NAME")
    private String name;
    @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")
    @ManyToOne(optional = false)
    private DatomalaUsers username;

    public DatomalaPortfolio() {
    }

    public DatomalaPortfolio(Integer id) {
        this.id = id;
    }

    public DatomalaPortfolio(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DatomalaUsers getUsername() {
        return username;
    }

    public void setUsername(DatomalaUsers username) {
        this.username = username;
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
        if (!(object instanceof DatomalaPortfolio)) {
            return false;
        }
        DatomalaPortfolio other = (DatomalaPortfolio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.datomala.ejb.eb.DatomalaPortfolio[ id=" + id + " ]";
    }
    
}
