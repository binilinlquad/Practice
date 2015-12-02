
package com.gandan.practice.factory.product.number;

import com.gandan.practice.factory.Factory;
import com.gandan.practice.factory.product.Product;


public class NumberFactory extends Factory {
    public Product createMobileNumber(int type){
        return new MobileNumber();
    }
    
    @Override
    public Product factoryMethod() {
        return new MobileNumber();
    }
    
}
