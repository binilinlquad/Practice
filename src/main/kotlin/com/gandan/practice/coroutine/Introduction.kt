package com.gandan.practice.coroutine

import kotlinx.coroutines.*


fun main() {
    sampleLaunchInCommonPool()

    // block run in same thread with current
    sampleBlocking()

    // shamelessly copied from kotlin https://github.com/Kotlin/kotlin-coroutines/blob/master/kotlin-coroutines-informal.md#asynchronous-computations
    // block sequence will run in same thread with current thread
    val fibonacci = sampleBuildSequence()

    // blocked by Thread.sleep inside runBlocking
    val threadId = Thread.currentThread().id
    System.out.println("5 line main in $threadId")
    System.out.println("Fibs ${fibonacci.take(10).joinToString()}")
    System.out.println("finish line main in $threadId")

    val assync: Deferred<Int> = sampleAsynCommonPool()

    sampleJointAndAwait(assync)

    sampleInherintParentContext()
}

private fun sampleJointAndAwait(assync: Deferred<Int>) {
    runBlocking {
        // What difference between join and await
        // https://stackoverflow.com/questions/46226518/what-is-the-difference-between-launch-join-and-async-await-in-kotlin-coroutines
        // basically join return Unit
        // assync.join()

        // await return value (integer here)
        System.out.println("async result : ${assync.await()}")

        System.out.println("Line after async")
    }
}

private fun sampleAsynCommonPool(): Deferred<Int> {
    val assync: Deferred<Int> = GlobalScope.async {
        val threadId = Thread.currentThread().id
        System.out.println("1 with async in $threadId")
        Thread.sleep(5000)
        System.out.println("2 with async in $threadId")
        5
    }
    return assync
}

private fun sampleBuildSequence(): Sequence<Int> {
    val fibonacci = sequence<Int> {
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
    return fibonacci
}

private fun sampleBlocking() {
    runBlocking {
        val threadId = Thread.currentThread().id
        System.out.println("3 with runBlocking in $threadId")
        Thread.sleep(5000)
        System.out.println("4 with runBlocking in $threadId")
    }
}

private fun sampleLaunchInCommonPool() {
    // block will run in different green thread
    val coroutine = GlobalScope.launch {
        val threadId = Thread.currentThread().id
        System.out.println("1 with launch in $threadId")
        Thread.sleep(5000)
        System.out.println("2 with launch in $threadId")
    }
    coroutine.start()
}

// inherit parent coroutine scope
fun sampleInherintParentContext() {
    val pretendCalculationAsync = GlobalScope.async {
        // suspend this coroutine, it will let caller continue
        Thread.sleep(1000)

        // child coroutine will be not runned because we cancel
        // parent coroutine in next line after async and
        // child inherint parent coroutine scope
        val pretendCalculationInside = async(coroutineContext) {
            // never printed
            System.out.println("Child coroutine")
        }
        // never printed
        System.out.println("Parent coroutine")

    }

    // to show that by doing it, it will cancel child too
    pretendCalculationAsync.cancel()

}