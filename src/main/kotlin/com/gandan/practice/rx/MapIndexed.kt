package com.gandan.practice.rx

import rx.Observable
import rx.functions.Func2
import rx.schedulers.Schedulers

// simple implementation with flatMap
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
// simple implementation with zipWith
class MapIndexedZ<V> : Observable.Transformer<V, Pair<Int, V>> {
    override fun call(t: Observable<V>): Observable<Pair<Int, V>> {
        return Observable.defer {
            t.zipWith(object : Iterable<Int> {
                val iterator = object : Iterator<Int> {
                    var index = 0

                    override fun hasNext() = true

                    override fun next() = index++

                }

                override fun iterator(): Iterator<Int> {
                    return iterator
                }

            }) { value, index -> index to value }
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
            .take(2)
            .subscribe({
                System.out.println(it)
            })

    val rangeIndexed = Observable.range(1, 10 )
            .compose(MapIndexedZ())
    rangeIndexed
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .subscribe({
                System.out.println(it)
            })
    rangeIndexed
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.newThread())
            .take(2)
            .subscribe({
                System.out.println(it)
            })

    Thread.sleep(5000)

}