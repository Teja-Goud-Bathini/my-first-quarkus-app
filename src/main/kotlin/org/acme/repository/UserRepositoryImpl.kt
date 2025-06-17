package com.example.repository

import com.example.domain.User
import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserRepositoryImpl : PanacheRepository<User>, UserRepository {

    override fun findByEmail(email: String): User? {
        return find("email", email).firstResult()
    }

    override fun findAdults(minAge: Int): List<User> {
        return find("age >= ?1", minAge).list()
    }

    // Implement other methods from Repo interface
    override fun findAll(): List<User> = listAll()
    override fun persist(entity: User): User = super.persist(entity)
    override fun deleteById(id: Long): Boolean = super.deleteById(id)
}