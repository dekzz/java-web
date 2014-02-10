/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.ws;

import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.foi.nwtis.datomala.db.DBManager;
import org.foi.nwtis.datomala.model.MeteoData;

/**
 *
 * @author dex
 */
@WebService(serviceName = "MeteoData")
public class MeteoDataWS {
    
    @WebMethod(operationName = "selectActiveZipCodes")
    public List<String> selectActiveZipCodes() {
        return DBManager.selectActiveZipCodes();
    }
    
    @WebMethod(operationName = "selectLatestDataForZipCode")
    public MeteoData selectLatestDataForZipCode(@WebParam(name = "zipcode") String zipcode) {
        return DBManager.selectLatestDataForZipCode(zipcode);
    }
    
    @WebMethod(operationName = "selectTopZipCodes")
    public List<String> selectTopZipCodes(@WebParam(name = "n") int n) {
        return DBManager.selectTopZipCodes(n);
    }
    
    @WebMethod(operationName = "selectNLatestDataForZipCode")
    public List<MeteoData> selectNLatestDataForZipCode(@WebParam(name = "zipcode") String zipcode, @WebParam(name = "n") int n) {
        return DBManager.selectLatestDataForZipCode(zipcode, n);
    }
    
    @WebMethod(operationName = "selectDataInTimeInterval")
    public List<MeteoData> selectDataInTimeInterval(@WebParam(name = "zipcode") String zipcode, @WebParam(name = "fromDate") String fromDate, @WebParam(name = "toDate") String toDate) {
        return DBManager.selectDataInTimeInterval(zipcode, fromDate, toDate);
    }
    
}
