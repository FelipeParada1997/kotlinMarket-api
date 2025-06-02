package com.felipe.kotlinMarket_api.domain

class PurchaseItem (
    var productId: Int,
    var quantity: Int = 1,
    var total: Double = 0.0,
    var active: Boolean = true

    ){
}