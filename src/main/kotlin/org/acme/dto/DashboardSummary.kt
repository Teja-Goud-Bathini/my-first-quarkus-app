package org.acme.dto

data class DashboardSummary(
    val totalUsers: Long,
    val totalProducts: Long,
    val totalOrders: Long,
    val totalRevenue: Double,
    val topSellingProducts: List<TopProduct>
)
