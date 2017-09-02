package com.gandan.practice.hashcode

fun main(args: Array<String>) {
    val a = "abc"
    val x = a.hashCode()

    System.out.println("Hashcode should be equal for some objects (it used in equals method)")
    System.out.println("abc has ${"abc".hashCode()} and x has $x then it should be equal ${"abc" == a}")
}