package com.gandan.practice.racing

import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger

fun main() {

    for (i in 1..1_000_000) {
        // race()
//        atomicIntegerForAvoidingRaceCondition()
        lockForAvoidingRaceCondition()
    }
}

private fun race() {
    var counter = 0
    val ready = CountDownLatch(1)
    val finish = CountDownLatch(2)  // as we are using 2 thread

    val updateOperation = Runnable {
        ready.await()
        counter += 1
        finish.countDown()
    }

    val threadA = Thread(updateOperation)
    val threadB = Thread(updateOperation)

    threadA.start()
    threadB.start()

    ready.countDown()
    finish.await()

    if (counter == 2) {
        System.out.println("counter value 2 is valid")
    } else {
        throw IllegalStateException("counter should be 2 but real value is $counter")
    }
}

private fun atomicIntegerForAvoidingRaceCondition() {
    val counter = AtomicInteger(0)
    val ready = CountDownLatch(1)
    val finish = CountDownLatch(2)  // as we are using 2 thread

    val updateOperation = Runnable {
        ready.await()
        counter.getAndIncrement()
        finish.countDown()
    }

    val threadA = Thread(updateOperation)
    val threadB = Thread(updateOperation)

    threadA.start()
    threadB.start()

    ready.countDown()
    finish.await()

    if (counter.get() == 2) {
        System.out.println("counter value 2 is valid")
    } else {
        throw IllegalStateException("counter should be 2 but real value is $counter")
    }
}

private fun lockForAvoidingRaceCondition() {
    val lock = Object()
    var counter = 0
    val ready = CountDownLatch(1)
    val finish = CountDownLatch(2)  // as we are using 2 thread

    val updateOperation = Runnable {
        ready.await()
        synchronized(lock) {
            ++counter
        }
        finish.countDown()
    }

    val threadA = Thread(updateOperation)
    val threadB = Thread(updateOperation)

    threadA.start()
    threadB.start()

    ready.countDown()
    finish.await()

    if (counter == 2) {
        System.out.println("counter value 2 is valid")
    } else {
        throw IllegalStateException("counter should be 2 but real value is $counter")
    }
}
