/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ws.clients;

import java.util.ArrayList;
import java.util.List;
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

    private static String weatherBugCode;
    
    public List<LiveWeatherData> getMeteoDataForManyZip(List<String> zips) {
        
        weatherBugCode = "";
        try {
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            weatherBugCode = (String) env.lookup("wb_code");
        } catch (NamingException ex) {
            Logger.getLogger(WeatherBugClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        List<LiveWeatherData> weatherData = new ArrayList<LiveWeatherData>();
        for (String zip : zips) {
            LiveWeatherData data = getLiveWeatherByUSZipCode(zip, UnitType.METRIC, weatherBugCode);
            weatherData.add(data);
        }
        return weatherData;
    }
    
    private static LiveWeatherData getLiveWeatherByUSZipCode(java.lang.String zipCode, net.wxbug.api.UnitType unittype, java.lang.String aCode) {
        net.wxbug.api.WeatherBugWebServices service = new net.wxbug.api.WeatherBugWebServices();
        net.wxbug.api.WeatherBugWebServicesSoap port = service.getWeatherBugWebServicesSoap();
        return port.getLiveWeatherByUSZipCode(zipCode, unittype, aCode);
    }    
    
}
