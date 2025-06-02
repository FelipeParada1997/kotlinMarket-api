package com.felipe.kotlinMarket_api.web.controller
package com.felipe.kotlinMarket_api.web.controller

import com.felipe.kotlinMarket_api.domain.Purchase
import com.felipe.kotlinMarket_api.domain.PurchaseItem
import com.felipe.kotlinMarket_api.domain.service.PurchaseService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import java.time.LocalDateTime
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class PurchaseControllerTest {

    @Mock
    private lateinit var purchaseService: PurchaseService

    @InjectMocks
    private lateinit var purchaseController: PurchaseController

    private lateinit var testPurchase: Purchase
    private lateinit var purchaseList: List<Purchase>

    @BeforeEach
    fun setup() {
        // Crear datos de prueba
        val purchaseItem = PurchaseItem(
            productId = 1,
            quantity = 2,
            total = 20.0,
            active = true
        )

        testPurchase = Purchase(
            purchaseId = 1,
            clientId = "123456",
            date = LocalDateTime.now(),
            paymentMethod = "EFECTIVO",
            comment = "Comentario de prueba",
            state = "PENDIENTE",
            items = listOf(purchaseItem)
        )

        purchaseList = listOf(
            testPurchase,
            Purchase(
                purchaseId = 2,
                clientId = "789012",
                date = LocalDateTime.now().minusDays(1),
                paymentMethod = "TARJETA",
                comment = "Otra compra",
                state = "ENTREGADO",
                items = listOf(purchaseItem)
            )
        )
    }

    @Test
    fun getAll_shouldReturnAllPurchases() {
        // Configurar mock
        Mockito.`when`(purchaseService.getAll()).thenReturn(purchaseList)

        // Ejecutar método a probar
        val response = purchaseController.getAll()

        // Verificar resultado
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(2, response.body?.size)
        assertEquals(testPurchase.purchaseId, response.body?.get(0)?.purchaseId)

        // Verificar que el método del servicio fue llamado
        Mockito.verify(purchaseService).getAll()
    }

    @Test
    fun getByClient_whenPurchasesExist_shouldReturnPurchases() {
        // Configurar mock
        Mockito.`when`(purchaseService.getByClient("123456")).thenReturn(Optional.of(listOf(testPurchase)))

        // Ejecutar método a probar
        val response = purchaseController.getByClient("123456")

        // Verificar resultado
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(1, response.body?.size)
        assertEquals(testPurchase.purchaseId, response.body?.get(0)?.purchaseId)

        // Verificar que el método del servicio fue llamado
        Mockito.verify(purchaseService).getByClient("123456")
    }

    @Test
    fun getByClient_whenNoPurchasesExist_shouldReturnNotFound() {
        // Configurar mock
        Mockito.`when`(purchaseService.getByClient("999999")).thenReturn(Optional.empty())

        // Ejecutar método a probar
        val response = purchaseController.getByClient("999999")

        // Verificar resultado
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertNull(response.body)

        // Verificar que el método del servicio fue llamado
        Mockito.verify(purchaseService).getByClient("999999")
    }

    @Test
    fun save_withValidPurchase_shouldSaveAndReturnPurchase() {
        // Configurar mock
        Mockito.`when`(purchaseService.save(testPurchase)).thenReturn(testPurchase)

        // Ejecutar método a probar
        val response = purchaseController.save(testPurchase)

        // Verificar resultado
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(testPurchase.purchaseId, (response.body as Purchase).purchaseId)

        // Verificar que el método del servicio fue llamado
        Mockito.verify(purchaseService).save(testPurchase)
    }

    @Test
    fun save_withEmptyItems_shouldReturnBadRequest() {
        // Crear purchase sin items
        val emptyPurchase = Purchase(
            purchaseId = 3,
            clientId = "123456",
            date = LocalDateTime.now(),
            paymentMethod = "EFECTIVO",
            comment = "Sin productos",
            state = "PENDIENTE",
            items = emptyList()
        )

        // Ejecutar método a probar
        val response = purchaseController.save(emptyPurchase)

        // Verificar resultado
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertTrue(response.body is Map<*, *>)
        assertEquals("La compra debe tener al menos un producto", (response.body as Map<*, *>)["error"])

        // Verificar que el método del servicio no fue llamado
        Mockito.verify(purchaseService, Mockito.never()).save(emptyPurchase)
    }

    @Test
    fun save_whenServiceThrowsIllegalArgumentException_shouldReturnBadRequest() {
        // Configurar mock para lanzar excepción
        val errorMessage = "Datos inválidos"
        Mockito.`when`(purchaseService.save(testPurchase)).thenThrow(IllegalArgumentException(errorMessage))

        // Ejecutar método a probar
        val response = purchaseController.save(testPurchase)

        // Verificar resultado
        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertTrue(response.body is Map<*, *>)
        assertEquals(errorMessage, (response.body as Map<*, *>)["error"])

        // Verificar que el método del servicio fue llamado
        Mockito.verify(purchaseService).save(testPurchase)
    }

    @Test
    fun save_whenServiceThrowsRuntimeException_shouldReturnInternalServerError() {
        // Configurar mock para lanzar excepción
        val errorMessage = "Error interno"
        Mockito.`when`(purchaseService.save(testPurchase)).thenThrow(RuntimeException(errorMessage))

        // Ejecutar método a probar
        val response = purchaseController.save(testPurchase)

        // Verificar resultado
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assertTrue(response.body is Map<*, *>)
        assertEquals("Error al procesar la compra", (response.body as Map<*, *>)["error"])
        assertEquals(errorMessage, (response.body as Map<*, *>)["message"])

        // Verificar que el método del servicio fue llamado
        Mockito.verify(purchaseService).save(testPurchase)
    }
}
import com.felipe.kotlinMarket_api.domain.Purchase
import com.felipe.kotlinMarket_api.domain.PurchaseItem
import com.felipe.kotlinMarket_api.domain.service.PurchaseService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.http.HttpStatus
import java.time.LocalDateTime
import java.util.Optional

class PurchaseControllerTest {

    @Mock
    private lateinit var purchaseService: PurchaseService

    @InjectMocks
    private lateinit var purchaseController: PurchaseController

    private lateinit var purchase: Purchase
    private lateinit var purchasesList: List<Purchase>

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)

        // Configuración de datos de prueba
        val purchaseItem = PurchaseItem(
            productId = 1,
            quantity = 2,
            total = 20.0,
            active = true
        )

        purchase = Purchase(
            purchaseId = 1,
            clientId = "123",
            date = LocalDateTime.now(),
            paymentMethod = "Efectivo",
            comment = "Compra de prueba",
            state = true,
            items = listOf(purchaseItem)
        )

        purchasesList = listOf(purchase)
    }

    @Test
    fun `getAll debería retornar todas las compras con estado OK`() {
        // Arrange
        `when`(purchaseService.getAll()).thenReturn(purchasesList)

        // Act
        val response = purchaseController.getAll()

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(purchasesList, response.body)
        verify(purchaseService, times(1)).getAll()
    }

    @Test
    fun `getByClient debería retornar compras con estado OK cuando el cliente tiene compras`() {
        // Arrange
        val clientId = "123"
        `when`(purchaseService.getByClient(clientId)).thenReturn(Optional.of(purchasesList))

        // Act
        val response = purchaseController.getByClient(clientId)

        // Assert
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(purchasesList, response.body)
        verify(purchaseService, times(1)).getByClient(clientId)
    }

    @Test
    fun `getByClient debería retornar estado NOT_FOUND cuando el cliente no tiene compras`() {
        // Arrange
        val clientId = "999"
        `when`(purchaseService.getByClient(clientId)).thenReturn(Optional.empty())

        // Act
        val response = purchaseController.getByClient(clientId)

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertNull(response.body)
        verify(purchaseService, times(1)).getByClient(clientId)
    }

    @Test
    fun `save debería retornar la compra guardada con estado CREATED`() {
        // Arrange
        `when`(purchaseService.save(purchase)).thenReturn(purchase)

        // Act
        val response = purchaseController.save(purchase)

        // Assert
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(purchase, response.body)
        verify(purchaseService, times(1)).save(purchase)
    }
}
