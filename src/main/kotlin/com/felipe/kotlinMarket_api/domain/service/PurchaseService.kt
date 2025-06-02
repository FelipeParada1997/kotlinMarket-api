package com.felipe.kotlinMarket_api.domain.service

import com.felipe.kotlinMarket_api.domain.Purchase
import com.felipe.kotlinMarket_api.domain.repositories.PurchaseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Optional
import java.util.logging.Logger

@Service
class PurchaseService(
    @Autowired
    private val purchaseRepository: PurchaseRepository
) {
    private val logger = Logger.getLogger(PurchaseService::class.java.name)

    fun getAll(): List<Purchase> {
        return purchaseRepository.getAll()
    }

    fun getByClient(clientId: String): Optional<List<Purchase>> {
        return purchaseRepository.getByClient(clientId)
    }

    @Transactional
    fun save(purchase: Purchase): Purchase {
        try {
            // Validaciones previas
            if (purchase.items.isEmpty()) {
                throw IllegalArgumentException("La compra debe tener al menos un producto")
            }

            // Guardar la compra
            return purchaseRepository.save(purchase)
        } catch (e: Exception) {
            logger.severe("Error al guardar la compra: ${e.message}")
            throw e
        }
    }
}
