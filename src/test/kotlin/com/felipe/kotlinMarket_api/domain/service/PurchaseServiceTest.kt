package com.felipe.kotlinMarket_api.domain.service
package com.felipe.kotlinMarket_api.domain.service

import com.felipe.kotlinMarket_api.domain.Purchase
import com.felipe.kotlinMarket_api.domain.PurchaseItem
import com.felipe.kotlinMarket_api.domain.repositories.PurchaseRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDateTime
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class PurchaseServiceTest {

    @Mock
    private lateinit var purchaseRepository: PurchaseRepository

    @InjectMocks
    private lateinit var purchaseService: PurchaseService

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
        Mockito.`when`(purchaseRepository.getAll()).thenReturn(purchaseList)

        // Ejecutar método a probar
        val result = purchaseService.getAll()

        // Verificar resultado
        assertEquals(2, result.size)
        assertEquals(testPurchase.purchaseId, result[0].purchaseId)
        assertEquals(testPurchase.clientId, result[0].clientId)

        // Verificar que el método del repositorio fue llamado
        Mockito.verify(purchaseRepository).getAll()
    }

    @Test
    fun getByClient_whenPurchasesExist_shouldReturnPurchases() {
        // Configurar mock
        Mockito.`when`(purchaseRepository.getByClient("123456")).thenReturn(Optional.of(listOf(testPurchase)))

        // Ejecutar método a probar
        val result = purchaseService.getByClient("123456")

        // Verificar resultado
        assertTrue(result.isPresent)
        assertEquals(1, result.get().size)
        assertEquals(testPurchase.purchaseId, result.get()[0].purchaseId)

        // Verificar que el método del repositorio fue llamado
        Mockito.verify(purchaseRepository).getByClient("123456")
    }

    @Test
    fun getByClient_whenNoPurchasesExist_shouldReturnEmpty() {
        // Configurar mock
        Mockito.`when`(purchaseRepository.getByClient("999999")).thenReturn(Optional.empty())

        // Ejecutar método a probar
        val result = purchaseService.getByClient("999999")

        // Verificar resultado
        assertFalse(result.isPresent)

        // Verificar que el método del repositorio fue llamado
        Mockito.verify(purchaseRepository).getByClient("999999")
    }

    @Test
    fun save_withValidPurchase_shouldSaveAndReturnPurchase() {
        // Configurar mock
        Mockito.`when`(purchaseRepository.save(testPurchase)).thenReturn(testPurchase)

        // Ejecutar método a probar
        val result = purchaseService.save(testPurchase)

        // Verificar resultado
        assertEquals(testPurchase.purchaseId, result.purchaseId)
        assertEquals(testPurchase.clientId, result.clientId)

        // Verificar que el método del repositorio fue llamado
        Mockito.verify(purchaseRepository).save(testPurchase)
    }

    @Test
    fun save_withEmptyItems_shouldThrowException() {
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

        // Ejecutar método a probar y verificar que lanza excepción
        val exception = assertThrows(IllegalArgumentException::class.java) {
            purchaseService.save(emptyPurchase)
        }

        // Verificar mensaje de la excepción
        assertEquals("La compra debe tener al menos un producto", exception.message)

        // Verificar que el método del repositorio no fue llamado
        Mockito.verify(purchaseRepository, Mockito.never()).save(emptyPurchase)
    }

    @Test
    fun save_whenRepositoryThrowsException_shouldPropagateException() {
        // Configurar mock para lanzar excepción
        val errorMessage = "Error en base de datos"
        Mockito.`when`(purchaseRepository.save(testPurchase)).thenThrow(RuntimeException(errorMessage))

        // Ejecutar método a probar y verificar que propaga la excepción
        val exception = assertThrows(RuntimeException::class.java) {
            purchaseService.save(testPurchase)
        }

        // Verificar mensaje de la excepción
        assertEquals(errorMessage, exception.message)

        // Verificar que el método del repositorio fue llamado
        Mockito.verify(purchaseRepository).save(testPurchase)
    }
}
import com.felipe.kotlinMarket_api.domain.Purchase
import com.felipe.kotlinMarket_api.domain.PurchaseItem
import com.felipe.kotlinMarket_api.domain.repositories.PurchaseRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.time.LocalDateTime
import java.util.Optional

class PurchaseServiceTest {

    @Mock
    private lateinit var purchaseRepository: PurchaseRepository

    @InjectMocks
    private lateinit var purchaseService: PurchaseService

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
            state = "A",
            items = listOf(purchaseItem)
        )

        purchasesList = listOf(purchase)
    }

    @Test
    fun `getAll debería retornar todas las compras`() {
        // Arrange
        `when`(purchaseRepository.getAll()).thenReturn(purchasesList)

        // Act
        val result = purchaseService.getAll()

        // Assert
        assertEquals(purchasesList, result)
        verify(purchaseRepository, times(1)).getAll()
    }

    @Test
    fun `getByClient debería retornar compras cuando el cliente existe`() {
        // Arrange
        val clientId = "123"
        `when`(purchaseRepository.getByClient(clientId)).thenReturn(Optional.of(purchasesList))

        // Act
        val result = purchaseService.getByClient(clientId)

        // Assert
        assertTrue(result.isPresent)
        assertEquals(purchasesList, result.get())
        verify(purchaseRepository, times(1)).getByClient(clientId)
    }

    @Test
    fun `getByClient debería retornar optional vacío cuando el cliente no tiene compras`() {
        // Arrange
        val clientId = "999"
        `when`(purchaseRepository.getByClient(clientId)).thenReturn(Optional.empty())

        // Act
        val result = purchaseService.getByClient(clientId)

        // Assert
        assertFalse(result.isPresent)
        verify(purchaseRepository, times(1)).getByClient(clientId)
    }

    @Test
    fun `save debería guardar y retornar la compra`() {
        // Arrange
        `when`(purchaseRepository.save(purchase)).thenReturn(purchase)

        // Act
        val result = purchaseService.save(purchase)

        // Assert
        assertEquals(purchase, result)
        verify(purchaseRepository, times(1)).save(purchase)
    }
}
