
package com.gandan.practice.factory;

import com.gandan.practice.factory.product.Product;


public abstract class Factory {
    public void doSomething(){


        Product product = factoryMethod();
    }
    

    public abstract Product factoryMethod();
}
