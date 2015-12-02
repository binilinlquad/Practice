package com.gandan.practice.task;

import com.gandan.practice.semaphore.BlockingSemaphore;


public class TaskSemaphore implements ITask {
    private BlockingSemaphore blocker;

    public TaskSemaphore() {
        blocker = new BlockingSemaphore();
    }

    @Override
    public void execute() {
        Runnable d1 = new Dummy();
        Runnable d2 = new Dummy2();

        System.out.println("Sample BlockingSemaphore is started");


        System.out.println("Blocking by Dummy");
        d1.run();

        d2.run();
        System.out.println("Unblock by Dummy2");
    }

    private class Dummy implements Runnable {
        public Dummy() {
        }

        public void run() {
            blocker.lock();
        }
    }

    public class Dummy2 implements Runnable {
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
