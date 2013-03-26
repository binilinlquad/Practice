/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practicejava.factory.product.number;

/**
 *
 * @author MSCI
 */
public class MobileNumber extends Number {
    private String countryCode; 
    private String phoneNumber;
   
    //  default constructor always exist and will be used because we didn't 
    //  need to set parameterized constructor. If we want to use parameterized
    //  constructor, we need to override default constructor too
    public void setCountryCode(String countryCode){
        this.countryCode = countryCode;
    }
    
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    
    @Override
    public long getNumber() {
        return Long.parseLong(this.countryCode+ this.phoneNumber);
    }
    
}
