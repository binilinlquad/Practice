package com.gandan.practice.factory

import com.gandan.practice.factory.product.Product


abstract class Factory {
    fun doSomething() {
        val product = factoryMethod()
    }

    abstract fun factoryMethod(): Product
}
