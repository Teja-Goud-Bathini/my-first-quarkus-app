package org.acme.model
import com.fasterxml.jackson.annotation.JsonIgnore

import jakarta.persistence.*
@Entity
@Table(name = "product_sizes")
data class ProductSize(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var size: String = "", // e.g., S, M, L, XL

    @ManyToMany(mappedBy = "sizes")
    @JsonIgnore
    var products: MutableList<Product> = mutableListOf()
)
