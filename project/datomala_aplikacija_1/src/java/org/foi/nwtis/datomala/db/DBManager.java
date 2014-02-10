/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.wxbug.api.LiveWeatherData;
import org.foi.nwtis.datomala.configurations.db.DB_Configuration;
import org.foi.nwtis.datomala.model.CommandLogData;
import org.foi.nwtis.datomala.model.MeteoData;
import org.foi.nwtis.datomala.model.UserRequestData;
import org.foi.nwtis.datomala.web.WeatherDataFetcher;

/**
 *
 * @author dex
 */
public class DBManager {

    private static DB_Configuration dbConf;
    private static String url;
    private static String user;
    private static String password;
    private static String query;
    private static Connection conn;
    private static Statement instr;
    private static ResultSet rs;
    private static String zip;
    private static List<String> zipList = new ArrayList<String>();
    private static MeteoData data;
    private static List<MeteoData> resultData = new ArrayList<MeteoData>();

    public static DB_Configuration getDbConf() {
        return dbConf;
    }

    public static void setDbConf(DB_Configuration dbConf) {
        DBManager.dbConf = dbConf;
    }

    public static List<String> selectActiveZipCodes() {
        query = "SELECT zipcode FROM datomala_activezipcodes";
        execQuery();
        return zips();
    }

    public static boolean existsInActiveZipCodes(String zipcode) {
        query = "SELECT * FROM datomala_activezipcodes WHERE zipcode = " + zipcode;
        execQuery();
        try {
            if (!rs.isBeforeFirst()) {  // if there's no data in result set returns false
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public static boolean insertIntoActiveZipCodes(String zipcode, String username) {
        query = "INSERT INTO datomala_activezipcodes VALUES (" + zipcode + ", '" + username + "')";
        return execQuery();
    }

    public static boolean insertIntoCommandLog(String username, String command, String status) {
        query = "INSERT INTO datomala_commandlog VALUES (DEFAULT, '" + username + "', '" + command + "', '" + status + "', DEFAULT)";
        return execQuery();
    }

    public static boolean insertIntoUserRequests(String username, String request, String duration) {
        query = "INSERT INTO datomala_userrequests VALUES (DEFAULT, '" + username + "', '" + request + "', '" + duration + "')";
        return execQuery();
    }

    public static boolean insertWeatherData(LiveWeatherData data) {
        query = "INSERT INTO datomala_weatherdata VALUES (" + data.getZipCode() + ", " + data.getZipCode() + ", '" // request zip, response zip
                + data.getTemperature() + "', '" + data.getHumidity() + "', '" // response data
                + data.getPressure() + "', '" + data.getWindSpeed() + "', '" // ...
                + data.getCurrDesc() + "', '" // ...
                + data.getLatitude() + "', '" + data.getLongitude() + "', '" // request coordinates
                + data.getLatitude() + "', '" + data.getLongitude() + "', '" // response coordinates
                + data.getCity() + "', '" + data.getCity() + "', " // requst city name, response city name
                + "DEFAULT)";                                                   // current timestamp (save time)
        return execQuery();
    }

    // hash teh password
    public static boolean authenticateUser(String username, String password) {
        query = "SELECT * FROM datomala_users WHERE username = '" + username + "' AND password = '" + password + "'";
        try {
            if (execQuery() && rs.first()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static List<String> selectTopZipCodes(int n) {
        query = "SELECT zipcode, COUNT(zipcode) as count FROM datomala_weatherdata GROUP BY zipcode ORDER BY count DESC LIMIT " + n;
        execQuery();
        return zips();
    }

    public static List<MeteoData> selectAllWeatherData() {
        query = "SELECT * FROM datomala_weatherdata";
        execQuery();
        return resultMeteoData();
    }

    public static List<MeteoData> selectFilteredWeatherData(String zipcode) {
        query = "SELECT * FROM datomala_weatherdata WHERE CAST(zipcode as CHAR(5)) LIKE '" + zipcode + "%'";
        execQuery();
        return resultMeteoData();
    }

    public static MeteoData selectLatestDataForZipCode(String zipcode) {
        query = "SELECT * FROM datomala_weatherdata WHERE zipcode = '" + zipcode + "' ORDER BY zipcode DESC LIMIT 1";
        execQuery();
        try {
            while (rs.next()) {
                data = new MeteoData(rs.getString("zipcode"), rs.getString("zipcodeadd"),
                        rs.getString("temperature"), rs.getString("humidity"),
                        rs.getString("pressure"), rs.getString("wind"),
                        rs.getString("weather"), rs.getString("latitude"),
                        rs.getString("longitude"), rs.getString("latitudeadd"),
                        rs.getString("longitudeadd"), rs.getString("city"),
                        rs.getString("cityadd"), rs.getString("dateadd"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }

    public static String selectLatestDataString(String zipcode) {
        query = "SELECT * FROM datomala_weatherdata WHERE zipcode = " + zipcode + " ORDER BY zipcode DESC LIMIT 1";
        execQuery();
        String dat = "No data";
        try {
            rs.next();
            dat = "TEMP " + rs.getString("temperature") + "Humidity" + rs.getString("humidity")
                    + "Pressure" + rs.getString("pressure")
                    + "Longitude " + rs.getString("longitude") + "Latitude " + rs.getString("latitudeadd");


        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dat;
    }

    public static List<MeteoData> selectLatestDataForZipCode(String zipcode, int n) {
        query = "SELECT * FROM datomala_weatherdata WHERE zipcode = " + zipcode + " ORDER BY zipcode DESC LIMIT " + n;
        execQuery();
        return resultMeteoData();
    }

    public static List<MeteoData> selectDataInTimeInterval(String fromDate, String toDate) {
        query = "SELECT * FROM datomala_weatherdata WHERE dateadd BETWEEN '" + fromDate + "' AND '" + toDate + "'";
        execQuery();
        return resultMeteoData();
    }

    public static List<MeteoData> selectDataInTimeInterval(String zipcode, String fromDate, String toDate) {
        query = "SELECT * FROM datomala_weatherdata WHERE dateadd BETWEEN '" + fromDate + "' AND '" + toDate + "' AND CAST(zipcode as CHAR(5)) LIKE '" + zipcode + "%'";
        execQuery();
        return resultMeteoData();
    }

    public static List<CommandLogData> selectCommandLogData() {
        query = "SELECT * FROM datomala_commandlog";
        execQuery();
        return resultCommandLogData();
    }

    public static List<CommandLogData> selectCommandLogDataStatusFilter(String status) {
        query = "SELECT * FROM datomala_commandlog WHERE status LIKE '%" + status + "%'";
        execQuery();
        return resultCommandLogData();
    }

    public static List<UserRequestData> selectUserRequestsData() {
        query = "SELECT * FROM datomala_userrequests";
        execQuery();
        return resultUserRequestsData();
    }

    public static List<UserRequestData> selectUserRequestsFilter(String request) {
        query = "SELECT * FROM datomala_commandlog WHERE status LIKE '%" + request + "%'";
        execQuery();
        return resultUserRequestsData();
    }

    private static boolean execQuery() {
        url = dbConf.getServer_database() + dbConf.getUser_database();
        user = dbConf.getUser_username();
        password = dbConf.getUser_password();

        try {
            Class.forName(dbConf.getDriver_database(url));
            conn = DriverManager.getConnection(url, user, password);
            instr = conn.createStatement();
            if (query.startsWith("SELECT")) {
                rs = instr.executeQuery(query);
            } else {
                instr.execute(query);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WeatherDataFetcher.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private static List<String> zips() {
        try {
            zipList.clear();
            while (rs.next()) {
                zip = rs.getString("zipcode");
                zipList.add(zip);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return zipList;
    }

    private static List<MeteoData> resultMeteoData() {
        data = null;
        resultData.clear();
        try {
            while (rs.next()) {
                data = new MeteoData(rs.getString("zipcode"), rs.getString("zipcodeadd"),
                        rs.getString("temperature"), rs.getString("humidity"),
                        rs.getString("pressure"), rs.getString("wind"),
                        rs.getString("weather"), rs.getString("latitude"),
                        rs.getString("longitude"), rs.getString("latitudeadd"),
                        rs.getString("longitudeadd"), rs.getString("city"),
                        rs.getString("cityadd"), rs.getString("dateadd"));
                resultData.add(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultData;
    }

    private static List<CommandLogData> resultCommandLogData() {
        CommandLogData clData = new CommandLogData();
        List<CommandLogData> clResultData = new ArrayList<CommandLogData>();
        try {
            while (rs.next()) {
                clData = new CommandLogData(rs.getInt("id"), rs.getString("username"), rs.getString("command"),
                        rs.getString("status"), rs.getString("receivetime"));
                clResultData.add(clData);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clResultData;
    }

    private static List<UserRequestData> resultUserRequestsData() {
        UserRequestData urData = new UserRequestData();
        List<UserRequestData> urResultData = new ArrayList<UserRequestData>();
        try {
            while (rs.next()) {
                urData = new UserRequestData(rs.getInt("id"), rs.getString("username"), rs.getString("request"), rs.getString("duration"));
                urResultData.add(urData);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return urResultData;
    }
}
