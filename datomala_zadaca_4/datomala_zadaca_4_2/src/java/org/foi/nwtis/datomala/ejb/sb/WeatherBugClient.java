/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ejb.sb;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.xml.ws.WebServiceRef;
import net.wxbug.api.LiveWeatherData;
import net.wxbug.api.UnitType;
import net.wxbug.api.WeatherBugWebServices;

/**
 *
 * @author dex
 */
@Stateless
@LocalBean
//TODO finish - load wb_code from web.xml
public class WeatherBugClient {
    
    @WebServiceRef(wsdlLocation = "META-INF/wsdl/api.wxbug.net/weatherservice.asmx.wsdl")
    private WeatherBugWebServices service;
    
    private static String weatherBugCode = "A3571208714";
    private String zip;
    private LiveWeatherData meteoData;

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public LiveWeatherData getMeteoData() {
        meteoData = getMeteoData(zip);
        return meteoData;
    }
    
    public LiveWeatherData getMeteoData(String zip) {
        // load wb_code from config
        return getLiveWeatherByUSZipCode(zip, UnitType.METRIC, weatherBugCode);
    }
    
    private LiveWeatherData getLiveWeatherByUSZipCode(java.lang.String zipCode, net.wxbug.api.UnitType unittype, java.lang.String aCode) {
        net.wxbug.api.WeatherBugWebServicesSoap port = service.getWeatherBugWebServicesSoap();
        return port.getLiveWeatherByUSZipCode(zipCode, unittype, aCode);
    }

}
