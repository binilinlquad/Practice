package com.gandan.practice;

import com.gandan.practice.semaphore.BlockingSemaphore;


public class PracticeSemaphore {
    private static BlockingSemaphore blocker = new BlockingSemaphore();

    public static void main(String args[]) {
        Runnable d1 = new Dummy();
        Runnable d2 = new Dummy2();

        System.out.println("Sample BlockingSemaphore is started");


        System.out.println("Blocking by Dummy");
        d1.run();

        System.out.println("Unblock by Dummy2");
    }

    private static class Dummy implements Runnable {
        public Dummy() {
        }

        public void run() {
            blocker.lock();
        }
    }

    public static class Dummy2 implements Runnable {
        public Dummy2() {
        }

        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            blocker.unlock();
        }
    }

}
