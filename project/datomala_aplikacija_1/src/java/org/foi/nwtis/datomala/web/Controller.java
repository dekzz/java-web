/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.web;

import org.foi.nwtis.datomala.model.MeteoData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.foi.nwtis.datomala.db.DBManager;
import org.foi.nwtis.datomala.model.CommandLogData;
import org.foi.nwtis.datomala.model.UserRequestData;

/**
 *
 * @author dex
 */
public class Controller extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FailedLogin {
        String req = request.getServletPath();
        String destination = null;
        HttpSession session = request.getSession();

        if (req.equals("/Controller")) {
            destination = "/index.jsp";
            
        } else if (req.equals("/Login")) {
            destination = "/login.jsp";
            
        } else if (req.equals("/Logout")) {
            session.removeAttribute("username");
            destination = "/Controller";
            
        } else if (req.equals("/UserCheck")) {
            String username = (request.getParameter("username") != null) ? request.getParameter("username") : "";
            String password = (request.getParameter("password") != null) ? request.getParameter("password") : "";

            if (username.equals("") || password.equals("")) {
                throw new FailedLogin("Invalid username/password");
            } else if (!DBManager.authenticateUser(username, password)) {
                throw new FailedLogin("Wrong username/password");
            }
            session.setAttribute("username", username);

            destination = "/Controller";
            
        } else if (req.equals("/MeteoData")) {
            List<MeteoData> meteoData = new ArrayList<MeteoData>();
            String zipcode = (request.getParameter("zipcode") != null) ? request.getParameter("zipcode").trim() : "";
            String fromDate = (request.getParameter("fromDate") != null) ? request.getParameter("fromDate").trim() : "";
            String toDate = (request.getParameter("toDate") != null) ? request.getParameter("toDate").trim() : "";
            int nrElementsPerPage = (request.getParameter("nrElementsPerPage") != null) ? Integer.parseInt(request.getParameter("nrElementsPerPage")) : 0;
            
            if (!zipcode.equals("")) {
                if (fromDate.equals("") || toDate.equals("")) {
                    meteoData = DBManager.selectFilteredWeatherData(zipcode);
                } else if (!fromDate.equals("") && !toDate.equals("")){
                    meteoData = DBManager.selectDataInTimeInterval(zipcode, fromDate, toDate);
                }
            } else if (!fromDate.equals("") && !toDate.equals("")) {
                meteoData = DBManager.selectDataInTimeInterval(fromDate, toDate);
            } else {
                meteoData = DBManager.selectAllWeatherData();
            }

            session.setAttribute("zipcode", zipcode);
            session.setAttribute("fromDate", fromDate);
            session.setAttribute("toDate", toDate);
            session.setAttribute("meteoData", meteoData);
            session.setAttribute("nrElementsPerPage", nrElementsPerPage);
            destination = "/private/meteoData.jsp";
            
        } else if (req.equals("/ServerLog")) {
            List<CommandLogData> clData = new ArrayList<CommandLogData>();
            String status = (request.getParameter("status") != null) ? request.getParameter("status").trim() : "";
            int nrElementsPerPage = (request.getParameter("nrElementsPerPage") != null) ? Integer.parseInt(request.getParameter("nrElementsPerPage")) : 0;
            
            if (status.equals("")) {
                clData = DBManager.selectCommandLogData();
            } else {
                clData = DBManager.selectCommandLogDataStatusFilter(status);
            }
            
            session.setAttribute("commandLogData", clData);
            session.setAttribute("status", status);
            session.setAttribute("nrElementsPerPage", nrElementsPerPage);
            destination = "/private/serverLog.jsp";
            
        } else if (req.equals("/UserRequests")) {
            List<UserRequestData> urData = new ArrayList<UserRequestData>();
            String userRequest = (request.getParameter("request") != null) ? request.getParameter("request").trim() : "";
            int nrElementsPerPage = (request.getParameter("nrElementsPerPage") != null) ? Integer.parseInt(request.getParameter("nrElementsPerPage")) : 0;
            
            if (userRequest.equals("")) {
                urData = DBManager.selectUserRequestsData();
            } else {
                urData = DBManager.selectUserRequestsFilter(userRequest);
            }
            
            session.setAttribute("userRequestsData", urData);
            session.setAttribute("request", userRequest);
            session.setAttribute("nrElementsPerPage", nrElementsPerPage);
            destination = "/private/userRequests.jsp";
            
        } else {
            ServletException up = new ServletException("Unknown request!");
            throw up;
        }

        RequestDispatcher rd = getServletContext().getRequestDispatcher(destination);
        rd.forward(request, response);
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);


        } catch (FailedLogin ex) {
            System.out.println("Failed Login:" + ex.getMessage());
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);

        } catch (FailedLogin ex) {
            System.out.println("Failed Login:" + ex.getMessage());
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
