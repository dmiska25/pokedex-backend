package com.dylanmiska.pokemonAPI.persistence.entities

import com.dylanmiska.pokemonAPI.domain.Ability
import javax.persistence.*

@Table(
    name="abilities",
)
@Entity
data class AbilityEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int? = null,
    @Column(unique = true)
    val name: String,
)

fun AbilityEntity.toDomain(): Ability = Ability(
    name = name,
)