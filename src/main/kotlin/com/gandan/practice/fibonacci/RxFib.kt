package com.gandan.practice.fibonacci

import rx.Observable
import rx.Subscriber
import rx.functions.Func1

fun main(args: Array<String>) {
    // print fibronnaci series using custom operator
    Observable
            .range(0, 20)
            .take(100)
            .lift(OperatorFib())
            .subscribe { System.out.println("Use Operator $it") }

    // print fibronnaci series using mapper
    Observable
            .range(0, 100)
            .take(20)
            .map(MapToFibonacci())
            .subscribe {
                System.out.println("Use Mapper $it")
            }
}

// this class will map each call with next fibonacci
// input will be ignored
private class MapToFibonacci : Func1<Int, Int> {
    var first = 0
    var second = 1

    override fun call(t: Int): Int {
        val curr = first
        first = second
        second += curr
        return curr
    }
}

// this is experiment with Operator. For proper implementation,
// I suggest to check take operator
private class OperatorFib() : Observable.Operator<Int, Int> {
    override fun call(child: Subscriber<in Int>?): Subscriber<in Int> {
        val parent = object : Subscriber<Int>() {
            var first = 0
            var second = 1

            override fun onCompleted() {
                child?.onCompleted()
            }

            override fun onNext(step: Int?) {
                if (!isUnsubscribed && step != null) {
                    child?.onNext(first)
                    val curr = first
                    first = second
                    second += curr
                }
            }

            override fun onError(e: Throwable?) {
                try {
                    child?.onError(e)
                } finally {
                    unsubscribe()
                }
            }

        }

        child?.add(parent)

        return parent
    }

}