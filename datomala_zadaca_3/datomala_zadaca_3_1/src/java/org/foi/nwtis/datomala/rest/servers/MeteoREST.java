/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.rest.servers;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import net.wxbug.api.LiveWeatherData;
import org.foi.nwtis.datomala.ws.clients.WeatherBugClient;

/**
 * REST Web Service
 *
 * @author dex
 */
public class MeteoREST {

    private String zip;

    /**
     * Creates a new instance of MeteoREST
     */
    private MeteoREST(String zip) {
        this.zip = zip;
    }

    /**
     * Get instance of the MeteoREST
     * @param zip 
     * @return 
     */
    public static MeteoREST getInstance(String zip) {
        // The user may use some kind of persistence mechanism
        // to store and restore instances of MeteoREST class.
        return new MeteoREST(zip);
    }

    /**
     * Retrieves representation of an instance of org.foi.nwtis.datomala.rest.servers.MeteoREST
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/html")
    public String getHtml() {
        WeatherBugClient client = new WeatherBugClient();
        LiveWeatherData data = client.getMeteoData(zip);
        String result = "<table border=\"1\">";
        result += "<tr><td class=\"header\">City</td><td>" + data.getCity() + "</td></tr>";
        result += "<tr><td class=\"header\">Temperature</td><td>" + data.getTemperature() + "°C</td></tr>";
        result += "<tr><td class=\"header\">Humidity</td><td>" + data.getHumidity() + "%</td></tr>";
        result += "<tr><td class=\"header\">Latitude</td><td>" + data.getLatitude() + "</td></tr>";
        result += "<tr><td class=\"header\">Longitude</td><td>" + data.getLongitude() + "</td></tr>";
        result += "</table>";
        
        return result;
    }

    /**
     * PUT method for updating or creating an instance of MeteoREST
     * @param content representation for the resource
     */
    @PUT
    @Consumes("text/html")
    public void putHtml(String content) {
    }

    /**
     * DELETE method for resource MeteoREST
     */
    @DELETE
    public void delete() {
    }
}
