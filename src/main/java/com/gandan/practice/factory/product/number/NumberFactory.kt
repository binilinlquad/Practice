package com.gandan.practice.factory.product.number

import com.gandan.practice.factory.Factory
import com.gandan.practice.factory.product.Product


class NumberFactory : Factory() {
    fun createMobileNumber(type: Int): Product = MobileNumber()

    override fun factoryMethod(): Product = MobileNumber()

}
