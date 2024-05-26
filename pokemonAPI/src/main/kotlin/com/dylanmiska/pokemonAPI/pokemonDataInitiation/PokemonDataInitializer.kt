package com.dylanmiska.pokemonAPI.pokemonDataInitiation

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.File


class PokemonDataInitializer() {

    fun initialize():List<Map<String, String>> {

        val csv = File("pokemonAPI/src/main/resources/pokedex.csv")
        return csvReader().readAllWithHeader(csv)

    }
}