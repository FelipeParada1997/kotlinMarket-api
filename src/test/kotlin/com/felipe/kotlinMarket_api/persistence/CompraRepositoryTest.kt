package com.felipe.kotlinMarket_api.persistence

import com.felipe.kotlinMarket_api.domain.Purchase
import com.felipe.kotlinMarket_api.domain.PurchaseItem
import com.felipe.kotlinMarket_api.persistence.crud.CompraCrudRepository
import com.felipe.kotlinMarket_api.persistence.entity.Compra
import com.felipe.kotlinMarket_api.persistence.entity.ComprasProducto
import com.felipe.kotlinMarket_api.persistence.entity.ComprasProductoPK
import com.felipe.kotlinMarket_api.persistence.mapper.PurchaseMapper
import jakarta.persistence.EntityManager
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
class CompraRepositoryTest {

    @Mock
    private lateinit var compraCrudRepository: CompraCrudRepository

    @Mock
    private lateinit var mapper: PurchaseMapper

    @Mock
    private lateinit var entityManager: EntityManager

    @InjectMocks
    private lateinit var compraRepository: CompraRepository

    private lateinit var testCompra: Compra
    private lateinit var testPurchase: Purchase
    private lateinit var comprasList: List<Compra>
    private lateinit var purchasesList: List<Purchase>

    @BeforeEach
    fun setup() {
        // Crear datos de prueba para entidades
        val compraProductoPK = ComprasProductoPK(1, 1)
        val compraProducto = ComprasProducto(
            id = compraProductoPK,
            cantidad = 2,
            total = 20.0,
            estado = "1"
        )

        testCompra = Compra(
            idCompra = 1,
            idCliente = "123456",
            fecha = LocalDateTime.now(),
            medioPago = "EFECTIVO",
            comentario = "Comentario de prueba",
            estado = "PENDIENTE"
        )
        testCompra.productos.add(compraProducto)

        comprasList = listOf(
            testCompra,
            Compra(
                idCompra = 2,
                idCliente = "789012",
                fecha = LocalDateTime.now().minusDays(1),
                medioPago = "TARJETA",
                comentario = "Otra compra",
                estado = "ENTREGADO"
            )
        )

        // Crear datos de prueba para modelos de dominio
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

        purchasesList = listOf(
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
        // Configurar mocks
        Mockito.`when`(compraCrudRepository.findAll()).thenReturn(comprasList)
        Mockito.`when`(mapper.toPurchases(comprasList)).thenReturn(purchasesList)

        // Ejecutar método a probar
        val result = compraRepository.getAll()

        // Verificar resultado
        assertEquals(2, result.size)
        assertEquals(testPurchase.purchaseId, result[0].purchaseId)

        // Verificar que los métodos fueron llamados
        Mockito.verify(compraCrudRepository).findAll()
        Mockito.verify(mapper).toPurchases(comprasList)
    }

    @Test
    fun getByClient_whenPurchasesExist_shouldReturnPurchases() {
        // Configurar mocks
        Mockito.`when`(compraCrudRepository.findByIdCliente("123456")).thenReturn(Optional.of(listOf(testCompra)))
        Mockito.`when`(mapper.toPurchases(listOf(testCompra))).thenReturn(listOf(testPurchase))

        // Ejecutar método a probar
        val result = compraRepository.getByClient("123456")

        // Verificar resultado
        assertTrue(result.isPresent)
        assertEquals(1, result.get().size)
        assertEquals(testPurchase.purchaseId, result.get()[0].purchaseId)

        // Verificar que los métodos fueron llamados
        Mockito.verify(compraCrudRepository).findByIdCliente("123456")
        Mockito.verify(mapper).toPurchases(listOf(testCompra))
    }

    @Test
    fun getByClient_whenNoPurchasesExist_shouldReturnEmpty() {
        // Configurar mock
        Mockito.`when`(compraCrudRepository.findByIdCliente("999999")).thenReturn(Optional.empty())

        // Ejecutar método a probar
        val result = compraRepository.getByClient("999999")

        // Verificar resultado
        assertFalse(result.isPresent)

        // Verificar que el método fue llamado
        Mockito.verify(compraCrudRepository).findByIdCliente("999999")
        Mockito.verify(mapper, Mockito.never()).toPurchases(Mockito.anyList())
    }

    @Test
    fun save_withValidPurchase_shouldSaveAndReturnPurchase() {
        // Configurar comportamiento del EntityManager
        Mockito.doNothing().`when`(entityManager).persist(Mockito.any(Compra::class.java))
        Mockito.doNothing().`when`(entityManager).flush()
        Mockito.doNothing().`when`(entityManager).persist(Mockito.any(ComprasProducto::class.java))
        Mockito.doNothing().`when`(entityManager).refresh(Mockito.any(Compra::class.java))

        // Configurar mock del mapper
        Mockito.`when`(mapper.toPurchase(Mockito.any(Compra::class.java))).thenReturn(testPurchase)

        // Ejecutar método a probar
        val result = compraRepository.save(testPurchase)

        // Verificar resultado
        assertNotNull(result)
        assertEquals(testPurchase.purchaseId, result.purchaseId)

        // Verificar que los métodos fueron llamados
        Mockito.verify(entityManager, Mockito.atLeastOnce()).persist(Mockito.any(Compra::class.java))
        Mockito.verify(entityManager).flush()
        Mockito.verify(entityManager).refresh(Mockito.any(Compra::class.java))
        Mockito.verify(mapper).toPurchase(Mockito.any(Compra::class.java))
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

        // Ejecutar método y verificar que lanza excepción
        val exception = assertThrows(IllegalArgumentException::class.java) {
            compraRepository.save(emptyPurchase)
        }

        // Verificar mensaje de error
        assertTrue(exception.message!!.contains("debe tener al menos un producto"))

        // Verificar que no se llamaron métodos de persistencia
        Mockito.verify(entityManager, Mockito.never()).persist(Mockito.any())
        Mockito.verify(mapper, Mockito.never()).toPurchase(Mockito.any())
    }
}
