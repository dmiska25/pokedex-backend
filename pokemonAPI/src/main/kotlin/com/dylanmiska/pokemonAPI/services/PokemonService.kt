package com.dylanmiska.pokemonAPI.services

import com.dylanmiska.pokemonAPI.domain.Pokemon
import com.dylanmiska.pokemonAPI.domain.toListResponse
import com.dylanmiska.pokemonAPI.domain.toResponse
import com.dylanmiska.pokemonAPI.persistence.repositories.PokemonRepository
import com.dylanmiska.pokemonAPI.web.responses.PokemonListResponse
import com.dylanmiska.pokemonAPI.web.responses.PokemonResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PokemonService(
    private val pokemonRepository: PokemonRepository
) {
    fun findByIDOrNull(id: Int): PokemonResponse? = pokemonRepository.findByIDOrNull(id)?.toResponse()

    fun findAll(pageable: Pageable): Page<PokemonListResponse> = pokemonRepository.findAll(pageable).map (Pokemon::toListResponse)

    fun findByName(pageable: Pageable, name: String): Page<PokemonListResponse> =
        pokemonRepository.findByName(pageable, name).map ( Pokemon::toListResponse )
}