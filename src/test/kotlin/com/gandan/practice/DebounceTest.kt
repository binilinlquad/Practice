package com.gandan.practice

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.junit.Test
import java.util.concurrent.TimeUnit

class DebounceTest {

    /**
     * This implementation will fail showing any result because
     * at first input (a), error occurs and at that time there is new
     * input (b). Because no subscriber (error happens and observable is
     * in progress disposing - retry operator only help to resubscribe but
     * cannot help getting value which is emitted before subscribe), input (b)
     * will be emitted without any subscriber.
     *
     */
    @Test
    fun incorrect_implementatin_debounce_click() {
        val inputs = listOf<InputCase>(
                InputCase("a", true),
                InputCase("b", false)
        )
        val fakeUserInput = PublishSubject.create<InputCase>()
        val observeInput : Observable<InputCase> = fakeUserInput
                .debounce(200, TimeUnit.MILLISECONDS)
                .log()
                .switchMap {
                    if (it.shouldError) {
                    Observable.just(1)
                            .delay(50, TimeUnit.MILLISECONDS)
                            .flatMap { Observable.error<InputCase>(RuntimeException("Faking error")) }
                    } else {
                    Observable.just(it)
                            .delay(50, TimeUnit.MILLISECONDS)
                    }
                }
                .log()
                .retry()

        observeInput
                .subscribe(System.out::println)

        fakeUserInput.onNext(inputs[0])
        // wait before emit new input.
        // 250 ms is total time of debounce time (200ms) + fake error delay time (50ms)
        TimeUnit.MILLISECONDS.sleep(250)
        fakeUserInput.onNext(inputs[1])

        TimeUnit.SECONDS.sleep(1)
    }

    /**
     * To fix issue above, we need to avoid error/complete event so
     * we won't have scenario that input (b) is emitted without any
     * subscriber. Here we convert error into Observable.just, and prevent
     * error occurs.
     */
    @Test
    fun correct_implementation_debounce_click() {
        val inputs = listOf<InputCase>(
                InputCase("a", true),
                InputCase("b", false)
        )
        val fakeUserInput = PublishSubject.create<InputCase>()
        val observeInput : Observable<InputCase> = fakeUserInput
                .debounce(200, TimeUnit.MILLISECONDS)
                .switchMap {
                    if (it.shouldError) {
                        // this is where it is different
                        Observable.just(InputCase("error replacement", false))
                                .delay(50, TimeUnit.MILLISECONDS)
                    } else {
                        Observable.just(it)
                                .delay(50, TimeUnit.MILLISECONDS)
                    }
                }
                .log()

        observeInput
                .subscribe(System.out::println)

        fakeUserInput.onNext(inputs[0])
        TimeUnit.MILLISECONDS.sleep(250)
        fakeUserInput.onNext(inputs[1])

        TimeUnit.SECONDS.sleep(1)
    }

    private data class InputCase(
            // fake user input
            val input: String,
            // flag for pretending error happened, i.e: request fails
            val shouldError: Boolean
    )

    // log helper to show whatever happens with observable
    private inline fun <reified T> Observable<T>.log() : Observable<T> {
        return this
                .doOnSubscribe { System.out.println("onSubsribe $it at time: ${System.currentTimeMillis()}") }
                .doOnNext { System.out.println("next : $it at time: ${System.currentTimeMillis()}") }
                .doOnError { System.out.println("error : $it at time: ${System.currentTimeMillis()}") }
                .doOnComplete { System.out.println("complete at time: ${System.currentTimeMillis()}") }
                .doOnDispose { System.out.println("onDispose at time: ${System.currentTimeMillis()}") }
    }
}