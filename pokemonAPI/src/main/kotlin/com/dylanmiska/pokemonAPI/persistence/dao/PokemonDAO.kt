package com.dylanmiska.pokemonAPI.persistence.dao

import com.dylanmiska.pokemonAPI.persistence.entities.PokemonEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PokemonDAO: JpaRepository<PokemonEntity, Int> {
    fun findByNameContainingIgnoreCase(pageable: Pageable, name: String): Page<PokemonEntity>
}