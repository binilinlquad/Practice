package com.gandan.practice.rx

import rx.Observable

class MapIndexed<V> : Observable.Transformer<V, Pair<Int, V>> {
    private var index = 0
    override fun call(t: Observable<V>): Observable<Pair<Int, V>> {
        return t.map { index++ to it }
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
}