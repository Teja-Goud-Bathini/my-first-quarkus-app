package org.acme.dto

data class WishlistRequest(
    val userId: Long,
    val productId: Long
)


data class WishlistResponse(
    val id: Long,
    val userId: Long,
    val productId: Long,
    val productName: String
)
