package com.felipe.kotlinMarket_api.persistence.mapper

import com.felipe.kotlinMarket_api.domain.Product
import com.felipe.kotlinMarket_api.persistence.entity.Producto
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
@ActiveProfiles("test")
class ProductMapperTest {

    @Autowired
    private lateinit var mapper: ProductMapper

    @Test
    fun `toProduct should map Producto to Product correctly`() {
        // Arrange
        val producto = Producto(
            idProducto = 1,
            nombre = "Test Producto",
            idCategoria = 1,
            codigoBarras = "1234567890",
            precioVenta = 10.0,
            cantidadStock = 100,
            estado = true
        )

        // Act
        val product = mapper.toProduct(producto)

        // Assert
        assertEquals(producto.idProducto, product.productId)
        assertEquals(producto.nombre, product.name)
        assertEquals(producto.idCategoria, product.categoryId)
        assertEquals(producto.precioVenta, product.price)
        assertEquals(producto.cantidadStock, product.stock)
        assertEquals(producto.estado, product.active)
    }

    @Test
    fun `toProducts should map list of Producto to list of Product correctly`() {
        // Arrange
        val productos = listOf(
            Producto(
                idProducto = 1,
                nombre = "Test Producto 1",
                idCategoria = 1,
                codigoBarras = "1234567890",
                precioVenta = 10.0,
                cantidadStock = 100,
                estado = true
            ),
            Producto(
                idProducto = 2,
                nombre = "Test Producto 2",
                idCategoria = 1,
                codigoBarras = "0987654321",
                precioVenta = 20.0,
                cantidadStock = 200,
                estado = true
            )
        )

        // Act
        val products = mapper.toProducts(productos)

        // Assert
        assertEquals(productos.size, products.size)
        for (i in productos.indices) {
            assertEquals(productos[i].idProducto, products[i].productId)
            assertEquals(productos[i].nombre, products[i].name)
            assertEquals(productos[i].idCategoria, products[i].categoryId)
            assertEquals(productos[i].precioVenta, products[i].price)
            assertEquals(productos[i].cantidadStock, products[i].stock)
            assertEquals(productos[i].estado, products[i].active)
        }
    }

    @Test
    fun `toProducto should map Product to Producto correctly`() {
        // Arrange
        val product = Product(
            productId = 1,
            name = "Test Product",
            categoryId = 1,
            price = 10.0,
            stock = 100,
            active = true,
            category = null
        )

        // Act
        val producto = mapper.toProducto(product)

        // Assert
        assertEquals(product.productId, producto.idProducto)
        assertEquals(product.name, producto.nombre)
        assertEquals(product.categoryId, producto.idCategoria)
        assertEquals(product.price, producto.precioVenta)
        assertEquals(product.stock, producto.cantidadStock)
        assertEquals(product.active, producto.estado)
        assertNull(producto.codigoBarras) // Debe ser ignorado según la configuración del mapper
    }
}
