package com.dylanmiska.pokemonAPI

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class PokemonApiApplication


fun main(args: Array<String>) {
	runApplication<PokemonApiApplication>(*args)
}