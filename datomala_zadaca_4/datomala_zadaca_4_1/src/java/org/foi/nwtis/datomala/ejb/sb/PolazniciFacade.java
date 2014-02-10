/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ejb.sb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.foi.nwtis.datomala.ejb.eb.Polaznici;

/**
 *
 * @author dex
 */
@Stateless
public class PolazniciFacade extends AbstractFacade<Polaznici> {
    @PersistenceContext(unitName = "datomala_zadaca_4_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PolazniciFacade() {
        super(Polaznici.class);
    }
    
}
