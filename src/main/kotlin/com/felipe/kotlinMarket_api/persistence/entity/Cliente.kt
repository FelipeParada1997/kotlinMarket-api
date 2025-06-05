package com.felipe.kotlinMarket_api.persistence.entity;

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Table
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
@Table(name = "clientes")
class Cliente (

    @Id
    var id : String?,
    var nombre : String?,
    var apellidos : String?,
    var celular : Long?,
    var direccion : String?,

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    var compras : MutableList<Compra>? = mutableListOf()
){}
