package com.felipe.kotlinMarket_api.persistence.crud


import com.felipe.kotlinMarket_api.persistence.entity.Compra
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface CompraCrudRepository : CrudRepository<Compra, Int> {
    fun findByIdCliente(idCliente: String): Optional<List<Compra>>

}