package com.felipe.kotlinMarket_api.persistence.mapper;

import kotlin.jvm.JvmStatic;
import org.mapstruct.Named;

object PurchaseItemMapperHelper {
@JvmStatic
@Named("estadoToBoolean")
fun estadoToBoolean(estado: String?): Boolean {
    return when (estado?.lowercase()) {
        "1", "true", "yes", "si" -> true
            else -> false
    }
}
}
