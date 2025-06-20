package org.acme.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.acme.model.User
import org.acme.repository.UserRepository

@ApplicationScoped
class UserService(private val userRepository: UserRepository) {

    fun getAllUsers(): List<User> = userRepository.listAll()

    fun getUserById(id: Long): User? = userRepository.findById(id)

    @Transactional
    fun createUser(user: User): User {
        userRepository.persist(user)
        return user
    }

    @Transactional
    fun updateUser(id: Long, updatedUser: User): User? {
        val user = userRepository.findById(id)
        user?.apply {
            name = updatedUser.name
            email = updatedUser.email
            age = updatedUser.age
        }
        return user
    }

    @Transactional
    fun deleteUser(id: Long): Boolean = userRepository.deleteById(id)
}
