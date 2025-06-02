package com.felipe.kotlinMarket_api.persistence.mapper

import com.felipe.kotlinMarket_api.domain.PurchaseItem
import com.felipe.kotlinMarket_api.persistence.entity.ComprasProducto
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.Named

@Mapper(componentModel = "spring", uses = [ProductMapper::class])
interface PurchaseItemMapper {

    @Mappings(
        Mapping(source = "id.idProducto", target = "productId"),
        Mapping(source = "cantidad", target = "quantity"),
        Mapping(source = "estado", target = "active", qualifiedByName = ["estadoToBoolean"])
    )
    fun toPurchaseItem(producto: ComprasProducto): PurchaseItem

    @Named("estadoToBoolean")
    fun estadoToBoolean(estado: String?): Boolean {
        return estado == "1"
    }

    // Este método no debe usarse directamente, ya que no establece el idCompra
    // Se mantiene para compatibilidad con MapStruct
    @InheritInverseConfiguration
    @Mapping(target = "compra", ignore = true)
    @Mapping(target = "producto", ignore = true)
    @Mapping(target = "id.idCompra", ignore = true)
    fun toComprasProducto(item: PurchaseItem): ComprasProducto

    // Método personalizado para crear ComprasProducto con ID de compra
    fun toComprasProductoWithCompraId(item: PurchaseItem, idCompra: Int): ComprasProducto {
        val comprasProducto = toComprasProducto(item)
        comprasProducto.id?.idCompra = idCompra
        return comprasProducto
    }
}
