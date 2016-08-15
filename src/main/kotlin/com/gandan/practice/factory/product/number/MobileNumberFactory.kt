package com.gandan.practice.factory.product.number

import com.gandan.practice.factory.Factory
import com.gandan.practice.factory.product.Product


class MobileNumberFactory : Factory() {

    override fun factoryMethod(): Product = MobileNumber()

}
