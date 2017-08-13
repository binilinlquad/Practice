package com.gandan.practice.rx2

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun main(args: Array<String>) {
    /**
     * Taken from stackoverflow by David Karnok
     * https://stackoverflow.com/questions/43280248/what-is-the-difference-between-rxjava-2-cancellable-and-disposable
     *
     * [Cancellable is] A functional interface that has a single cancel method that can throw.
     * The Disposable is not a functional interface plus when implementing its dispose() method, you are not allowed to throw checked exceptions.
     */

    val s = Flowable.create<Int>({
        emitter ->
        (1..10).forEach {
        Thread.sleep(1000)
        emitter.onNext(it)
    }
        emitter.setCancellable { System.err.println("Cancelled") }
        (11..15).forEach {
            Thread.sleep(1000)
            emitter.onNext(it)
        }
        emitter.setDisposable(object : Disposable {
            override fun isDisposed(): Boolean {
                System.err.println("IsDisposed?")
                return true
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
    Thread.sleep(20000)
    System.err.println("disposing")
    s.dispose() // if not called, then it wont dispose

}