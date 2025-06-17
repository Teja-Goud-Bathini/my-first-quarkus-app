package org.acme.repository

import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.acme.dto.DashboardSummary
import org.acme.dto.TopProduct

@ApplicationScoped
class DashboardRepository {

    @PersistenceContext
    lateinit var em: EntityManager

    fun getSummary(): DashboardSummary {
        val totalUsers = (em.createQuery("SELECT COUNT(u) FROM User u").singleResult as Number).toLong()
        val totalProducts = (em.createQuery("SELECT COUNT(p) FROM Product p").singleResult as Number).toLong()
        val totalOrders = (em.createQuery("SELECT COUNT(o) FROM Order o").singleResult as Number).toLong()

        // Corrected column name from order_total to totalPrice
        val totalRevenue = (em.createNativeQuery("SELECT COALESCE(SUM(totalPrice), 0) FROM Orders")
            .singleResult as Number).toDouble()

        // SQL Server: TOP 5 instead of LIMIT 5
        val topSellingProductsResult = em.createNativeQuery(
            """
        SELECT TOP 5 p.name, COUNT(*) as total_sold
        FROM Orders o
        JOIN order_products op ON o.id = op.order_id
        JOIN Products p ON p.id = op.product_id
        GROUP BY p.name
        ORDER BY total_sold DESC
        """
        ).resultList

        val topSellingProducts = topSellingProductsResult.map {
            val row = it as Array<Any>
            TopProduct(
                name = row[0].toString(),
                totalSold = (row[1] as Number).toLong()
            )
        }

        return DashboardSummary(
            totalUsers = totalUsers,
            totalProducts = totalProducts,
            totalOrders = totalOrders,
            totalRevenue = totalRevenue,
            topSellingProducts = topSellingProducts
        )
    }

}
