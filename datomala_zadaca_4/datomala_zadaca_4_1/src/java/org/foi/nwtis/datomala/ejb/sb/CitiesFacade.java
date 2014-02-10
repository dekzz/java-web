/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ejb.sb;

import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.foi.nwtis.datomala.ejb.eb.Cities;

/**
 *
 * @author dex
 */
@Stateless
public class CitiesFacade extends AbstractFacade<Cities> {
    @PersistenceContext(unitName = "datomala_zadaca_4_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CitiesFacade() {
        super(Cities.class);
    }
    
    public List<Cities> filterCities(Set<String> state) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Cities> states = cq.from(Cities.class);
        cq.select(states);
        cq.where(states.<String>get("citiesPK").<String>get("state").in(state));
        return em.createQuery(cq).getResultList();
    }

    public List<Cities> filterCities(Set<String> state, String filterCities) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Cities> cities = cq.from(Cities.class);
        cq.select(cities);
        cq.where(cb.and(cities.<String>get("citiesPK").<String>get("state").in(state),
                    cb.like(cities.<String>get("name"), filterCities + "%")));
                
        return em.createQuery(cq).getResultList();
    }
    
}
