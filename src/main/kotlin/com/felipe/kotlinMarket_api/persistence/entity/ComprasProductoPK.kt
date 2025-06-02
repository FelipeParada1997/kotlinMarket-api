package com.felipe.kotlinMarket_api.persistence.entity

import jakarta.persistence.Embeddable
import jakarta.persistence.Column
import java.io.Serializable
import java.util.Objects

@Embeddable
class ComprasProductoPK : Serializable {

    @Column(name = "id_compra")
    var idCompra : Int? = null

    @Column(name = "id_producto")
    var idProducto : Int? = null

    constructor()

    constructor(idCompra: Int?, idProducto: Int?) {
        this.idCompra = idCompra
        this.idProducto = idProducto
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val that = other as ComprasProductoPK

        // Usar Objects.equals para manejar nulos correctamente
        return Objects.equals(idCompra, that.idCompra) &&
               Objects.equals(idProducto, that.idProducto)
    }

    override fun hashCode(): Int {
        // Usar Objects.hash para una implementación correcta con nulos
        return Objects.hash(idCompra, idProducto)
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}


