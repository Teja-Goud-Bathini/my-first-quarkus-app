package org.acme.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "Brands")
data class Brand(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String = "",

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JsonIgnore
    var products: MutableList<Product> = mutableListOf()
)
