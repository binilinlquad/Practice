package com.gandan.practice.coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.*
import java.util.concurrent.ArrayBlockingQueue
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun main() {
    runBlocking {
        log("Start blocking coroutine")
        val queue = NaiveSuspendingQueue<Int>(2)
        // use async to let take method keep working
        // because add/take method is blocking when queue is full/empty
        // interestingly, even though we use same Coroutine Context
        // but suspend coroutine (look NaiveSuspendingQueue implementation)
        // not blocking other coroutine (async)
        val printer = async(coroutineContext) {
            delay(1000)
            while (true) {
                // it will block async coroutine when queue empty
                val num = queue.take()
                log("Number is $num")
                delay(100)
            }
        }

        queue.apply {
            add(1)
            add(2)
            // it will block because coroutine is full
            add(3)
            delay(100)
            add(4)

            // to let printer take when queue is empty
            delay(1000)
            add(5)

        }

        delay(5000)

        printer.cancel()
    }

}

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

/**
 * Copy from http://blog.pronghorn.tech/optimizing-suspending-functions-in-kotlin/
 * with small modification to make it compileable
 * for my learning of how coroutine is working
 */

interface SuspendingQueue<T> {
    suspend fun take(): T
    suspend fun add(value: T): Boolean
}

class NaiveSuspendingQueue<T>(capacity: Int) : SuspendingQueue<T> {
    private val underlying: Queue<T> = ArrayBlockingQueue(capacity)
    private var fullWaiter: Continuation<T>? = null
    private var emptyWaiter: Continuation<T>? = null

    override suspend fun add(value: T): Boolean {
        val emptyWaiter = this.emptyWaiter
        if (emptyWaiter != null) {
            this.emptyWaiter = null

            log("""There was TAKE request when queue is EMPTY""")

            // continue suspended emptyWaiter, which is in take method
            // value will be returned to suspendResult in that method
            emptyWaiter.resume(value)
        }
        else {
            while (!underlying.offer(value)) {
                log("Queue is full in ADD $value, Will SUSPEND")
                suspendCoroutine<T> { continuation -> fullWaiter = continuation }
                log("Queue is not full now, try to ADD again $value")
            }
        }
        return true
    }

    override suspend fun take(): T {
        val result = underlying.poll()
        if (result != null) {
            val fullWaiter = this.fullWaiter
            if (fullWaiter != null) {
                this.fullWaiter = null
                log("""There was ADD request when queue was FULL before.""")

                // continue suspended fullWaiter, which is in add method
                // value won't be used in that method, but it will continue looping which is block before
                fullWaiter.resume(result)
            }

            log("Return poll result $result")
            return result
        }
        else {
            val suspendResult = suspendCoroutine<T> { continuation -> emptyWaiter = continuation }

            log("Continuation of TAKE after queue NOT EMPTY now with result $suspendResult")
            return suspendResult
        }
    }
}