package org.acme.model
import jakarta.persistence.*
import java.io.Serializable
@Entity
@Table(name = "cart_items")
data class CartItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "cart_id")
    var cart: Cart? = null,

    @ManyToOne
    @JoinColumn(name = "product_id")
    var product: Product? = null,

    var quantity: Int = 1
) : Serializable
