/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practicejava.factory;

import practicejava.factory.product.Product;
import practicejava.factory.product.ConcreteProduct;
/**
 *
 * @author MSCI
 */
public class ConcreteFactory extends Factory {

    @Override
    public Product factoryMethod() {
        // the original create object of product
        return (Product) new ConcreteProduct(); 
    }
    
}
