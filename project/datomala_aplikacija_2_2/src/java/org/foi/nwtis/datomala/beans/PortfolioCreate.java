/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import org.foi.datomala.ws.WSClient;
import org.foi.nwtis.datomala.ejb.eb.Cities;
import org.foi.nwtis.datomala.ejb.eb.DatomalaPortfolio;
import org.foi.nwtis.datomala.ejb.eb.States;
import org.foi.nwtis.datomala.ejb.eb.ZipCodes;
import org.foi.nwtis.datomala.ejb.jms.ZipJMS;
import org.foi.nwtis.datomala.ejb.sb.CitiesFacade;
import org.foi.nwtis.datomala.ejb.sb.DatomalaPortfolioFacade;
import org.foi.nwtis.datomala.ejb.sb.DatomalaZipPortfolioFacade;
import org.foi.nwtis.datomala.ejb.sb.StatesFacade;
import org.foi.nwtis.datomala.ejb.sb.ZipCodesFacade;
import org.foi.nwtis.datomala.listener.ApplicationListener;

/**
 *
 * @author dex
 */
@Named(value = "portfolioCreate")
@SessionScoped
public class PortfolioCreate implements Serializable {
    @EJB
    private StatesFacade statesFacade;
    @EJB
    private CitiesFacade citiesFacade;
    @EJB
    private DatomalaPortfolioFacade datomalaPortfolioFacade;
    @EJB
    private ZipCodesFacade zipCodesFacade;
    @EJB
    private DatomalaZipPortfolioFacade datomalaZipPortfolioFacade;
    @EJB
    private ZipJMS zipJMS;

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
    private List<Cities> cities;
    
    private Map<String, Object> listZipCodes;
    private List<String> listZipCodesSelected;
    private Map<String, Object> selectedZipCodes;
    private List<String> selectedZipCodesSelected;
    private String filterZipCodes;
    
    private String portfolioName;
    
    private List<String> activeZipCodes;

    public PortfolioCreate() {
        context = FacesContext.getCurrentInstance();
        session = (HttpSession)(context.getExternalContext().getSession(true)); 
        activeZipCodes = WSClient.selectActiveZipCodes();
    }
     
    private HttpSession session;
    private FacesContext context;
    
    private int minNrZips = Integer.parseInt(ApplicationListener.config.getSettings("min_zips"));
    private int maxNrZips = Integer.parseInt(ApplicationListener.config.getSettings("max_zips"));


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
        if (selectedStatesSelected != null) {
            for (String s : selectedStatesSelected) {
                selectedStates.remove(s);
            }
        }
        return "";
    }
    

    public String fetchCities() {
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
        if (selectedCitiesSelected != null) {
            for (String c : selectedCitiesSelected) {
                selectedCities.remove(c);
            }
        }
        return "";
    }
    

    public String fetchZipCodes() {
        return "";
    }
    

    public String addZipCodes() {
        if (listZipCodesSelected == null || listZipCodesSelected.isEmpty()) {
            return "";
        }
        if (selectedZipCodes == null) {
            selectedZipCodes = new TreeMap<String, Object>();
        }
        for (String z : listZipCodesSelected) {
            selectedZipCodes.put(z, z);
        }
        return "";
    }
    

    public String deleteZipCodes() {
        if (selectedZipCodesSelected != null) {
            for (String c : selectedZipCodesSelected) {
                selectedZipCodes.remove(c);
            }
        }
        return "";
    }
    

    public void checkEmpty (FacesContext context, UIComponent component, Object value) {
        Locale l = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        String field = (String) value;
        if (field.equals("")){
            FacesMessage message = new FacesMessage();
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            if (l.equals(Locale.ENGLISH)){
                message.setDetail("Field can not be empty.");
            } else {
                message.setDetail("Polje ne smije biti prazno.");
            }
            context.addMessage(null, message);
            throw new ValidatorException(message);
        }
    }
    
    public String savePortfolio() throws NamingException{
        String username = (String) session.getAttribute("username");
        FacesContext c = FacesContext.getCurrentInstance();
        if (selectedZipCodes == null || selectedZipCodes.isEmpty()){
            return "";
        }
        if (selectedZipCodes.size() < minNrZips){
           c.addMessage(null, new FacesMessage("Required minimum " + minNrZips + " zip codes."));
           return "";
        } else if (selectedZipCodes.size() > maxNrZips){
            c.addMessage(null, new FacesMessage("Maximum allowed " + maxNrZips + " zip codes."));
            return "";
        }
        DatomalaPortfolio portf = datomalaPortfolioFacade.addPortfolio(portfolioName, username);
        if (portf == null){
            c.addMessage(null, new FacesMessage("Taj portfolio već postoji."));
            return "";
        }
        for (String s : selectedZipCodes.keySet()){
            String[] data = s.split("-");
            String zip = data[3].trim();
            datomalaZipPortfolioFacade.addZP(portf, Integer.parseInt(zip));
            if (!activeZipCodes.contains(zip)){
                try {
                    System.out.println("Sending JMS for zip: " + zip);
                    zipJMS.sendJMSMessageToNWTiS_datomala_2(zip);
                } catch (JMSException ex) {
                    Logger.getLogger(PortfolioCreate.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return "OK";
    }
    
   
    public Map<String, Object> getListStates() {
        listStates = new TreeMap<String, Object>();
        List<States> drzave;
        if (filterStates == null || filterStates.trim().isEmpty()) {
            drzave = statesFacade.findAll();
        } else {
            drzave = statesFacade.filterStates(filterStates.toUpperCase());
        }
        for (States d : drzave) {
            listStates.put(d.getName(), d.getName());
        }
        return listStates;
    }

    public void setListStates(Map<String, Object> popisDrzava) {
        this.listStates = popisDrzava;
    }

    public List<String> getListStatesSelected() {
        return listStatesSelected;
    }

    public void setListStatesSelected(List<String> popisDrzavaOdabrano) {
        this.listStatesSelected = popisDrzavaOdabrano;
    }

    public Map<String, Object> getSelectedStates() {
        return selectedStates;
    }

    public void setSelectedStates(Map<String, Object> odabraneDrzave) {
        this.selectedStates = odabraneDrzave;
    }

    public List<String> getSelectedStatesSelected() {
        return selectedStatesSelected;
    }

    public void setSelectedStatesSelected(List<String> odabraneDrzaveOdabrano) {
        this.selectedStatesSelected = odabraneDrzaveOdabrano;
    }

    public String getFilterStates() {
        return filterStates;
    }

    public void setFilterStates(String filterDrzava) {
        this.filterStates = filterDrzava;
    }

    public Map<String, Object> getListCities() {
        listCities = new TreeMap<String, Object>();
        if (selectedStates == null || selectedStates.isEmpty()) {
            return listCities;
        }

        if (filterCities == null || filterCities.isEmpty()) {
            cities = citiesFacade.filterCities(selectedStates.keySet());
        } else {
            cities = citiesFacade.filterCities(selectedStates.keySet(), filterCities.toUpperCase());
        }

        for (Cities c : cities) {
            String grad = c.getCitiesPK().getState() + " - " + c.getCitiesPK().getCounty() + " -  " + c.getCitiesPK().getCity();
            listCities.put(grad, grad);
        }
        return listCities;
    }

    public void setListCities(Map<String, Object> popisGradova) {
        this.listCities = popisGradova;
    }

    public List<String> getListCitiesSelected() {
        return listCitiesSelected;
    }

    public void setListCitiesSelected(List<String> popisGradovaOdabrano) {
        this.listCitiesSelected = popisGradovaOdabrano;
    }

    public Map<String, Object> getSelectedCities() {
        return selectedCities;
    }

    public void setSelectedCities(Map<String, Object> odabraniGradovi) {
        this.selectedCities = odabraniGradovi;
    }

    public List<String> getSelectedCitiesSelected() {
        return selectedCitiesSelected;
    }

    public void setSelectedCitiesSelected(List<String> odabraniGradoviOdabrano) {
        this.selectedCitiesSelected = odabraniGradoviOdabrano;
    }

    public String getFilterCities() {
        return filterCities;
    }

    public void setFilterCities(String filterGradova) {
        this.filterCities = filterGradova;
    }

    public Map<String, Object> getListZipCodes() {
        listZipCodes = new TreeMap<String, Object>();
        List<ZipCodes> zips;
        if (selectedCities == null || selectedCities.isEmpty()) {
            return listZipCodes;
        }
        if (filterZipCodes == null || filterZipCodes.isEmpty()) {
            zips = zipCodesFacade.filterZips(selectedCities.keySet());
        } else {
            zips = zipCodesFacade.filterZips(selectedCities.keySet(), filterZipCodes);
        }
        for (ZipCodes zc : zips) {
            String res = zc.getCities().getCitiesPK().getState() + " - " + zc.getCities().getCitiesPK().getCounty() + " -  " + 
                         zc.getCities().getCitiesPK().getCity() + " - " + zc.getZip();
            listZipCodes.put(res.toString(), res.toString());
        }
        return listZipCodes;
    }

    public void setListZipCodes(Map<String, Object> popisZipKodova) {
        this.listZipCodes = popisZipKodova;
    }

    public List<String> getListZipCodesSelected() {
        return listZipCodesSelected;
    }

    public void setListZipCodesSelected(List<String> popisZipKodovaOdabrano) {
        this.listZipCodesSelected = popisZipKodovaOdabrano;
    }

    public Map<String, Object> getSelectedZipCodes() {
        return selectedZipCodes;
    }

    public void setSelectedZipCodes(Map<String, Object> odabraniZipKodovi) {
        this.selectedZipCodes = odabraniZipKodovi;
    }

    public List<String> getSelectedZipCodesSelected() {
        return selectedZipCodesSelected;
    }

    public void setSelectedZipCodesSelected(List<String> odabraniZipKodoviOdabrano) {
        this.selectedZipCodesSelected = odabraniZipKodoviOdabrano;
    }

    public String getFilterZipCodes() {
        return filterZipCodes;
    }

    public void setFilterZipCodes(String filterZipKodova) {
        this.filterZipCodes = filterZipKodova;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String nazivPortfolia) {
        this.portfolioName = nazivPortfolia;
    }
    
}
