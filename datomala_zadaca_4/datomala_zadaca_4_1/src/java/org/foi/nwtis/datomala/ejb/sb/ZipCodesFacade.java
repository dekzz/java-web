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

    @PersistenceContext(unitName = "datomala_zadaca_4_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ZipCodesFacade() {
        super(ZipCodes.class);
    }

    public List<ZipCodes> filterZipCodes(Set<String> data) {
        List<String> cities = new ArrayList<String>();
        List<String> states = new ArrayList<String>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<ZipCodes> zipCodes = cq.from(ZipCodes.class);
        cq.select(zipCodes);
        for (String s : data) {
            cities.add(s.split("-")[2].trim());
            states.add(s.split("-")[0].trim());
        }
        cq.where(cb.and(zipCodes.<String>get("cities").<String>get("citiesPK").<String>get("city").in(cities)),
                zipCodes.<String>get("cities").<String>get("citiesPK").<String>get("state").in(states));

        return em.createQuery(cq).getResultList();
    }

    public List<ZipCodes> filterZipCodes(Set<String> data, String filterZipCodes) {
        List<String> cities = new ArrayList<String>();
        List<String> states = new ArrayList<String>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<ZipCodes> zipCodes = cq.from(ZipCodes.class);
        cq.select(zipCodes);
        
        int size = filterZipCodes.length();
        String threeMin = filterZipCodes;
        String threeMax = filterZipCodes;
        String fourMin = filterZipCodes;
        String fourMax = filterZipCodes;
        String fiveMin = filterZipCodes;
        String fiveMax = filterZipCodes;
        
        for (String s : data) {
            cities.add(s.split("-")[2].trim());
            states.add(s.split("-")[0].trim());
        }
        while (size != 5) {
            fiveMin += "0";
            fiveMax += "9";
            if (size < 4) {
                fourMin += "0";
                fourMax += "9";
            }
            if (size < 3) {
                threeMin += "0";
                threeMax += "9";
            }
            size++;
        }
        cq.where(cb.and(
                zipCodes.<String>get("cities").<String>get("citiesPK").<String>get("city").in(cities),
                zipCodes.<String>get("cities").<String>get("citiesPK").<String>get("state").in(states),
                cb.or(cb.between(zipCodes.<Integer>get("zip"), Integer.parseInt(threeMin), Integer.parseInt(threeMax)),
                      cb.between(zipCodes.<Integer>get("zip"), Integer.parseInt(fourMin), Integer.parseInt(fourMax)),
                      cb.between(zipCodes.<Integer>get("zip"), Integer.parseInt(fiveMin), Integer.parseInt(fiveMax)))));


        return em.createQuery(cq).getResultList();
    }
}
