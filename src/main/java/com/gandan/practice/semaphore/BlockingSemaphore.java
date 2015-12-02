package com.gandan.practice.semaphore;

import java.util.concurrent.Semaphore;


public class BlockingSemaphore {
    Semaphore sem;

    public BlockingSemaphore(){
        sem = new Semaphore(0);
    }

    public void lock(){
         sem.acquireUninterruptibly();
    }

    public void unlock(){
         sem.release();
    }
}
