package com.gandan.practice.rx

import rx.Observable

fun main(args: Array<String>) {
    // RxJava Operators for cast and filter operation
    val elements = listOf("String", null, 1, 2L, "Hello")
    Observable.from(elements)
    .ofType(String::class.java)
    .subscribe { str ->  println(str) }
}

