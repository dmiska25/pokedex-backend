package com.dylanmiska.pokemonAPI.services

import com.dylanmiska.pokemonAPI.domain.CapturedPokemon
import com.dylanmiska.pokemonAPI.domain.Pokemon
import com.dylanmiska.pokemonAPI.domain.Trainer
import com.dylanmiska.pokemonAPI.domain.toPokemon
import com.dylanmiska.pokemonAPI.persistence.repositories.CapturedPokemonRepository
import com.dylanmiska.pokemonAPI.persistence.repositories.PokemonRepository
import com.dylanmiska.pokemonAPI.persistence.repositories.TrainerRepository
import com.dylanmiska.pokemonAPI.utilities.JwtUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class TrainerService(
    private val trainerRepository: TrainerRepository,
    private val capturedPokemonRepository: CapturedPokemonRepository,
    private val pokemonRepository: PokemonRepository,
    private val request: HttpServletRequest,
    private val jwtUtil: JwtUtil,
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val trainer  = trainerRepository.findByUsername(username) ?:
            throw NoSuchElementException("Username does not exist!")
        return trainer
    }

    fun saveNewTrainer(trainer: Trainer) {
        if(trainerRepository.exists(trainer.username))
            throw IllegalArgumentException("Username already exists!")
        trainerRepository.persist(trainer)
    }

    fun getTrainerPokedex(pageable: Pageable): Page<Pokemon> {
        val trainer = getRequestedTrainer() // already authenticated

        return capturedPokemonRepository.getAllTrainerPokemon(pageable, trainer)
            .map(CapturedPokemon::toPokemon)
    }

    fun addTrainerPokemon(pokemonID: Int) =
        capturedPokemonRepository.persist(
            CapturedPokemon(
                trainer = getRequestedTrainer(),
                pokemon = pokemonRepository.findByIDOrNull(pokemonID) ?:
                    throw NoSuchElementException("Pokemon Does Not Exist!")
            )
        )

    private fun getRequestedTrainer(): Trainer {
        val authorizationHeader = request.getHeader("Authorization")
        val jwt = authorizationHeader.substring(7)
        val username = jwtUtil.extractUsername(jwt)
        return trainerRepository.findByUsername(username)!! // already authenticated
    }
}
