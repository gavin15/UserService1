/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UserWS;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.ws.WebServiceRef;
import modulewebservices.CurrencyConversionWSService;
import modulewebservices.IOException_Exception;
import modulewebservices.MalformedURLException_Exception;
import modulewebservices.ProtocolException_Exception;
import org.netbeans.xml.schema.userxmlschema.CompanyType;
import org.netbeans.xml.schema.userxmlschema.UserType;
import org.netbeans.xml.schema.userxmlschema.Users;

/**
 *
 * @author gavin
 */
@WebService(serviceName = "UserWS")
public class UserWS {

    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_13797/CurrencyConvertor/CurrencyConversionWSService.wsdl")
    private CurrencyConversionWSService service;

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Create new user.
     */
    @WebMethod(operationName = "CreateUser")
    public void CreateUser(@WebParam(name = "userName") String userName, @WebParam(name = "password") String password, @WebParam(name = "currency") String currency) throws FileNotFoundException {
        Users user = new Users();

        //Unmarshals the file.
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(user.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            user = (Users) unmarshaller.unmarshal(new java.io.File("Users.xml")); //NOI18N
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }

        // Adds the new user information
        UserType user1 = new UserType();
        user1.setUserName(userName);
        user1.setPassword(password);
        user1.setCurrency(currency);
        user1.setAmount(1000);
        user.getUser().add(user1);

        // Marshal back to the file.
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(user.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            OutputStream os = new FileOutputStream("Users.xml");
            marshaller.marshal(user, os);
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
    }

    /**
     * Lists the shares owned by the user.
     */
    @WebMethod(operationName = "ListUserShares")
    public List<CompanyType> ListUserShares(@WebParam(name = "userName") String userName) {
        Users userInfo = new Users();

        //Unmarshal the file.
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(userInfo.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            userInfo = (Users) unmarshaller.unmarshal(new java.io.File("Users.xml")); //NOI18N
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }

        // Find for the requested user details.
        List<CompanyType> companyList = null;
        for (int i = 0; i < userInfo.getUser().size(); i++) {
            if (userInfo.getUser().get(i).getUserName().equalsIgnoreCase(userName)) {
                companyList = userInfo.getUser().get(i).getShares();
            }
        }

        // return the user share list.
        return companyList;
    }

    /**
     * Store the shares purchased by the user against his account.
     */
    @WebMethod(operationName = "BuyUserShare")
    public String BuyUserShare(@WebParam(name = "userName") String userName, @WebParam(name = "companySymbol") String companySymbol, @WebParam(name = "companyName") String companyName, @WebParam(name = "noOfShares") int noOfShares, @WebParam(name = "currency") String currency, @WebParam(name = "sharePrice") float sharePrice) throws FileNotFoundException, IOException_Exception, MalformedURLException_Exception, ProtocolException_Exception {
        Users users = new Users();

        // Unmarshals the file.
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(users.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            users = (Users) unmarshaller.unmarshal(new java.io.File("Users.xml")); //NOI18N
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }

        // Store the share. Create new company if not found or update the existing company.
        boolean found = false;
        for (int i = 0; i < users.getUser().size(); i++) {
            if (users.getUser().get(i).getUserName().equalsIgnoreCase(userName)) {
                for (int j = 0; j < users.getUser().get(i).getShares().size(); j++) {
                    // Update if the company is found under the user.
                    if (users.getUser().get(i).getShares().get(j).getCompanySymbol().equalsIgnoreCase(companySymbol)) {
                        found = true;
                        users.getUser().get(i).getShares().get(j).setSharesPurchased(users.getUser().get(i).getShares().get(j).getSharesPurchased() + noOfShares);                       
                    }
                }
                // If not found, create a new company.
                if (!found) {
                    CompanyType company = new CompanyType();
                    company.setCompanyName(companyName);
                    company.setCompanySymbol(companySymbol);
                    company.setSharesPurchased(noOfShares);
                    users.getUser().get(i).getShares().add(company);
                }
                        // Update the total avaialable amount.
                        double sharesPrice = sharePrice;
                        double totalAmount = Double.valueOf(noOfShares * sharesPrice);
                        double conversionRate = Double.valueOf(getConversionRate(currency, users.getUser().get(i).getCurrency()));
                        float newTotalAmount = (float) (totalAmount * conversionRate);
                        System.out.println(newTotalAmount);
                        users.getUser().get(i).setAmount(users.getUser().get(i).getAmount() - newTotalAmount);
            }
        }

        // Marshal back to the file.
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(users.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //marshaller.marshal(users, System.out);
            OutputStream os = new FileOutputStream("Users.xml");
            marshaller.marshal(users, os);
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }

        return null;
    }

    /**
     * Sell user share back to the market, update the shares under the user.
     */
    @WebMethod(operationName = "SellUserShare")
    public List<CompanyType> SellUserShare(@WebParam(name = "userName") String userName, @WebParam(name = "companyName") String companyName, @WebParam(name = "companySymbol") String companySymbol, @WebParam(name = "noOfShares") int noOfShares, @WebParam(name = "currency") String currency, @WebParam(name = "sharePrice") float sharePrice) throws FileNotFoundException, IOException_Exception, MalformedURLException_Exception, ProtocolException_Exception {
        Users users = new Users();
        
        // unmarshal from the file.
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(users.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            users = (Users) unmarshaller.unmarshal(new java.io.File("Users.xml")); //NOI18N
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
        
        for (int i = 0; i < users.getUser().size(); i++) {
            // Find for the matching user.
            if (users.getUser().get(i).getUserName().equalsIgnoreCase(userName)) {
                for (int j = 0; j < users.getUser().get(i).getShares().size(); j++) {
                    // find for the company.
                    if (users.getUser().get(i).getShares().get(j).getCompanySymbol().equalsIgnoreCase(companySymbol.trim())) {
                        // update the value after selling.
                        users.getUser().get(i).getShares().get(j).setSharesPurchased(users.getUser().get(i).getShares().get(j).getSharesPurchased() - noOfShares);
                        // Update the avaialble price.
                        double sharesPrice = sharePrice;
                        double totalAmount = Double.valueOf(noOfShares * sharesPrice);
                        double conversionRate = Double.valueOf(getConversionRate(currency, users.getUser().get(i).getCurrency()));
                        float newTotalAmount = (float) (totalAmount * conversionRate);
                        users.getUser().get(i).setAmount(users.getUser().get(i).getAmount() + newTotalAmount);
                        
                        // if 0 shares for that company, delete the compant from the list.
                        if (users.getUser().get(i).getShares().get(j).getSharesPurchased() == 0) {
                            users.getUser().get(i).getShares().remove(j);
                        }
                    }
                }
            }
        }

        // marshal back to the file.
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(users.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //marshaller.marshal(users, System.out);
            OutputStream os = new FileOutputStream("Users.xml");
            marshaller.marshal(users, os);
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            System.out.println(ex);
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }

        return null;
    }

    private String getConversionRate(java.lang.String arg0, java.lang.String arg1) throws IOException_Exception, MalformedURLException_Exception, ProtocolException_Exception {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        modulewebservices.CurrencyConversionWS port = service.getCurrencyConversionWSPort();
        return port.getConversionRate(arg0, arg1);
    }

    /**
     * Get available amount of the user.
     */
    @WebMethod(operationName = "GetUserAmount")
    public String GetUserAmount(@WebParam(name = "userName") String userName) {
        Users users = new Users();
        
        //Unmarshal from the file.
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(users.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            users = (Users) unmarshaller.unmarshal(new java.io.File("Users.xml")); //NOI18N
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }

        for (int i = 0; i < users.getUser().size(); i++) {
            // Find for the user and return the amount with the user currency.
            if (users.getUser().get(i).getUserName().equalsIgnoreCase(userName)) {
                return String.valueOf(users.getUser().get(i).getAmount()) + " " + users.getUser().get(i).getCurrency();
            }

        }

        return "0";
    }

    /**
     * Get the user currency, for a particular user.
     */
    @WebMethod(operationName = "GetUserCurrency")
    public String GetUserCurrency(@WebParam(name = "userName") String userName, @WebParam(name = "password") String password) {
        Users users = new Users();
        
        //Unmarshal the file
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(users.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            users = (Users) unmarshaller.unmarshal(new java.io.File("Users.xml")); //NOI18N
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }

        // find for the requested user and return the user currency.
        for (int i = 0; i < users.getUser().size(); i++) {
            if (users.getUser().get(i).getUserName().equalsIgnoreCase(userName.trim())) {
                return users.getUser().get(i).getCurrency();
            }
        }
        return null;
    }

    /**
     * Checks if the user exists.
     */
    @WebMethod(operationName = "CheckUserExists")
    public Boolean CheckUserExists(@WebParam(name = "userName") String userName, @WebParam(name = "password") String password) {
        Users users = new Users();
        
        //Unmarshall the file.
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(users.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            users = (Users) unmarshaller.unmarshal(new java.io.File("Users.xml")); //NOI18N
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
        
        // Check for the requested user in the list. If found, return true; else false.
        for (int i = 0; i < users.getUser().size(); i++) {
            if (users.getUser().get(i).getUserName().equalsIgnoreCase(userName.trim())) {
                return false;
            }
        }
        return true;
    }
}
