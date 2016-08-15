package com.gandan.practice.task

import com.gandan.practice.semaphore.BlockingSemaphore


class TaskSemaphore : ITask {
    private val blocker: BlockingSemaphore

    init {
        blocker = BlockingSemaphore()
    }

    override fun execute() {
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

    inner class Dummy2 : Runnable {

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
