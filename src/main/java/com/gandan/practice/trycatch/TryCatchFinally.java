package com.gandan.practice.trycatch;

import com.gandan.practice.task.ITask;

/**
 * Return in try, catch, and finally.
 *
 * Created by chandra on 12/3/15.
 */
public class TryCatchFinally implements ITask {
    @Override
    public void execute() {
        print();
    }

    private void print() {
        System.out.println("Return in Try-Finally result in " + returnInTryAndFinally());
        System.out.println("Return in Catch-Finally result in " + returnInTryAndFinally());
    }

    private String returnInTryAndFinally() {
        try {
            return "Try clause";
        } finally {
            return "Finally clause";
        }
    }

    private String returnInCatchAndFinally() {
        try {
            throw new RuntimeException("simulate exception");
        } catch (Exception e) {
            return "Catch Clause";
        } finally {
            return "Finally Clause";
        }

    }
}
