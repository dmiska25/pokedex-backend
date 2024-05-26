package com.dylanmiska.pokemonAPI.domain

import com.dylanmiska.pokemonAPI.persistence.entities.PokemonEntity
import com.dylanmiska.pokemonAPI.web.responses.PokemonListResponse
import com.dylanmiska.pokemonAPI.web.responses.PokemonResponse


data class Pokemon(
    val id: Int? = null,
    val name: String,
    val types: List<Type>,
    val height: Double,
    val weight: Double,
    val abilities: List<Ability>,
    val eggGroups: List<EggGroup>,
    val stats: Stats,
    val genus: String,
    val description: String,
    val image: String
)

fun Pokemon.toEntity(): PokemonEntity = PokemonEntity(
    id = id,
    name  = name,
    types = types.map { type -> type.toEntity() },
    height = height,
    weight = weight,
    abilities = abilities.map { ability -> ability.toEntity() },
    eggGroups = eggGroups.map { eggGroup -> eggGroup.toEntity() },
    HP = stats.HP,
    attack = stats.attack,
    defense = stats.defense,
    speed = stats.speed,
    specialAttack = stats.specialAttack,
    specialDefense = stats.specialDefense,
    genus = genus,
    description = description,
    image = image
)

fun Pokemon.toResponse(): PokemonResponse = PokemonResponse(
    id = id!!,
    name = name,
    types = types.map { type -> type.name },
    height = height,
    weight = weight,
    abilities = abilities.map { ability -> ability.name },
    egg_groups = eggGroups.map { eggGroup -> eggGroup.name },
    stats = mapOf(
        "hp" to stats.HP,
        "speed" to stats.speed,
        "attack" to stats.attack,
        "defense" to stats.defense,
        "special-attack" to stats.specialAttack,
        "special-defense" to stats.specialDefense),
    genus = genus,
    description = description,
    image = image
)

fun Pokemon.toListResponse(): PokemonListResponse = PokemonListResponse(
    id = id!!,
    name = name,
    types = types.map { type -> type.name },
    image = image,
)
