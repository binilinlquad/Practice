/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practicejava.factory.product.number;

import practicejava.factory.Factory;
import practicejava.factory.product.Product;

/**
 *
 * @author MSCI
 */
public class NumberFactory extends Factory {
    public Product createMobileNumber(int type){
        return new MobileNumber();
    }
    
    @Override
    public Product factoryMethod() {
        return new MobileNumber();
    }
    
}
