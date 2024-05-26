package com.dylanmiska.pokemonAPI.domain

import com.dylanmiska.pokemonAPI.persistence.entities.TrainerEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

data class Trainer(
    val trainerUsername: String,
    val trainerPasswordHash: String,
) : User (
    trainerUsername,
    trainerPasswordHash,
    listOf<GrantedAuthority>(),
)

fun Trainer.toEntity() : TrainerEntity = TrainerEntity(
    trainerUsername = trainerUsername,
    trainerPasswordHash = trainerPasswordHash,
)