package com.gandan.practice.task

class TaskFibonnaci : ITask {
    override fun execute() {
        com.gandan.practice.fibonacci.RecursiveFibonacci.print(10)
    }
}
