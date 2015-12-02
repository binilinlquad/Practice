
package com.gandan.practice.factory.product.number;

import com.gandan.practice.factory.Factory;
import com.gandan.practice.factory.product.Product;


public class MobileNumberFactory extends Factory {

    @Override
    public Product factoryMethod() {
        return new MobileNumber();
    }
    
}
