package com.dylanmiska.pokemonAPI.domain

import com.dylanmiska.pokemonAPI.persistence.entities.CapturedPokemonEntity

class CapturedPokemon (
    val trainer: Trainer,
    val pokemon: Pokemon
)

fun CapturedPokemon.toEntity() = CapturedPokemonEntity(
    trainer = trainer.toEntity(),
    pokemon = pokemon.toEntity()
)

fun CapturedPokemon.toPokemon() = pokemon