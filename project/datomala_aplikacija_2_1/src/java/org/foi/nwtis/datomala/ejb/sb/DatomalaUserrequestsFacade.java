/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ejb.sb;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.foi.nwtis.datomala.ejb.eb.DatomalaUserrequests;

/**
 *
 * @author dex
 */
@Stateless
public class DatomalaUserrequestsFacade extends AbstractFacade<DatomalaUserrequests> {
    @PersistenceContext(unitName = "datomala_aplikacija_2_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DatomalaUserrequestsFacade() {
        super(DatomalaUserrequests.class);
    }
    
    public void addUserRequest(String request, String username) {
        DatomalaUserrequests req = new DatomalaUserrequests();
        req.setRequest(request);
        req.setUsername(username);
        req.setDuration(new Date().toString());
 
        em.persist(req);
    }
    
    /**
     * Dohvaća sve zahtjeve
     * @return
     */
    public List<DatomalaUserrequests> fetchUserRequests(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<DatomalaUserrequests> data = cq.from(DatomalaUserrequests.class);
        cq.select(data);
        return em.createQuery(cq).getResultList();
    }
    
    /**
     * Dohvaća zahtjeve filtrirane prema datumu
     * @param date1 - prvi datum
     * @param date2 - drugi datum
     * @return 
     */
    public List<DatomalaUserrequests> fetchUserRequestsByInterval(Date date1, Date date2){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<DatomalaUserrequests> data = cq.from(DatomalaUserrequests.class);
        cq.select(data);
        cq.where(cb.and(cb.between(data.<Date>get("duration"), date1, date2)));
        return em.createQuery(cq).getResultList();
    }
     
    /**
     * Dohvaća zahtjeve filtrirano po korisniku
     * @param username
     * @return 
     */
    public List<DatomalaUserrequests> fetchUserRequestsByUsername(String username){
       CriteriaBuilder cb = em.getCriteriaBuilder();
       CriteriaQuery cq = cb.createQuery();
       Root<DatomalaUserrequests> data = cq.from(DatomalaUserrequests.class);
       cq.select(data);
       cq.where(cb.like(data.<String>get("username"), username + "%"));
       return em.createQuery(cq).getResultList();
    }
    
}
