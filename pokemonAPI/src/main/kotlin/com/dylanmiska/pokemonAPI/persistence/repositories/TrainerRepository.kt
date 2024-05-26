package com.dylanmiska.pokemonAPI.persistence.repositories

import com.dylanmiska.pokemonAPI.domain.Trainer
import com.dylanmiska.pokemonAPI.domain.toEntity
import com.dylanmiska.pokemonAPI.persistence.dao.TrainerDAO
import com.dylanmiska.pokemonAPI.persistence.entities.toDomain
import org.springframework.stereotype.Repository

@Repository
class TrainerRepository(
    private val dao: TrainerDAO
) {
    fun findByUsername(username: String): Trainer? =
        dao.findByTrainerUsername(username)?.toDomain()

    fun persist(trainer: Trainer): Trainer =
        dao.save(trainer.toEntity()).toDomain()

    fun exists(username: String): Boolean =
        dao.existsByTrainerUsername(username)


}