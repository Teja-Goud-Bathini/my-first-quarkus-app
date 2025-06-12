package org.acme.model
import com.fasterxml.jackson.annotation.JsonIgnore

import jakarta.persistence.*
import java.io.Serializable
@Entity
@Table(name = "AgeGroups")
data class AgeGroup(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    var label: String = "", // e.g., "Kids", "Teens", "Adults"

    @OneToMany(mappedBy = "ageGroup", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JsonIgnore
    var products: MutableList<Product> = mutableListOf()
)
