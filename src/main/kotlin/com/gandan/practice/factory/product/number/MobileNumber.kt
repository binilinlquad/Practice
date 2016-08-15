package com.gandan.practice.factory.product.number


class MobileNumber : Number() {
    var countryCode: String? = null
    var phoneNumber: String? = null

    override val number: Long
        get() = java.lang.Long.parseLong(this.countryCode!! + this.phoneNumber!!)

}
