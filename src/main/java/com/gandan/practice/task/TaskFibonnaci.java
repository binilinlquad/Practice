package com.gandan.practice.task;

public class TaskFibonnaci implements ITask {
    @Override
    public void execute() {
        com.gandan.practice.fibonacci.RecursiveFibonacci.print(10);
    }
}
