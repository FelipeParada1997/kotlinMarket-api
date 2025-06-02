package com.felipe.kotlinMarket_api.persistence.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ComprasProductoTest {

    private lateinit var comprasProducto1: ComprasProducto
    private lateinit var comprasProducto2: ComprasProducto
    private lateinit var comprasProducto3: ComprasProducto

    @BeforeEach
    fun setup() {
        val pk1 = ComprasProductoPK(1, 1)
        val pk2 = ComprasProductoPK(1, 1) // Mismo PK que pk1
        val pk3 = ComprasProductoPK(2, 2) // PK diferente

        comprasProducto1 = ComprasProducto(
            id = pk1,
            cantidad = 2,
            total = 20.0,
            estado = "1"
        )

        comprasProducto2 = ComprasProducto(
            id = pk2,
            cantidad = 3, // Cantidad diferente
            total = 30.0, // Total diferente
            estado = "1"
        )

        comprasProducto3 = ComprasProducto(
            id = pk3,
            cantidad = 2,
            total = 20.0,
            estado = "1"
        )
    }

    @Test
    fun equals_withSameObject_shouldReturnTrue() {
        assertTrue(comprasProducto1.equals(comprasProducto1))
    }

    @Test
    fun equals_withNullObject_shouldReturnFalse() {
        assertFalse(comprasProducto1.equals(null))
    }

    @Test
    fun equals_withDifferentClassObject_shouldReturnFalse() {
        assertFalse(comprasProducto1.equals("Not a ComprasProducto"))
    }

    @Test
    fun equals_withSamePK_shouldReturnTrue() {
        // Dos objetos con mismo PK deben ser iguales aunque otros campos sean diferentes
        assertTrue(comprasProducto1.equals(comprasProducto2))
        assertTrue(comprasProducto2.equals(comprasProducto1))
    }

    @Test
    fun equals_withDifferentPK_shouldReturnFalse() {
        // Dos objetos con PK diferente no deben ser iguales aunque otros campos sean iguales
        assertFalse(comprasProducto1.equals(comprasProducto3))
    }

    @Test
    fun hashCode_withSamePK_shouldBeEqual() {
        assertEquals(comprasProducto1.hashCode(), comprasProducto2.hashCode())
    }

    @Test
    fun hashCode_withDifferentPK_shouldBeDifferent() {
        assertNotEquals(comprasProducto1.hashCode(), comprasProducto3.hashCode())
    }

    @Test
    fun accessors_shouldGetAndSetValues() {
        val comprasProducto = ComprasProducto()
        val pk = ComprasProductoPK(5, 10)

        comprasProducto.id = pk
        comprasProducto.cantidad = 5
        comprasProducto.total = 50.0
        comprasProducto.estado = "0"

        assertEquals(pk, comprasProducto.id)
        assertEquals(5, comprasProducto.cantidad)
        assertEquals(50.0, comprasProducto.total)
        assertEquals("0", comprasProducto.estado)
    }
}
