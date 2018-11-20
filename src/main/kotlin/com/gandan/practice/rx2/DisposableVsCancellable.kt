package com.gandan.practice.rx2

import io.reactivex.Observable
import io.reactivex.disposables.Disposable

/**
 *
 * Little experiment to compare emitter setDisposable vs setCancellable
 * why we should not use that together
 *
 * Actually everything is written in RxJava 2 Javadoc
 */
fun main(args: Array<String>) {


    val x = Observable.create<Int> { emitter ->
        // never emit, error, or complete
        // so we focus on setDisposable and setCancellable
        emitter.setDisposable(object : Disposable {
            var disposed = false

            override fun isDisposed(): Boolean {
                System.out.println("isDisposed called")
                return disposed
            }

            override fun dispose() {
                System.out.println("dispose called")
                disposed = true
            }

        })

        /**
         *
         * it used to wrap block invocation inside try-catch
         * which is not provided by default if we use setDisposable.
         * and every time invocation setCancellable will invoce setDisposable
         * implementation and will invoke previous set diposable implementation
         * before set new disposable implementation
         * see [io.reactivex.internal.operators.observable.ObservableCreate]
         * and [io.reactivex.internal.disposables.DisposableHelper.set]
         *
         *
         */
        emitter.setCancellable {
            System.out.println("cancellable called")
        }

    }

    @Suppress("UNUSED_VARIABLE")
    val disposable = x.subscribe()

    // because we set cancellable after set disposable, we could see
    // line "dispose called" even though we don't dispose it

    // uncomment line below if you want see "cancellable called"
    // disposable.dispose()

}
