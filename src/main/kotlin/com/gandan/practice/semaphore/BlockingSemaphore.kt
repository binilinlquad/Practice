package com.gandan.practice.semaphore

import java.util.concurrent.Semaphore


class BlockingSemaphore {
    internal var sem: Semaphore

    init {
        sem = Semaphore(0)
    }

    fun lock() {
        sem.acquireUninterruptibly()
    }

    fun unlock() {
        sem.release()
    }
}
