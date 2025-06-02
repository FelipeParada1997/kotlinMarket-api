package com.felipe.kotlinMarket_api.persistence

import com.felipe.kotlinMarket_api.domain.Product
import com.felipe.kotlinMarket_api.domain.repositories.ProductRepository
import com.felipe.kotlinMarket_api.persistence.crud.ProductoCrudRepository
import com.felipe.kotlinMarket_api.persistence.entity.Producto
import com.felipe.kotlinMarket_api.persistence.mapper.ProductMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.Optional
/*
 * Implementación de ProductRepository que interactúa con la base de datos a través de ProductoCrudRepository.
 * Utiliza ProductMapper para convertir entre entidades y modelos de dominio.
 */
@Repository
class ProductoRepository (
    // Inyecta ProductoCrudRepository
    @Autowired
    private val productoCrudRepository : ProductoCrudRepository,
    // Inyecta ProductMapper para convertir entre Producto y Product
    @Autowired
    private val mapper: ProductMapper

): ProductRepository {

    // Obtiene todos los productos de la base de datos y los convierte a una lista de Product usando el mapper.
    override fun getAll(): List<Product> {
        val productos : List<Producto> = productoCrudRepository.findAll().toList()

        return mapper.toProducts(productos)
    }

    // Obtiene productos por categoría, ordenados por nombre ascendente.
    override fun getByCategory(categoryId: Int): Optional<List<Product>> {
        val productos: List<Producto> = productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId)
        //
        return Optional.of(mapper.toProducts(productos))
    }

    // Obtiene productos con stock menor a la cantidad dada y activos.
    // Devuelve Optional<List<Product>>, vacío si no hay resultados.
    override fun getScarseProducts(quantity: Int): Optional<List<Product>> {
        val productos: Optional<List<Producto>> = productoCrudRepository.findByCantidadStockLessThanAndEstado(quantity, true)
    //.map convertirá Optional<List<Producto>> en Optional<List<Product>>.
        return productos.map {  listaProds -> mapper.toProducts(listaProds) }
    }

    // Busca el producto por su ID. Si lo encuentra, lo convierte a Product usando el mapper.
    override fun getProduct(productId: Int): Optional<Product> {
        return productoCrudRepository.findById(productId).map { prod -> mapper.toProduct(prod) }
    }

    // Guarda un producto en la base de datos y lo convierte a Product usando el mapper.
    override fun saveProduct(product: Product): Product {

        return mapper.toProduct(
            productoCrudRepository.save(mapper.toProducto(product))
        )
    }

    // Elimina un producto por su ID
    override fun deleteProduct(idProducto: Int) {
        productoCrudRepository.deleteById(idProducto)
    }
}