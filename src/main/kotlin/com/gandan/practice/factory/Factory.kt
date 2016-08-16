package com.gandan.practice.factory

import com.gandan.practice.factory.product.Product
import com.gandan.practice.factory.product.number.MobileNumber
import com.gandan.practice.factory.product.number.NumberFactory

fun main(args: Array<String>) {
    val mobileNumberFactory = NumberFactory()

    val mn = mobileNumberFactory.factoryMethod() as MobileNumber
    mn.apply {
        countryCode = "021"
        phoneNumber = "6878743"
    }.run { println("Mobile Number : " + number) }
}

abstract class Factory {
    abstract fun factoryMethod(): Product
}
