package com.felipe.kotlinMarket_api.domain

class Product(
    var productId: Int?,
    var name: String,
    var categoryId: Int?,
    var price: Double?,
    var stock: Int?,
    var active: Boolean?,
    var category: Category?
) {
}