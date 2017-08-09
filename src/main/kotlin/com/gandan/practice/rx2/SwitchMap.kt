package com.gandan.practice.rx2

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    val pubsub = PublishSubject.create<Long>()
    val flowable = pubsub.toFlowable(BackpressureStrategy.ERROR)

    Flowable.interval(1, TimeUnit.SECONDS)
            .doOnNext({System.out.println("next: $it")})
            .switchMap { num -> flowable.map { num } }
            .subscribeOn(Schedulers.newThread())
            .subscribe {
                System.out.println("subscribe:$it")
            }

    Thread.sleep(5000)
    pubsub.onNext(1)    // it will trigger print 0
    pubsub.onComplete() // complete won't stop source observables

    Thread.sleep(5000) // wait 5 seconds for look the result

}