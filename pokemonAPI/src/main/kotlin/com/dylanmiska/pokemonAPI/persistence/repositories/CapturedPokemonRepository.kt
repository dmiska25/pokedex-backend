package com.dylanmiska.pokemonAPI.persistence.repositories

import com.dylanmiska.pokemonAPI.domain.CapturedPokemon
import com.dylanmiska.pokemonAPI.domain.Trainer
import com.dylanmiska.pokemonAPI.domain.toEntity
import com.dylanmiska.pokemonAPI.persistence.dao.CapturedPokemonDAO
import com.dylanmiska.pokemonAPI.persistence.dao.TrainerDAO
import com.dylanmiska.pokemonAPI.persistence.entities.CapturedPokemonEntity
import com.dylanmiska.pokemonAPI.persistence.entities.toDomain
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class CapturedPokemonRepository(
    private val dao: CapturedPokemonDAO,
    private val trainerDAO: TrainerDAO,
) {
    fun getAllTrainerPokemon(pageable: Pageable, trainer: Trainer): Page<CapturedPokemon> {
        val trainerEntity = trainerDAO.findByTrainerUsername(trainer.trainerUsername) ?:
            throw NoSuchElementException("Trainer does not exist!")
        return dao.findByTrainerId(pageable, trainerEntity.id!!).map(CapturedPokemonEntity::toDomain)
    }

    fun persist(capturedPokemon: CapturedPokemon): CapturedPokemon =
        dao.save(capturedPokemon.toEntity()).toDomain()
}