package com.dylanmiska.pokemonAPI.pokemonDataInitiation

import com.dylanmiska.pokemonAPI.domain.*
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

data class CSVPokemonMap(
    val CSVPokemon: Map<String, String>
) { val objMap = jacksonObjectMapper() }

fun CSVPokemonMap.toPokemon(): Pokemon {
    return Pokemon(
        id = CSVPokemon["id"]!!.toInt(),
        name = CSVPokemon["name"]!!,
        types = objMap.readTree(CSVPokemon["types"]).map
            { type -> Type(name = type.toString().replace("\"", "")) },
        height = CSVPokemon["height"]!!.toDouble(),
        weight = CSVPokemon["weight"]!!.toDouble(),
        abilities = objMap.readTree(CSVPokemon["abilities"]).map
            { ability -> Ability(name = ability.toString().replace("\"", "")) },
        eggGroups = objMap.readTree(CSVPokemon["egg_groups"]).map
            { eggGroup -> EggGroup(name = eggGroup.toString().replace("\"", "")) },
        stats = Stats(
        HP = mapStat("hp"),
        speed = mapStat("speed"),
        attack = mapStat("attack"),
        defense = mapStat("defense"),
        specialAttack = mapStat("special-attack"),
        specialDefense = mapStat("special-defense")
        ),
        genus = CSVPokemon["genus"]!!,
        description = CSVPokemon["description"]!!,
        image = "http://localhost:8080/pokemon-api/image/${CSVPokemon["id"]!!}"
    )
}

fun CSVPokemonMap.mapStat(statType: String):Int =
    objMap.readTree(CSVPokemon["stats"])[statType].toString().toInt()

