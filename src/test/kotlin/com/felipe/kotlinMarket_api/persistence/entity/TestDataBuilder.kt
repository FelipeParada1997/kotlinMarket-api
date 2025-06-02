package com.felipe.kotlinMarket_api.persistence.entity

import com.felipe.kotlinMarket_api.domain.Product
import com.felipe.kotlinMarket_api.domain.Purchase
import com.felipe.kotlinMarket_api.domain.PurchaseItem
import java.time.LocalDateTime

/**
 * Clase de utilidad para crear objetos de prueba
 */
class TestDataBuilder {
    companion object {

        /**
         * Crea un objeto Producto para pruebas
         */
        fun createProducto(id: Int = 1): Producto {
            return Producto(
                idProducto = id,
                nombre = "Producto Test $id",
                idCategoria = 1,
                codigoBarras = "123456789$id",
                precioVenta = 10.0 * id,
                cantidadStock = 100,
                estado = "true"
            )
        }

        /**
         * Crea un objeto Product para pruebas
         */
        fun createProduct(id: Int = 1): Product {
            return Product(
                productId = id,
                name = "Product Test $id",
                categoryId = 1,
                price = 10.0 * id,
                stock = 100,
                active = true,
                category = null
            )
        }

        /**
         * Crea un objeto Categoria para pruebas
         */
        fun createCategoria(id: Int = 1): Categoria {
            return Categoria(
                idCategoria = id,
                descripcion = "Categoría Test $id",
                estado = true
            )
        }

        /**
         * Crea un objeto Compra para pruebas
         */
        fun createCompra(id: Int = 1): Compra {
            return Compra(
                idCompra = id,
                idCliente = "123",
                fecha = LocalDateTime.now(),
                medioPago = "Efectivo",
                comentario = "Compra Test $id",
                estado = "true"
            )
        }

        /**
         * Crea un objeto Purchase para pruebas
         */
        fun createPurchase(id: Int = 1): Purchase {
            return Purchase(
                purchaseId = id,
                clientId = "123",
                date = LocalDateTime.now(),
                paymentMethod = "Efectivo",
                comment = "Purchase Test $id",
                state = "true",
                items = listOf(createPurchaseItem())
            )
        }

        /**
         * Crea un objeto PurchaseItem para pruebas
         */
        fun createPurchaseItem(id: Int = 1): PurchaseItem {
            return PurchaseItem(
                productId = id,
                quantity = 2,
                total = 20.0 * id,
                active = true
            )
        }
    }
}
