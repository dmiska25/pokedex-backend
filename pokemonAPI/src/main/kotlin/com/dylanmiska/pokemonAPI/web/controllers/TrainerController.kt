package com.dylanmiska.pokemonAPI.web.controllers

import com.dylanmiska.pokemonAPI.domain.Pokemon
import com.dylanmiska.pokemonAPI.domain.toListResponse
import com.dylanmiska.pokemonAPI.services.TrainerService
import com.dylanmiska.pokemonAPI.utilities.JwtUtil
import com.dylanmiska.pokemonAPI.web.requests.AuthenticationRequest
import com.dylanmiska.pokemonAPI.web.requests.CapturedPokemonRequest
import com.dylanmiska.pokemonAPI.web.requests.RegisterTrainerRequest
import com.dylanmiska.pokemonAPI.web.requests.toDomain
import com.dylanmiska.pokemonAPI.web.responses.AuthenticationResponse
import com.dylanmiska.pokemonAPI.web.responses.PokemonListResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/pokemon-api")
class TrainerController(
    private val authenticationManager: AuthenticationManager,
    private val trainerService: TrainerService,
    private val jwtTokenUtil: JwtUtil,
) {
    @PostMapping("/trainer/register")
    fun registerTrainer(
        @RequestBody
        registerTrainer: RegisterTrainerRequest
    ): ResponseEntity<String> {
        try {
            if (registerTrainer.password == "" || registerTrainer.password.contains(' '))
                throw IllegalArgumentException("Invalid Password")

            trainerService.saveNewTrainer(registerTrainer.toDomain())

            return ResponseEntity.ok("Successfully registered trainer with username: ${registerTrainer.username}")
        }
        catch (ex: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, ex.message)
        }
    }

    @GetMapping("/trainer/authenticate")
    fun createAuthenticationToken(@RequestBody authenticationRequest: AuthenticationRequest):ResponseEntity<AuthenticationResponse> {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(authenticationRequest.username, authenticationRequest.password)
        )
        val userDetails = trainerService.loadUserByUsername(authenticationRequest.username)
        val jwt = jwtTokenUtil.generateToken(userDetails)
        return ResponseEntity.ok(AuthenticationResponse(jwt))
    }

    @GetMapping("/trainer/pokedex")
    fun getTrainerPokedex(pageable: Pageable):ResponseEntity<Page<PokemonListResponse>> = ResponseEntity.ok(
        trainerService.getTrainerPokedex(pageable).map(Pokemon::toListResponse)
    )

    @PostMapping("/trainer/addcapturedpokemon")
    fun addCapturedPokemon(@RequestBody capturedPokemonRequest: CapturedPokemonRequest): ResponseEntity<String> {
        try {

            trainerService.addTrainerPokemon(capturedPokemonRequest.pokemonId)
            return ResponseEntity.ok("Successfully added captured pokemon!")
        }
        catch(ex: NoSuchElementException) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "No Pokemon associated with this id")
        }

    }
}