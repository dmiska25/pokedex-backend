package com.dylanmiska.pokemonAPI.persistence.dao

import com.dylanmiska.pokemonAPI.persistence.entities.CapturedPokemonEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CapturedPokemonDAO: JpaRepository<CapturedPokemonEntity, Int> {
    fun findByTrainerId(pageable: Pageable, trainerId: Int): Page<CapturedPokemonEntity>
}