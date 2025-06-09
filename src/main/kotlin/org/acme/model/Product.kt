package org.acme.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "Products")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String = "",

    var description: String = "",

    var price: Double = 0.0,

    @ManyToMany(mappedBy = "products")
    @JsonIgnore // 🔥 Important: Prevents infinite recursion and lazy loading issues
    var orders: MutableList<Order> = mutableListOf()
) : Serializable
