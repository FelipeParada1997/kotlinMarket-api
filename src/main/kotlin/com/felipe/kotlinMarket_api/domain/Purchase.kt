package com.felipe.kotlinMarket_api.domain

import java.time.LocalDateTime

class Purchase(
    var purchaseId: Int? = null,
    var clientId: String?,
    var date: LocalDateTime?,
    var paymentMethod: String?,
    var comment: String?,
    var state: String?,
    var items: List<PurchaseItem> = emptyList()
) {}