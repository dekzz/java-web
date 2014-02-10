/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.web.beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.EJB;
import net.wxbug.api.LiveWeatherData;
import org.foi.nwtis.datomala.ejb.eb.Cities;
import org.foi.nwtis.datomala.ejb.eb.States;
import org.foi.nwtis.datomala.ejb.eb.ZipCodes;
import org.foi.nwtis.datomala.ejb.sb.CitiesFacade;
import org.foi.nwtis.datomala.ejb.sb.StatesFacade;
import org.foi.nwtis.datomala.ejb.sb.WeatherBugClient;
import org.foi.nwtis.datomala.ejb.sb.ZipCodesFacade;

/**
 *
 * @author dex
 */
@Named(value = "selectCitiesZipCodes")
@SessionScoped
public class selectCitiesZipCodes implements Serializable {

    @EJB
    private WeatherBugClient weatherBugClient;
    @EJB
    private ZipCodesFacade zipCodesFacade;
    @EJB
    private CitiesFacade citiesFacade;
    @EJB
    private StatesFacade statesFacade;
    private Map<String, Object> listStates;
    private List<String> listStatesSelected;
    private Map<String, Object> selectedStates;
    private List<String> selectedStatesSelected;
    private String filterStates;
    private Map<String, Object> listCities;
    private List<String> listCitiesSelected;
    private Map<String, Object> selectedCities;
    private List<String> selectedCitiesSelected;
    private String filterCities;
    private Map<String, Object> listZipCodes;
    private List<String> listZipCodesSelected;
    private Map<String, Object> selectedZipCodes;
    private List<String> selectedZipCodesSelected;
    private String filterZipCodes;
    private List<LiveWeatherData> listMeteoData;

    /**
     * Creates a new instance of selectCitiesZipCodes
     */
    public selectCitiesZipCodes() {
    }

    public Map<String, Object> getListStates() {
        listStates = new TreeMap<String, Object>();
        List<States> states;

        if (filterStates == null || filterStates.trim().isEmpty()) {
            states = statesFacade.findAll();
        } else {
            states = statesFacade.filterStates(filterStates);
        }

        for (States s : states) {
            listStates.put(s.getName(), s.getName());
        }

        return listStates;
    }

    public void setListStates(Map<String, Object> listStates) {
        this.listStates = listStates;
    }

    public List<String> getListStatesSelected() {
        return listStatesSelected;
    }

    public void setListStatesSelected(List<String> listStatesSelected) {
        this.listStatesSelected = listStatesSelected;
    }

    public Map<String, Object> getSelectedStates() {
        return selectedStates;
    }

    public void setSelectedStates(Map<String, Object> selectedStates) {
        this.selectedStates = selectedStates;
    }

    public List<String> getSelectedStatesSelected() {
        return selectedStatesSelected;
    }

    public void setSelectedStatesSelected(List<String> selectedStatesSelected) {
        this.selectedStatesSelected = selectedStatesSelected;
    }

    public String getFilterStates() {
        return filterStates;
    }

    public void setFilterStates(String filterStates) {
        this.filterStates = filterStates;
    }

    public Map<String, Object> getListCities() {
        listCities = new TreeMap<String, Object>();

        if (selectedStates == null || selectedStates.isEmpty()) {
            return listCities;
        }
        List<Cities> cities;
        if (filterCities == null || filterCities.trim().isEmpty()) {
            cities = citiesFacade.filterCities(selectedStates.keySet());
        } else {
            cities = citiesFacade.filterCities(selectedStates.keySet(), filterCities);
        }
        for (Cities c : cities) {
            String city = c.getCitiesPK().getState() + " - "
                    + c.getCitiesPK().getCounty() + " - "
                    + c.getCitiesPK().getCity();
            listCities.put(city, city);
        }

        return listCities;
    }

    public void setListCities(Map<String, Object> listCities) {
        this.listCities = listCities;
    }

    public List<String> getListCitiesSelected() {
        return listCitiesSelected;
    }

    public void setListCitiesSelected(List<String> listCitiesSelected) {
        this.listCitiesSelected = listCitiesSelected;
    }

    public Map<String, Object> getSelectedCities() {
        return selectedCities;
    }

    public void setSelectedCities(Map<String, Object> selectedCities) {
        this.selectedCities = selectedCities;
    }

    public List<String> getSelectedCitiesSelected() {
        return selectedCitiesSelected;
    }

    public void setSelectedCitiesSelected(List<String> selectedCitiesSelected) {
        this.selectedCitiesSelected = selectedCitiesSelected;
    }

    public String getFilterCities() {
        return filterCities;
    }

    public void setFilterCities(String filterCities) {
        this.filterCities = filterCities;
    }

    public Map<String, Object> getListZipCodes() {
        listZipCodes = new TreeMap<String, Object>();

        if (selectedCities == null || selectedCities.isEmpty()) {
            return listZipCodes;
        }

        List<ZipCodes> zipCodes;

        if (filterZipCodes == null || filterZipCodes.trim().isEmpty()) {
            zipCodes = zipCodesFacade.filterZipCodes(selectedCities.keySet());
        } else {
            zipCodes = zipCodesFacade.filterZipCodes(selectedCities.keySet(), filterZipCodes);
        }
        for (ZipCodes z : zipCodes) {
            String zip = z.getZip() + " - " + z.getCities().getCitiesPK().getState() + " - " + z.getCities().getCitiesPK().getCounty() + " -  "
                    + z.getCities().getCitiesPK().getCity();
            listZipCodes.put(zip, zip);
        }

        return listZipCodes;
    }

    public void setListZipCodes(Map<String, Object> listZipCodes) {
        this.listZipCodes = listZipCodes;
    }

    public List<String> getListZipCodesSelected() {
        return listZipCodesSelected;
    }

    public void setListZipCodesSelected(List<String> listZipCodesSelected) {
        this.listZipCodesSelected = listZipCodesSelected;
    }

    public Map<String, Object> getSelectedZipCodes() {
        return selectedZipCodes;
    }

    public void setSelectedZipCodes(Map<String, Object> selectedZipCodes) {
        this.selectedZipCodes = selectedZipCodes;
    }

    public List<String> getSelectedZipCodesSelected() {
        return selectedZipCodesSelected;
    }

    public void setSelectedZipCodesSelected(List<String> selectedZipCodesSelected) {
        this.selectedZipCodesSelected = selectedZipCodesSelected;
    }

    public String getFilterZipCodes() {
        return filterZipCodes;
    }

    public void setFilterZipCodes(String filterZipCodes) {
        this.filterZipCodes = filterZipCodes;
    }

    public List<LiveWeatherData> getListMeteoData() {
        return listMeteoData;
    }

    public void setListMeteoData(List<LiveWeatherData> listMeteoData) {
        this.listMeteoData = listMeteoData;
    }

    public String addStates() {
        if (listStatesSelected == null) {
            return "";
        }
        if (selectedStates == null) {
            selectedStates = new TreeMap<String, Object>();
        }
        for (String s : listStatesSelected) {
            selectedStates.put(s, s);
        }
        return "";
    }

    public String deleteStates() {
        if (selectedStatesSelected == null) {
            return "";
        }
        if (selectedStates == null) {
            selectedStates = new TreeMap<String, Object>();
        }
        if (selectedStatesSelected != null) {
            for (String s : selectedStatesSelected) {
                selectedStates.remove(s);
            }
        }
        return "";
    }

    public String loadCities() {

        return "";
    }

    public String addCities() {
        if (listCitiesSelected == null || listCitiesSelected.isEmpty()) {
            return "";
        }
        if (selectedCities == null) {
            selectedCities = new TreeMap<String, Object>();
        }
        for (String c : listCitiesSelected) {
            selectedCities.put(c, c);
        }
        return "";
    }

    public String deleteCities() {
        if (selectedCitiesSelected == null) {
            return "";
        }
        if (selectedCities == null) {
            selectedCities = new TreeMap<String, Object>();
        }
        if (selectedCitiesSelected != null) {
            for (String s : selectedCitiesSelected) {
                selectedCities.remove(s);
            }
        }
        return "";
    }

    public String loadZipCodes() {

        return "";
    }

    public String addZipCodes() {
        if (listZipCodesSelected == null) {
            return "";
        }
        if (selectedZipCodes == null) {
            selectedZipCodes = new TreeMap<String, Object>();
        }
        for (String s : listZipCodesSelected) {
            selectedZipCodes.put(s, s);
        }
        return "";
    }

    public String deleteZipCodes() {
        if (selectedZipCodesSelected == null) {
            return "";
        }
        if (selectedZipCodes == null) {
            selectedZipCodes = new TreeMap<String, Object>();
        }
        if (selectedZipCodesSelected != null) {
            for (String s : selectedZipCodesSelected) {
                selectedZipCodes.remove(s);
            }
        }
        return "";
    }

    public String loadMeteoData() {
        listMeteoData = new ArrayList<LiveWeatherData>();
        for (String zc : selectedZipCodes.keySet()) {
            String[] zip = zc.split(" - ");
            listMeteoData.add(weatherBugClient.getMeteoData(zip[0]));
        }
        if (!listMeteoData.isEmpty()) {
            processMeteoData();
        }

        return "";
    }

    private void processMeteoData() {
        String[] zip;
        int i = 0;
        for (String selectedZip : selectedZipCodes.keySet()) {
            zip = selectedZip.split(" - ");
            if (listMeteoData.get(i).getZipCode().equals(zip[0])) {
                listMeteoData.get(i).setCity(zip[1] + " - " + zip[2] + " - " + zip[3]);
            } else {
                listMeteoData.get(i).setZipCode(zip[0] + " (" + listMeteoData.get(i).getZipCode() + ")");
                listMeteoData.get(i).setCity(zip[1] + " - " + zip[2] + " - " + zip[3] + " (" + listMeteoData.get(i).getCity() + ")");
            }
            if (i == listMeteoData.size() - 1) {
                break;
            }
            i++;
        }
    }
}
