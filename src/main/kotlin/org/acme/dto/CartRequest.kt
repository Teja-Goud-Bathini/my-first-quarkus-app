package org.acme.dto

data class CartRequest(
    val userId: Long,
    val productIds: List<Long>
)
