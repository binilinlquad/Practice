package com.gandan.practice.coroutine

import kotlinx.coroutines.experimental.channels.ReceiveChannel
import kotlinx.coroutines.experimental.channels.produce
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import java.util.concurrent.TimeUnit
import kotlin.coroutines.experimental.buildSequence

fun main(args: Array<String>) {
    // take first ten fibonacci
    println("Generate fibonacci sequence")
    fibonacci().take(10).forEach { println(it) }

    println("Experiments with channel and fibonnaci")

    // get fibonacci number through channel
    val fibProducer = fibonacciProducer()
    launch {
        // it will generate infinite number
        //  fibProducer.consumeEach { println(it) }

        // print first five
        for(i in 1..5) println(fibProducer.receive())

        // should cancel it
        fibonacciProducer().cancel()
    }

    println("couroutine runBlocking ")
    runBlocking {
        takeANap()
        println("Finish Nap")
    }

//    val job = kotlinx.coroutines.experimental.run()
}

suspend fun takeANap() {
    delay(5, TimeUnit.SECONDS)
    println("Wake Up")
}

/**
 * create infinite fibonacci sequence lazily
 * sequence will generate lazy sequence which could be accessed through iterator
 *
 */
fun fibonacci() = buildSequence {
    var (a, b) = 0 to 1
    while (true) {
        yield(a)
        val c = a
        a = b
        b = a + c
    }
}

/**
 * Produce will create channel which we could send/offer value into.
 * Producer will result ReceiveChannel. Actor will result SendChannel
 *
 */
fun fibonacciProducer() : ReceiveChannel<Int> = produce {
    var (a, b) = 0 to 1
    while (true) {
        send(a)
        val c = a
        a = b
        b = a + c
    }
}