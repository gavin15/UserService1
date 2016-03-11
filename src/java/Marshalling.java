
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import org.netbeans.xml.schema.userxmlschema.CompanyType;
import org.netbeans.xml.schema.userxmlschema.PriceType;
import org.netbeans.xml.schema.userxmlschema.UserType;
import org.netbeans.xml.schema.userxmlschema.Users;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gavin
 */
public class Marshalling {
    public static void main(String args[]) throws FileNotFoundException
    {
        Users user  = new Users();
        //List<UserType> userList= user.getUser();
        
        try {
            javax.xml.bind.JAXBContext jaxbCtx = javax.xml.bind.JAXBContext.newInstance(user.getClass().getPackage().getName());
            javax.xml.bind.Unmarshaller unmarshaller = jaxbCtx.createUnmarshaller();
            user = (Users) unmarshaller.unmarshal(new java.io.File("Users.xml")); //NOI18N
        } catch (javax.xml.bind.JAXBException ex) {
            // XXXTODO Handle exception
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.SEVERE, null, ex); //NOI18N
        }
        
        UserType user1 = new UserType();
        user1.setUserName("Gavin12");
        user1.setPassword("password");
        user1.setCurrency("INR1");
        user1.setAmount(1000);
        CompanyType company1 = new CompanyType();
        company1.setCompanyName("TestCompany");
        company1.setCompanySymbol("TC");
        PriceType price = new PriceType();
        price.setValue(100);
        price.setLastUpdate("11-03-2016");
        company1.setSharePrice(price);
        company1.setSharesPurchased(1);
        user1.getShares().add(company1);
        
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
}
