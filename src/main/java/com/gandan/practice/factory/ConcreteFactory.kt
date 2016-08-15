package com.gandan.practice.factory

import com.gandan.practice.factory.product.Product
import com.gandan.practice.factory.product.ConcreteProduct

class ConcreteFactory : Factory() {

    override fun factoryMethod(): Product = ConcreteProduct()

}
