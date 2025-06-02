package com.felipe.kotlinMarket_api.persistence

import com.felipe.kotlinMarket_api.domain.Purchase
import com.felipe.kotlinMarket_api.domain.PurchaseItem
import com.felipe.kotlinMarket_api.domain.repositories.PurchaseRepository
import com.felipe.kotlinMarket_api.persistence.crud.CompraCrudRepository
import com.felipe.kotlinMarket_api.persistence.entity.Compra
import com.felipe.kotlinMarket_api.persistence.entity.ComprasProducto
import com.felipe.kotlinMarket_api.persistence.entity.ComprasProductoPK
import com.felipe.kotlinMarket_api.persistence.mapper.PurchaseMapper
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class CompraRepository(
    private val compraCrudRepository: CompraCrudRepository,
    private val mapper: PurchaseMapper,
    private val entityManager: EntityManager
) : PurchaseRepository {

    override fun getAll(): List<Purchase> {
        return mapper.toPurchases(compraCrudRepository.findAll().toList())
    }

    override fun getByClient(clientId: String): Optional<List<Purchase>> {
        return compraCrudRepository.findByIdCliente(clientId)
            .map { compras -> mapper.toPurchases(compras) }
    }

    override fun save(purchase: Purchase): Purchase {
        val compra = mapper.toCompra(purchase)
        compra.productos.forEach { it.compra = compra }
        return mapper.toPurchase(compraCrudRepository.save(compra))
    }
}
