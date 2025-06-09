

package org.acme.model

import jakarta.persistence.*
import java.io.Serializable
import com.fasterxml.jackson.annotation.*
@Entity
@Table(name = "Users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String = "",

    var email: String = "",

    var age: Int = 0,
    @OneToMany(mappedBy = "user")
    var orders: MutableList<Order> = mutableListOf()

)
