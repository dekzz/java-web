/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.datomala.rest.clients;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

/**
 * Jersey REST client generated for REST resource:MeteoREST<br>
 * USAGE:
 * <pre>
 *        MeteoRESTClient client = new MeteoRESTClient();
 *        Object response = client.XXX(...);
 *        // do whatever with response
 *        client.close();
 * </pre>
 *
 * @author dex
 */
public class MeteoRESTClient {
    private WebResource webResource;
    private Client client;
    private static final String BASE_URI = "http://localhost:8084/datomala_zadaca_3_1/webresources";

    /**
     *
     * @param zip Zip code of the city
     */
    public MeteoRESTClient(String zip) {
        com.sun.jersey.api.client.config.ClientConfig config = new com.sun.jersey.api.client.config.DefaultClientConfig();
        client = Client.create(config);
        String resourcePath = java.text.MessageFormat.format("MeteoREST/{0}", new Object[]{zip});
        webResource = client.resource(BASE_URI).path(resourcePath);
    }

    /**
     *
     * @param zip
     */
    public void setResourcePath(String zip) {
        String resourcePath = java.text.MessageFormat.format("MeteoREST/{0}", new Object[]{zip});
        webResource = client.resource(BASE_URI).path(resourcePath);
    }

    /**
     *
     * @throws UniformInterfaceException
     */
    public void delete() throws UniformInterfaceException {
        webResource.delete();
    }

    /**
     *
     * @return
     * @throws UniformInterfaceException
     */
    public String getHtml() throws UniformInterfaceException {
        WebResource resource = webResource;
        return resource.accept(javax.ws.rs.core.MediaType.TEXT_HTML).get(String.class);
    }

    /**
     *
     * @param requestEntity
     * @throws UniformInterfaceException
     */
    public void putHtml(Object requestEntity) throws UniformInterfaceException {
        webResource.type(javax.ws.rs.core.MediaType.TEXT_HTML).put(requestEntity);
    }

    /**
     *
     */
    public void close() {
        client.destroy();
    }
    
}
