package com.gandan.practice.rx

import rx.Observable
import rx.schedulers.Schedulers

fun main(args: Array<String>) {
    RxUsing().execute()
}

class RxUsing {

     fun execute() {
        Observable.using<Any, Any>({
            System.out.println("resource factory")
            System.out.println(">> Thread id " + Thread.currentThread().id)
            "Func 0"
        }, { s ->
            System.out.println("observable factory")
            System.out.println(">> Thread id " + Thread.currentThread().id)
            Observable.just(s).observeOn(Schedulers.newThread())
        }) { s ->
            System.out.println("dispose")
            System.out.println(">> Thread id " + Thread.currentThread().id)
            System.out.println(s)
        }.subscribe { str ->
            System.out.println("Subscribe $str")
            System.out.println(">> Thread id " + Thread.currentThread().id)
        }
    }
}
