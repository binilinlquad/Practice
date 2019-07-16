package com.gandan.practice.rx2

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.Semaphore

fun main() {
    observableSubscribeOnNewThread()

    createObservableInAnotherThread()

    subjectSubscribeOnhNewThread()
}

fun createObservableInAnotherThread() {
    val waiter = Semaphore(0)
    Observable.create<Int> {
        Thread(Runnable {
            println("Emitting on ${Thread.currentThread().name}")
            it.onNext(1)
            it.onComplete()
        }, "Thread A").start()
    }
            .subscribeOn(Schedulers.newThread())
            .subscribe {

                println("Subscriber on ${Thread.currentThread().name}")
                println("value: $it")

                waiter.release()
            }


    waiter.acquireUninterruptibly()
}

fun observableSubscribeOnNewThread() {
    val ready = Semaphore(0)
    val waiter = Semaphore(0)
    Observable.create<Int> {
            println("Emitting on ${Thread.currentThread().name}")
            it.onNext(1)
            it.onComplete()
        }
            .subscribeOn(Schedulers.newThread())
            .subscribe {
                ready.acquireUninterruptibly()

                println("Subscriber on ${Thread.currentThread().name}")
                println("value: $it")

                waiter.release()
            }

    ready.release()

    waiter.acquireUninterruptibly()
}

fun subjectSubscribeOnhNewThread() {
    val ready = Semaphore(0)
    val waiter = Semaphore(0)

    val subject = PublishSubject.create<Int>()

    subject
            .subscribeOn(Schedulers.newThread())
            .subscribe {
                ready.acquireUninterruptibly()

                println("Subscriber on ${Thread.currentThread().name}")
                println("value: $it")

                waiter.release()
            }

    ready.release()

    subject.onNext(1)

    waiter.acquireUninterruptibly()

    subject.onComplete()

}

