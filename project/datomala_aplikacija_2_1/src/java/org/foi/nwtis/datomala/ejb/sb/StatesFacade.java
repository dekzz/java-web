/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ejb.sb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.foi.nwtis.datomala.ejb.eb.States;

/**
 *
 * @author dex
 */
@Stateless
public class StatesFacade extends AbstractFacade<States> {
    @PersistenceContext(unitName = "datomala_aplikacija_2_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StatesFacade() {
        super(States.class);
    }
    
    /**
     * Filtrira drzave prema unesenom nazivu (LIKE operator)
     * @param naziv - proslijeđena vrijednost
     * @return rezultate upita
     */
    public List<States> filterStates(String naziv){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<States> states = cq.from(States.class);
        cq.select(states);
        cq.where(cb.like(states.<String>get("name"), naziv + "%"));
        return em.createQuery(cq).getResultList();
    }
}
