/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ejb.sb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.foi.nwtis.datomala.ejb.eb.Counties;

/**
 *
 * @author dex
 */
@Stateless
public class CountiesFacade extends AbstractFacade<Counties> {
    @PersistenceContext(unitName = "datomala_aplikacija_2_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CountiesFacade() {
        super(Counties.class);
    }
    
}
