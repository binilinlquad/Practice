package com.gandan.practice.coroutine

import kotlinx.coroutines.*


fun main() {
    runBlocking {
        // if we didn't define coroutine context where async is running, it will inherit context
        // from parent context.
        // we could move it by pass Dispatcher to async
        val longOperation = async {
            System.out.println("Async in thread ${Thread.currentThread()}")
            Thread.sleep(1000)
            10
        }

        /**
         * Result of coroutine will be saved for next calling
         * Because of that, second operation won't take times to return value
         */
        val start = System.currentTimeMillis()
        val firstCall = longOperation.await()
        val end = System.currentTimeMillis()
        System.out.println("First time invocation long operation coroutine returns ${firstCall} in ${end - start}ms")

        val start2 = System.currentTimeMillis()
        val secondCall = longOperation.await()
        val end2 = System.currentTimeMillis()
        System.out.println("Second time invocation long operation cotourine return ${secondCall} in ${end2 - start2}ms")
    }

    // what if coroutine access var from outside its own blcok
    runBlocking {
        var value = 10
        println("Var initial value is $value")
        val printer = GlobalScope.async {
            delay(1000)
            println("Var value inside coroutine is $value")
        }
        value = 99
        println("Mutate var value to $value")
        printer.join()
    }
}
