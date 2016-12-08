package com.gandan.practice.common

import io.reactivex.Flowable
import io.reactivex.Observable


fun <T> Flowable<T>.log() : Flowable<T> {
    return materialize().doOnNext {
        if (it.isOnNext) System.out.println(it.value)
        else if (it.isOnError) System.err.println(it.value)
        else if (it.isOnComplete) System.out.println("Completed")
    }
            .dematerialize<T>()
}

fun <T> Observable<T>.log() : Observable<T> {
    return materialize().doOnNext {
        if (it.isOnNext) System.out.println(it.value)
        else if (it.isOnError) System.err.println(it.value)
        else if (it.isOnComplete) System.out.println("Completed")
    }
            .dematerialize<T>()
}