package com.felipe.kotlinMarket_api.persistence.mapper

import com.felipe.kotlinMarket_api.domain.Purchase
import com.felipe.kotlinMarket_api.persistence.entity.Compra
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.*

@ExtendWith(SpringExtension::class)
@SpringBootTest
@ActiveProfiles("test")
class PurchaseMapperTest {

    @Autowired
    private lateinit var mapper: PurchaseMapper

    @Test
    fun `toPurchase should map Compra to Purchase correctly`() {
        // Arrange
        val fecha = LocalDateTime.now()
        val compra = Compra(
            idCompra = 1,
            idCliente = "123",
            fecha = fecha,
            medioPago = "Efectivo",
            comentario = "Test compra",
            estado = "A"
        )

        // Act
        val purchase = mapper.toPurchase(compra)

        // Assert
        assertEquals(compra.idCompra, purchase.purchaseId)
        assertEquals(compra.idCliente, purchase.clientId)
        assertEquals(compra.fecha, purchase.date)
        assertEquals(compra.medioPago, purchase.paymentMethod)
        assertEquals(compra.comentario, purchase.comment)
        assertEquals(compra.estado, purchase.state)
        assertTrue(purchase.items.isEmpty())
    }

    @Test
    fun `toPurchases should map list of Compra to list of Purchase correctly`() {
        // Arrange
        val fecha = LocalDateTime.now()
        val compras = listOf(
            Compra(
                idCompra = 1,
                idCliente = "123",
                fecha = fecha,
                medioPago = "Efectivo",
                comentario = "Test compra 1",
                estado = "A"
            ),
            Compra(
                idCompra = 2,
                idCliente = "456",
                fecha = fecha,
                medioPago = "Tarjeta",
                comentario = "Test compra 2",
                estado = "A"
            )
        )

        // Act
        val purchases = mapper.toPurchases(compras)

        // Assert
        assertEquals(compras.size, purchases.size)
        for (i in compras.indices) {
            assertEquals(compras[i].idCompra, purchases[i].purchaseId)
            assertEquals(compras[i].idCliente, purchases[i].clientId)
            assertEquals(compras[i].fecha, purchases[i].date)
            assertEquals(compras[i].medioPago, purchases[i].paymentMethod)
            assertEquals(compras[i].comentario, purchases[i].comment)
            assertEquals(compras[i].estado, purchases[i].state)
        }
    }

    @Test
    fun `toCompra should map Purchase to Compra correctly`() {
        // Arrange
        val fecha = LocalDateTime.now()
        val purchase = Purchase(
            purchaseId = 1,
            clientId = "123",
            date = fecha,
            paymentMethod = "Efectivo",
            comment = "Test purchase",
            state = "A"
        )

        // Act
        val compra = mapper.toCompra(purchase)

        // Assert
        assertEquals(purchase.purchaseId, compra.idCompra)
        assertEquals(purchase.clientId, compra.idCliente)
        assertEquals(purchase.date, compra.fecha)
        assertEquals(purchase.paymentMethod, compra.medioPago)
        assertEquals(purchase.comment, compra.comentario)
        assertEquals(purchase.state, compra.estado)
        assertNull(compra.cliente) // Debe ser ignorado según la configuración del mapper
    }
}
