package com.dylanmiska.pokemonAPI.persistence.dao

import com.dylanmiska.pokemonAPI.persistence.entities.TypeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TypeDAO: JpaRepository<TypeEntity, Int> {
    fun findByName(name: String): TypeEntity?
}