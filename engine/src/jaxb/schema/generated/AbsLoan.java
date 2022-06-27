//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2022.06.26 at 08:22:27 PM IDT 
//


package jaxb.schema.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element ref="{}abs-category"/>
 *         &lt;element ref="{}abs-capital"/>
 *         &lt;element ref="{}abs-total-yaz-time"/>
 *         &lt;element ref="{}abs-pays-every-yaz"/>
 *         &lt;element ref="{}abs-intrist-per-payment"/>
 *       &lt;/sequence>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "absCategory",
    "absCapital",
    "absTotalYazTime",
    "absPaysEveryYaz",
    "absIntristPerPayment"
})
@XmlRootElement(name = "abs-loan")
public class AbsLoan {

    @XmlElement(name = "abs-category", required = true)
    protected String absCategory;
    @XmlElement(name = "abs-capital")
    protected int absCapital;
    @XmlElement(name = "abs-total-yaz-time")
    protected int absTotalYazTime;
    @XmlElement(name = "abs-pays-every-yaz")
    protected int absPaysEveryYaz;
    @XmlElement(name = "abs-intrist-per-payment")
    protected int absIntristPerPayment;
    @XmlAttribute(name = "id", required = true)
    protected String id;

    /**
     * Gets the value of the absCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAbsCategory() {
        return absCategory;
    }

    /**
     * Sets the value of the absCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAbsCategory(String value) {
        this.absCategory = value;
    }

    /**
     * Gets the value of the absCapital property.
     * 
     */
    public int getAbsCapital() {
        return absCapital;
    }

    /**
     * Sets the value of the absCapital property.
     * 
     */
    public void setAbsCapital(int value) {
        this.absCapital = value;
    }

    /**
     * Gets the value of the absTotalYazTime property.
     * 
     */
    public int getAbsTotalYazTime() {
        return absTotalYazTime;
    }

    /**
     * Sets the value of the absTotalYazTime property.
     * 
     */
    public void setAbsTotalYazTime(int value) {
        this.absTotalYazTime = value;
    }

    /**
     * Gets the value of the absPaysEveryYaz property.
     * 
     */
    public int getAbsPaysEveryYaz() {
        return absPaysEveryYaz;
    }

    /**
     * Sets the value of the absPaysEveryYaz property.
     * 
     */
    public void setAbsPaysEveryYaz(int value) {
        this.absPaysEveryYaz = value;
    }

    /**
     * Gets the value of the absIntristPerPayment property.
     * 
     */
    public int getAbsIntristPerPayment() {
        return absIntristPerPayment;
    }

    /**
     * Sets the value of the absIntristPerPayment property.
     * 
     */
    public void setAbsIntristPerPayment(int value) {
        this.absIntristPerPayment = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
