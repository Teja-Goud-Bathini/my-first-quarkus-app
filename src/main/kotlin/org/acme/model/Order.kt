package org.acme.model

import jakarta.persistence.*
import java.io.Serializable

@Entity
@Table(name = "Orders")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var orderDate: String = "",

    @Column(nullable = true)
    var totalPrice: Double? = 0.0,
    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @ManyToMany
    @JoinTable(
        name = "order_products",
        joinColumns = [JoinColumn(name = "order_id")],
        inverseJoinColumns = [JoinColumn(name = "product_id")]
    )
    var products: MutableList<Product> = mutableListOf()
) : Serializable
