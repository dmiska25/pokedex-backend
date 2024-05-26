package com.dylanmiska.pokemonAPI.persistence.entities

import com.dylanmiska.pokemonAPI.domain.EggGroup
import javax.persistence.*

@Table(name = "egg_groups")
@Entity
data class EggGroupEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int? = null,
    @Column(unique = true)
    val name: String,
)

fun EggGroupEntity.toDomain():EggGroup = EggGroup(
    name = name,
)
