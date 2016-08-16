package com.gandan.practice.reflection

import java.lang.reflect.Modifier

fun main(args: Array<String>) {
    SampleClassReflection(String::class.java)
            .apply {
                displayClassnameAtRuntime()
                dipslayModifier()
                print("Generate Empty String with newInstance")
                val emptyString = newInstance()
                println(" " + ("" == emptyString))
            }

}

class SampleClassReflection(internal var myClass: Class<*>) {


    fun displayClassnameAtRuntime() {
        println("Name Long class : " + this.myClass.name)
        println("Canonical Name Long class : " + this.myClass.canonicalName)
    }

    fun dipslayModifier() {
        val mod = this.myClass.modifiers
        println("Modifiers : " + mod)
        println("Modifier.isAbstract:" + Modifier.isAbstract(mod))
        println("Modifier.isFinal:" + Modifier.isFinal(mod))
        println("Modifier.isInterface:" + Modifier.isInterface(mod))
        println("Modifier.isNative:" + Modifier.isNative(mod))
        println("Modifier.isPrivate:" + Modifier.isPrivate(mod))
        println("Modifier.isProtected:" + Modifier.isProtected(mod))
        println("Modifier.isPublic:" + Modifier.isPublic(mod))
        println("Modifier.isStatic:" + Modifier.isStatic(mod))
        println("Modifier.isStrict:" + Modifier.isStrict(mod))
        println("Modifier.isSynchronized:" + Modifier.isSynchronized(mod))
        println("Modifier.isTransient:" + Modifier.isTransient(mod))
        println("Modifier.isVolatile:" + Modifier.isVolatile(mod))
    }


    fun newInstance(): Any? {
        try {
            return this.myClass.newInstance()
        } catch (e1: IllegalAccessException) {
            e1.printStackTrace()
        } catch (e2: InstantiationException) {
            e2.printStackTrace()
        }

        return null
    }

}
