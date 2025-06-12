package org.acme.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "Users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String = "",

    var email: String = "",

    var age: Int = 0,

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JsonIgnore
    var orders: MutableList<Order> = mutableListOf(),

    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL])
    @JsonIgnore
    var cart: Cart? = null
)
