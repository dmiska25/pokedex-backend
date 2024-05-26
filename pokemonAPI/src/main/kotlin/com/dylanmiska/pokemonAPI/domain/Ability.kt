package com.dylanmiska.pokemonAPI.domain

import com.dylanmiska.pokemonAPI.persistence.entities.AbilityEntity

data class Ability(
    val name: String,
)

fun Ability.toEntity(): AbilityEntity = AbilityEntity (
    name = name,
)
