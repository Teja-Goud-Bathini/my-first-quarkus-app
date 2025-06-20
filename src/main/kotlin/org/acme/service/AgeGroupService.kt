package org.acme.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.acme.model.AgeGroup
import org.acme.repository.AgeGroupRepository

@ApplicationScoped
class AgeGroupService(private val ageGroupRepo: AgeGroupRepository) {

    fun listAll(): List<AgeGroup> = ageGroupRepo.listAll()

    fun findById(id: Long): AgeGroup? = ageGroupRepo.findById(id)

    @Transactional
    fun add(ageGroup: AgeGroup): AgeGroup {
        ageGroupRepo.persist(ageGroup)
        return ageGroup
    }

    @Transactional
    fun update(id: Long, updated: AgeGroup): AgeGroup? {
        val existing = ageGroupRepo.findById(id)
        existing?.name = updated.name
        return existing
    }

    @Transactional
    fun delete(id: Long): Boolean = ageGroupRepo.deleteById(id)
}
