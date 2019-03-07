package com.gandan.practice.rx2

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.sql.Time
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    val numThread = Runtime.getRuntime().availableProcessors()
    val subscribers = (1..numThread).mapIndexed { index, i ->
        object : Subscriber<Int> {
            override fun onComplete() {
                System.out.println("[$i]complete")
            }

            override fun onSubscribe(s: Subscription?) {
                System.out.println("[$i] onSubscribe")
                s?.request(Long.MAX_VALUE)
            }

            override fun onNext(t: Int?) {
                System.out.println("[$i] next $t in ${Thread.currentThread()}")
            }

            override fun onError(t: Throwable?) {
                System.out.println("[$i] error $t")
            }
        }
    }.toTypedArray()

    Flowable.range(1, 100)
            .parallel(numThread)    // parallel itself will run on current thread if not using runOn
            .runOn(Schedulers.newThread())  // should use runOn to decide in what thread operation is running
            .subscribe(subscribers)

    TimeUnit.SECONDS.sleep(100)
}