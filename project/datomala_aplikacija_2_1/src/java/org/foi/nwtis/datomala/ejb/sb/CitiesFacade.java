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
    @PersistenceContext(unitName = "datomala_aplikacija_2_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CitiesFacade() {
        super(Cities.class);
    }
    
    /**
     * filtrira gradove prema državama
     * @param drzava popis drzava za filtriranje
     * @return rezultate upita
     */
    public List<Cities> filterCities(Set<String> drzava){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Cities> cities = cq.from(Cities.class);
        cq.select(cities);
        cq.where(cities.<String>get("citiesPK").<String>get("state").in(drzava));
        return em.createQuery(cq).getResultList();
    }
    
    /**
     * filtrira gradove prema državama koristeći LIKE operator
     * @param drzava - popis drzava za filtriranje
     * @param filterGradova - dodatan filter za LIKE operator
     * @return rezultate upita
     */
    public List<Cities> filterCities(Set<String> drzava, String filterGradova){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<Cities> cities = cq.from(Cities.class);
        cq.select(cities);
        cq.where(cb.and(cities.<String>get("citiesPK").<String>get("state").in(drzava), 
                 cb.like(cities.<String>get("name"), filterGradova + "%")));
        return em.createQuery(cq).getResultList();
    }
    
}
