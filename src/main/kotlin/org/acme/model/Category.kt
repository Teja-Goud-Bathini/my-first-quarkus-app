package org.acme.model
import com.fasterxml.jackson.annotation.JsonIgnore

import jakarta.persistence.*

@Entity
@Table(name = "Categories")
data class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String = "",

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JsonIgnore
    var products: MutableList<Product> = mutableListOf()
)
