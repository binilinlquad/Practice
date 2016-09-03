package com.gandan.practice.rx

import rx.Notification
import rx.Observable

fun main(args: Array<String>) {
    // warming up with range
    System.out.println("Range")
    val a = Observable.range(1, 10)
    val b = Observable.range(50, 10)

    a.concatWith(b).subscribe { System.out.print("$it ") }
    System.out.print(" It's correct")

    System.out.println("\nRepeat with counter")
    val c = Observable.just("c").repeat(10)
    val d = Observable.just("d").repeat(10)

    c.concatWith(d).subscribe { System.out.print("$it ") }
    System.out.print(" It's correct")

    System.out.println("\nRepeat with flatmap")
    var flagE = false
    var flagF = false
    val e = Observable.just("e").repeatWhen {
        it.flatMap {
            if (!flagE) {
                flagE = true
                Observable.just("$it 0")
            } else {
                Observable.empty()
            }
        }
    }
            .doOnCompleted { System.out.println("Complete") }

    val f = Observable.just("f").repeatWhen {
        it.flatMap {
            if (!flagF) {
                flagF = true
                Observable.just("$it 1")
            } else {
                Observable.empty()
            }
        }
    }
            .doOnCompleted { System.out.println("Complete") }

    e.concatWith(f).subscribe { System.out.print("$it ") }
    System.out.print(" It's wrong")

    System.out.println("\nRepeat with map and dematerialize")
    var flagG = false
    val g = Observable.just("g").repeatWhen { source ->
        source.map {
            if (!flagG) {
                flagG = true
                Notification.createOnNext(Unit)
            } else {
                Notification.createOnCompleted<Unit>()
            }
        }.dematerialize<Unit>()
    }
            .doOnCompleted { System.out.println("Complete") }

    var flagH = false
    val h = Observable.just("h").repeatWhen { source ->
        source.map {
            if (!flagH) {
                flagH = true
                Notification.createOnNext(Unit)
            } else {
                Notification.createOnCompleted<Unit>()
            }
        }.dematerialize<Unit>()
    }
            .doOnCompleted { System.out.println("Complete") }

    g.concatWith(h).subscribe { System.out.print("$it ") }
    System.out.print(" It's correct")

    // ask andri
    var ka = 0
    var bulenA = true
    val aa = Observable.defer { Observable.just("A ${ka++}") }
            .repeatWhen { it.takeWhile { bulenA.apply { bulenA = false } } }

    var kb = 0
    var bulenB = true
    val bb = Observable.defer { Observable.just("B ${kb++}") }
            .repeatWhen { it.takeWhile { bulenB.apply { bulenB = false } } }

    aa.concatWith(bb).subscribe { System.err.println("WPF ${it}") }
}
