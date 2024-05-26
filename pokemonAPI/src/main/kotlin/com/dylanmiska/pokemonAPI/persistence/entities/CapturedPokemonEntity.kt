package com.dylanmiska.pokemonAPI.persistence.entities

import com.dylanmiska.pokemonAPI.domain.CapturedPokemon
import javax.persistence.*

@Table(name = "captured_pokemon")
@Entity
data class CapturedPokemonEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int? = null,
    @ManyToOne
    val trainer: TrainerEntity,
    @ManyToOne
    val pokemon: PokemonEntity,
)

fun CapturedPokemonEntity.toDomain() = CapturedPokemon(
    trainer = trainer.toDomain(),
    pokemon = pokemon.toDomain()
)