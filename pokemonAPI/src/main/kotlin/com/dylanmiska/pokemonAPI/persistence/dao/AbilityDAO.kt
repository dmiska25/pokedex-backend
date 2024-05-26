package com.dylanmiska.pokemonAPI.persistence.dao

import com.dylanmiska.pokemonAPI.persistence.entities.AbilityEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AbilityDAO: JpaRepository<AbilityEntity, Int> {
    fun findByName(name: String):AbilityEntity?
}