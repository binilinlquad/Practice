package com.gandan.practice.rx

import rx.Observable

fun main(args: Array<String>) {
    // RxJava Operators for cast and filter operation
    val maybeNullA = "ANotNull"
    val maybeNullB: String? =  null
    val elements = listOf("String", null, 1, 2L, "Hello", maybeNullA, maybeNullB)
    Observable.from(elements)
    .ofType(String::class.java)
    .subscribe { str ->  println(str) }
}

