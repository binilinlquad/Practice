package com.gandan.practice;

import com.gandan.practice.reflection.SampleClassReflection;

public class PracticeReflection {
    public static void main(String[] args){
        SampleClassReflection sample = new SampleClassReflection(String.class);
        sample.displayClassnameAtRuntime();
        sample.dipslayModifier();

        System.out.print("Generate Empty String with newInstance");
        String emptyString = (String)sample.newInstance();
        System.out.println(" " + "".equals(emptyString));
    }
}
