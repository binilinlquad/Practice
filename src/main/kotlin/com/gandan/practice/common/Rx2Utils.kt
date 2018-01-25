package com.gandan.practice.common

import io.reactivex.Flowable
import io.reactivex.Observable


fun <T> Flowable<T>.log(tag: String="") : Flowable<T> {
    return this.doOnNext { System.out.println("$tag onNext: $it") }
            .doOnError { System.err.println("$tag onError $it") }
            .doOnComplete { System.out.println("$tag Completed")}
            .doOnSubscribe { System.out.println("$tag Subscribed") }
            .doOnTerminate { System.out.println("$tag Terminated") }
}

fun <T> Observable<T>.log(tag: String = "") : Observable<T> {
    return this.doOnNext { System.out.println("$tag onNext: $it") }
            .doOnError { System.err.println("$tag onError $it") }
            .doOnComplete { System.out.println("$tag Completed")}
            .doOnSubscribe { System.out.println("$tag Subscribed") }
            .doOnTerminate { System.out.println("$tag Terminated") }
            .doOnDispose { System.out.println("$tag Disposed") }
}