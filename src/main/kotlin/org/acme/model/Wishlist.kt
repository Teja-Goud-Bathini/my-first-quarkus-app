package org.acme.model

import jakarta.persistence.*

@Entity
@Table(name = "wishlists")
data class Wishlist(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User = User(),  // default constructor

    @ManyToOne
    @JoinColumn(name = "product_id")
    var product: Product = Product()  // default constructor
)
