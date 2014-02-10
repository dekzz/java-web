
package org.foi.nwtis.datomala.ws.servers;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.foi.nwtis.datomala.ws.servers package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetMeteoWSDataForManyZip_QNAME = new QName("http://servers.ws.datomala.nwtis.foi.org/", "getMeteoWSDataForManyZip");
    private final static QName _GetMeteoWSDataForManyZipResponse_QNAME = new QName("http://servers.ws.datomala.nwtis.foi.org/", "getMeteoWSDataForManyZipResponse");
    private final static QName _GetMeteoWSDataForZip_QNAME = new QName("http://servers.ws.datomala.nwtis.foi.org/", "getMeteoWSDataForZip");
    private final static QName _GetMeteoWSDataForZipResponse_QNAME = new QName("http://servers.ws.datomala.nwtis.foi.org/", "getMeteoWSDataForZipResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.foi.nwtis.datomala.ws.servers
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetMeteoWSDataForZip }
     * 
     */
    public GetMeteoWSDataForZip createGetMeteoWSDataForZip() {
        return new GetMeteoWSDataForZip();
    }

    /**
     * Create an instance of {@link GetMeteoWSDataForZipResponse }
     * 
     */
    public GetMeteoWSDataForZipResponse createGetMeteoWSDataForZipResponse() {
        return new GetMeteoWSDataForZipResponse();
    }

    /**
     * Create an instance of {@link GetMeteoWSDataForManyZip }
     * 
     */
    public GetMeteoWSDataForManyZip createGetMeteoWSDataForManyZip() {
        return new GetMeteoWSDataForManyZip();
    }

    /**
     * Create an instance of {@link GetMeteoWSDataForManyZipResponse }
     * 
     */
    public GetMeteoWSDataForManyZipResponse createGetMeteoWSDataForManyZipResponse() {
        return new GetMeteoWSDataForManyZipResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMeteoWSDataForManyZip }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servers.ws.datomala.nwtis.foi.org/", name = "getMeteoWSDataForManyZip")
    public JAXBElement<GetMeteoWSDataForManyZip> createGetMeteoWSDataForManyZip(GetMeteoWSDataForManyZip value) {
        return new JAXBElement<GetMeteoWSDataForManyZip>(_GetMeteoWSDataForManyZip_QNAME, GetMeteoWSDataForManyZip.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMeteoWSDataForManyZipResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servers.ws.datomala.nwtis.foi.org/", name = "getMeteoWSDataForManyZipResponse")
    public JAXBElement<GetMeteoWSDataForManyZipResponse> createGetMeteoWSDataForManyZipResponse(GetMeteoWSDataForManyZipResponse value) {
        return new JAXBElement<GetMeteoWSDataForManyZipResponse>(_GetMeteoWSDataForManyZipResponse_QNAME, GetMeteoWSDataForManyZipResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMeteoWSDataForZip }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servers.ws.datomala.nwtis.foi.org/", name = "getMeteoWSDataForZip")
    public JAXBElement<GetMeteoWSDataForZip> createGetMeteoWSDataForZip(GetMeteoWSDataForZip value) {
        return new JAXBElement<GetMeteoWSDataForZip>(_GetMeteoWSDataForZip_QNAME, GetMeteoWSDataForZip.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMeteoWSDataForZipResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://servers.ws.datomala.nwtis.foi.org/", name = "getMeteoWSDataForZipResponse")
    public JAXBElement<GetMeteoWSDataForZipResponse> createGetMeteoWSDataForZipResponse(GetMeteoWSDataForZipResponse value) {
        return new JAXBElement<GetMeteoWSDataForZipResponse>(_GetMeteoWSDataForZipResponse_QNAME, GetMeteoWSDataForZipResponse.class, null, value);
    }

}
