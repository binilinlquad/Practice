package com.gandan.practice.coroutine

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import kotlin.coroutines.experimental.buildSequence

fun main(args: Array<String>) {
    // block will run in different thread
    val coroutine = launch(CommonPool) {
        val threadId = Thread.currentThread().id
        System.out.println("1 with launch in $threadId")
        Thread.sleep(5000)
        System.out.println("2 with launch in $threadId")
    }
    coroutine.start()

    // block run in same thread with current
    runBlocking {
        val threadId = Thread.currentThread().id
        System.out.println("3 with runBlocking in $threadId")
        Thread.sleep(5000)
        System.out.println("4 with runBlocking in $threadId")
    }

    // shamelessly copied from kotlin https://github.com/Kotlin/kotlin-coroutines/blob/master/kotlin-coroutines-informal.md#asynchronous-computations
    // block sequence will run in same thread with current thread
    val fibonacci = buildSequence<Int> {
        System.out.println("Sequence in ${Thread.currentThread().id}")
        yield(1) // first Fibonacci number
        var cur = 1
        var next = 1
        while (true) {
            yield(next) // next Fibonacci number
            val tmp = cur + next
            cur = next
            next = tmp
        }
    }

    // blocked by Thread.sleep inside runBlocking
    val threadId = Thread.currentThread().id
    System.out.println("5 line main in $threadId")
    System.out.println("Fibs ${fibonacci.take(10).joinToString()}")
    System.out.println("finish line main in $threadId")
}