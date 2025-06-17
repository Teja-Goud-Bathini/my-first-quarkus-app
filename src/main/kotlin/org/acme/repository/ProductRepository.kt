package org.acme.repository

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.acme.model.Product

@ApplicationScoped
class ProductRepository : PanacheRepository<Product> {

    @PersistenceContext
    lateinit var em: EntityManager

    fun findByFilters(
        genderId: Long?,
        categoryId: Long?,
        brandId: Long?,
        ageGroupId: Long?,
        priceMin: Double?,
        priceMax: Double?,
        sort: String?
    ): List<Product> {
        val baseQuery = StringBuilder("""
            SELECT p FROM Product p
            LEFT JOIN FETCH p.gender
            LEFT JOIN FETCH p.category
            LEFT JOIN FETCH p.brand
            LEFT JOIN FETCH p.ageGroup
            WHERE 1=1
        """.trimIndent())

        if (genderId != null) baseQuery.append(" AND p.gender.id = :genderId")
        if (categoryId != null) baseQuery.append(" AND p.category.id = :categoryId")
        if (brandId != null) baseQuery.append(" AND p.brand.id = :brandId")
        if (ageGroupId != null) baseQuery.append(" AND p.ageGroup.id = :ageGroupId")
        if (priceMin != null) baseQuery.append(" AND p.price >= :priceMin")
        if (priceMax != null) baseQuery.append(" AND p.price <= :priceMax")

        sort?.lowercase()?.let {
            if (it == "asc") baseQuery.append(" ORDER BY p.price ASC")
            else if (it == "desc") baseQuery.append(" ORDER BY p.price DESC")
        }

        val query = em.createQuery(baseQuery.toString(), Product::class.java)

        genderId?.let { query.setParameter("genderId", it) }
        categoryId?.let { query.setParameter("categoryId", it) }
        brandId?.let { query.setParameter("brandId", it) }
        ageGroupId?.let { query.setParameter("ageGroupId", it) }
        priceMin?.let { query.setParameter("priceMin", it) }
        priceMax?.let { query.setParameter("priceMax", it) }

        return query.resultList
    }

    fun findByFilterNames(
        gender: String?,
        category: String?,
        brand: String?,
        ageGroup: String?,
        name: String?,
        priceMin: Double?,
        priceMax: Double?,
        sort: String?
    ): List<Product> {
        val query = StringBuilder("""
            SELECT p FROM Product p
            LEFT JOIN FETCH p.gender
            LEFT JOIN FETCH p.category
            LEFT JOIN FETCH p.brand
            LEFT JOIN FETCH p.ageGroup
            WHERE 1=1
        """.trimIndent())

        val params = mutableMapOf<String, Any>()

        gender?.let {
            query.append(" AND LOWER(p.gender.name) = LOWER(:gender)")
            params["gender"] = it
        }
        category?.let {
            query.append(" AND LOWER(p.category.name) = LOWER(:category)")
            params["category"] = it
        }
        brand?.let {
            query.append(" AND LOWER(p.brand.name) = LOWER(:brand)")
            params["brand"] = it
        }
        ageGroup?.let {
            query.append(" AND LOWER(p.ageGroup.name) = LOWER(:ageGroup)")
            params["ageGroup"] = it
        }
        name?.let {
            query.append(" AND LOWER(p.name) LIKE LOWER(:name)")
            params["name"] = "%$it%"
        }
        priceMin?.let {
            query.append(" AND p.price >= :priceMin")
            params["priceMin"] = it
        }
        priceMax?.let {
            query.append(" AND p.price <= :priceMax")
            params["priceMax"] = it
        }

        sort?.lowercase()?.let {
            if (it == "asc") query.append(" ORDER BY p.price ASC")
            else if (it == "desc") query.append(" ORDER BY p.price DESC")
        }

        val jpaQuery = em.createQuery(query.toString(), Product::class.java)
        params.forEach { (key, value) -> jpaQuery.setParameter(key, value) }

        return jpaQuery.resultList
    }
}
