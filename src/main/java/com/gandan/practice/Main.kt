package com.gandan.practice

import com.gandan.practice.rx.RxUsing
import com.gandan.practice.rx.RxThread
import com.gandan.practice.task.*
import com.gandan.practice.trycatch.TryCatchFinally

fun main(args: Array<String>) {
    arrayOf(
            TaskFactory(),
            TaskFibonnaci(),
            TaskReflection(),
            TaskSemaphore(),
            TryCatchFinally(),
            RxThread(),
            RxUsing()
    ).forEach { it.execute() }
}
