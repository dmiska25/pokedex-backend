package com.dylanmiska.pokemonAPI.web.requests

import org.valiktor.functions.isGreaterThan
import org.valiktor.functions.isNotNull
import org.valiktor.validate

data class CapturedPokemonRequest (
    val pokemonId: Int
) {
    init {
        validate(this) {
            validate(CapturedPokemonRequest::pokemonId).isGreaterThan(0)
            validate(CapturedPokemonRequest::pokemonId).isNotNull()
        }
    }
}