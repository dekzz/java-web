/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.datomala.ws;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.foi.nwtis.datomala.ejb.eb.DatomalaPortfolio;
import org.foi.nwtis.datomala.ejb.eb.DatomalaZipPortfolio;
import org.foi.nwtis.datomala.ejb.sb.DatomalaPortfolioFacade;
import org.foi.nwtis.datomala.ejb.sb.DatomalaZipPortfolioFacade;

/**
 * REST Web Service
 *
 * @author dex
 */
@Path("/port")
@Stateless
public class PortfolioResourceContainer {
    @EJB
    private DatomalaPortfolioFacade datomalaPortfolioFacade;
    @EJB
    private DatomalaZipPortfolioFacade datomalaZipPortfolioFacade;

    private List<String> activeUsers = new ArrayList<String>();
    private List<DatomalaPortfolio> portfolios;
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PortfolioResourceContainer
     */
    public PortfolioResourceContainer() {
    }

    /**
     * Retrieves representation of an instance of org.foi.datomala.ws.PortfolioResourceContainer
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/html")
    public String getHtml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * POST method for creating an instance of PortfolioResource
     * @param content representation for the new resource
     * @return an HTTP response with content of the created resource
     */
    @POST
    @Consumes("text/html")
    @Produces("text/html")
    public Response postHtml(String content) {
        //TODO
        return Response.created(context.getAbsolutePath()).build();
    }

    @Path("p/")
    @GET
    public String activeUsers(@Context HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        String s = "";
        if (session == null)
            return "empty";
        if (session.getServletContext().getAttribute("activeUsers") == null){
            return "empty";
        } else{
            activeUsers = (List<String>) session.getServletContext().getAttribute("activeUsers");
            for (Iterator<String> it = activeUsers.iterator(); it.hasNext();) {
                String p = it.next();
                s += p + "<br/>";
            }
            return s;
        }
    }
    
    @Path("p/{name}")
    @GET
    public String activeUsers(@PathParam("name") String name) {
        String s = "";
        if (activeUsers.contains(name)){
            portfolios = datomalaPortfolioFacade.fetchPortfolios(name);
            for (DatomalaPortfolio p : portfolios){
                s += p.getName() + "<br/>";
            }
        }
        return s;
    }
    
    @Path("p/{name}/{portfolio}")
    @GET
    public String listZip(@PathParam("name") String name, @PathParam("portfolio") String portfolio) {
        String s = "";
        if (activeUsers.contains(name)){
            List<DatomalaZipPortfolio> port = datomalaZipPortfolioFacade.fetchZP(datomalaPortfolioFacade.fetchPortfolioByName(portfolio));
            for (DatomalaZipPortfolio p : port){
                s += p.getZip().getZip() +"<br/>";
            }
        }
        return s;
    }
}
