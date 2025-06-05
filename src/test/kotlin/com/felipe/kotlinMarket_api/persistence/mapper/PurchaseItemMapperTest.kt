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
        val pk = ComprasProductoPK(1, 5)
        val comprasProducto = ComprasProducto(
            id = pk,
            cantidad = 3,
            total = 30.0,
            estado = "1",
            compra = null,
            producto = null
        )

        val result = mapper.toPurchaseItem(comprasProducto)

        assertEquals(5, result.productId)
        assertEquals(3, result.quantity)
        assertEquals(30.0, result.total)
        assertTrue(result.active)
    }

    @Test
    fun toComprasProducto_shouldMapBasicFields() {
        val purchaseItem = PurchaseItem(
            productId = 5,
            quantity = 3,
            total = 30.0,
            active = true
        )

        val result = mapper.toComprasProducto(purchaseItem)

        assertNotNull(result.id)
        assertEquals(5, result.id?.idProducto)
        assertEquals(3, result.cantidad)
        assertEquals(30.0, result.total)
        assertEquals("1", result.estado)
    }
}