/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ws.clients;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import net.wxbug.api.LiveWeatherData;
import net.wxbug.api.UnitType;

/**
 *
 * @author dex
 */
public class WeatherBugClient {
    
    private String weatherBugCode;
    private String zip;
    private LiveWeatherData meteoData;

    /**
     *
     * @return
     */
    public String getZip() {
        return zip;
    }

    /**
     *
     * @param zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     *
     * @return
     */
    public LiveWeatherData getMeteoData() {
        meteoData = getMeteoData(zip);
        return meteoData;
    }
    
    /**
     *
     * @param zip
     * @return
     */
    public LiveWeatherData getMeteoData(String zip) {
        weatherBugCode = "";
        try {
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            weatherBugCode = (String) env.lookup("wb_code");
        } catch (NamingException ex) {
            Logger.getLogger(WeatherBugClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getLiveWeatherByUSZipCode(zip, UnitType.METRIC, weatherBugCode);
    }
    
    private static LiveWeatherData getLiveWeatherByUSZipCode(java.lang.String zipCode, net.wxbug.api.UnitType unittype, java.lang.String aCode) {
        net.wxbug.api.WeatherBugWebServices service = new net.wxbug.api.WeatherBugWebServices();
        net.wxbug.api.WeatherBugWebServicesSoap port = service.getWeatherBugWebServicesSoap();
        return port.getLiveWeatherByUSZipCode(zipCode, unittype, aCode);
    }
    
    /**
     *
     * @param args
     */
    public static void main(String args[]) {
        WeatherBugClient clint = new WeatherBugClient();
        LiveWeatherData data = clint.getMeteoData("90001");
        System.out.println(data.getCity() + " " + data.getTemperature());
    }
    
}
