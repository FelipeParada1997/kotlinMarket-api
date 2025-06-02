package com.felipe.kotlinMarket_api.persistence.mapper

import com.felipe.kotlinMarket_api.domain.PurchaseItem
import com.felipe.kotlinMarket_api.persistence.entity.ComprasProducto
import com.felipe.kotlinMarket_api.persistence.entity.ComprasProductoPK
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers

class PurchaseItemMapperTest {

    private val mapper = Mappers.getMapper(PurchaseItemMapper::class.java)

    @Test
    fun toPurchaseItem_shouldMapCorrectly() {
        // Preparar datos de prueba
        val pk = ComprasProductoPK(1, 5)
        val comprasProducto = ComprasProducto(
            id = pk,
            cantidad = 3,
            total = 30.0,
            estado = "1"
        )

        // Ejecutar mapper
        val result = mapper.toPurchaseItem(comprasProducto)

        // Verificar mapeo correcto
        assertEquals(5, result.productId) // Desde id.idProducto
        assertEquals(3, result.quantity) // Desde cantidad
        assertEquals(30.0, result.total) // Desde total
        assertTrue(result.active) // Desde estado "1"
    }

    @Test
    fun estadoToBoolean_whenEstadoIs1_shouldReturnTrue() {
        // Ejecutar método
        val result = mapper.estadoToBoolean("1")

        // Verificar resultado
        assertTrue(result)
    }

    @Test
    fun estadoToBoolean_whenEstadoIsNot1_shouldReturnFalse() {
        // Ejecutar método con diferentes valores
        assertFalse(mapper.estadoToBoolean("0"))
        assertFalse(mapper.estadoToBoolean("2"))
        assertFalse(mapper.estadoToBoolean("ACTIVO"))
        assertFalse(mapper.estadoToBoolean(null))
    }

    @Test
    fun toComprasProducto_shouldMapBasicFields() {
        // Preparar datos de prueba
        val purchaseItem = PurchaseItem(
            productId = 5,
            quantity = 3,
            total = 30.0,
            active = true
        )

        // Ejecutar mapper
        val result = mapper.toComprasProducto(purchaseItem)

        // Verificar mapeo básico
        assertNotNull(result.id)
        assertEquals(5, result.id?.idProducto)
        assertEquals(3, result.cantidad)
        assertEquals(30.0, result.total)
        assertEquals("1", result.estado)
    }

    @Test
    fun toComprasProductoWithCompraId_shouldSetIdCompra() {
        // Preparar datos de prueba
        val purchaseItem = PurchaseItem(
            productId = 5,
            quantity = 3,
            total = 30.0,
            active = true
        )

        // Ejecutar método
        val result = mapper.toComprasProductoWithCompraId(purchaseItem, 10)

        // Verificar que se estableció el idCompra
        assertEquals(10, result.id?.idCompra)
        assertEquals(5, result.id?.idProducto)
    }
}
