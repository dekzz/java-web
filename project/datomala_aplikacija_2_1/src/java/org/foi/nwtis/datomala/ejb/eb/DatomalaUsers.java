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
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "DATOMALA_USERS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DatomalaUsers.findAll", query = "SELECT d FROM DatomalaUsers d"),
    @NamedQuery(name = "DatomalaUsers.findByUsername", query = "SELECT d FROM DatomalaUsers d WHERE d.username = :username"),
    @NamedQuery(name = "DatomalaUsers.findByPassword", query = "SELECT d FROM DatomalaUsers d WHERE d.password = :password"),
    @NamedQuery(name = "DatomalaUsers.findByType", query = "SELECT d FROM DatomalaUsers d WHERE d.type = :type")})
public class DatomalaUsers implements Serializable {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "username")
    private List<DatomalaPortfolio> datomalaPortfolioList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TYPE")
    private int type;

    public DatomalaUsers() {
    }

    public DatomalaUsers(String username) {
        this.username = username;
    }

    public DatomalaUsers(String username, String password, int type) {
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DatomalaUsers)) {
            return false;
        }
        DatomalaUsers other = (DatomalaUsers) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.foi.nwtis.datomala.ejb.eb.DatomalaUsers[ username=" + username + " ]";
    }

    @XmlTransient
    public List<DatomalaPortfolio> getDatomalaPortfolioList() {
        return datomalaPortfolioList;
    }

    public void setDatomalaPortfolioList(List<DatomalaPortfolio> datomalaPortfolioList) {
        this.datomalaPortfolioList = datomalaPortfolioList;
    }
    
}
