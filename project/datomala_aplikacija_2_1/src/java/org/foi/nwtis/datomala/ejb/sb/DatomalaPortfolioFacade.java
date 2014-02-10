/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ejb.sb;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.foi.nwtis.datomala.ejb.eb.DatomalaPortfolio;
import org.foi.nwtis.datomala.ejb.eb.DatomalaUsers;

/**
 *
 * @author dex
 */
@Stateless
public class DatomalaPortfolioFacade extends AbstractFacade<DatomalaPortfolio> {
    @EJB
    private DatomalaUsersFacade datomalaUsersFacade;
    
    @PersistenceContext(unitName = "datomala_aplikacija_2_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DatomalaPortfolioFacade() {
        super(DatomalaPortfolio.class);
    }
    
    /**
     * Dohvaćanje portflia od danog vlasnika
     * @param owner
     * @return 
     */
    public List<DatomalaPortfolio> fetchPortfolios(String owner){
        DatomalaUsers user = datomalaUsersFacade.fetchUser(owner);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<DatomalaPortfolio> data = cq.from(DatomalaPortfolio.class);
        cq.select(data);
        cq.where(cb.equal(data.get("username"), user));
        return em.createQuery(cq).getResultList();
    }
    
    /**
     * Dohvaćanje portfolia prema id-u
     * @param id
     * @return 
     */
    public DatomalaPortfolio fetchPortfolio(int id){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<DatomalaPortfolio> data = cq.from(DatomalaPortfolio.class);
        cq.select(data);
        cq.where(cb.equal(data.get("id"), id));
        return (DatomalaPortfolio) em.createQuery(cq).getResultList().get(0);
    }
    
    /**
     * Dodavanje novog portfolia u bazu
     * @param name
     * @param owner
     * @return
     * @throws NamingException 
     */
    public DatomalaPortfolio addPortfolio(String name, String owner) throws NamingException{
        DatomalaUsers owenerName = datomalaUsersFacade.fetchUser(owner);
        DatomalaPortfolio portfolio = new DatomalaPortfolio();
        portfolio.setName(name);
        portfolio.setUsername(owenerName);
        em.persist(portfolio);
        return portfolio;
    }
    
    /**
     * Dohvaćanje portfola prema nazivu portfolia
     * (ime je malo čudno, al neda mi se mijenjat, bilo je nekog refactoringa)
     * @param name
     * @return 
     */
    public DatomalaPortfolio fetchPortfolioByName(String name){
        System.out.println("Name: " + name);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<DatomalaPortfolio> data = cq.from(DatomalaPortfolio.class);
        cq.select(data);
        cq.where(cb.equal(data.get("name"), name));
        System.out.println("Q: " + em.createQuery(cq).getResultList());
        if (em.createQuery(cq).getResultList() != null)
            return (DatomalaPortfolio) em.createQuery(cq).getResultList().get(0);
        else
            return null;
    }
    
}
