/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practicejava.factory;

import practicejava.factory.product.Product;

/**
 *
 * @author MSCI
 */
public abstract class Factory {
    public void doSomething(){
        //  create product which using method that 
        //  will be overriden by ConcreteFactory
        Product product = factoryMethod();
    }
    
    //  abstract method to create product
    public abstract Product factoryMethod();
}
