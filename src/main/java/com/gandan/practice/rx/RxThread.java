package com.gandan.practice.rx;

import com.gandan.practice.task.ITask;
import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;


/**
 * Result could be different each run if using new thread
 * for subscribeOn or observeOn
 *
 * Created by chandra on 12/4/15.
 */
public class RxThread implements ITask {

    @Override
    public void execute() {
        ITask[] tasks = new ITask[] {
//                plainObservableTest,
//                subscribeOnObservableTest,
//                observeOnObservableTest,
                subscribeAndObserveOnObservableTest
        };
        for (ITask task : tasks) {
            task.execute();
            System.out.println();
        }
    }

    private ITask plainObservableTest = () -> {
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
                () -> print("Complete on ", getCurrentThread()));

        print("=== Finish Test === on thread ", getCurrentThread());
    };

    private ITask subscribeOnObservableTest = () -> {
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
                        () -> print("Complete on ", getCurrentThread()));

        print("=== Finish Test === on thread ", getCurrentThread());
    };

    private ITask observeOnObservableTest = () -> {
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
                        () -> print("Complete on ", getCurrentThread()));

        print("=== Finish Test === on thread ", getCurrentThread());
    };

    private ITask subscribeAndObserveOnObservableTest = () -> {
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
                        () -> print("Complete on ", getCurrentThread()));

        print("=== Finish Test === on thread ", getCurrentThread());
    };
    private void print(String exposition, long id) {
        System.out.println(exposition + id);
    }

    private long getCurrentThread() {
        return Thread.currentThread().getId();
    }
}
