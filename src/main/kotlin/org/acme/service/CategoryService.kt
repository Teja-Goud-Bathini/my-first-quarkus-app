package org.acme.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.acme.model.Category
import org.acme.repository.CategoryRepository
import jakarta.ws.rs.NotFoundException

@ApplicationScoped
class CategoryService(private val categoryRepository: CategoryRepository) {

    fun listAll(): List<Category> = categoryRepository.listAll()

    fun findById(id: Long): Category =
        categoryRepository.findById(id) ?: throw NotFoundException("Category not found with id $id")

    @Transactional
    fun create(category: Category): Category {
        categoryRepository.persist(category)
        return category
    }

    @Transactional
    fun update(id: Long, updatedCategory: Category): Category {
        val category = findById(id)
        category.name = updatedCategory.name
        return category
    }

    @Transactional
    fun delete(id: Long): Boolean {
        return categoryRepository.deleteById(id)
    }
}
