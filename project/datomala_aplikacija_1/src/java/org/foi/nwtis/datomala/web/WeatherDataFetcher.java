/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.web;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import net.wxbug.api.LiveWeatherData;
import org.foi.nwtis.datomala.config.Configuration;
import org.foi.nwtis.datomala.configurations.db.DB_Configuration;
import org.foi.nwtis.datomala.db.DBManager;
import org.foi.nwtis.datomala.ws.clients.WeatherBugClient;

/**
 *
 * @author dex
 */
public class WeatherDataFetcher extends Thread {

    private DB_Configuration dbConf;
    private Configuration config;
    private List<LiveWeatherData> weatherData;
    private int interval;
    private static boolean running;
    private static boolean end;
    private long start;
    private long stop;
    private long work;

    public WeatherDataFetcher() {
        super();
    }

    public DB_Configuration getDbConf() {
        return dbConf;
    }

    public void setDbConf(DB_Configuration dbConf) {
        this.dbConf = dbConf;
    }

    public Configuration getConfig() {
        return config;
    }

    public void setConfig(Configuration config) {
        this.config = config;
    }

    public List<LiveWeatherData> getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(List<LiveWeatherData> weatherData) {
        this.weatherData = weatherData;
    }

    public static boolean isRunning() {
        return running;
    }

    public static void setRunning(boolean running) {
        WeatherDataFetcher.running = running;
    }

    public static boolean isEnd() {
        return end;
    }

    public static void setEnd(boolean end) {
        WeatherDataFetcher.end = end;
    }

    @Override
    public synchronized void start() {
        interval = Integer.parseInt(config.getSettings("interval"));
        setRunning(true);
        setEnd(false);
        super.start();
    }

    @Override
    public void run() {
        WeatherBugClient wbClient = new WeatherBugClient();
        weatherData = new ArrayList();
        List<String> zips = new ArrayList();

        while (isRunning()) {
            start = System.currentTimeMillis();
            zips = DBManager.selectActiveZipCodes();
            
            weatherData = wbClient.getMeteoDataForManyZip(zips);
            for (LiveWeatherData data : weatherData) {
                DBManager.insertWeatherData(data);
            }

            stop = System.currentTimeMillis();
            work = stop - start;

            if (isEnd()) {
                interrupt();
            }
            
            if (work < (interval * 1000)) {
                try {
                    sleep((interval * 1000) - work);
                } catch (InterruptedException ex) {
                    System.out.println("WDF Thread error: " + ex.getMessage());
                }
            }

        }

    }

    @Override
    public void interrupt() {
        setRunning(false);
        super.interrupt();
    }
}
