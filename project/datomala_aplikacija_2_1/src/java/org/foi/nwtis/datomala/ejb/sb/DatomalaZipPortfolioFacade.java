/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ejb.sb;

import java.util.List;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.foi.nwtis.datomala.ejb.eb.DatomalaPortfolio;
import org.foi.nwtis.datomala.ejb.eb.DatomalaZipPortfolio;
import org.foi.nwtis.datomala.ejb.eb.ZipCodes;

/**
 *
 * @author dex
 */
@Stateless
public class DatomalaZipPortfolioFacade extends AbstractFacade<DatomalaZipPortfolio> {
    @PersistenceContext(unitName = "datomala_aplikacija_2_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DatomalaZipPortfolioFacade() {
        super(DatomalaZipPortfolio.class);
    }
    
    /**
     * Dodavanje novih podataka u ZP (zipovi-portfolio) tablicu
     * @param portfolio
     * @param zip
     * @throws NamingException 
     */
    public void addZP(DatomalaPortfolio portfolio, int zip) throws NamingException{
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<ZipCodes> zips = cq.from(ZipCodes.class);
        cq.select(zips);
        cq.where(cb.equal(zips.get("zip"), zip));
        ZipCodes zc =  (ZipCodes) em.createQuery(cq).getResultList().get(0);
        
        DatomalaZipPortfolio zipPortfolio = new DatomalaZipPortfolio();
        zipPortfolio.setPortfolioId(portfolio);
        zipPortfolio.setZip(zc);

        em.persist(zipPortfolio);
        
    }
    
    /**
     * Dohvaćanje podataka prema portfolio id-u
     * @param portfolio
     * @return 
     */
    public List<DatomalaZipPortfolio> fetchZP(DatomalaPortfolio portfolio){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<DatomalaZipPortfolio> data = cq.from(DatomalaZipPortfolio.class);
        cq.select(data);
        cq.where(cb.equal(data.get("portfolio_id"), portfolio));
        if ( em.createQuery(cq).getResultList() == null)
            return null;
        else
            return (List<DatomalaZipPortfolio>) em.createQuery(cq).getResultList();
    }
    
}
