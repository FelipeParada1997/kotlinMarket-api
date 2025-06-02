package com.felipe.kotlinMarket_api.persistence.crud

import com.felipe.kotlinMarket_api.persistence.entity.Producto
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface ProductoCrudRepository : CrudRepository<Producto, Int> {

    fun findByIdCategoriaOrderByNombreAsc(idCategoria : Int): List<Producto>

    fun findByCantidadStockLessThanAndEstado(cantidadStock : Int, estado : Boolean): Optional<List<Producto>>
}