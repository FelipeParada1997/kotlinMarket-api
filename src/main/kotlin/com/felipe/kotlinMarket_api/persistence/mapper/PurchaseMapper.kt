package com.felipe.kotlinMarket_api.persistence.mapper

import com.felipe.kotlinMarket_api.domain.Purchase
import com.felipe.kotlinMarket_api.persistence.entity.Compra
import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring", uses = [PurchaseItemMapper::class])
interface PurchaseMapper {



    @Mappings(
        Mapping(source = "idCompra", target = "purchaseId"),
        Mapping(source = "idCliente", target = "clientId"),
        Mapping(source = "fecha", target = "date"),
        Mapping(source = "medioPago", target = "paymentMethod"),
        Mapping(source = "comentario", target = "comment"),
        Mapping(source = "estado", target = "state"),
        Mapping(source = "productos", target = "items")
    )
     fun toPurchase(compra: Compra): Purchase

     fun toPurchases(compras: List<Compra>): List<Purchase>

    @InheritInverseConfiguration
    @Mapping(target = "cliente", ignore = true)
    @Mapping(target = "productos", ignore = true)
     fun toCompra(purchase: Purchase): Compra




}
