package com.dylanmiska.pokemonAPI.web.requests

import com.dylanmiska.pokemonAPI.domain.Trainer
import com.dylanmiska.pokemonAPI.security.SecurityConfigurer
import org.valiktor.functions.doesNotContainAny
import org.valiktor.functions.isNotBlank
import org.valiktor.validate

data class RegisterTrainerRequest(
    val username: String,
    val password: String,
) {
    init {
        validate(this) {
            validate(RegisterTrainerRequest::username).doesNotContainAny(listOf<String>(" ", "\\", "\"", "'", ";", "%"))
            validate(RegisterTrainerRequest::username).isNotBlank()
            validate(RegisterTrainerRequest::password).doesNotContainAny(listOf<String>(" ", "\\", "\"", "'", ";", "%"))
            validate(RegisterTrainerRequest::password).isNotBlank()
        }
    }
}

fun RegisterTrainerRequest.toDomain() =
    Trainer (
        trainerUsername = username,
        trainerPasswordHash = SecurityConfigurer.passwordEncoder().encode(password),
    )
