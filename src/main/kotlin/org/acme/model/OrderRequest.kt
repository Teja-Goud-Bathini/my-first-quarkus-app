package org.acme.model

import java.time.LocalDate

data class OrderRequest(
    val userId: Long,
    val productIds: List<Long>,
    val orderDate: String
)
