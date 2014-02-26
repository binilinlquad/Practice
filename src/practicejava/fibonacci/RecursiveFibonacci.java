/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practicejava.fibonacci;

/**
 * Recursive Fibonacci implementation
 *
 * @author Chandra
 */
public class RecursiveFibonacci {
    public static void print(int n){
        for(int i=0; i<n; i++){
            System.out.println(fib(i));
        }
    }

    /**
     * Return n-th fibonacci value
     *
     * @param n n-th
     * @return fibonacci value
     */
    private static int fib(int n){
        if(n==2||n==1) {
            return 1;
        } else if(n==0){
            return 0;
        }
        return fib(n-2)+fib(n-1);
    }
}
