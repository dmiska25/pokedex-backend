package com.dylanmiska.pokemonAPI.web.responses

data class PokemonListResponse(
    val id: Int,
    val name: String,
    val types: List<String>,
    val image: String,
)
