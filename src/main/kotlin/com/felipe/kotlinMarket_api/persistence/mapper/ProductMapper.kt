package com.felipe.kotlinMarket_api.persistence.mapper

import com.felipe.kotlinMarket_api.domain.Product
import com.felipe.kotlinMarket_api.persistence.entity.Producto
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring", uses = [CategoryMapper::class])
interface ProductMapper {
    @Mappings(
        Mapping(source = "idProducto", target = "productId"),
        Mapping(source = "nombre", target = "name"),
        Mapping(source = "idCategoria", target = "categoryId"),
        Mapping(source = "precioVenta", target = "price"),
        Mapping(source = "cantidadStock", target = "stock"),
        Mapping(source = "estado", target = "active"),
        Mapping(source = "categoria", target = "category")
    )
    fun toProduct(producto: Producto): Product
    fun toProducts(productos: List<Producto>): List<Product>

    @InheritInverseConfiguration
    @Mapping(target = "codigoBarras", ignore = true)
    fun toProducto(product: Product): Producto
}