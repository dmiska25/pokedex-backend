package com.dylanmiska.pokemonAPI.domain

import com.dylanmiska.pokemonAPI.persistence.entities.TypeEntity

data class Type(
    val name: String,
)

fun Type.toEntity(): TypeEntity = TypeEntity(
    name = name,
)
