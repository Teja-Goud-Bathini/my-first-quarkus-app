package com.example.service

import com.example.domain.User
import com.example.repository.UserRepository
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional

@ApplicationScoped
class UserServiceImpl(private val userRepository: UserRepository) : UserService {

    override fun getAllUsers(): List<User> = userRepository.findAll()

    override fun getUserById(id: Long): User? = userRepository.findById(id)

    @Transactional
    override fun createUser(user: User): User {
        userRepository.persist(user)
        return user
    }

    @Transactional
    override fun updateUser(id: Long, updatedUser: User): User? {
        val user = userRepository.findById(id) ?: return null
        user.apply {
            name = updatedUser.name
            email = updatedUser.email
            age = updatedUser.age
        }
        return user
    }

    @Transactional
    override fun deleteUser(id: Long): Boolean = userRepository.deleteById(id)

    override fun getAdults(minAge: Int): List<User> = userRepository.findAdults(minAge)
}