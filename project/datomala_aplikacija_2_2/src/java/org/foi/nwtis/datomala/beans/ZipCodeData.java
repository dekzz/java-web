/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import javax.faces.component.html.HtmlDataTable;
import org.foi.datomala.ws.WSClient;
import org.foi.nwtis.datomala.ws.MeteoData;

/**
 *
 * @author dex
 */
@Named(value = "zipCodeData")
@SessionScoped
public class ZipCodeData implements Serializable {

    private List<String> activeZipCodes;
       
    private TreeMap<String, String> listCities;
    private TreeMap<String, String> listCitiesSelected;
    private List<String> cities;
    private List<String> citiesDelete;
    private boolean selected = false;
    
    
    private List<MeteoData> freshData = new ArrayList<MeteoData>();
    private List<MeteoData> freshDataSelected = new ArrayList<MeteoData>();
    
    private HtmlDataTable dataTable = new HtmlDataTable();

    
    public ZipCodeData() {   
    }
    
    public String fetchFreshestDataForZip(){
        return "OK";
    }
    
    public String addCity() {
        if (cities == null || cities.isEmpty()) {
            return "";
        }
        if (listCitiesSelected == null) {
            listCitiesSelected = new TreeMap<String, String>();
        }
        for (String c : cities) {
            if (!listCitiesSelected.containsKey(c))
                listCitiesSelected.put(c, c);
        }
        return "";
    }
    
    public String deleteCity() {
        if (citiesDelete != null) {
            for (String c : citiesDelete) {
                listCitiesSelected.remove(c);
            }
        }
        return "";
    }
    
    public String fetchMeteoData(){
        return "";
    }
    

    public List<String> getActiveZipCodes() {
        if (activeZipCodes != null)
            activeZipCodes.clear();
        activeZipCodes = WSClient.selectActiveZipCodes();
        return activeZipCodes;
    }

    public void setActiveZipCodes(List<String> activeZipCodes) {
        this.activeZipCodes = activeZipCodes;
    }

    public List<MeteoData> getFreshData() {
        List<String> zips = getActiveZipCodes();
        if (freshData == null)
            freshData = new ArrayList<MeteoData>();
        freshData.clear();
        for (String z : zips){
            freshData.add(WSClient.selectLatestDataForZipCode(z));
        }
        return freshData;
    }

    public void setFreshData(List<MeteoData> freshData) {
        this.freshData = freshData;
    }

    public TreeMap<String, String> getListCities() {
        listCities = new TreeMap<String, String>();
        List<MeteoData> data = getFreshData();
        for (MeteoData md : data){
            if (!listCities.containsKey(md.getCity())) {
                listCities.put(md.getCity(), md.getCity());
            }
        }
        return listCities;
    }

    public void setListCities(TreeMap<String, String> listCities) {
        this.listCities = listCities;
    }

    public TreeMap<String, String> getListCitiesSelected() {
        return listCitiesSelected;
    }

    public void setListCitiesSelected(TreeMap<String, String> listCitiesSelected) {
        this.listCitiesSelected = listCitiesSelected;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public List<String> getCitiesDelete() {
        return citiesDelete;
    }

    public void setCitiesDelete(List<String> citiesDelete) {
        this.citiesDelete = citiesDelete;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public List<MeteoData> getFreshDataSelected() {
        freshDataSelected = new ArrayList<MeteoData>();  
        if (listCitiesSelected == null )
            return freshDataSelected;
        for (MeteoData md : freshData){            
            if (listCitiesSelected.containsKey(md.getCity())){
                if (!freshDataSelected.contains(md)) {
                    freshDataSelected.add(md);
                }
            }
        }
        if (freshDataSelected.size() > 0)
            selected = true;
        else
            selected = false;
        
        return freshDataSelected;
    }

    public void setFreshDataSelected(List<MeteoData> freshDataSelected) {
        this.freshDataSelected = freshDataSelected;
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }
    
    // Pagination
    /**
     *
     */
    public void pageFirst() {
        dataTable.setFirst(0);
    }

    /**
     *
     */
    public void pagePrevious() {
        dataTable.setFirst(dataTable.getFirst() - dataTable.getRows());
    }

    /**
     *
     */
    public void pageNext() {
        dataTable.setFirst(dataTable.getFirst() + dataTable.getRows());
    }

    /**
     *
     */
    public void pageLast() {
        int count = dataTable.getRowCount();
        int rows = dataTable.getRows();
        dataTable.setFirst(count - ((count % rows != 0) ? count % rows : rows));
    }
    // get the current page number

    /**
     *
     * @return
     */
    public int getCurrentPage() {
        int rows = dataTable.getRows();
        int first = dataTable.getFirst();
        int count = dataTable.getRowCount();
        return (count / rows) - ((count - first) / rows) + 1;
    }
    // get the total no of pages

    /**
     *
     * @return
     */
    public int getTotalPages() {
        int rows = dataTable.getRows();
        int count = dataTable.getRowCount();
        return (count / rows) + ((count % rows != 0) ? 1 : 0);
    }
    
}
