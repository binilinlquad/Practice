package com.gandan.practice.rx

import rx.AsyncEmitter
import rx.Observable
import rx.Observable.*
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timer

/* Ask @andrisama */
fun main(args: Array<String>) {
    var autoTriggerKounter = 1
    var manualTriggerKounter = 1
    var totalTrigger = 1

    var primitiveOnClick: () -> Unit = { }

    val onClicks = Observable.fromEmitter<Unit>({
        primitiveOnClick = { it.onNext(Unit) }
    }, AsyncEmitter.BackpressureMode.LATEST).publish()

    val obs = defer {
        // perform something
        val msg = "auto Retry = $autoTriggerKounter manually Retry = $manualTriggerKounter"
        error<Unit>(Throwable(msg))
    }.materialize()
            .switchMap {
                when {
                    it.isOnCompleted -> just(it).concatWith(never())
                    else -> just(it)
                }.delay(100, TimeUnit.MILLISECONDS)
            }
            .repeatWhen {
                val signal = it.share()
                merge(
                        signal.zipWith(range(1, 3), { a, b -> Unit }).doOnNext { autoTriggerKounter++ },
                        signal.zipWith(onClicks, { a, b -> Unit }).doOnNext { manualTriggerKounter++ }
                )
            }
            .publish()

    val repeater = obs.doOnError { System.err.println("Retry $it") }.retryWhen {
        it.flatMap {
            timer(100, TimeUnit.MILLISECONDS)
        }
    }.subscribe()
    val displayRefresh = onClicks.subscribe { System.out.println("Show Refresh") }
    val displayError = obs.subscribe {
        System.err.println("Show Error Message ${totalTrigger++} ${it.throwable}")
    }

    val subscription = CompositeSubscription(repeater, displayError, displayRefresh,
            onClicks.connect(), obs.connect())

    // BACK PRESSURED!
    for (i in (1..10000)) {
        primitiveOnClick() // Manually Retry by Impatient Users
    }

    Thread.sleep(1000)
    primitiveOnClick() // Manually Retry by Users
    Thread.sleep(500)
    subscription.clear()
}