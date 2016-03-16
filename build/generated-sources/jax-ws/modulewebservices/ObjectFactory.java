
package modulewebservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the modulewebservices package. 
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

    private final static QName _GetConversionRate_QNAME = new QName("http://ModuleWebServices/", "GetConversionRate");
    private final static QName _GetConversionRateResponse_QNAME = new QName("http://ModuleWebServices/", "GetConversionRateResponse");
    private final static QName _GetCurrencyCodes_QNAME = new QName("http://ModuleWebServices/", "GetCurrencyCodes");
    private final static QName _GetCurrencyCodesResponse_QNAME = new QName("http://ModuleWebServices/", "GetCurrencyCodesResponse");
    private final static QName _IOException_QNAME = new QName("http://ModuleWebServices/", "IOException");
    private final static QName _MalformedURLException_QNAME = new QName("http://ModuleWebServices/", "MalformedURLException");
    private final static QName _ProtocolException_QNAME = new QName("http://ModuleWebServices/", "ProtocolException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: modulewebservices
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetConversionRate }
     * 
     */
    public GetConversionRate createGetConversionRate() {
        return new GetConversionRate();
    }

    /**
     * Create an instance of {@link GetConversionRateResponse }
     * 
     */
    public GetConversionRateResponse createGetConversionRateResponse() {
        return new GetConversionRateResponse();
    }

    /**
     * Create an instance of {@link GetCurrencyCodes }
     * 
     */
    public GetCurrencyCodes createGetCurrencyCodes() {
        return new GetCurrencyCodes();
    }

    /**
     * Create an instance of {@link GetCurrencyCodesResponse }
     * 
     */
    public GetCurrencyCodesResponse createGetCurrencyCodesResponse() {
        return new GetCurrencyCodesResponse();
    }

    /**
     * Create an instance of {@link IOException }
     * 
     */
    public IOException createIOException() {
        return new IOException();
    }

    /**
     * Create an instance of {@link MalformedURLException }
     * 
     */
    public MalformedURLException createMalformedURLException() {
        return new MalformedURLException();
    }

    /**
     * Create an instance of {@link ProtocolException }
     * 
     */
    public ProtocolException createProtocolException() {
        return new ProtocolException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConversionRate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ModuleWebServices/", name = "GetConversionRate")
    public JAXBElement<GetConversionRate> createGetConversionRate(GetConversionRate value) {
        return new JAXBElement<GetConversionRate>(_GetConversionRate_QNAME, GetConversionRate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConversionRateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ModuleWebServices/", name = "GetConversionRateResponse")
    public JAXBElement<GetConversionRateResponse> createGetConversionRateResponse(GetConversionRateResponse value) {
        return new JAXBElement<GetConversionRateResponse>(_GetConversionRateResponse_QNAME, GetConversionRateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCurrencyCodes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ModuleWebServices/", name = "GetCurrencyCodes")
    public JAXBElement<GetCurrencyCodes> createGetCurrencyCodes(GetCurrencyCodes value) {
        return new JAXBElement<GetCurrencyCodes>(_GetCurrencyCodes_QNAME, GetCurrencyCodes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCurrencyCodesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ModuleWebServices/", name = "GetCurrencyCodesResponse")
    public JAXBElement<GetCurrencyCodesResponse> createGetCurrencyCodesResponse(GetCurrencyCodesResponse value) {
        return new JAXBElement<GetCurrencyCodesResponse>(_GetCurrencyCodesResponse_QNAME, GetCurrencyCodesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IOException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ModuleWebServices/", name = "IOException")
    public JAXBElement<IOException> createIOException(IOException value) {
        return new JAXBElement<IOException>(_IOException_QNAME, IOException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MalformedURLException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ModuleWebServices/", name = "MalformedURLException")
    public JAXBElement<MalformedURLException> createMalformedURLException(MalformedURLException value) {
        return new JAXBElement<MalformedURLException>(_MalformedURLException_QNAME, MalformedURLException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProtocolException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ModuleWebServices/", name = "ProtocolException")
    public JAXBElement<ProtocolException> createProtocolException(ProtocolException value) {
        return new JAXBElement<ProtocolException>(_ProtocolException_QNAME, ProtocolException.class, null, value);
    }

}
