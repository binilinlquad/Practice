package com.gandan.practice.rx2

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun main(args: Array<String>) {
    // Observable with thread
    Observable.just(1, 2, 3, 4, 5)
            .doOnNext { System.out.println("just $it " + Thread.currentThread().id) }
            .flatMap { num ->
                Observable.just(num).subscribeOn(Schedulers.newThread())
                        .map {
                            System.out.println(">> start sleep " + Thread.currentThread().id)
                            Thread.sleep((6000 - num * 1000).toLong())
                            System.out.println(">> end sleep " + Thread.currentThread().id)
                            num
                        }
            }
            .observeOn(Schedulers.newThread())
            .subscribe {
                System.out.println("Result $it " + Thread.currentThread().id)
            }

    Thread.sleep(7000)  // wait untill all printed
}