package com.felipe.kotlinMarket_api.domain.service

import com.felipe.kotlinMarket_api.domain.Product
import com.felipe.kotlinMarket_api.domain.repositories.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class ProductService (
    @Autowired
    private val productRepository: ProductRepository
){

    fun getAll(): List<Product>{
        return productRepository.getAll()
    }

    fun getProduct(productId: Int): Optional<Product> {
        return productRepository.getProduct(productId)
    }

    fun getByCategory(categoryId: Int): Optional<List<Product>> {
        return productRepository.getByCategory(categoryId)
    }

    fun saveProduct(product: Product): Product {
        return productRepository.saveProduct(product)
    }

    fun deleteProduct(productId: Int): Boolean {
        return getProduct(productId).map {
            productRepository.deleteProduct(productId)
            true
        }.orElse(false)
    }

}


