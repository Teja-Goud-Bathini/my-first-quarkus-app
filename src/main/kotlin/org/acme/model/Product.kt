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
    @ManyToMany
    @JoinTable(
        name = "product_product_sizes",
        joinColumns = [JoinColumn(name = "product_id")],
        inverseJoinColumns = [JoinColumn(name = "size_id")]
    )
    var sizes: MutableList<ProductSize> = mutableListOf(),

    @ManyToOne
    @JoinColumn(name = "gender_id")
    var gender: Gender? = null,

    @ManyToOne
    @JoinColumn(name = "age_group_id")
    var ageGroup: AgeGroup? = null,

    @ManyToOne
    @JoinColumn(name = "category_id")
    var category: Category? = null,

    @ManyToOne
    @JoinColumn(name = "brand_id")
    var brand: Brand? = null,

    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    var orders: MutableList<Order> = mutableListOf()
) : Serializable
