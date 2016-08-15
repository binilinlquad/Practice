package com.gandan.practice.task

import com.gandan.practice.factory.product.number.MobileNumber
import com.gandan.practice.factory.product.number.NumberFactory


class TaskFactory : ITask {

    private val mobileNumberFactory = NumberFactory()

    override fun execute() {
        val mn = mobileNumberFactory.factoryMethod() as MobileNumber
        mn.apply {
            countryCode = "021"
            phoneNumber = "6878743"
        }.run { println("Mobile Number : " + number) }

    }
}
