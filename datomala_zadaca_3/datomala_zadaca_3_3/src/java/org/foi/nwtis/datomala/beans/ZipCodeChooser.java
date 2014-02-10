/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import net.wxbug.api.LiveWeatherData;
import org.foi.nwtis.datomala.configurations.db.DB_Configuration;
import org.foi.nwtis.datomala.rest.clients.MeteoRESTClient;
import org.foi.nwtis.datomala.ws.clients.MeteoWSClient;

/**
 * Hold zip codes from database
 * @author dex
 */
@ManagedBean
@SessionScoped
public class ZipCodeChooser implements Serializable{

    private List<String> zipCodes = new ArrayList<>();
    private String zipCodeAdd;
    private List<String> choosenZipCodes = new ArrayList<>();
    private String zipCodeDelete;
    private List<LiveWeatherData> meteoWSData = new ArrayList<>();
    private String meteoRESTData;
    private boolean showData = false;

    /**
     * Creates a new instance of ZipCodeChooser
     */
    public ZipCodeChooser() {
    }
    
    /**
     *
     * @return
     */
    public List<String> getZipCodes() {
        if (zipCodes.isEmpty()) {
            zipCodes = new ArrayList<>();
        
            DB_Configuration dbConf = (DB_Configuration)((ServletContext)FacesContext.
                    getCurrentInstance().getExternalContext().getContext()).
                    getAttribute("DB_Configuration");
            String query = "SELECT zip FROM mycities";
            String url = dbConf.getServer_database() + dbConf.getUser_database();
            String user = dbConf.getUser_username();
            String password = dbConf.getUser_password();

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement instr = conn.createStatement();
                 ResultSet rs = instr.executeQuery(query);) 
            {
                while (rs.next()) {
                    String zip = rs.getString("zip");
                    zipCodes.add(zip);
                }
            } catch (SQLException e){
                e.printStackTrace();
            } 
        }
        
        return zipCodes;
    }

    /**
     *
     * @param zipCodes
     */
    public void setZipCodes(List<String> zipCodes) {
        this.zipCodes = zipCodes;
    }

    /**
     *
     * @return
     */
    public String getZipCodeAdd() {
        return zipCodeAdd;
    }

    /**
     *
     * @param zipCodeAdd
     */
    public void setZipCodeAdd(String zipCodeAdd) {
        this.zipCodeAdd = zipCodeAdd;
    }

    /**
     *
     * @return
     */
    public List<String> getChoosenZipCodes() {
        return choosenZipCodes;
    }

    /**
     *
     * @param choosenZipCodes
     */
    public void setChoosenZipCodes(List<String> choosenZipCodes) {
        this.choosenZipCodes = choosenZipCodes;
    }

    /**
     *
     * @return
     */
    public String getZipCodeDelete() {
        return zipCodeDelete;
    }

    /**
     *
     * @param zipCodeDelete
     */
    public void setZipCodeDelete(String zipCodeDelete) {
        this.zipCodeDelete = zipCodeDelete;
    }

    /**
     *
     * @return
     */
    public boolean isShowData() {
        return showData;
    }

    /**
     *
     * @param showData
     */
    public void setShowData(boolean showData) {
        this.showData = showData;
    }

    /**
     *
     * @return
     */
    public List<LiveWeatherData> getMeteoWSData() {
        return meteoWSData;
    }

    /**
     *
     * @param meteoWSData
     */
    public void setMeteoWSData(List<LiveWeatherData> meteoWSData) {
        this.meteoWSData = meteoWSData;
    }

    /**
     *
     * @return
     */
    public String getMeteoRESTData() {
        return meteoRESTData;
    }

    /**
     *
     * @param meteoRESTData
     */
    public void setMeteoRESTData(String meteoRESTData) {
        this.meteoRESTData = meteoRESTData;
    }
    
    /**
     *
     * @return
     */
    public String addZipCode() {
        if (!choosenZipCodes.contains(zipCodeAdd)) {
            choosenZipCodes.add(zipCodeAdd);
        }
        return "";
    }
    
    /**
     *
     * @return
     */
    public String deleteZipCode() {
        choosenZipCodes.remove(zipCodeDelete);
        return "";
    }
    
    /**
     * Grabs meteo data for choosen zip codes
     * @return
     */
    public String grabMeteoWSData() {
        if ((choosenZipCodes.size() >= 5) && (choosenZipCodes.size() <= 10)) {
            meteoWSData = MeteoWSClient.getMeteoWSDataForManyZips(choosenZipCodes);
            setShowData(true);
            return "";
        } else {
            setShowData(false);
            FacesContext.getCurrentInstance().addMessage("nrZipError", new FacesMessage("Choose between 5 and 10 zip codes!"));
            return "";
        }
    }
    
    /**
     * Grabs meteo data for selected zip code
     * @return
     */
    public String grabMeteoRESTData() {
        MeteoRESTClient mrc = new MeteoRESTClient(zipCodeAdd);
        meteoRESTData = mrc.getHtml();
        return "";
    }
    
}
