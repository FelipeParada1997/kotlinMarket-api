package com.felipe.kotlinMarket_api.persistence.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.Date

class ComprasProductoTest {

    private lateinit var comprasProducto1: ComprasProducto
    private lateinit var comprasProducto2: ComprasProducto
    private lateinit var comprasProducto3: ComprasProducto

    @BeforeEach
    fun setup() {
        val pk1 = ComprasProductoPK(1, 1)
        val pk2 = ComprasProductoPK(1, 1)
        val pk3 = ComprasProductoPK(2, 2)

        val compraDummy = Compra(idCliente = null,
            fecha = LocalDateTime.now(),
            medioPago = "EFECTIVO",
            comentario = "Compra de prueba",
            estado = "ACTIVO")

        val productoDummy = Producto(
            idProducto = 1,
            nombre = "Producto de prueba",
            precioVenta = 10.0,
            cantidadStock = 100,
            estado = "ACTIVO",
            idCategoria = 1,
            codigoBarras = "1234567890"
        )

        comprasProducto1 = ComprasProducto(
            id = pk1,
            cantidad = 2,
            total = 20.0,
            estado = "1",
            compra = compraDummy,
            producto = productoDummy
        )

        comprasProducto2 = ComprasProducto(
            id = pk2,
            cantidad = 3,
            total = 30.0,
            estado = "1",
            compra = compraDummy,
            producto = productoDummy
        )

        comprasProducto3 = ComprasProducto(
            id = pk3,
            cantidad = 2,
            total = 20.0,
            estado = "1",
            compra = compraDummy,
            producto = productoDummy
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
        val pk = ComprasProductoPK(5, 10)
        val compraDummy = Compra(
            idCliente = null,
            fecha = LocalDateTime.now(),
            medioPago = "EFECTIVO",
            comentario = "Compra de prueba",
            estado = "ACTIVO"
        )
        val productoDummy = Producto(
            idProducto = 1,
            nombre = "Producto de prueba",
            precioVenta = 10.0,
            cantidadStock = 100,
            estado = "ACTIVO",
            idCategoria = 1,
            codigoBarras = "1234567890"
        )

        val comprasProducto = ComprasProducto(
            id = pk,
            cantidad = 5,
            total = 50.0,
            estado = "0",
            compra = compraDummy,
            producto = productoDummy
        )

        assertEquals(pk, comprasProducto.id)
        assertEquals(5, comprasProducto.cantidad)
        assertEquals(50.0, comprasProducto.total)
        assertEquals("0", comprasProducto.estado)
    }
}
