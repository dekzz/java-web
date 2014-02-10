/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ejb.eb;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "GRUPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupe.findAll", query = "SELECT g FROM Grupe g"),
    @NamedQuery(name = "Grupe.findByGrIme", query = "SELECT g FROM Grupe g WHERE g.grIme = :grIme"),
    @NamedQuery(name = "Grupe.findByNaziv", query = "SELECT g FROM Grupe g WHERE g.naziv = :naziv")})
public class Grupe implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "GR_IME")
    private String grIme;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "NAZIV")
    private String naziv;

    public Grupe() {
    }

    public Grupe(String grIme) {
        this.grIme = grIme;
    }

    public Grupe(String grIme, String naziv) {
        this.grIme = grIme;
        this.naziv = naziv;
    }

    public String getGrIme() {
        return grIme;
    }

    public void setGrIme(String grIme) {
        this.grIme = grIme;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grIme != null ? grIme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grupe)) {
            return false;
        }
        Grupe other = (Grupe) object;
        if ((this.grIme == null && other.grIme != null) || (this.grIme != null && !this.grIme.equals(other.grIme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.datomala.ejb.eb.Grupe[ grIme=" + grIme + " ]";
    }
    
}
