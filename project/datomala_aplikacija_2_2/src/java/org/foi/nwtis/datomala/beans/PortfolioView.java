/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.component.html.HtmlDataTable;
import org.foi.datomala.ws.WSClient;
import org.foi.nwtis.datomala.ejb.eb.DatomalaPortfolio;
import org.foi.nwtis.datomala.ejb.eb.DatomalaZipPortfolio;
import org.foi.nwtis.datomala.ejb.eb.ZipCodes;
import org.foi.nwtis.datomala.ejb.sb.DatomalaPortfolioFacade;
import org.foi.nwtis.datomala.ejb.sb.DatomalaZipPortfolioFacade;
import org.foi.nwtis.datomala.model.MeteoDataExtended;
import org.foi.nwtis.datomala.ws.MeteoData;



/**
 *
 * @author dex
 */
@Named(value = "portfolioView")
@SessionScoped
public class PortfolioView implements Serializable {
    @EJB
    private DatomalaPortfolioFacade datomalaPortfolioFacade;
    @EJB
    private DatomalaZipPortfolioFacade datomalaZipPortfolioFacade;

    private String portfolioId;
    private DatomalaPortfolio portfolio;
    private List<DatomalaZipPortfolio> listZP;
    private List<ZipCodes> listZipCodes;
    private static List<MeteoDataExtended> meteoData;
    private static List<MeteoDataExtended> meteoDataFilter;
    private MeteoData md;
    private MeteoDataExtended mde;
    
    private int start = 0, pagination = 3;
    private static int viewId = 0;
    private boolean changed = false;
    
    private String date1;
    private String date2;
    
    private HtmlDataTable dataTable = new HtmlDataTable();
    
    /**
     * Creates a new instance of PortfolioView
     */
    public PortfolioView() {
    }
    
    public String calculateGeoDistance(double lat1, double lng1, double lat2, double lng2){
        double earthRadius = 3958.75;
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        int meterConversion = 1609;

        return new Double(dist * meterConversion).floatValue() + "";

    }
    
    public String fetchFilteredData(){
        viewId = 1;
        setChanged(true);
        start = 0;
        pagination = 3;
        return "";
    }

    public String getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(String portfolioId) {
        this.portfolioId = portfolioId;
    }

    public DatomalaPortfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(DatomalaPortfolio portfolio) {
        this.portfolio = portfolio;
    }

    public List<DatomalaZipPortfolio> getListZP() {
        return listZP;
    }

    public void setListZP(List<DatomalaZipPortfolio> listZP) {
        this.listZP = listZP;
    }

    public List<ZipCodes> getListZipCodes() {
        return listZipCodes;
    }

    public void setListZipCodes(List<ZipCodes> listZipCodes) {
        this.listZipCodes = listZipCodes;
    }

    public List<MeteoDataExtended> getMeteoData() throws ParseException {
        if (meteoData == null || isChanged()){
            meteoData = new ArrayList<MeteoDataExtended>();
            setChanged(false);
        }
        else {
            int step = start + pagination;
            if (step > meteoData.size())
                 step = meteoData.size();
            return meteoData.subList(start, step);
        }
        List<MeteoData> meteoDataList;
        String distance = "0";
        portfolio = datomalaPortfolioFacade.fetchPortfolio(Integer.parseInt(portfolioId));
        System.out.println("PORTFOLIO ID: " + portfolioId);
        listZP = new ArrayList<DatomalaZipPortfolio>();
        System.out.println("LP: " + portfolio);
        listZP = datomalaZipPortfolioFacade.fetchZP(portfolio);
        System.out.println("LZP: " + listZP);
        listZipCodes = new ArrayList<ZipCodes>();
        for (DatomalaZipPortfolio zp : listZP) {
            listZipCodes.add(zp.getZip());
        }
        for (ZipCodes zc : listZipCodes){
            if (viewId == 0){
                SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss");
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                md = WSClient.selectLatestDataForZipCode(zc.getZip() + "");
                if (md == null){
                    mde =  new MeteoDataExtended(zc.getZip() + "", "N/A", "N/A", "N/A",
                                                       "N/A", "N/A", "N/A", "N/A", "N/A",
                                                       "N/A", "N/A", "N/A", "N/A", "N/A",
                                                        "N/A"
                                                      );
                    meteoData.add(mde);
                    continue;
                }
                if (zc.getZip()  != Integer.parseInt(md.getZipcodeadd())){
                    distance = calculateGeoDistance(zc.getLatitude(), 
                                                         zc.getLongitude(),
                                                         Double.parseDouble(md.getLatitude()),
                                                         Double.parseDouble(md.getLongitude())
                                                        );
                }
                mde =  new MeteoDataExtended(md.getZipcode(),
                                                md.getZipcodeadd(),
                                                md.getTemperature(),
                                                md.getHumidity(),
                                                md.getPressure(),
                                                md.getWind(),
                                                md.getWeather(),
                                                md.getLatitude(),
                                                md.getLongitude(),
                                                md.getLatitudeadd(),
                                                md.getLongitudeadd(),
                                                md.getCity(),
                                                md.getCityadd(),
                                                df.format(df2.parse(md.getDateadd())),
                                                distance
                                               );
                meteoData.add(mde);
            }
            else {
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss");
                String dat1 = df2.format(df.parse(date1));
                String dat2 = df2.format(df.parse(date2));
                meteoDataList = WSClient.selectDataInTimeInterval(zc.getZip() + "", dat1, dat2);
                if (meteoDataList == null)
                    continue;
                for (MeteoData md2 : meteoDataList){
                    if (md2.getZipcodeadd() != null && !md2.getZipcodeadd().equals("null")){
                        if (zc.getZip()  != Integer.parseInt(md2.getZipcodeadd())){
                        distance = calculateGeoDistance(zc.getLatitude(), 
                                                             zc.getLongitude(),
                                                             Double.parseDouble(md2.getLatitudeadd()),
                                                             Double.parseDouble(md2.getLongitudeadd())
                                                            );
                        }
                    }
                    mde =  new MeteoDataExtended(md2.getZipcode(),
                                                    md2.getZipcodeadd(),
                                                    md2.getTemperature(),
                                                    md2.getHumidity(),
                                                    md2.getPressure(),
                                                    md2.getWind(),
                                                    md2.getWeather(),
                                                    md2.getLatitude(),
                                                    md2.getLongitude(),
                                                    md2.getLatitudeadd(),
                                                    md2.getLongitudeadd(),
                                                    md2.getCity(),
                                                    md2.getCityadd(),
                                                    df.format(df2.parse(md2.getDateadd())),
                                                    distance
                                                   );
                    meteoData.add(mde);
                }
            }
        }
        int step = start + pagination;
        if (step > meteoData.size())
            step = meteoData.size();
        return meteoData.subList(start, step);
    }

    public static void setMeteoData(List<MeteoDataExtended> meteoData) {
        PortfolioView.meteoData = meteoData;
    }

    public static List<MeteoDataExtended> getMeteoDataFilter() {
        return meteoDataFilter;
    }

    public static void setMeteoDataFilter(List<MeteoDataExtended> meteoDataFilter) {
        PortfolioView.meteoDataFilter = meteoDataFilter;
    }

    public MeteoData getMd() {
        return md;
    }

    public void setMd(MeteoData md) {
        this.md = md;
    }

    public MeteoDataExtended getMde() {
        return mde;
    }

    public void setMde(MeteoDataExtended mde) {
        this.mde = mde;
    }

    public static int getViewId() {
        return viewId;
    }

    public static void setViewId(int viewId) {
        PortfolioView.viewId = viewId;
    }

    public boolean isChanged() {
        return changed;
    }

    public void setChanged(boolean changed) {
        this.changed = changed;
    }

    public String getDate1() {
        return date1;
    }

    public void e1(String date1) {
        this.date1 = date1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
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
