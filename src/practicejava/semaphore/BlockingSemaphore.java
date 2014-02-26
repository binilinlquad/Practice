package practicejava.semaphore;

import java.util.concurrent.Semaphore;

/**
 * Semaphore could be used to block program flow with using 0 semaphore.
 * It tries to mimic lock/unlock but won't check owner of lock (different with lock/unlock),
 * so another thread which didn't call lock could unlock it.
 *
 * User: chandra
 * Date: 2/26/14
 * Time: 5:21 PM
 */
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
