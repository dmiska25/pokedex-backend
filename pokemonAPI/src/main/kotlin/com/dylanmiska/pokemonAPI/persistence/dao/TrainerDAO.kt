package com.dylanmiska.pokemonAPI.persistence.dao

import com.dylanmiska.pokemonAPI.persistence.entities.TrainerEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TrainerDAO: JpaRepository<TrainerEntity, Int> {
    fun findByTrainerUsername(username: String): TrainerEntity?
    fun existsByTrainerUsername(username: String): Boolean
}