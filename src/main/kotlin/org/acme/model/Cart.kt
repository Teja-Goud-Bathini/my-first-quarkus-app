package org.acme.model

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "Carts")
data class Cart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @OneToOne
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @ManyToMany
    @JoinTable(
        name = "cart_products",
        joinColumns = [JoinColumn(name = "cart_id")],
        inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    var products: MutableList<Product> = mutableListOf()
) : Serializable
