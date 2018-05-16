package com.gandan.practice.coroutine

import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking

fun main(args: Array<String>) {
    runBlocking {
        val longOperation = async {
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
        System.out.println("First operation returns ${firstCall} in ${end - start}ms")

        val start2 = System.currentTimeMillis()
        val secondCall = longOperation.await()
        val end2 = System.currentTimeMillis()
        System.out.println("Second operation return ${secondCall} in ${end2 - start2}ms")
    }
}
