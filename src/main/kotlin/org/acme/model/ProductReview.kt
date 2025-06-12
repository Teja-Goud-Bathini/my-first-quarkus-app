package org.acme.model
import com.fasterxml.jackson.annotation.JsonIgnore

import jakarta.persistence.*
@Entity
@Table(name = "product_reviews")
data class ProductReview(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var rating: Int = 0,
    var comment: String = "",

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @ManyToOne
    @JoinColumn(name = "product_id")
    var product: Product? = null
)
