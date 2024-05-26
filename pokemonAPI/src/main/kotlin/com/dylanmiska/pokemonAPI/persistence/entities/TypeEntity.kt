package com.dylanmiska.pokemonAPI.persistence.entities

import com.dylanmiska.pokemonAPI.domain.Type
import javax.persistence.*

@Table(name = "Types")
@Entity
data class TypeEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int? = null,
    @Column(unique = true)
    val name: String
)

fun TypeEntity.toDomain(): Type = Type (
    name = name,
)