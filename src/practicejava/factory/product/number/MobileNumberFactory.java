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
public class MobileNumberFactory extends Factory {

    @Override
    public Product factoryMethod() {
        return new MobileNumber();
    }
    
}
