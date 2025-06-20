package org.acme.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.acme.model.Brand
import org.acme.repository.BrandRepository

@ApplicationScoped
@Transactional
class BrandService(private val brandRepo: BrandRepository) {

    fun listAll(): List<Brand> = brandRepo.listAll()


    fun add(brand: Brand): Brand {
        brandRepo.persist(brand)
        return brand
    }

    fun update(id: Long, updatedBrand: Brand): Brand? {
        val existing = brandRepo.findById(id)
        existing?.name = updatedBrand.name
        return existing
    }
}
