package org.acme.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.acme.model.Gender
import org.acme.repository.GenderRepository

@ApplicationScoped
class GenderService(private val genderRepo: GenderRepository) {

    fun listAll(): List<Gender> = genderRepo.listAll()

    fun findById(id: Long): Gender? = genderRepo.findById(id)

    @Transactional
    fun add(gender: Gender): Gender {
        genderRepo.persist(gender)
        return gender
    }

    @Transactional
    fun update(id: Long, updated: Gender): Gender? {
        val existing = genderRepo.findById(id) ?: return null
        existing.name = updated.name
        return existing
    }
}
