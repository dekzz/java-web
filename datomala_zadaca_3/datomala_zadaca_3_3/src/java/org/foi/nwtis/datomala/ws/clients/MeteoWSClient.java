/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ws.clients;

import net.wxbug.api.LiveWeatherData;

/**
 *
 * @author dex
 */
public class MeteoWSClient {
    
    /**
     *
     * @param zip City zip code
     * @return
     */
    public static LiveWeatherData getMeteoWSDataForZip(java.lang.String zip) {
        org.foi.nwtis.datomala.ws.servers.MeteoWS_Service service = new org.foi.nwtis.datomala.ws.servers.MeteoWS_Service();
        org.foi.nwtis.datomala.ws.servers.MeteoWS port = service.getMeteoWSPort();
        return port.getMeteoWSDataForZip(zip);
    }
    
    /**
     *
     * @param zips List of zip codes
     * @return
     */
    public static java.util.List<net.wxbug.api.LiveWeatherData> getMeteoWSDataForManyZips(java.util.List<java.lang.String> zips) {
        org.foi.nwtis.datomala.ws.servers.MeteoWS_Service service = new org.foi.nwtis.datomala.ws.servers.MeteoWS_Service();
        org.foi.nwtis.datomala.ws.servers.MeteoWS port = service.getMeteoWSPort();
        return port.getMeteoWSDataForManyZip(zips);
    }
    
}
