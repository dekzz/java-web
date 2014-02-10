/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.model;

import java.io.Serializable;

/**
 *
 * @author dex
 */
public class MeteoDataExtended implements Serializable{
    
    public static String portfolioOwner;
    public static String portfolioName;
    
    private String zipcode;
    private String zipcodeadd;
    private String temperature;
    private String humidity;
    private String pressure;
    private String wind;
    private String weather;
    private String latitude;
    private String longitude;
    private String latitudeadd;
    private String longitudeadd;
    private String city;
    private String cityadd;
    private String dateadd;
    private String distance;

    public MeteoDataExtended() {
    }

    public MeteoDataExtended(String zipcode, String zipcodeadd, String temperature, String humidity, String pressure, String wind, String weather, String latitude, String longitude, String latitudeadd, String longitudeadd, String city, String cityadd, String dateadd, String distance) {
        this.zipcode = zipcode;
        this.zipcodeadd = zipcodeadd;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
        this.weather = weather;
        this.latitude = latitude;
        this.longitude = longitude;
        this.latitudeadd = latitudeadd;
        this.longitudeadd = longitudeadd;
        this.city = city;
        this.cityadd = cityadd;
        this.dateadd = dateadd;
        this.distance = distance;
    }

    public static String getPortfolioOwner() {
        return portfolioOwner;
    }

    public static void setPortfolioOwner(String portfolioOwner) {
        MeteoDataExtended.portfolioOwner = portfolioOwner;
    }

    public static String getPortfolioName() {
        return portfolioName;
    }

    public static void setPortfolioName(String portfolioName) {
        MeteoDataExtended.portfolioName = portfolioName;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getZipcodeadd() {
        return zipcodeadd;
    }

    public void setZipcodeadd(String zipcodeadd) {
        this.zipcodeadd = zipcodeadd;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitudeadd() {
        return latitudeadd;
    }

    public void setLatitudeadd(String latitudeadd) {
        this.latitudeadd = latitudeadd;
    }

    public String getLongitudeadd() {
        return longitudeadd;
    }

    public void setLongitudeadd(String longitudeadd) {
        this.longitudeadd = longitudeadd;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityadd() {
        return cityadd;
    }

    public void setCityadd(String cityadd) {
        this.cityadd = cityadd;
    }

    public String getDateadd() {
        return dateadd;
    }

    public void setDateadd(String dateadd) {
        this.dateadd = dateadd;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
    
}