package com.felipe.kotlinMarket_api.persistence.mapper

import com.felipe.kotlinMarket_api.domain.Category
import com.felipe.kotlinMarket_api.persistence.entity.Categoria
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring")
interface CategoryMapper {

    @Mappings(
        Mapping(source = "idCategoria", target = "categoryId"),
        Mapping(source = "descripcion", target = "category"),
        Mapping(source = "estado", target = "active")
    )
    fun toCategory(categoria: Categoria): Category

    @InheritInverseConfiguration
    @Mapping(target = "productos", ignore = true)
    fun toCategoria(category: Category): Categoria
}