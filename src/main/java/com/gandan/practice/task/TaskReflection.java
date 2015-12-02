package com.gandan.practice.task;

import com.gandan.practice.reflection.SampleClassReflection;

public class TaskReflection implements ITask {
    @Override
    public void execute() {
        SampleClassReflection sample = new SampleClassReflection(String.class);
        sample.displayClassnameAtRuntime();
        sample.dipslayModifier();

        System.out.print("Generate Empty String with newInstance");
        String emptyString = (String)sample.newInstance();
        System.out.println(" " + "".equals(emptyString));
    }
}
