/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ejb.sb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.foi.nwtis.datomala.ejb.eb.PolazniciGrupe;

/**
 *
 * @author dex
 */
@Stateless
public class PolazniciGrupeFacade extends AbstractFacade<PolazniciGrupe> {
    @PersistenceContext(unitName = "datomala_zadaca_4_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PolazniciGrupeFacade() {
        super(PolazniciGrupe.class);
    }
    
}
