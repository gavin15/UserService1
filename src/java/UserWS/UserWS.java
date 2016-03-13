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
import org.netbeans.xml.schema.userxmlschema.CompanyType;
import org.netbeans.xml.schema.userxmlschema.PriceType;
import org.netbeans.xml.schema.userxmlschema.UserType;
import org.netbeans.xml.schema.userxmlschema.Users;

/**
 *
 * @author gavin
 */
@WebService(serviceName = "UserWS")
public class UserWS {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "CreateUser")
    public void CreateUser(@WebParam(name = "userName") String userName,@WebParam(name = "password") String password, @WebParam(name = "currency") String currency) throws FileNotFoundException {
        Users user  = new Users();
        //List<UserType> userList= user.getUser();
        
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(user.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            user =   (Users) unmarshaller.unmarshal(new java.io.File("Users.xml")); //NOI18N
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
        
        
        
        
        UserType user1 = new UserType();
        user1.setUserName(userName);
        user1.setPassword(password);
        user1.setCurrency(currency);
        user1.setAmount(1000);
        user.getUser().add(user1);
        
        try {            
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(user.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //marshaller.marshal(userList, System.out);
            OutputStream os = new FileOutputStream( "Users.xml" );
            marshaller.marshal( user, os );
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "ListUserShares")
    public List<CompanyType> ListUserShares(@WebParam(name = "userName") String userName) {
        Users userInfo = new Users();
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(userInfo.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            userInfo = (Users) unmarshaller.unmarshal(new java.io.File("Users.xml")); //NOI18N
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
        System.out.println("UserWS.UserWS.ListShares()");
         List<CompanyType> companyList = null;
        for (int i = 0; i < userInfo.getUser().size(); i++) {
            System.out.println("UserWS.UserWS.ListShares()Hello");
            if (userInfo.getUser().get(i).getUserName().equalsIgnoreCase(userName)) {      
                System.out.println("UserWS.UserWS.ListShares()");
                companyList= userInfo.getUser().get(i).getShares();
            }
        }
        
        return companyList;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "BuyUserShare")
    public String BuyUserShare(@WebParam(name = "userName") String userName, @WebParam(name = "companySymbol") String companySymbol, @WebParam(name = "companyName") String companyName, @WebParam(name = "noOfShares") int noOfShares) throws FileNotFoundException {
        Users users = new Users();
        
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(users.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            users = (Users) unmarshaller.unmarshal(new java.io.File("Users.xml")); //NOI18N
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
        
        boolean found=false;
        for (int i = 0; i < users.getUser().size(); i++) {
            if (users.getUser().get(i).getUserName().equalsIgnoreCase(userName)) {
                   for (int j = 0; j < users.getUser().get(i).getShares().size(); j++) {
                       if (users.getUser().get(i).getShares().get(j).getCompanySymbol().equalsIgnoreCase(companySymbol)) {
                           found=true;
                           users.getUser().get(i).getShares().get(j).setSharesPurchased(users.getUser().get(i).getShares().get(j).getSharesPurchased()+noOfShares);
                       }
                }
                   if (!found) {
                            CompanyType company = new CompanyType();
                           company.setCompanyName(companyName);
                           company.setCompanySymbol(companySymbol);
                           company.setSharesPurchased(noOfShares);
                           users.getUser().get(i).getShares().add(company);
                }
            }
        }
        
        try {            
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(users.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //marshaller.marshal(users, System.out);
            OutputStream os = new FileOutputStream( "Users.xml" );
            marshaller.marshal( users, os );
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
        
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "SellUserShare")
    public List<CompanyType> SellUserShare(@WebParam(name = "userName") String userName, @WebParam(name = "companyName") String companyName, @WebParam(name = "companySymbol") String companySymbol, @WebParam(name = "noOfShares") int noOfShares) throws FileNotFoundException {
        System.out.println("hi1");
        Users users = new Users();
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(users.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            users = (Users) unmarshaller.unmarshal(new java.io.File("Users.xml")); //NOI18N
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
        for (int i = 0; i < users.getUser().size(); i++) {
            if (users.getUser().get(i).getUserName().equalsIgnoreCase(userName)) {
                 System.out.println("i1");
                   for (int j = 0; j < users.getUser().get(i).getShares().size(); j++) {
                       if (users.getUser().get(i).getShares().get(j).getCompanySymbol().equalsIgnoreCase(companySymbol.trim())) {
                           System.out.println("i2");
                           users.getUser().get(i).getShares().get(j).setSharesPurchased(users.getUser().get(i).getShares().get(j).getSharesPurchased()-noOfShares);
                           if (users.getUser().get(i).getShares().get(j).getSharesPurchased()==0) {
                                System.out.println("i3");
                               users.getUser().get(i).getShares().remove(j);
                           }
                       }
                }
            }
        }
        
        try {            
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(users.getClass().getPackage().getName());
            javax.xml.bind.Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
            marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            //marshaller.marshal(users, System.out);
            OutputStream os = new FileOutputStream( "Users.xml" );
            marshaller.marshal( users, os );
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            System.out.println(ex);
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
        
        return null;
    }
}
