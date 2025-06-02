package com.felipe.kotlinMarket_api.web.controller

import com.felipe.kotlinMarket_api.domain.Product
import com.felipe.kotlinMarket_api.domain.service.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Productos", description = "Operaciones relacionadas con productos")
@RestController
@RequestMapping("/products")
class ProductController(
    @Autowired
    private val productService : ProductService

) {

    @Operation(summary = "Obtener todos los productos", description = "Retorna una lista de todos los productos disponibles.")
    @GetMapping("/all")
    fun getAllProducts(): ResponseEntity<List<Product>> {
        return ResponseEntity(productService.getAll(), HttpStatus.OK)
    }

    @Operation(summary = "Obtener producto por ID", description = "Retorna un producto específico según su ID.")
    @GetMapping("/{productId}")
    fun getProductById(@PathVariable("productId") productId: Int): ResponseEntity<Product> {
        return productService.getProduct(productId)
            .map { product -> ResponseEntity(product, HttpStatus.OK)}
            .orElse(ResponseEntity(HttpStatus.NOT_FOUND))
    }

    @Operation(summary = "Obtener productos por categoría", description = "Retorna productos filtrados por el ID de la categoría.")
    @GetMapping("/category/{categoryId}")
    fun getProductsByCategory(@PathVariable("categoryId") categoryId: Int): ResponseEntity<List<Product>> {
        return productService.getByCategory(categoryId)
            .map { products -> ResponseEntity(products, HttpStatus.OK) }
            .orElse(ResponseEntity(HttpStatus.NOT_FOUND))
    }

    @Operation(summary = "Guardar producto", description = "Guarda un nuevo producto en la base de datos.")
    @PostMapping("/save")
    fun saveProduct(@RequestBody product: Product): ResponseEntity<Product> {
        return ResponseEntity(productService.saveProduct(product), HttpStatus.CREATED)
    }

    @Operation(summary = "Eliminar producto", description = "Elimina un producto específico según su ID.")
    @DeleteMapping("/{productId}")
    fun deleteProduct(@PathVariable("productId") productId: Int): ResponseEntity<Unit> {
        return if (productService.deleteProduct(productId)) {
            ResponseEntity(HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

}

