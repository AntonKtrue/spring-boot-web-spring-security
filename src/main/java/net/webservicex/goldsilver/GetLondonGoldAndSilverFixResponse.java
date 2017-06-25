
package net.webservicex.goldsilver;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetLondonGoldAndSilverFixResult" type="{http://www.webservicex.net}LondonMarketData"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getLondonGoldAndSilverFixResult"
})
@XmlRootElement(name = "GetLondonGoldAndSilverFixResponse")
public class GetLondonGoldAndSilverFixResponse {

    @XmlElement(name = "GetLondonGoldAndSilverFixResult", required = true)
    protected LondonMarketData getLondonGoldAndSilverFixResult;

    /**
     * Gets the value of the getLondonGoldAndSilverFixResult property.
     * 
     * @return
     *     possible object is
     *     {@link LondonMarketData }
     *     
     */
    public LondonMarketData getGetLondonGoldAndSilverFixResult() {
        return getLondonGoldAndSilverFixResult;
    }

    /**
     * Sets the value of the getLondonGoldAndSilverFixResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link LondonMarketData }
     *     
     */
    public void setGetLondonGoldAndSilverFixResult(LondonMarketData value) {
        this.getLondonGoldAndSilverFixResult = value;
    }

}
