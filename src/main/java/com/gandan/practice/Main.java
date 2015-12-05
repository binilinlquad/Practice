package com.gandan.practice;

import com.gandan.practice.rx.RxUsing;
import com.gandan.practice.rx.RxThread;
import com.gandan.practice.task.*;
import com.gandan.practice.trycatch.TryCatchFinally;

public class Main {

    public static void main(String args[]) {
        ITask[] practices = {
                  new TaskFactory(),
                new TaskFibonnaci(),
                new TaskReflection(),
                new TaskSemaphore(),
                new TryCatchFinally(),
                new RxThread(),
                new RxUsing()};
        for (ITask practice : practices) {
            practice.execute();
        }
    }
}
