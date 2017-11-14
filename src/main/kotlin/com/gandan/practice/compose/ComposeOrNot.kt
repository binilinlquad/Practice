package com.gandan.practice.compose

import com.gandan.practice.common.log
import io.reactivex.Observable

/* Check what is difference between using compose or not */
fun main(args: Array<String>) {
    System.out.println("No Compose : you can operate on emitted value, but not source Observable")
    Observable.range(1, 10)
            .log()
            .map { it + 1 }
            .filter { it % 2 == 0 }
            .log()
            .reduce(mutableListOf<Int>(),
                    { list, e ->
                        System.out.println("Reduce $list $e")
                        list.apply { add(e) }
                    })
            .subscribe { list: List<Int> -> System.out.print("$list ") }

    System.out.println()
    System.out.println("With Compose : you can operate on source observable and you can replace it with another observable")
    Observable.range(1, 10)
            .log()
            .compose { source ->
                Observable.range(2, 11)
                        .filter { it % 2 == 0 }
            }
            .log()
            .reduce(mutableListOf<Int>(),
                    { list, e ->
                        System.out.println("Reduce $list $e")
                        list.apply { add(e) }
                    })
            .subscribe { list: List<Int> -> System.out.print("$list ") }

}
