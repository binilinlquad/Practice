package com.gandan.practice.racing

import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

fun main() {

    // uncomment this to simulate race condition
//    measureTimeMillis {
//        repeat(1_000_000) { race() }
//    }

    repeat(1 /* 100 */) {
        // solution using atomic
        val measuredTimeForAtomicSolution = measureTimeMillis {
            repeat(1_000_000) { atomicIntegerForAvoidingRaceCondition() }
        }

        val measuredTimeForLockSolution = measureTimeMillis {
            repeat(1_000_000) { lockForAvoidingRaceCondition() }
        }

        System.out.println("times for atomic integer : $measuredTimeForAtomicSolution and lock object: $measuredTimeForLockSolution")
    }

    // Result of repeating 5 times
//    times for atomic integer : 174363 and lock object: 172566
//    times for atomic integer : 190477 and lock object: 176733
//    times for atomic integer : 196610 and lock object: 179015
//    times for atomic integer : 174998 and lock object: 178198
//    times for atomic integer : 176344 and lock object: 181678

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
