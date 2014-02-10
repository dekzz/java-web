/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.datomala.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import javax.enterprise.context.RequestScoped;

/**
 * REST Web Service
 *
 * @author dex
 */
@RequestScoped
public class PortfolioResource {

    private String id;

    /**
     * Creates a new instance of PortfolioResource
     */
    private PortfolioResource(String id) {
        this.id = id;
    }

    /**
     * Get instance of the PortfolioResource
     */
    public static PortfolioResource getInstance(String id) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of PortfolioResource class.
        return new PortfolioResource(id);
    }

    /**
     * Retrieves representation of an instance of org.foi.datomala.ws.PortfolioResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/html")
    public String getHtml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of PortfolioResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/html")
    public void putHtml(String content) {
    }

    /**
     * DELETE method for resource PortfolioResource
     */
    @DELETE
    public void delete() {
    }
}
