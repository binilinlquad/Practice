package com.gandan.practice.rx;

import com.gandan.practice.task.ITask;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/**
 * Result could be different each run if using new thread
 * for subscribeOn or observeOn
 * <p>
 * Created by chandra on 12/4/15.
 */
public class RxThread implements ITask {

    @Override
    public void execute() {
        ITask[] tasks = new ITask[]{
                new PlainObservableTest(),
                new SubscribeOnObservableTest(),
                new SubscribeAndObserveOnObervableTest(),
                new ObserveOnObservabeTest(),
                new SubscribeFromTest(),
        };
        for (ITask task : tasks) {
            task.execute();
            System.out.println();
        }
    }

    private abstract class LatchTask implements ITask {
        public void execute() {
            try {
                invokeExecution();
            } catch (InterruptedException e) {
                System.out.println("Test " + getClass().getSimpleName() + " is error");
                e.printStackTrace();
            }
        }

        ;

        public abstract void invokeExecution() throws InterruptedException;
    }

    private class PlainObservableTest extends LatchTask {

        @Override
        public void invokeExecution() throws InterruptedException {
            CountDownLatch latch = new CountDownLatch(1);

            print("=== Plain Observable ==== on thread ", getCurrentThread());
            Observable.create(new Observable.OnSubscribe<Integer>() {
                @Override
                public void call(Subscriber<? super Integer> subscriber) {
                    print("OnSubscribe call on ", getCurrentThread());
                    subscriber.onStart();
                    subscriber.onNext(1);
                    subscriber.onCompleted();
                }
            }).subscribe(value -> print("onNext  On ", getCurrentThread()),
                    error -> print("onError on ", getCurrentThread()),
                    () -> {
                        print("Complete on ", getCurrentThread());
                        latch.countDown();
                    });

            print("=== Finish Test === on thread ", getCurrentThread());

            latch.await(500, TimeUnit.MILLISECONDS);
        }
    }

    private class SubscribeOnObservableTest extends LatchTask {

        @Override
        public void invokeExecution() throws InterruptedException {
            CountDownLatch latch = new CountDownLatch(1);

            print("=== SubscribeOn Observable ==== on thread ", getCurrentThread());
            Observable.create(new Observable.OnSubscribe<Integer>() {

                @Override
                public void call(Subscriber<? super Integer> subscriber) {
                    print("OnSubscribe call on ", getCurrentThread());
                    subscriber.onStart();
                    subscriber.onNext(1);
                    subscriber.onCompleted();
                }
            })
                    .subscribeOn(Schedulers.newThread())
                    .subscribe(value -> print("onNext  On ", getCurrentThread()),
                            error -> print("onError on ", getCurrentThread()),
                            () -> {
                                print("Complete on ", getCurrentThread());
                                latch.countDown();
                            });

            print("=== Finish Test === on thread ", getCurrentThread());

            latch.await(500, TimeUnit.MILLISECONDS);

        }
    }

    private class ObserveOnObservabeTest extends LatchTask {

        @Override
        public void invokeExecution() throws InterruptedException {
            CountDownLatch latch = new CountDownLatch(1);

            print("=== SubscribeOn Observable ==== on thread ", getCurrentThread());
            Observable.create(new Observable.OnSubscribe<Integer>() {

                @Override
                public void call(Subscriber<? super Integer> subscriber) {
                    print("OnSubscribe call on ", getCurrentThread());
                    subscriber.onStart();
                    subscriber.onNext(1);
                    subscriber.onCompleted();
                }
            })
                    .observeOn(Schedulers.newThread())
                    .subscribe(value -> print("onNext  On ", getCurrentThread()),
                            error -> print("onError on ", getCurrentThread()),
                            () -> {
                                print("Complete on ", getCurrentThread());
                                latch.countDown();
                            });

            print("=== Finish Test === on thread ", getCurrentThread());

            latch.await(500, TimeUnit.MILLISECONDS);
        }
    }

    private class SubscribeAndObserveOnObervableTest extends LatchTask {

        @Override
        public void invokeExecution() throws InterruptedException {
            CountDownLatch latch = new CountDownLatch(1);

            print("=== SubscribeOn ObservableOn ==== on thread ", getCurrentThread());
            Observable.create(new Observable.OnSubscribe<Integer>() {

                @Override
                public void call(Subscriber<? super Integer> subscriber) {
                    print("OnSubscribe call on ", getCurrentThread());
                    subscriber.onStart();
                    subscriber.onNext(1);
                    subscriber.onCompleted();
                }
            })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(Schedulers.newThread())
                    .subscribe(value -> print("onNext  On ", getCurrentThread()),
                            error -> print("onError on ", getCurrentThread()),
                            () -> {
                                print("Complete on ", getCurrentThread());
                                latch.countDown();
                            });

            print("=== Finish Test === on thread ", getCurrentThread());

            latch.await(500, TimeUnit.MILLISECONDS);
        }
    }

    private class SubscribeFromTest extends LatchTask {

        @Override
        public void invokeExecution() throws InterruptedException {
            CountDownLatch latch = new CountDownLatch(1);

            print("=== SubscribeOn From ==== on thread ", getCurrentThread());
            Observable.from(() -> {
                print("Create new iterator on ", getCurrentThread());
                List<Integer> list = new ArrayList<>();
                list.add(10);
                list.add(20);
                list.add(30);
                list.add(40);
                return list.iterator();
            })
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(Schedulers.newThread())
                    .subscribe(value -> print("onNext  On ", getCurrentThread()),
                            error -> print("onError on ", getCurrentThread()),
                            () -> {
                                print("Complete on ", getCurrentThread());
                                latch.countDown();
                            });

            print("=== Finish Test === on thread ", getCurrentThread());

            latch.await(500, TimeUnit.MILLISECONDS);
        }
    }

    private void print(String exposition, long id) {
        System.out.println(exposition + id);
    }

    private long getCurrentThread() {
        return Thread.currentThread().getId();
    }
}
