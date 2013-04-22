/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practicejava;

import practicejava.factory.product.number.MobileNumber;
import practicejava.factory.product.number.NumberFactory;

/**
 *
 * @author MSCI
 */
public class PracticeFactory {
    //  sample singleton
    private static NumberFactory mobileNumberFactory = new NumberFactory();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        MobileNumber mn = (MobileNumber) mobileNumberFactory.factoryMethod(); 
        mn.setCountryCode("021");
        mn.setPhoneNumber("6878743");
        System.out.println("Mobile Number : " + mn.getNumber());
    }
}
