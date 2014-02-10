/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author dex
 */
@WebFilter(filterName = "FilterRequest", urlPatterns = {"/faces/private/*"})
public class FilterRequest implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
         if (request instanceof HttpServletRequest){
                HttpServletRequest re = (HttpServletRequest) request;
                HttpSession session = ((HttpServletRequest)request).getSession();
                String req = ((HttpServletRequest) request).getRequestURI();
                Object username = session.getAttribute("username");                
                if (username == null){
                    HttpServletResponse res = (HttpServletResponse) response;
                    res.sendRedirect(re.getContextPath() + "/faces/login.xhtml");
                }
                else{
                    if ((req.contains("/emailPanel") || req.contains("/userRequests")) && session.getAttribute("admin") == null){
                        RequestDispatcher rd = request.getRequestDispatcher("/faces/error.xhtml");
                        rd.forward(request, response);
                        return;
                    }
                    chain.doFilter(request, response);
                }
         }
    }

    @Override
    public void destroy() {

    }
}
