package com.gandan.practice.task;

import com.gandan.practice.factory.product.number.MobileNumber;
import com.gandan.practice.factory.product.number.NumberFactory;


public class TaskFactory implements ITask {

    private NumberFactory mobileNumberFactory;

    public TaskFactory() {
        mobileNumberFactory = new NumberFactory();
    }

    @Override
    public void execute() {
        MobileNumber mn = (MobileNumber) mobileNumberFactory.factoryMethod();
        mn.setCountryCode("021");
        mn.setPhoneNumber("6878743");
        System.out.println("Mobile Number : " + mn.getNumber());
    }
}
