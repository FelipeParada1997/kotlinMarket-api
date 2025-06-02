package com.felipe.kotlinMarket_api.persistence
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
        testProducto = Producto().apply {
            idProducto = 1
            nombre = "Producto Test"
            idCategoria = 1
            precioVenta = 10.0
            cantidadStock = 100
            estado = true
        }

        productosList = listOf(
            testProducto,
            Producto().apply {
                idProducto = 2
                nombre = "Otro Producto"
                idCategoria = 1
                precioVenta = 20.0
                cantidadStock = 50
                estado = true
            }
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
        Mockito.`when`(productoCrudRepository.findByCantidadStockLessThanAndEstado(10, true))
            .thenReturn(Optional.of(productosList))
        Mockito.`when`(mapper.toProducts(productosList)).thenReturn(productsList)

        // Ejecutar método a probar
        val result = productoRepository.getScarseProducts(10)

        // Verificar resultado
        assertTrue(result.isPresent)
        assertEquals(2, result.get().size)

        // Verificar que los métodos fueron llamados
        Mockito.verify(productoCrudRepository).findByCantidadStockLessThanAndEstado(10, true)
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
import com.felipe.kotlinMarket_api.domain.Product
import com.felipe.kotlinMarket_api.persistence.crud.ProductoCrudRepository
import com.felipe.kotlinMarket_api.persistence.entity.Producto
import com.felipe.kotlinMarket_api.persistence.mapper.ProductMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.Optional
import org.junit.jupiter.api.Assertions.*

class ProductoRepositoryTest {

    @Mock
    private lateinit var productoCrudRepository: ProductoCrudRepository

    @Mock
    private lateinit var mapper: ProductMapper

    @InjectMocks
    private lateinit var productoRepository: ProductoRepository

    private lateinit var producto: Producto
    private lateinit var productosList: List<Producto>
    private lateinit var product: Product
    private lateinit var productsList: List<Product>

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)

        // Configuración de datos de prueba
        producto = Producto(
            idProducto = 1,
            nombre = "Test Producto",
            idCategoria = 1,
            codigoBarras = "1234567890",
            precioVenta = 10.0,
            cantidadStock = 100,
            estado = true
        )

        product = Product(
            productId = 1,
            name = "Test Product",
            categoryId = 1,
            price = 10.0,
            stock = 100,
            active = true,
            category = null
        )

        productosList = listOf(producto)
        productsList = listOf(product)
    }

    @Test
    fun `getAll should return all products`() {
        // Arrange
        `when`(productoCrudRepository.findAll()).thenReturn(productosList)
        `when`(mapper.toProducts(productosList)).thenReturn(productsList)

        // Act
        val result = productoRepository.getAll()

        // Assert
        assertEquals(productsList, result)
        verify(productoCrudRepository, times(1)).findAll()
        verify(mapper, times(1)).toProducts(productosList)
    }

    @Test
    fun `getByCategory should return products by category`() {
        // Arrange
        val categoryId = 1
        `when`(productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId)).thenReturn(productosList)
        `when`(mapper.toProducts(productosList)).thenReturn(productsList)

        // Act
        val result = productoRepository.getByCategory(categoryId)

        // Assert
        assertTrue(result.isPresent)
        assertEquals(productsList, result.get())
        verify(productoCrudRepository, times(1)).findByIdCategoriaOrderByNombreAsc(categoryId)
        verify(mapper, times(1)).toProducts(productosList)
    }

    @Test
    fun `getScarseProducts should return products with stock less than quantity`() {
        // Arrange
        val quantity = 10
        `when`(productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true)).thenReturn(Optional.of(productosList))
        `when`(mapper.toProducts(productosList)).thenReturn(productsList)

        // Act
        val result = productoRepository.getScarseProducts(quantity)

        // Assert
        assertTrue(result.isPresent)
        assertEquals(productsList, result.get())
        verify(productoCrudRepository, times(1)).findByCantidadStockLessThanAndEstado(quantity, true)
        verify(mapper, times(1)).toProducts(productosList)
    }

    @Test
    fun `getProduct should return product when exists`() {
        // Arrange
        val productId = 1
        `when`(productoCrudRepository.findById(productId)).thenReturn(Optional.of(producto))
        `when`(mapper.toProduct(producto)).thenReturn(product)

        // Act
        val result = productoRepository.getProduct(productId)

        // Assert
        assertTrue(result.isPresent)
        assertEquals(product, result.get())
        verify(productoCrudRepository, times(1)).findById(productId)
        verify(mapper, times(1)).toProduct(producto)
    }

    @Test
    fun `getProduct should return empty optional when product does not exist`() {
        // Arrange
        val productId = 99
        `when`(productoCrudRepository.findById(productId)).thenReturn(Optional.empty())

        // Act
        val result = productoRepository.getProduct(productId)

        // Assert
        assertFalse(result.isPresent)
        verify(productoCrudRepository, times(1)).findById(productId)
        verify(mapper, never()).toProduct(any())
    }

    @Test
    fun `saveProduct should save and return product`() {
        // Arrange
        `when`(mapper.toProducto(product)).thenReturn(producto)
        `when`(productoCrudRepository.save(producto)).thenReturn(producto)
        `when`(mapper.toProduct(producto)).thenReturn(product)

        // Act
        val result = productoRepository.saveProduct(product)

        // Assert
        assertEquals(product, result)
        verify(mapper, times(1)).toProducto(product)
        verify(productoCrudRepository, times(1)).save(producto)
        verify(mapper, times(1)).toProduct(producto)
    }

    @Test
    fun `deleteProduct should delete product`() {
        // Arrange
        val productId = 1
        doNothing().`when`(productoCrudRepository).deleteById(productId)

        // Act
        productoRepository.deleteProduct(productId)

        // Assert
        verify(productoCrudRepository, times(1)).deleteById(productId)
    }
}
