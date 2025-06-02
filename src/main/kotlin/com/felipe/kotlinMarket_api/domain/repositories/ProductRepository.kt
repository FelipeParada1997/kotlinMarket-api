package com.felipe.kotlinMarket_api.domain.repositories

import com.felipe.kotlinMarket_api.domain.Product
import java.util.Optional

interface ProductRepository {

    fun getAll(): List<Product>
    fun getByCategory(categoryId: Int): Optional<List<Product>>
    fun getScarseProducts(quantity: Int): Optional<List<Product>>
    fun getProduct(productId: Int): Optional<Product>
    fun saveProduct(product: Product): Product;
    fun deleteProduct(productId: Int);
}