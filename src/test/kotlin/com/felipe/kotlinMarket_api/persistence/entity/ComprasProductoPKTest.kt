package com.felipe.kotlinMarket_api.persistence.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ComprasProductoPKTest {

    @Test
    fun equals_withSameObject_shouldReturnTrue() {
        val pk = ComprasProductoPK(1, 1)
        assertTrue(pk.equals(pk))
    }

    @Test
    fun equals_withNullObject_shouldReturnFalse() {
        val pk = ComprasProductoPK(1, 1)
        assertFalse(pk.equals(null))
    }

    @Test
    fun equals_withDifferentClassObject_shouldReturnFalse() {
        val pk = ComprasProductoPK(1, 1)
        assertFalse(pk.equals("Not a ComprasProductoPK"))
    }

    @Test
    fun equals_withSameValues_shouldReturnTrue() {
        val pk1 = ComprasProductoPK(1, 1)
        val pk2 = ComprasProductoPK(1, 1)
        assertTrue(pk1.equals(pk2))
        assertTrue(pk2.equals(pk1))
    }

    @Test
    fun equals_withDifferentIdCompra_shouldReturnFalse() {
        val pk1 = ComprasProductoPK(1, 1)
        val pk2 = ComprasProductoPK(2, 1)
        assertFalse(pk1.equals(pk2))
    }

    @Test
    fun equals_withDifferentIdProducto_shouldReturnFalse() {
        val pk1 = ComprasProductoPK(1, 1)
        val pk2 = ComprasProductoPK(1, 2)
        assertFalse(pk1.equals(pk2))
    }

    @Test
    fun hashCode_withSameValues_shouldBeEqual() {
        val pk1 = ComprasProductoPK(1, 1)
        val pk2 = ComprasProductoPK(1, 1)
        assertEquals(pk1.hashCode(), pk2.hashCode())
    }

    @Test
    fun hashCode_withDifferentValues_shouldBeDifferent() {
        val pk1 = ComprasProductoPK(1, 1)
        val pk2 = ComprasProductoPK(2, 2)
        assertNotEquals(pk1.hashCode(), pk2.hashCode())
    }

    @Test
    fun constructor_withNullValues_shouldCreateObjectWithNullValues() {
        val pk = ComprasProductoPK(null, null)
        assertNull(pk.idCompra)
        assertNull(pk.idProducto)
    }
}
