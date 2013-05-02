/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practicejava.fibonnaci;

/**
 *
 * @author MSCI
 */
public class Fibonnaci {
    public static void print(int n){
        for(int i=0; i<n; i++){
            System.out.println(fib(i));
        }
    }
    
    public static int fib(int n){
        int a=0, b=1;
        if(n==2||n==1) {
            return 1;
        } else if(n==0){
            return 0;
        }
        return fib(n-2)+fib(n-1);
    }
}
