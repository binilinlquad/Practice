
package com.gandan.practice.factory;

import com.gandan.practice.factory.product.Product;
import com.gandan.practice.factory.product.ConcreteProduct;

public class ConcreteFactory extends Factory {

    @Override
    public Product factoryMethod() {

        return (Product) new ConcreteProduct(); 
    }
    
}
