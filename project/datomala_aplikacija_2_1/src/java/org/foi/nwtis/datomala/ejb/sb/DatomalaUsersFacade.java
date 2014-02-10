/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ejb.sb;

import javax.ejb.Stateless;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.foi.nwtis.datomala.ejb.eb.DatomalaUsers;

/**
 *
 * @author dex
 */
@Stateless
public class DatomalaUsersFacade extends AbstractFacade<DatomalaUsers> {
    @PersistenceContext(unitName = "datomala_aplikacija_2_1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DatomalaUsersFacade() {
        super(DatomalaUsers.class);
    }
    
    /**
     * Dodavanjenovog korisnika u bazu
     * @param username
     * @param password
     * @throws NamingException 
     */
    public void addUser(String username, String password) throws NamingException{
        DatomalaUsers user = new DatomalaUsers();
        user.setUsername(username);
        user.setPassword(password);
        user.setType(1);
        em.persist(user);
    }
    
    /**
     * Provjera je li dani korisnik administrator
     * @param username
     * @return 
     */
    public boolean checkAdmin(String username){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<DatomalaUsers> users = cq.from(DatomalaUsers.class);
        cq.select(users).where(cb.and(cb.equal(users.get("username"), username), 
                                          cb.equal(users.get("type"), 0)
                ));
        if (em.createQuery(cq).getResultList().isEmpty())
            return false;
        else
            return true;
    }
    
    /**
     * Provjera postoji li korisnik u bazi
     * @param username
     * @param password
     * @return 
     */
    public boolean checkUser(String username, String password){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<DatomalaUsers> users = cq.from(DatomalaUsers.class);
        cq.select(users).where(cb.and(cb.equal(users.get("username"), username),
                                          cb.equal(users.get("password"), password)));
        if (em.createQuery(cq).getResultList().isEmpty())
            return false;
        else
            return true;
    }
    
    /**
     * Dohvaćanje podataka o korisnku prema imenu
     * @param username
     * @return 
     */
    public DatomalaUsers fetchUser(String username){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root<DatomalaUsers> user = cq.from(DatomalaUsers.class);
        cq.select(user).where(cb.equal(user.get("username"), username));
        return (DatomalaUsers) em.createQuery(cq).getResultList().get(0);
    }
    
}
