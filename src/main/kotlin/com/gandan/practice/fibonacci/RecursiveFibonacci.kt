package com.gandan.practice.fibonacci

fun main(args: Array<String>) {
    RecursiveFibonacci.print(10)
}

object RecursiveFibonacci {
    fun print(n: Int) {
        (0..n-1).map { fib(it) }.forEach { println(it) }
    }


    private fun fib(n: Int): Int {
        return when(n) {
            2, 1 -> 1
            0 -> 0
            else -> fib(n-2) + fib(n-1)
        }
    }
}
