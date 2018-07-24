package com.gandan.practice.rx2

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Publisher
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    // I deliberately set it to two
    val wantedThread = Math.min(java.lang.Runtime.getRuntime().availableProcessors(), 2)
    System.out.println("Wanted Thread amunt $wantedThread")
    val poolScheduler = Schedulers.from(Executors.newFixedThreadPool(wantedThread))

    /* Pool Scheduler is to control how many works together, for example if you have Observable a and Observable b
    * and those observables subsribeOn same pool, then if it will work together as long there is idle thread.
    * So, it doesn't mean you can subscribe or observe on more than one thread in one stream if you are using
    * thread pool.
    * If you really want parallelism in RxJava2, check https://github.com/ReactiveX/RxJava/wiki/Parallel-flows */

    Observable.fromPublisher<Int>(infiniteNumber())
            .take(10)
            .subscribeOn(poolScheduler)
            .subscribe {
                System.out.println("> $it ${Thread.currentThread()}")
                TimeUnit.MILLISECONDS.sleep(500)

            }

    Observable.fromPublisher<Int>(infiniteNumber())
            .take(10)
            .subscribeOn(poolScheduler)
            .subscribe {
                System.out.println(">> $it ${Thread.currentThread()}")
                TimeUnit.SECONDS.sleep(1)

            }

    /**
     * This stream will wait until existing thread is < number I set above
     */
    Observable.fromPublisher<Int>(infiniteNumber())
            .take(10)
            .subscribeOn(poolScheduler)
            .subscribe {
                System.out.println(">>> $it ${Thread.currentThread()}")
                TimeUnit.SECONDS.sleep(1)

            }

    TimeUnit.SECONDS.sleep(25)
}

private fun infiniteNumber(): Publisher<Int> {
    return Publisher<Int> { s ->
        val random = Random(System.currentTimeMillis())
        var num = 0
        while (true) {
            s.onNext(++num)
            TimeUnit.MILLISECONDS.sleep(random.nextInt(100).toLong())
        }
    }
}