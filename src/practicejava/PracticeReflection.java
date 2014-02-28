/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practicejava;

import practicejava.reflection.SampleClassReflection;
/**
 *
 * @author MSCI
 */
public class PracticeReflection {
    public static void main(String[] args){
        SampleClassReflection sample = new SampleClassReflection(String.class);
        sample.displayClassnameAtRuntime();
        sample.dipslayModifier();

        System.out.print("Generate Empty String with newInstance");
        String emptyString = (String)sample.newInstance();
        System.out.println(" " + "".equals(emptyString));
    }
}
