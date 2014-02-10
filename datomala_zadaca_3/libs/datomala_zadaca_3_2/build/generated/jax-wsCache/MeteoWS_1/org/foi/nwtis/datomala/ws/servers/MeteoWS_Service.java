
package org.foi.nwtis.datomala.ws.servers;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "MeteoWS", targetNamespace = "http://servers.ws.datomala.nwtis.foi.org/", wsdlLocation = "http://localhost:8084/datomala_zadaca_3_1/MeteoWS?wsdl")
public class MeteoWS_Service
    extends Service
{

    private final static URL METEOWS_WSDL_LOCATION;
    private final static WebServiceException METEOWS_EXCEPTION;
    private final static QName METEOWS_QNAME = new QName("http://servers.ws.datomala.nwtis.foi.org/", "MeteoWS");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://localhost:8084/datomala_zadaca_3_1/MeteoWS?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        METEOWS_WSDL_LOCATION = url;
        METEOWS_EXCEPTION = e;
    }

    public MeteoWS_Service() {
        super(__getWsdlLocation(), METEOWS_QNAME);
    }

    public MeteoWS_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), METEOWS_QNAME, features);
    }

    public MeteoWS_Service(URL wsdlLocation) {
        super(wsdlLocation, METEOWS_QNAME);
    }

    public MeteoWS_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, METEOWS_QNAME, features);
    }

    public MeteoWS_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MeteoWS_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns MeteoWS
     */
    @WebEndpoint(name = "MeteoWSPort")
    public MeteoWS getMeteoWSPort() {
        return super.getPort(new QName("http://servers.ws.datomala.nwtis.foi.org/", "MeteoWSPort"), MeteoWS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MeteoWS
     */
    @WebEndpoint(name = "MeteoWSPort")
    public MeteoWS getMeteoWSPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://servers.ws.datomala.nwtis.foi.org/", "MeteoWSPort"), MeteoWS.class, features);
    }

    private static URL __getWsdlLocation() {
        if (METEOWS_EXCEPTION!= null) {
            throw METEOWS_EXCEPTION;
        }
        return METEOWS_WSDL_LOCATION;
    }

}
