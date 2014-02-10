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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dex
 */
@Entity
@Table(name = "DATOMALA_ZIP_PORTFOLIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DatomalaZipPortfolio.findAll", query = "SELECT d FROM DatomalaZipPortfolio d"),
    @NamedQuery(name = "DatomalaZipPortfolio.findById", query = "SELECT d FROM DatomalaZipPortfolio d WHERE d.id = :id")})
public class DatomalaZipPortfolio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "ZIP", referencedColumnName = "ZIP")
    @ManyToOne(optional = false)
    private ZipCodes zip;
    @JoinColumn(name = "PORTFOLIO_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private DatomalaPortfolio portfolioId;

    public DatomalaZipPortfolio() {
    }

    public DatomalaZipPortfolio(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ZipCodes getZip() {
        return zip;
    }

    public void setZip(ZipCodes zip) {
        this.zip = zip;
    }

    public DatomalaPortfolio getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(DatomalaPortfolio portfolioId) {
        this.portfolioId = portfolioId;
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
        if (!(object instanceof DatomalaZipPortfolio)) {
            return false;
        }
        DatomalaZipPortfolio other = (DatomalaZipPortfolio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.datomala.ejb.eb.DatomalaZipPortfolio[ id=" + id + " ]";
    }
    
}
