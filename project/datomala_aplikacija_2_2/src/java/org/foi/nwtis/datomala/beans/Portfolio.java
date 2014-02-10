/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.datomala.ejb.eb.DatomalaPortfolio;
import org.foi.nwtis.datomala.ejb.sb.DatomalaPortfolioFacade;

/**
 *
 * @author dex
 */
@Named(value = "portfolio")
@SessionScoped
public class Portfolio implements Serializable {
    @EJB
    private DatomalaPortfolioFacade datomalaPortfolioFacade;

    private List<DatomalaPortfolio> portfolioList;
    private boolean exist = false;
    private String portfolioID;
    
    private HttpSession session;
    private FacesContext context;
    

    /**
     * Creates a new instance of Portfolio
     */
    public Portfolio() {
        context = FacesContext.getCurrentInstance();
        session = (HttpSession)(context.getExternalContext().getSession(true)); 
    }
    

    public List<DatomalaPortfolio> getPortfolioList() {
        String username = (String) session.getAttribute("username");
        portfolioList = datomalaPortfolioFacade.fetchPortfolios(username);
        if (portfolioList.size() > 0)
            exist = true;
        return portfolioList;
    }
    
    public String portfoliosView(){
        PortfolioView.setMeteoData(null);
        PortfolioView.setViewId(0);
        return "OK";
    }


    public void setPortfolioList(List<DatomalaPortfolio> portfolioList) {
        this.portfolioList = portfolioList;
    }

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public String getPortfolioID() {
        return portfolioID;
    }

    public void setPortfolioID(String portfolioID) {
        this.portfolioID = portfolioID;
    }
}
