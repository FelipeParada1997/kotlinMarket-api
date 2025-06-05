package com.felipe.kotlinMarket_api.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
class Categoria (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_categoria")
        var idCategoria : Int? = null,

        var descripcion : String,

        var estado : Boolean,

        @OneToMany(mappedBy = "categoria", fetch = FetchType.LAZY)
        var productos : MutableList<Producto>? = mutableListOf()
){}
