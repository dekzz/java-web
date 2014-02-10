/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ws.servers;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import net.wxbug.api.LiveWeatherData;
import org.foi.nwtis.datomala.ws.clients.WeatherBugClient;

/**
 * Web service for retrieving meteo data
 * @author dex
 */
@WebService(serviceName = "MeteoWS")
public class MeteoWS {

    /**
     * Web service operation
     * @param zip Zip code of the city
     * @return  
     */
    @WebMethod(operationName = "getMeteoWSDataForZip")
    public LiveWeatherData getMeteoWSDataForZip(@WebParam(name = "zip") String zip) {
        WeatherBugClient client = new WeatherBugClient();
        return client.getMeteoData(zip);
    }

    /**
     * Web service operation
     * @param zips Zip codes of one of more cities
     * @return  
     */
    @WebMethod(operationName = "getMeteoWSDataForManyZip")
        public java.util.List<net.wxbug.api.LiveWeatherData> getMeteoWSDataForManyZip(@WebParam(name = "zips") java.util.List<java.lang.String> zips) {
        List<LiveWeatherData> list = new ArrayList<LiveWeatherData>();
        WeatherBugClient client = new WeatherBugClient();
        
        for (String zip : zips) {
            LiveWeatherData data = client.getMeteoData(zip);
            list.add(data);
        }
        
        return list;
    }
}
