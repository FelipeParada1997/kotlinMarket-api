package com.felipe.kotlinMarket_api.web.controller

import com.felipe.kotlinMarket_api.domain.Product
import com.felipe.kotlinMarket_api.domain.service.ProductService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class ProductControllerTest {

    @Mock
    private lateinit var productService: ProductService

    @InjectMocks
    private lateinit var productController: ProductController

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
    fun getAllProducts_shouldReturnAllProducts() {
        // Configurar mock
        Mockito.`when`(productService.getAll()).thenReturn(productList)

        // Ejecutar método a probar
        val response = productController.getAllProducts()

        // Verificar resultado
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(2, response.body?.size)
        assertEquals(testProduct.productId, response.body?.get(0)?.productId)

        // Verificar que el método del servicio fue llamado
        Mockito.verify(productService).getAll()
    }

    @Test
    fun getProductById_whenProductExists_shouldReturnProduct() {
        // Configurar mock
        Mockito.`when`(productService.getProduct(1)).thenReturn(Optional.of(testProduct))

        // Ejecutar método a probar
        val response = productController.getProductById(1)

        // Verificar resultado
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(testProduct.productId, response.body?.productId)
        assertEquals(testProduct.name, response.body?.name)

        // Verificar que el método del servicio fue llamado
        Mockito.verify(productService).getProduct(1)
    }

    @Test
    fun getProductById_whenProductDoesNotExist_shouldReturnNotFound() {
        // Configurar mock
        Mockito.`when`(productService.getProduct(999)).thenReturn(Optional.empty())

        // Ejecutar método a probar
        val response = productController.getProductById(999)

        // Verificar resultado
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertNull(response.body)

        // Verificar que el método del servicio fue llamado
        Mockito.verify(productService).getProduct(999)
    }

    @Test
    fun getProductsByCategory_whenProductsExist_shouldReturnProducts() {
        // Configurar mock
        Mockito.`when`(productService.getByCategory(1)).thenReturn(Optional.of(productList))

        // Ejecutar método a probar
        val response = productController.getProductsByCategory(1)

        // Verificar resultado
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(2, response.body?.size)

        // Verificar que el método del servicio fue llamado
        Mockito.verify(productService).getByCategory(1)
    }

    @Test
    fun getProductsByCategory_whenNoProductsExist_shouldReturnNotFound() {
        // Configurar mock
        Mockito.`when`(productService.getByCategory(999)).thenReturn(Optional.empty())

        // Ejecutar método a probar
        val response = productController.getProductsByCategory(999)

        // Verificar resultado
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertNull(response.body)

        // Verificar que el método del servicio fue llamado
        Mockito.verify(productService).getByCategory(999)
    }

    @Test
    fun saveProduct_shouldSaveAndReturnProduct() {
        // Configurar mock
        Mockito.`when`(productService.saveProduct(testProduct)).thenReturn(testProduct)

        // Ejecutar método a probar
        val response = productController.saveProduct(testProduct)

        // Verificar resultado
        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(testProduct.productId, response.body?.productId)
        assertEquals(testProduct.name, response.body?.name)

        // Verificar que el método del servicio fue llamado
        Mockito.verify(productService).saveProduct(testProduct)
    }

    @Test
    fun deleteProduct_whenProductExists_shouldReturnOk() {
        // Configurar mock
        Mockito.`when`(productService.deleteProduct(1)).thenReturn(true)

        // Ejecutar método a probar
        val response = productController.deleteProduct(1)

        // Verificar resultado
        assertEquals(HttpStatus.OK, response.statusCode)

        // Verificar que el método del servicio fue llamado
        Mockito.verify(productService).deleteProduct(1)
    }

    @Test
    fun deleteProduct_whenProductDoesNotExist_shouldReturnNotFound() {
        // Configurar mock
        Mockito.`when`(productService.deleteProduct(999)).thenReturn(false)

        // Ejecutar método a probar
        val response = productController.deleteProduct(999)

        // Verificar resultado
        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)

        // Verificar que el método del servicio fue llamado
        Mockito.verify(productService).deleteProduct(999)
    }
}
