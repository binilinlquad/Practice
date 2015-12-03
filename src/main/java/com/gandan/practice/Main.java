package com.gandan.practice;

import com.gandan.practice.task.*;
import com.gandan.practice.trycatch.TryCatchFinally;

public class Main {

    public static void main(String args[]) {
        ITask[] practices = {new TaskFactory(), new TaskFibonnaci(),
                new TaskReflection(), new TaskSemaphore(), new TryCatchFinally()};
        for (ITask practice : practices) {
            practice.execute();
        }
    }
}
