
package org.foi.nwtis.datomala.ws.servers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import net.wxbug.api.LiveWeatherData;


/**
 * <p>Java class for getMeteoWSDataForZipResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getMeteoWSDataForZipResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://api.wxbug.net/}LiveWeatherData" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getMeteoWSDataForZipResponse", propOrder = {
    "_return"
})
public class GetMeteoWSDataForZipResponse {

    @XmlElement(name = "return")
    protected LiveWeatherData _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link LiveWeatherData }
     *     
     */
    public LiveWeatherData getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link LiveWeatherData }
     *     
     */
    public void setReturn(LiveWeatherData value) {
        this._return = value;
    }

}
