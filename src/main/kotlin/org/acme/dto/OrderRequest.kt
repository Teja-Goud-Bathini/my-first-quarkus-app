package org.acme.dto
data class OrderRequest(
    val userId: Long,
    val productIds: List<Long>,
    val orderDate: String
)
