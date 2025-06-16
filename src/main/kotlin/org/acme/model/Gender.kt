package org.acme.model
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
@Entity
@Table(name = "Genders")
data class Gender(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var name: String = "", // e.g., "Men", "Women", "Unisex"

    @OneToMany(mappedBy = "gender", fetch = FetchType.LAZY)
    @JsonIgnore
    var products: MutableList<Product> = mutableListOf()
)
