package com.dylanmiska.pokemonAPI.web.controllers

import com.dylanmiska.pokemonAPI.services.PokemonService
import com.dylanmiska.pokemonAPI.web.responses.PokemonListResponse
import com.dylanmiska.pokemonAPI.web.responses.PokemonResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/pokemon-api")
class PokemonController(
    private val service: PokemonService,
) {
    @GetMapping("/pokemon")
    fun returnAllPokemon(pageable: Pageable, @RequestParam(required = false, defaultValue = "") name:String): ResponseEntity<Page<PokemonListResponse>> =
        ResponseEntity.ok(
            when (name) {
                "" -> service.findAll(pageable)
                else -> service.findByName(pageable, name)
            }
        )

    @GetMapping("/pokemon/{id}")
    fun returnPokemonByID(@PathVariable id: Int): ResponseEntity<PokemonResponse?> =
        when(val it = service.findByIDOrNull(id)) {
            null -> throw ResponseStatusException(HttpStatus.NOT_FOUND, "No Pokemon associated with this id")
            else -> ResponseEntity.ok(it)
        }
}