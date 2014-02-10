/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ejb.eb;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dex
 */
@Entity
@Table(name = "POLAZNICI")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Polaznici.findAll", query = "SELECT p FROM Polaznici p"),
    @NamedQuery(name = "Polaznici.findByKorIme", query = "SELECT p FROM Polaznici p WHERE p.korIme = :korIme"),
    @NamedQuery(name = "Polaznici.findByIme", query = "SELECT p FROM Polaznici p WHERE p.ime = :ime"),
    @NamedQuery(name = "Polaznici.findByPrezime", query = "SELECT p FROM Polaznici p WHERE p.prezime = :prezime"),
    @NamedQuery(name = "Polaznici.findByLozinka", query = "SELECT p FROM Polaznici p WHERE p.lozinka = :lozinka"),
    @NamedQuery(name = "Polaznici.findByEmailAdresa", query = "SELECT p FROM Polaznici p WHERE p.emailAdresa = :emailAdresa"),
    @NamedQuery(name = "Polaznici.findByVrsta", query = "SELECT p FROM Polaznici p WHERE p.vrsta = :vrsta"),
    @NamedQuery(name = "Polaznici.findByDatumKreiranja", query = "SELECT p FROM Polaznici p WHERE p.datumKreiranja = :datumKreiranja"),
    @NamedQuery(name = "Polaznici.findByDatumPromjene", query = "SELECT p FROM Polaznici p WHERE p.datumPromjene = :datumPromjene")})
public class Polaznici implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "KOR_IME")
    private String korIme;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "IME")
    private String ime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 25)
    @Column(name = "PREZIME")
    private String prezime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "LOZINKA")
    private String lozinka;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "EMAIL_ADRESA")
    private String emailAdresa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VRSTA")
    private int vrsta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATUM_KREIRANJA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumKreiranja;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATUM_PROMJENE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumPromjene;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "polaznici")
    private List<PolazniciGrupe> polazniciGrupeList;

    public Polaznici() {
    }

    public Polaznici(String korIme) {
        this.korIme = korIme;
    }

    public Polaznici(String korIme, String ime, String prezime, String lozinka, String emailAdresa, int vrsta, Date datumKreiranja, Date datumPromjene) {
        this.korIme = korIme;
        this.ime = ime;
        this.prezime = prezime;
        this.lozinka = lozinka;
        this.emailAdresa = emailAdresa;
        this.vrsta = vrsta;
        this.datumKreiranja = datumKreiranja;
        this.datumPromjene = datumPromjene;
    }

    public String getKorIme() {
        return korIme;
    }

    public void setKorIme(String korIme) {
        this.korIme = korIme;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getEmailAdresa() {
        return emailAdresa;
    }

    public void setEmailAdresa(String emailAdresa) {
        this.emailAdresa = emailAdresa;
    }

    public int getVrsta() {
        return vrsta;
    }

    public void setVrsta(int vrsta) {
        this.vrsta = vrsta;
    }

    public Date getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(Date datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }

    public Date getDatumPromjene() {
        return datumPromjene;
    }

    public void setDatumPromjene(Date datumPromjene) {
        this.datumPromjene = datumPromjene;
    }

    @XmlTransient
    public List<PolazniciGrupe> getPolazniciGrupeList() {
        return polazniciGrupeList;
    }

    public void setPolazniciGrupeList(List<PolazniciGrupe> polazniciGrupeList) {
        this.polazniciGrupeList = polazniciGrupeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (korIme != null ? korIme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Polaznici)) {
            return false;
        }
        Polaznici other = (Polaznici) object;
        if ((this.korIme == null && other.korIme != null) || (this.korIme != null && !this.korIme.equals(other.korIme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.datomala.ejb.eb.Polaznici[ korIme=" + korIme + " ]";
    }
    
}
