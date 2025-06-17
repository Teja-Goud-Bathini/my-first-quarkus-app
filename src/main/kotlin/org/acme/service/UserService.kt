package com.example.service

import com.example.domain.User

interface UserService {
    fun getAllUsers(): List<User>
    fun getUserById(id: Long): User?
    fun createUser(user: User): User
    fun updateUser(id: Long, updatedUser: User): User?
    fun deleteUser(id: Long): Boolean
    fun getAdults(minAge: Int = 18): List<User>
}