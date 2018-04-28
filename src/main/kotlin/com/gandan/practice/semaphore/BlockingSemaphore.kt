package com.gandan.practice.semaphore

import java.util.concurrent.Semaphore

fun main(args: Array<String>) {
    TestSemaphore().execute()
}

class TestSemaphore {
    private val blocker: BlockingSemaphore

    init {
        blocker = BlockingSemaphore()
    }

    fun execute() {
        val d1 = Dummy()
        val d2 = Dummy2()

        println("Sample BlockingSemaphore is started")


        println("Blocking by Dummy")
        d1.run()

        d2.run()
        println("Unblock by Dummy2")
    }

    private inner class Dummy : Runnable {

        override fun run() {
            blocker.lock()
        }
    }

    private inner class Dummy2 : Runnable {

        override fun run() {
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            blocker.unlock()
        }
    }

}

class BlockingSemaphore {
    private var sem: Semaphore = Semaphore(0)

    fun lock() {
        sem.acquireUninterruptibly()
    }

    fun unlock() {
        sem.release()
    }
}
