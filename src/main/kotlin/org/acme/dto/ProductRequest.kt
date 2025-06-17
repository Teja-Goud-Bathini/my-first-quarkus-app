package org.acme.dto

data class ProductRequest(
    val name: String,
    val description: String,
    val price: Double,
    val genderId: Long,
    val ageGroupId: Long,
    val categoryId: Long,
    val brandId: Long
)

