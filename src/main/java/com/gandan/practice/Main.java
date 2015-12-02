package com.gandan.practice;

import com.gandan.practice.task.*;

public class Main {

    public static void main(String args[]) {
        ITask[] practices = {new TaskFactory(), new TaskFibonnaci(),
                new TaskReflection(), new TaskSemaphore()};
        for (ITask practice : practices) {
            practice.execute();
        }
    }
}
