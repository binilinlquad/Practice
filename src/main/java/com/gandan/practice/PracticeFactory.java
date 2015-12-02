package com.gandan.practice;

import com.gandan.practice.factory.product.number.MobileNumber;
import com.gandan.practice.factory.product.number.NumberFactory;


public class PracticeFactory {

    private static NumberFactory mobileNumberFactory = new NumberFactory();
    
    public static void main(String[] args) {

        MobileNumber mn = (MobileNumber) mobileNumberFactory.factoryMethod(); 
        mn.setCountryCode("021");
        mn.setPhoneNumber("6878743");
        System.out.println("Mobile Number : " + mn.getNumber());
    }
}
