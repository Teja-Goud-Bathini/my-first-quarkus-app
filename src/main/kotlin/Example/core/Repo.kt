package com.Example.core

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository

interface Repo<T> : PanacheRepository<T> {
    fun findAll(): List<T>
    fun persist(entity: T): T
    fun deleteById(id: Long): Boolean
}