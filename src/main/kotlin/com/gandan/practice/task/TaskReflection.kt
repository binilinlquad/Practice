package com.gandan.practice.task

import com.gandan.practice.reflection.SampleClassReflection

class TaskReflection : ITask {
    override fun execute() {
        val sample = SampleClassReflection(String::class.java)
        sample.displayClassnameAtRuntime()
        sample.dipslayModifier()

        print("Generate Empty String with newInstance")
        val emptyString = sample.newInstance() as String
        println(" " + ("" == emptyString))
    }
}
