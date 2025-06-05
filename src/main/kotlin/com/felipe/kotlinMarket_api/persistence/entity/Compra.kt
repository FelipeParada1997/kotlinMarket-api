package com.felipe.kotlinMarket_api.persistence.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Column
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import java.time.LocalDateTime

@Entity
@Table(name = "compras")
class Compra (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_compra")
    var idCompra : Int? = null,

    @Column(name = "id_cliente")
    var idCliente : String?,

    var fecha : LocalDateTime?,

    @Column(name = "medio_pago")
    var medioPago : String?,

    var comentario : String?,

    var estado : String?,

    @ManyToOne
    @JoinColumn(name = "id_cliente", insertable = false, updatable = false )
    var cliente : Cliente? = null,

    @OneToMany(mappedBy = "compra", cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    var productos : MutableList<ComprasProducto> = mutableListOf()
){}