package com.felipe.kotlinMarket_api.persistence.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.JoinColumn

@Entity
@Table(name = "productos")
class Producto (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    var idProducto : Int? = null,

    var nombre : String? = null,

    @Column(name = "id_categoria")
    var idCategoria : Int? ,

    @Column(name = "codigo_barras")
    var codigoBarras : String? ,

    @Column(name = "precio_venta")
    var precioVenta : Double?,

    @Column(name = "cantidad_stock")
    var cantidadStock : Int ?,

    var estado : Boolean? = null,

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id_categoria", insertable = false, updatable = false)
    var categoria : Categoria? = null
    ) {}