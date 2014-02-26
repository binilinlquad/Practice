package practicejava;

import practicejava.semaphore.BlockingSemaphore;

/**
 * Sample blocking using Semaphore
 *
 * User: chandra
 * Date: 2/26/14
 * Time: 5:27 PM
 */
public class PracticeSemaphore {
    private static BlockingSemaphore blocker = new BlockingSemaphore();

    public static void main(String args[]) {
        Runnable d1 = new Dummy();
        Runnable d2 = new Dummy2();

        System.out.println("Sample BlockingSemaphore is started");

        //d2.run();  // uncomment this for unlock

        System.out.println("Blocking by Dummy");
        d1.run();

        System.out.println("Unblock by Dummy2");
    }

    private static class Dummy implements Runnable {
        public Dummy(){
            // do nothing
        }
        public void run(){
            // System.out.println("This is runnable which will block");
            blocker.lock();
        }
    }

    public static class Dummy2 implements Runnable {
        public Dummy2(){
            // do nothing
        }

        public void run(){
            // System.out.println("This is runnable which will unlock");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            blocker.unlock();
        }
    }

}
