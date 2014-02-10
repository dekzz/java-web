/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.filter;

import java.io.IOException;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.datomala.ejb.sb.DatomalaUserrequestsFacade;

/**
 *
 * @author dex
 */
@WebFilter(filterName = "FilterLog", urlPatterns = {"/*"})
@Stateless
public class FilterLog implements Filter {
    @EJB
    private DatomalaUserrequestsFacade datomalaUserrequestsFacade;
    
   
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
         if (request instanceof HttpServletRequest){
            HttpSession session = ((HttpServletRequest)request).getSession();
            String req = ((HttpServletRequest) request).getRequestURI();
            Object username = session.getAttribute("username");                
            if (username == null){
                datomalaUserrequestsFacade.addUserRequest(req, "unknown");
            }
            else{
                datomalaUserrequestsFacade.addUserRequest(req, (String) username);
            }
            chain.doFilter(request, response);
         }
    }

    @Override
    public void destroy() {

    }
}
