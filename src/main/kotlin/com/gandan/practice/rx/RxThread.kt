package com.gandan.practice.rx

import rx.Observable
import rx.functions.Action0
import rx.schedulers.Schedulers
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    RxThread().execute()
}

class RxThread {

    fun execute() {
        val actions = arrayOf<Action0>(PlainObservableTest(), SubscribeOnObservableTest(), SubscribeAndObserveOnObervableTest(), ObserveOnObservabeTest(), SubscribeFromTest())
        actions.forEach {
            it.call()
        }
    }

    private abstract inner class LatchTask : Action0 {
        override fun call() {
            try {
                invokeExecution()
            } catch (e: InterruptedException) {
                System.out.println("Test ${RxThread::class.simpleName} is error")
            }
        }

        abstract fun invokeExecution()
    }

    private inner class PlainObservableTest : LatchTask() {

        override fun invokeExecution() {
            val latch = CountDownLatch(1)

            print("=== Plain Observable ==== on thread ", currentThread)
            Observable.create(Observable.OnSubscribe<Int> { subscriber ->
                print("OnSubscribe call on ", currentThread)
                subscriber.onStart()
                subscriber.onNext(1)
                subscriber.onCompleted()
            }).subscribe({ value -> print("onNext $value On ", currentThread) },
                    { error -> print("onError $error on ", currentThread) }
            ) {
                print("Complete on ", currentThread)
                latch.countDown()
            }

            print("=== Finish Test === on thread ", currentThread)

            latch.await(500, TimeUnit.MILLISECONDS)
        }
    }

    private inner class SubscribeOnObservableTest : LatchTask() {

        override fun invokeExecution() {
            val latch = CountDownLatch(1)

            print("=== SubscribeOn Observable ==== on thread ", currentThread)
            Observable.create(Observable.OnSubscribe<Int> { subscriber ->
                print("OnSubscribe call on ", currentThread)
                subscriber.onStart()
                subscriber.onNext(1)
                subscriber.onCompleted()
            }).subscribeOn(Schedulers.newThread()).subscribe({ value -> print("onNext $value On ", currentThread) },
                    { error -> print("onError $error on ", currentThread) }
            ) {
                print("Complete on ", currentThread)
                latch.countDown()
            }

            print("=== Finish Test === on thread ", currentThread)

            latch.await(500, TimeUnit.MILLISECONDS)

        }
    }

    private inner class ObserveOnObservabeTest : LatchTask() {

        override fun invokeExecution() {
            val latch = CountDownLatch(1)

            print("=== SubscribeOn Observable ==== on thread ", currentThread)
            Observable.create(Observable.OnSubscribe<Int> { subscriber ->
                print("OnSubscribe call on ", currentThread)
                subscriber.onStart()
                subscriber.onNext(1)
                subscriber.onCompleted()
            }).observeOn(Schedulers.newThread()).subscribe({ value -> print("onNext $value On ", currentThread) },
                    { error -> print("onError $error on ", currentThread) }
            ) {
                print("Complete on ", currentThread)
                latch.countDown()
            }

            print("=== Finish Test === on thread ", currentThread)

            latch.await(500, TimeUnit.MILLISECONDS)
        }
    }

    private inner class SubscribeAndObserveOnObervableTest : LatchTask() {

        override fun invokeExecution() {
            val latch = CountDownLatch(1)

            print("=== SubscribeOn ObservableOn ==== on thread ", currentThread)
            Observable.create(Observable.OnSubscribe<Int> { subscriber ->
                print("OnSubscribe call on ", currentThread)
                subscriber.onStart()
                subscriber.onNext(1)
                subscriber.onCompleted()
            }).subscribeOn(Schedulers.newThread())
                    .observeOn(Schedulers.newThread())
                    .subscribe({ value -> print("onNext $value On ", currentThread) },
                            { error -> print("onError $error on ", currentThread) }
                    ) {
                        print("Complete on ", currentThread)
                        latch.countDown()
                    }

            print("=== Finish Test === on thread ", currentThread)

            latch.await(500, TimeUnit.MILLISECONDS)
        }
    }

    private inner class SubscribeFromTest : LatchTask() {

        override fun invokeExecution() {
            val latch = CountDownLatch(1)

            print("=== SubscribeOn From ==== on thread ", currentThread)
            Observable.just({
                print("Create new iterator on ", currentThread)
                arrayOf(10, 20, 30, 40).iterator()
            })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(Schedulers.newThread())
                    .subscribe({ value -> print("onNext $value On ", currentThread) },
                            { error -> print("onError $error on ", currentThread) },
                            {
                                print("Complete on ", currentThread)
                                latch.countDown()
                            })

            print("=== Finish Test === on thread ", currentThread)

            latch.await(500, TimeUnit.MILLISECONDS)
        }
    }

    private fun print(exposition: String, id: Long) {
        System.out.println(exposition + id)
    }

    private val currentThread: Long
        get() = Thread.currentThread().id
}
