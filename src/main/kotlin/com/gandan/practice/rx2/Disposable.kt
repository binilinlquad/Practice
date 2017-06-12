package com.gandan.practice.rx2

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Cancel method is called at the end of emit evntes before dispose happened.
 * Dispose only happened when dispose() called.
 */
fun main(args: Array<String>) {
    val s = Flowable.create<Int>({
        emitter -> (1..10).forEach {
        emitter.onNext(it) }
        emitter.setCancellable { System.err.println("Cancelled") }
        emitter.setDisposable(object : Disposable {
            override fun isDisposed(): Boolean {
                System.err.println("IsDisposed?")
                return false
            }

            override fun dispose() {
                System.err.println("disposed")
            }

        })
    }, BackpressureStrategy.ERROR)
            .subscribeOn(Schedulers.newThread())
            .subscribe{ it ->
        System.err.println(it)
    }

    System.err.println("Sleep 2000")
    Thread.sleep(2000)
    System.err.println("disposing")
    s.dispose()

}