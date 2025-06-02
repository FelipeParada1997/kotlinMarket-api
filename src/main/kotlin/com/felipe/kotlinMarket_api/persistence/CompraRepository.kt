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

    @Transactional
    override fun save(purchase: Purchase): Purchase {
        // Validar que la compra tenga productos
        if (purchase.items.isEmpty()) {
            throw IllegalArgumentException("La compra debe tener al menos un producto en la lista 'items'")
        }

        try {
            // Crear la compra sin productos primero
            val compra = Compra(
                idCliente = purchase.clientId,
                fecha = purchase.date,
                medioPago = purchase.paymentMethod,
                comentario = purchase.comment,
                estado = purchase.state
            )

            // Guardar la compra para obtener su ID
            entityManager.persist(compra)
            entityManager.flush()

            // Una vez que tenemos el ID de compra, crear los productos
            for (item in purchase.items) {
                // Crear el ID compuesto
                val pk = ComprasProductoPK(compra.idCompra, item.productId)

                // Crear el producto de compra
                val compraProducto = ComprasProducto(
                    id = pk,
                    cantidad = item.quantity,
                    total = item.total,
                    estado = if (item.active) "1" else "0",
                    compra = compra
                )

                // Agregar a la lista de productos de la compra
                compra.productos.add(compraProducto)
            }

            // Guardar todo en un solo paso
            compraCrudRepository.save(compra)

            // Convertir y devolver
            return mapper.toPurchase(compra)
        } catch (e: Exception) {
            throw RuntimeException("Error al guardar la compra: ${e.message}", e)
        }
    }
}
