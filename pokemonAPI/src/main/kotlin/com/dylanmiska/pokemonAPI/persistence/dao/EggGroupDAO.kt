package com.dylanmiska.pokemonAPI.persistence.dao

import com.dylanmiska.pokemonAPI.persistence.entities.EggGroupEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EggGroupDAO: JpaRepository<EggGroupEntity, Int> {
    fun findByName(name: String): EggGroupEntity?
}