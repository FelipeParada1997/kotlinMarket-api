package com.felipe.kotlinMarket_api.domain.repositories

import com.felipe.kotlinMarket_api.domain.Purchase
import java.util.Optional

interface PurchaseRepository {
    fun getAll(): List<Purchase>
    fun getByClient(clientId: String): Optional<List<Purchase>>
    fun save(purchase: Purchase): Purchase
}