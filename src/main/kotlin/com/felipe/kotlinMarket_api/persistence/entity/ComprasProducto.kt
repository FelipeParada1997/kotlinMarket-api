package com.felipe.kotlinMarket_api.persistence.entity

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.EmbeddedId
import jakarta.persistence.ManyToOne
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import java.io.Serializable
import java.util.Objects

@Entity
@Table(name = "compras_productos")
class ComprasProducto (

    @EmbeddedId
    var id : ComprasProductoPK? = null,

    var cantidad : Int? = null,

    var total : Double? = null,

    var estado : String? = null,

    @ManyToOne

    @MapsId("idCompra")
    @JoinColumn(name = "id_compra")
    var compra : Compra? = null,

    @ManyToOne
    @JoinColumn(name = "id_producto", insertable = false, updatable = false)
    var producto : Producto? = null
): Serializable {

    // Sobreescribir equals y hashCode para asegurar identidad correcta
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        val that = other as ComprasProducto
        return Objects.equals(id, that.id)
    }

    override fun hashCode(): Int {
        return Objects.hash(id)
    }
}