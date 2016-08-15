package com.gandan.practice.rx

import rx.Observable
import rx.schedulers.Schedulers

class MapIndexed<V> : Observable.Transformer<V, Pair<Int, V>> {
    override fun call(t: Observable<V>): Observable<Pair<Int, V>> {
        return Observable.defer {
            var index = 0
            t.flatMap {
                Observable.just(index++ to it)
            }
        }
    }
}

fun main(args: Array<String>) {
    Observable.just("A", "B", "C", "D")
            .compose(MapIndexed())
            .subscribe({
                System.out.println(it)
            })

    Observable.just(1000, 2323, 121, 5454)
            .compose(MapIndexed())
            .subscribe({
                System.out.println(it)
            })

    val observable = Observable.just("A1", "B2", "C3", "D4")
            .compose(MapIndexed())
    observable
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribe({
                System.out.println(it)
            })
    observable
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribe({
                System.out.println(it)
            })

}