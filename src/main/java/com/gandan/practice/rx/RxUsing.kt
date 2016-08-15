package com.gandan.practice.rx

import com.gandan.practice.task.ITask
import com.sun.glass.ui.SystemClipboard
import rx.Observable
import rx.functions.Action1
import rx.functions.Func0
import rx.functions.Func1
import rx.schedulers.Schedulers

/**
 * Looking for Using operation in RxJava
 * Created by chandra on 12/5/15.
 */
class RxUsing : ITask {

    override fun execute() {
        Observable.using<Any, Any>({
            System.out.println("Func 0")
            System.out.println(">> Thread id " + Thread.currentThread().id)
            "Func 0"
        }, { s ->
            System.out.println("Func 1")
            System.out.println(">> Thread id " + Thread.currentThread().id)
            Observable.just(s).observeOn(Schedulers.newThread())
        }) { s ->
            System.out.println("Action 1")
            System.out.println(">> Thread id " + Thread.currentThread().id)
            System.out.println(s)
        }.subscribe { str ->
            System.out.println("String " + str)
            System.out.println(">> Thread id " + Thread.currentThread().id)
        }
    }
}
