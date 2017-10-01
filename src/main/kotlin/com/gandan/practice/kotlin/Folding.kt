package com.gandan.practice.kotlin

fun main(args: Array<String>) {
    val a = listOf(1, 2, 3, 4, 5)
    a.fold(emptyList<Int>(), { result, element ->
        System.err.println("Before $result")
        val r = result + (result.lastOrNull() ?: element)
        System.err.println("After $r")
        r
    } )
}
