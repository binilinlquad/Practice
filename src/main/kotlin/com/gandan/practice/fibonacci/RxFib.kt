package com.gandan.practice.fibonacci

import com.gandan.practice.common.log
import rx.Observable
import rx.Subscriber

fun main(args: Array<String>) {
    Observable.just(10, 2).lift(OperatorFib()).take(3)
            .subscribe { System.err.println(it) }
}

internal class OperatorFib() : Observable.Operator<Int, Int> {

    override fun call(child: Subscriber<in Int>?): Subscriber<in Int> {
        val parent = object: Subscriber<Int>() {
            override fun onCompleted() {
                child?.onCompleted()
            }

            override fun onNext(step: Int?) {
                if (!isUnsubscribed() && step != null) {
                    var first = 0
                    var second = 1
                    (0..step-1).forEach {
                        child?.onNext(first)
                        first = first + second
                        second = first - second
                    }
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