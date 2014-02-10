/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.datomala.ws;

import org.foi.nwtis.datomala.ws.MeteoData;

/**
 *
 * @author dex
 */
public class WSClient {

    public static java.util.List<java.lang.String> selectActiveZipCodes() {
        org.foi.nwtis.datomala.ws.MeteoData_Service service = new org.foi.nwtis.datomala.ws.MeteoData_Service();
        org.foi.nwtis.datomala.ws.MeteoDataWS port = service.getMeteoDataWSPort();
        return port.selectActiveZipCodes();
    }

    public static MeteoData selectLatestDataForZipCode(java.lang.String zipcode) {
        org.foi.nwtis.datomala.ws.MeteoData_Service service = new org.foi.nwtis.datomala.ws.MeteoData_Service();
        org.foi.nwtis.datomala.ws.MeteoDataWS port = service.getMeteoDataWSPort();
        return port.selectLatestDataForZipCode(zipcode);
    }

    public static java.util.List<org.foi.nwtis.datomala.ws.MeteoData> selectDataInTimeInterval(java.lang.String zipcode, java.lang.String fromDate, java.lang.String toDate) {
        org.foi.nwtis.datomala.ws.MeteoData_Service service = new org.foi.nwtis.datomala.ws.MeteoData_Service();
        org.foi.nwtis.datomala.ws.MeteoDataWS port = service.getMeteoDataWSPort();
        return port.selectDataInTimeInterval(zipcode, fromDate, toDate);
    }
    
}
