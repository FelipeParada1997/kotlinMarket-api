package com.felipe.kotlinMarket_api.persistence

import com.felipe.kotlinMarket_api.domain.Product
import com.felipe.kotlinMarket_api.persistence.crud.ProductoCrudRepository
import com.felipe.kotlinMarket_api.persistence.entity.Producto
import com.felipe.kotlinMarket_api.persistence.mapper.ProductMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class ProductoRepositoryTest {

    @Mock
    private lateinit var productoCrudRepository: ProductoCrudRepository

    @Mock
    private lateinit var mapper: ProductMapper

    @InjectMocks
    private lateinit var productoRepository: ProductoRepository

    private lateinit var testProducto: Producto
    private lateinit var testProduct: Product
    private lateinit var productosList: List<Producto>
    private lateinit var productsList: List<Product>

    @BeforeEach
    fun setup() {
        // Crear datos de prueba para entidades
        testProducto = Producto(
            idProducto = 1,
            nombre = "Producto Test",
            idCategoria = 1,
            codigoBarras = "123456",
            precioVenta = 10.0,
            cantidadStock = 100,
            estado = "true"
        )

        productosList = listOf(
            testProducto,
            Producto(
                idProducto = 2,
                nombre = "Otro Producto",
                idCategoria = 1,
                codigoBarras = "654321",
                precioVenta = 20.0,
                cantidadStock = 50,
                estado = "true"
            )
        )

        // Crear datos de prueba para modelos de dominio
        testProduct = Product(
            productId = 1,
            name = "Producto Test",
            categoryId = 1,
            price = 10.0,
            stock = 100,
            active = true,
            category = null
        )

        productsList = listOf(
            testProduct,
            Product(
                productId = 2,
                name = "Otro Producto",
                categoryId = 1,
                price = 20.0,
                stock = 50,
                active = true,
                category = null
            )
        )
    }

    @Test
    fun getAll_shouldReturnAllProducts() {
        // Configurar mocks
        Mockito.`when`(productoCrudRepository.findAll()).thenReturn(productosList)
        Mockito.`when`(mapper.toProducts(productosList)).thenReturn(productsList)

        // Ejecutar método a probar
        val result = productoRepository.getAll()

        // Verificar resultado
        assertEquals(2, result.size)
        assertEquals(testProduct.productId, result[0].productId)

        // Verificar que los métodos fueron llamados
        Mockito.verify(productoCrudRepository).findAll()
        Mockito.verify(mapper).toProducts(productosList)
    }

    @Test
    fun getByCategory_shouldReturnProductsByCategory() {
        // Configurar mocks
        Mockito.`when`(productoCrudRepository.findByIdCategoriaOrderByNombreAsc(1)).thenReturn(productosList)
        Mockito.`when`(mapper.toProducts(productosList)).thenReturn(productsList)

        // Ejecutar método a probar
        val result = productoRepository.getByCategory(1)

        // Verificar resultado
        assertTrue(result.isPresent)
        assertEquals(2, result.get().size)

        // Verificar que los métodos fueron llamados
        Mockito.verify(productoCrudRepository).findByIdCategoriaOrderByNombreAsc(1)
        Mockito.verify(mapper).toProducts(productosList)
    }

    @Test
    fun getScarseProducts_whenProductsExist_shouldReturnProducts() {
        // Configurar mocks
        Mockito.`when`(productoCrudRepository.findByCantidadStockLessThanAndEstado(10, "true"))
            .thenReturn(Optional.of(productosList))
        Mockito.`when`(mapper.toProducts(productosList)).thenReturn(productsList)

        // Ejecutar método a probar
        val result = productoRepository.getScarseProducts(10)

        // Verificar resultado
        assertTrue(result.isPresent)
        assertEquals(2, result.get().size)

        // Verificar que los métodos fueron llamados
        Mockito.verify(productoCrudRepository).findByCantidadStockLessThanAndEstado(10, "true")
        Mockito.verify(mapper).toProducts(productosList)
    }

    @Test
    fun getProduct_whenProductExists_shouldReturnProduct() {
        // Configurar mocks
        Mockito.`when`(productoCrudRepository.findById(1)).thenReturn(Optional.of(testProducto))
        Mockito.`when`(mapper.toProduct(testProducto)).thenReturn(testProduct)

        // Ejecutar método a probar
        val result = productoRepository.getProduct(1)

        // Verificar resultado
        assertTrue(result.isPresent)
        assertEquals(testProduct.productId, result.get().productId)

        // Verificar que los métodos fueron llamados
        Mockito.verify(productoCrudRepository).findById(1)
        Mockito.verify(mapper).toProduct(testProducto)
    }

    @Test
    fun getProduct_whenProductDoesNotExist_shouldReturnEmpty() {
        // Configurar mocks
        Mockito.`when`(productoCrudRepository.findById(999)).thenReturn(Optional.empty())

        // Ejecutar método a probar
        val result = productoRepository.getProduct(999)

        // Verificar resultado
        assertFalse(result.isPresent)

        // Verificar que los métodos fueron llamados
        Mockito.verify(productoCrudRepository).findById(999)
        Mockito.verify(mapper, Mockito.never()).toProduct(Mockito.any())
    }

    @Test
    fun saveProduct_shouldSaveAndReturnProduct() {
        // Configurar mocks
        Mockito.`when`(mapper.toProducto(testProduct)).thenReturn(testProducto)
        Mockito.`when`(productoCrudRepository.save(testProducto)).thenReturn(testProducto)
        Mockito.`when`(mapper.toProduct(testProducto)).thenReturn(testProduct)

        // Ejecutar método a probar
        val result = productoRepository.saveProduct(testProduct)

        // Verificar resultado
        assertEquals(testProduct.productId, result.productId)

        // Verificar que los métodos fueron llamados
        Mockito.verify(mapper).toProducto(testProduct)
        Mockito.verify(productoCrudRepository).save(testProducto)
        Mockito.verify(mapper).toProduct(testProducto)
    }

    @Test
    fun deleteProduct_shouldCallCrudRepository() {
        // Configurar mock para no hacer nada al llamar a deleteById
        Mockito.doNothing().`when`(productoCrudRepository).deleteById(1)

        // Ejecutar método a probar
        productoRepository.deleteProduct(1)

        // Verificar que el método fue llamado
        Mockito.verify(productoCrudRepository).deleteById(1)
    }
}
