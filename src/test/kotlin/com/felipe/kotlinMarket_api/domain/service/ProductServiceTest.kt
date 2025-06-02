package com.felipe.kotlinMarket_api.domain.service
package com.felipe.kotlinMarket_api.domain.service

import com.felipe.kotlinMarket_api.domain.Product
import com.felipe.kotlinMarket_api.domain.repositories.ProductRepository
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
class ProductServiceTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @InjectMocks
    private lateinit var productService: ProductService

    private lateinit var testProduct: Product
    private lateinit var productList: List<Product>

    @BeforeEach
    fun setup() {
        // Crear datos de prueba
        testProduct = Product(
            productId = 1,
            name = "Producto Test",
            categoryId = 1,
            price = 10.0,
            stock = 100,
            active = true,
            category = null
        )

        productList = listOf(
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
        // Configurar mock
        Mockito.`when`(productRepository.getAll()).thenReturn(productList)

        // Ejecutar método a probar
        val result = productService.getAll()

        // Verificar resultado
        assertEquals(2, result.size)
        assertEquals(testProduct.productId, result[0].productId)
        assertEquals(testProduct.name, result[0].name)

        // Verificar que el método del repositorio fue llamado
        Mockito.verify(productRepository).getAll()
    }

    @Test
    fun getProduct_whenProductExists_shouldReturnProduct() {
        // Configurar mock
        Mockito.`when`(productRepository.getProduct(1)).thenReturn(Optional.of(testProduct))

        // Ejecutar método a probar
        val result = productService.getProduct(1)

        // Verificar resultado
        assertTrue(result.isPresent)
        assertEquals(testProduct.productId, result.get().productId)
        assertEquals(testProduct.name, result.get().name)

        // Verificar que el método del repositorio fue llamado
        Mockito.verify(productRepository).getProduct(1)
    }

    @Test
    fun getProduct_whenProductDoesNotExist_shouldReturnEmpty() {
        // Configurar mock
        Mockito.`when`(productRepository.getProduct(999)).thenReturn(Optional.empty())

        // Ejecutar método a probar
        val result = productService.getProduct(999)

        // Verificar resultado
        assertFalse(result.isPresent)

        // Verificar que el método del repositorio fue llamado
        Mockito.verify(productRepository).getProduct(999)
    }

    @Test
    fun getByCategory_shouldReturnProductsByCategory() {
        // Configurar mock
        Mockito.`when`(productRepository.getByCategory(1)).thenReturn(Optional.of(productList))

        // Ejecutar método a probar
        val result = productService.getByCategory(1)

        // Verificar resultado
        assertTrue(result.isPresent)
        assertEquals(2, result.get().size)

        // Verificar que el método del repositorio fue llamado
        Mockito.verify(productRepository).getByCategory(1)
    }

    @Test
    fun saveProduct_shouldSaveAndReturnProduct() {
        // Configurar mock
        Mockito.`when`(productRepository.saveProduct(testProduct)).thenReturn(testProduct)

        // Ejecutar método a probar
        val result = productService.saveProduct(testProduct)

        // Verificar resultado
        assertEquals(testProduct.productId, result.productId)
        assertEquals(testProduct.name, result.name)

        // Verificar que el método del repositorio fue llamado
        Mockito.verify(productRepository).saveProduct(testProduct)
    }

    @Test
    fun deleteProduct_whenProductExists_shouldReturnTrue() {
        // Configurar mock
        Mockito.`when`(productRepository.getProduct(1)).thenReturn(Optional.of(testProduct))
        Mockito.doNothing().`when`(productRepository).deleteProduct(1)

        // Ejecutar método a probar
        val result = productService.deleteProduct(1)

        // Verificar resultado
        assertTrue(result)

        // Verificar que los métodos del repositorio fueron llamados
        Mockito.verify(productRepository).getProduct(1)
        Mockito.verify(productRepository).deleteProduct(1)
    }

    @Test
    fun deleteProduct_whenProductDoesNotExist_shouldReturnFalse() {
        // Configurar mock
        Mockito.`when`(productRepository.getProduct(999)).thenReturn(Optional.empty())

        // Ejecutar método a probar
        val result = productService.deleteProduct(999)

        // Verificar resultado
        assertFalse(result)

        // Verificar que el método getProduct fue llamado pero deleteProduct no
        Mockito.verify(productRepository).getProduct(999)
        Mockito.verify(productRepository, Mockito.never()).deleteProduct(999)
    }
}
import com.felipe.kotlinMarket_api.domain.Product
import com.felipe.kotlinMarket_api.domain.repositories.ProductRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.Optional

class ProductServiceTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @InjectMocks
    private lateinit var productService: ProductService

    private lateinit var product: Product
    private lateinit var productsList: List<Product>

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)

        // Configuración de datos de prueba
        product = Product(
            productId = 1,
            name = "Test Product",
            categoryId = 1,
            price = 10.0,
            stock = 100,
            active = true,
            category = null
        )

        productsList = listOf(product)
    }

    @Test
    fun `getAll should return all products`() {
        // Arrange
        `when`(productRepository.getAll()).thenReturn(productsList)

        // Act
        val result = productService.getAll()

        // Assert
        assertEquals(productsList, result)
        verify(productRepository, times(1)).getAll()
    }

    @Test
    fun `getProduct should return product when exists`() {
        // Arrange
        val productId = 1
        `when`(productRepository.getProduct(productId)).thenReturn(Optional.of(product))

        // Act
        val result = productService.getProduct(productId)

        // Assert
        assertTrue(result.isPresent)
        assertEquals(product, result.get())
        verify(productRepository, times(1)).getProduct(productId)
    }

    @Test
    fun `getProduct should return empty optional when product does not exist`() {
        // Arrange
        val productId = 99
        `when`(productRepository.getProduct(productId)).thenReturn(Optional.empty())

        // Act
        val result = productService.getProduct(productId)

        // Assert
        assertFalse(result.isPresent)
        verify(productRepository, times(1)).getProduct(productId)
    }

    @Test
    fun `getByCategory should return products when category exists`() {
        // Arrange
        val categoryId = 1
        `when`(productRepository.getByCategory(categoryId)).thenReturn(Optional.of(productsList))

        // Act
        val result = productService.getByCategory(categoryId)

        // Assert
        assertTrue(result.isPresent)
        assertEquals(productsList, result.get())
        verify(productRepository, times(1)).getByCategory(categoryId)
    }

    @Test
    fun `saveProduct should save and return product`() {
        // Arrange
        `when`(productRepository.saveProduct(product)).thenReturn(product)

        // Act
        val result = productService.saveProduct(product)

        // Assert
        assertEquals(product, result)
        verify(productRepository, times(1)).saveProduct(product)
    }

    @Test
    fun `deleteProduct should return true when product exists`() {
        // Arrange
        val productId = 1
        `when`(productRepository.getProduct(productId)).thenReturn(Optional.of(product))
        doNothing().`when`(productRepository).deleteProduct(productId)

        // Act
        val result = productService.deleteProduct(productId)

        // Assert
        assertTrue(result)
        verify(productRepository, times(1)).getProduct(productId)
        verify(productRepository, times(1)).deleteProduct(productId)
    }

    @Test
    fun `deleteProduct should return false when product does not exist`() {
        // Arrange
        val productId = 99
        `when`(productRepository.getProduct(productId)).thenReturn(Optional.empty())

        // Act
        val result = productService.deleteProduct(productId)

        // Assert
        assertFalse(result)
        verify(productRepository, times(1)).getProduct(productId)
        verify(productRepository, never()).deleteProduct(productId)
    }
}
