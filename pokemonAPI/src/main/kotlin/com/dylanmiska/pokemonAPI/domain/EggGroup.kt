package com.dylanmiska.pokemonAPI.domain

import com.dylanmiska.pokemonAPI.persistence.entities.EggGroupEntity

data class EggGroup(
    val name: String,
)

fun EggGroup.toEntity(): EggGroupEntity = EggGroupEntity(
    name = name,
)
