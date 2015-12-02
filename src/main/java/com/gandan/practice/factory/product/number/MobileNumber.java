
package com.gandan.practice.factory.product.number;


public class MobileNumber extends Number {
    private String countryCode; 
    private String phoneNumber;

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
