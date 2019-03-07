package com.gandan.practice.coroutine

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume

/**
 * Test solution from https://discuss.kotlinlang.org/t/unit-testing-coroutines/6453/5
 * for unit test by change scope
 */
fun main() {
    println("Main run in thread ${Thread.currentThread()}")

    val scope = CoroutineScope(ImmediateDispatcher())
    scope.async {
        val start = System.currentTimeMillis()
        println("Async run in thread ${Thread.currentThread()}")
        println("Before delay $start")
        println("Delay 100 ms.")
        delay(100)
        println("After delay with difference ${System.currentTimeMillis() - start}.")
        println("Should not need if immediate scope will launch in same thread with main")
    }

    println("If you look closely, here we are not using await. It is deliberate to show async operation become synchonous")
}

/**
 * ImmediateDispatcher will dispatch in current thread and will delay by using Thread.sleep to block current thread
 * Should instantiate it in thread we want
 *
 */
@UseExperimental(InternalCoroutinesApi::class)
private class ImmediateDispatcher : CoroutineDispatcher(), Delay {
    override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
        // this is only sample. For real implementation, should replace it with more flexible way
        Thread.sleep(timeMillis)
        continuation.resume(Unit)
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        if (context.isActive) {
            block.run()
        }
    }
}