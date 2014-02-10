/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ejb.sb;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.foi.nwtis.datomala.ejb.eb.ZipCodes;

/**
 *
 * @author dex
 */
@Stateless
public class ZipCodesFacade extends AbstractFacade<ZipCodes> {
    @PersistenceContext(unitName = "datomala_aplikacija_2_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ZipCodesFacade() {
        super(ZipCodes.class);
    }
    
    /**
    * Filtrira zip kodove prema državama i gradovima 
    * @param data - popis drzava i gradova za filtriranje
    * @return rezultate upita
    */
    public List<ZipCodes> filterZips(Set<String> data){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<ZipCodes> zips = cq.from(ZipCodes.class);
        cq.select(zips);
        List<String> cities = new ArrayList<String>();
        List<String> states = new ArrayList<String>();
        for (String s : data){
            cities.add(s.split("-")[2].trim());
            states.add(s.split("-")[0].trim());
        }
        cq.where(cb.and(zips.<String>get("cities").<String>get("citiesPK").<String>get("city").in(cities), 
                        zips.<String>get("cities").<String>get("citiesPK").<String>get("state").in(states)));
        return em.createQuery(cq).getResultList();
    }
    
    /**
     * Filtrira zip kodove prema državama i gradovima i dodatnim parametrom za filtriranje
     * @param data - popis drzava i gradova za filtriranje
     * @param filter - dodatni filter
     * @return rezultate upita
     */
     public List<ZipCodes> filterZips(Set<String> data, String filter){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<ZipCodes> zips = cq.from(ZipCodes.class);
        cq.select(zips);
        List<String> cities = new ArrayList<String>();
        List<String> states = new ArrayList<String>();
        int size = filter.length();
        String threeMin = filter;
        String fourMin = filter;
        String fiveMin = filter;
        String threeMax = filter;
        String fourMax = filter;
        String fiveMax = filter;
        for (String s : data){
            cities.add(s.split("-")[2].trim());
            states.add(s.split("-")[0].trim());
        }
        while (size != 5){
            fiveMin += "0";
            fiveMax += "9";
            if (size < 4){
                fourMin += "0";
                fourMax += "9";
            }
            if (size < 3){
                threeMin += "0";
                threeMax += "9";
            }
            size++;
        }
        cq.where(cb.and(
                        zips.<String>get("cities").<String>get("citiesPK").<String>get("city").in(cities), 
                        zips.<String>get("cities").<String>get("citiesPK").<String>get("state").in(states),
                        cb.or(cb.between(zips.<Integer>get("zip"), Integer.parseInt(threeMin), Integer.parseInt(threeMax)),
                        cb.between(zips.<Integer>get("zip"), Integer.parseInt(fourMin), Integer.parseInt(fourMax)),
                        cb.between(zips.<Integer>get("zip"), Integer.parseInt(fiveMin), Integer.parseInt(fiveMax)))
                       )
                );
        return em.createQuery(cq).getResultList();
    } 
    
}
