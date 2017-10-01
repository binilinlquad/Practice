package com.gandan.practice.trycatch

fun main(args: Array<String>) {
    TryCatchFinally().execute()
}

class TryCatchFinally {
    fun execute() {
        print()
    }

    private fun print() {
        println("Return in Try-Finally result in " + returnInTryAndFinally())
        println("Return in Catch-Finally result in " + returnInCatchAndFinally())
    }

    @Suppress("UNREACHABLE_CODE")
    private fun returnInTryAndFinally(): String {
        try {
            return "Try clause"
        } finally {
            return "Finally clause"
        }
    }

    @Suppress("UNREACHABLE_CODE")
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
