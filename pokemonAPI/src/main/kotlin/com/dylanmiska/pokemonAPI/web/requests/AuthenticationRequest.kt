package com.dylanmiska.pokemonAPI.web.requests

import org.valiktor.functions.doesNotContainAny
import org.valiktor.functions.isNotBlank
import org.valiktor.validate

data class AuthenticationRequest(
    val username: String,
    val password: String
) {
    init {
        validate(this) {
            validate(AuthenticationRequest::username).doesNotContainAny(listOf<String>(" ", "\\", "\"", "'", ";", "%"))
            validate(AuthenticationRequest::username).isNotBlank()
            validate(AuthenticationRequest::password).doesNotContainAny(listOf<String>(" ", "\\", "\"", "'", ";", "%"))
            validate(AuthenticationRequest::password).isNotBlank()
        }
    }
}
