/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala;

import java.util.ArrayList;
import java.util.List;
import net.wxbug.api.LiveWeatherData;
import org.foi.nwtis.datomala.rest.clients.MeteoRESTClient;

/**
 * Picks between normal or REST web service for fetching meteo data
 * @author dex
 */
public class Zadaca_3_2 {
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Min 2 arguments required!");
            return;
        }
        if (args[0].equals("1")) {
            List<String> zips = new ArrayList<>();
            for(int i = 0; i < args.length; i++) {
                zips.add(args[i]);
            }
            List<LiveWeatherData> data = getMeteoWSDataForManyZip(zips);
            for(LiveWeatherData dat : data){
                System.out.println("City: " + dat.getCity()
                                + " Temp: " + dat.getTemperature()
                                + " Humidity: " + dat.getHumidity());
            }
        } else if (args[0].equals("2")) {
            MeteoRESTClient client = new MeteoRESTClient(args[1]);
            System.out.println(client.getHtml());
        } else {
            System.out.println("Unknown request!");
        }
    }

    private static LiveWeatherData getMeteoWSDataForZip(java.lang.String zip) {
        org.foi.nwtis.datomala.ws.servers.MeteoWS_Service service = new org.foi.nwtis.datomala.ws.servers.MeteoWS_Service();
        org.foi.nwtis.datomala.ws.servers.MeteoWS port = service.getMeteoWSPort();
        return port.getMeteoWSDataForZip(zip);
    }

    private static java.util.List<net.wxbug.api.LiveWeatherData> getMeteoWSDataForManyZip(java.util.List<java.lang.String> zips) {
        org.foi.nwtis.datomala.ws.servers.MeteoWS_Service service = new org.foi.nwtis.datomala.ws.servers.MeteoWS_Service();
        org.foi.nwtis.datomala.ws.servers.MeteoWS port = service.getMeteoWSPort();
        return port.getMeteoWSDataForManyZip(zips);
    }
}
