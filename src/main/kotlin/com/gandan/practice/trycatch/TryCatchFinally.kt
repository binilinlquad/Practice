package com.gandan.practice.trycatch

import com.gandan.practice.task.ITask

/**
 * Return in try, catch, and finally.

 * Created by chandra on 12/3/15.
 */
class TryCatchFinally : ITask {
    override fun execute() {
        print()
    }

    private fun print() {
        println("Return in Try-Finally result in " + returnInTryAndFinally())
        println("Return in Catch-Finally result in " + returnInTryAndFinally())
    }

    private fun returnInTryAndFinally(): String {
        try {
            return "Try clause"
        } finally {
            return "Finally clause"
        }
    }

    private fun returnInCatchAndFinally(): String {
        try {
            throw RuntimeException("simulate exception")
        } catch (e: Exception) {
            return "Catch Clause"
        } finally {
            return "Finally Clause"
        }

    }
}
