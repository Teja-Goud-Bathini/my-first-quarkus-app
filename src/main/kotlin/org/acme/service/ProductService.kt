package org.acme.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.NotFoundException
import org.acme.dto.ProductRequest
import org.acme.model.Product
import org.acme.repository.*

@ApplicationScoped
class ProductService(
    private val productRepo: ProductRepository,
    private val genderRepo: GenderRepository,
    private val ageGroupRepo: AgeGroupRepository,
    private val categoryRepo: CategoryRepository,
    private val brandRepo: BrandRepository
) {

    fun getAll(): List<Product> = productRepo.listAll()

    fun getById(id: Long): Product? = productRepo.findById(id)

    fun searchProducts(
        gender: String?, category: String?, brand: String?, ageGroup: String?,
        name: String?, priceMin: Double?, priceMax: Double?, sort: String?
    ): List<Product> {
        return productRepo.findByFilterNames(gender, category, brand, ageGroup, name, priceMin, priceMax, sort)
    }

    fun filterProducts(
        genderId: Long?, categoryId: Long?, brandId: Long?,
        ageGroupId: Long?, priceMin: Double?, priceMax: Double?, sort: String?
    ): List<Product> {
        return productRepo.findByFilters(genderId, categoryId, brandId, ageGroupId, priceMin, priceMax, sort)
    }

    fun create(productRequest: ProductRequest): Product {
        val gender = genderRepo.findById(productRequest.genderId)
            ?: throw NotFoundException("Gender not found with id ${productRequest.genderId}")
        val ageGroup = ageGroupRepo.findById(productRequest.ageGroupId)
            ?: throw NotFoundException("Age group not found with id ${productRequest.ageGroupId}")
        val category = categoryRepo.findById(productRequest.categoryId)
            ?: throw NotFoundException("Category not found with id ${productRequest.categoryId}")
        val brand = brandRepo.findById(productRequest.brandId)
            ?: throw NotFoundException("Brand not found with id ${productRequest.brandId}")

        val product = Product(
            name = productRequest.name,
            description = productRequest.description,
            price = productRequest.price,
            gender = gender,
            ageGroup = ageGroup,
            category = category,
            brand = brand
        )

        productRepo.persist(product)
        return product
    }

    fun delete(id: Long): Boolean = productRepo.deleteById(id)
}
