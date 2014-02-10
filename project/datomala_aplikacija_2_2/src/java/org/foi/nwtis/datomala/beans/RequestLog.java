/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import org.foi.nwtis.datomala.ejb.eb.DatomalaUserrequests;
import org.foi.nwtis.datomala.ejb.sb.DatomalaUserrequestsFacade;
import org.foi.nwtis.datomala.model.UserRequestData;

/**
 *
 * @author dex
 */
@Named(value = "requestLog")
@RequestScoped
public class RequestLog {
    
    @EJB
    private DatomalaUserrequestsFacade datomalaUserrequestsFacade;
    
    private HtmlDataTable dataTable = new HtmlDataTable();
    private static List<UserRequestData> requestList;
    private List<DatomalaUserrequests> userRequests;
    
    private int start = 0, pagination = 0;
    private static int viewId = 0;
    
    private String date1;
    private String date2;
    private String user;
    
    SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss");
    
    /**
     * Creates a new instance of RequestLog
     */
    public RequestLog() {
        viewId = 0;
        start = 0;
        pagination = 5;
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }
    
    // Pagination
    /**
     *
     */
    public void pageFirst() {
        dataTable.setFirst(0);
    }

    /**
     *
     */
    public void pagePrevious() {
        dataTable.setFirst(dataTable.getFirst() - dataTable.getRows());
    }

    /**
     *
     */
    public void pageNext() {
        dataTable.setFirst(dataTable.getFirst() + dataTable.getRows());
    }

    /**
     *
     */
    public void pageLast() {
        int count = dataTable.getRowCount();
        int rows = dataTable.getRows();
        dataTable.setFirst(count - ((count % rows != 0) ? count % rows : rows));
    }
    // get the current page number

    /**
     *
     * @return
     */
    public int getCurrentPage() {
        int rows = dataTable.getRows();
        int first = dataTable.getFirst();
        int count = dataTable.getRowCount();
        return (count / rows) - ((count - first) / rows) + 1;
    }
    // get the total no of pages

    /**
     *
     * @return
     */
    public int getTotalPages() {
        int rows = dataTable.getRows();
        int count = dataTable.getRowCount();
        return (count / rows) + ((count % rows != 0) ? 1 : 0);
    }
    
    public String fetchFilteredData(){
        requestList = null;
        viewId = 1;
        start = 0;
        pagination = 3;
        return "";
    }
    

    public String fetchByUser(){
        requestList = null;
        viewId = 2;
        start = 0;
        pagination = 3;
        return "";
    }


    public List<UserRequestData> getRequestList() {
        if (requestList != null){
            int korak = start + pagination;
            if (korak > requestList.size())
                korak = requestList.size();
            return requestList.subList(start, korak);
        }
        requestList = new ArrayList<UserRequestData>();
        if (viewId == 0){
            userRequests = datomalaUserrequestsFacade.fetchUserRequests();
            for (DatomalaUserrequests ur : userRequests){
                UserRequestData r = new UserRequestData(ur.getId(), ur.getUsername(), ur.getRequest(), ur.getDuration()); //df.format(new Date(ur.getDuration())) + ""
                requestList.add(r);
            }
        } else if (viewId == 1) {
            
            try{
                Date d1 = df2.parse(df2.format(df.parse(date1)));
                Date d2 = df2.parse(df2.format(df.parse(date2)));
                userRequests = datomalaUserrequestsFacade.fetchUserRequestsByInterval(d1, d2);

                for (DatomalaUserrequests ur : userRequests){
                    UserRequestData r = new UserRequestData(ur.getId(), ur.getRequest(), ur.getUsername(), ur.getDuration());
                    requestList.add(r);
                }
            } catch (ParseException pe){
                
            }
        } else if (viewId == 2) {
            System.out.println("KORISNIK: " + user);
            userRequests = datomalaUserrequestsFacade.fetchUserRequestsByUsername(user);
            for (DatomalaUserrequests ur : userRequests){
                UserRequestData r = new UserRequestData(ur.getId(), ur.getRequest(), ur.getUsername(), ur.getDuration());
                requestList.add(r);
            }
        }
        int korak = start + pagination;
        if (korak > requestList.size())
            korak = requestList.size();
        return requestList.subList(start, korak);
    }
    
    public static void setRequestList(List<UserRequestData> requestList) {
        RequestLog.requestList = requestList;
    }

    public static int getViewId() {
        return viewId;
    }

    public static void setViewId(int viewId) {
        RequestLog.viewId = viewId;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
}
