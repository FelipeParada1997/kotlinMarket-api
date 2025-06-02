package com.felipe.kotlinMarket_api.persistence.mapper

import com.felipe.kotlinMarket_api.domain.PurchaseItem
import com.felipe.kotlinMarket_api.persistence.entity.ComprasProducto
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings


@Mapper(componentModel = "spring", uses = [ProductMapper::class])
interface PurchaseItemMapper {

    @Mappings(
        Mapping(source = "id.idProducto", target = "productId"),
        Mapping(source = "cantidad", target = "quantity"),
        Mapping(source = "estado", target = "active")
    )
    fun toPurchaseItem(producto: ComprasProducto): PurchaseItem

    //Compatibilidad con MapStruct
    @InheritInverseConfiguration
    @Mapping(target = "compra", ignore = true)
    @Mapping(target = "producto", ignore = true)
    @Mapping(target = "id.idCompra", ignore = true)
    fun toComprasProducto(item: PurchaseItem): ComprasProducto


}
