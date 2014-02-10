/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ejb.eb;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dex
 */
@Entity
@Table(name = "POLAZNICI_GRUPE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PolazniciGrupe.findAll", query = "SELECT p FROM PolazniciGrupe p"),
    @NamedQuery(name = "PolazniciGrupe.findByKorIme", query = "SELECT p FROM PolazniciGrupe p WHERE p.polazniciGrupePK.korIme = :korIme"),
    @NamedQuery(name = "PolazniciGrupe.findByGrIme", query = "SELECT p FROM PolazniciGrupe p WHERE p.polazniciGrupePK.grIme = :grIme")})
public class PolazniciGrupe implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PolazniciGrupePK polazniciGrupePK;
    @JoinColumn(name = "KOR_IME", referencedColumnName = "KOR_IME", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Polaznici polaznici;

    public PolazniciGrupe() {
    }

    public PolazniciGrupe(PolazniciGrupePK polazniciGrupePK) {
        this.polazniciGrupePK = polazniciGrupePK;
    }

    public PolazniciGrupe(String korIme, String grIme) {
        this.polazniciGrupePK = new PolazniciGrupePK(korIme, grIme);
    }

    public PolazniciGrupePK getPolazniciGrupePK() {
        return polazniciGrupePK;
    }

    public void setPolazniciGrupePK(PolazniciGrupePK polazniciGrupePK) {
        this.polazniciGrupePK = polazniciGrupePK;
    }

    public Polaznici getPolaznici() {
        return polaznici;
    }

    public void setPolaznici(Polaznici polaznici) {
        this.polaznici = polaznici;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (polazniciGrupePK != null ? polazniciGrupePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PolazniciGrupe)) {
            return false;
        }
        PolazniciGrupe other = (PolazniciGrupe) object;
        if ((this.polazniciGrupePK == null && other.polazniciGrupePK != null) || (this.polazniciGrupePK != null && !this.polazniciGrupePK.equals(other.polazniciGrupePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.datomala.ejb.eb.PolazniciGrupe[ polazniciGrupePK=" + polazniciGrupePK + " ]";
    }
    
}
