package com.dylanmiska.pokemonAPI.persistence.repositories

import com.dylanmiska.pokemonAPI.domain.Pokemon
import com.dylanmiska.pokemonAPI.domain.toEntity
import com.dylanmiska.pokemonAPI.persistence.dao.AbilityDAO
import com.dylanmiska.pokemonAPI.persistence.dao.EggGroupDAO
import com.dylanmiska.pokemonAPI.persistence.dao.PokemonDAO
import com.dylanmiska.pokemonAPI.persistence.dao.TypeDAO
import com.dylanmiska.pokemonAPI.persistence.entities.*
import com.dylanmiska.pokemonAPI.pokemonDataInitiation.CSVPokemonMap
import com.dylanmiska.pokemonAPI.pokemonDataInitiation.PokemonDataInitializer
import com.dylanmiska.pokemonAPI.pokemonDataInitiation.toPokemon
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class PokemonRepository(
    private val pokemonDAO: PokemonDAO,
    private val abilityDAO: AbilityDAO,
    private val eggGroupDAO: EggGroupDAO,
    private val typeDAO: TypeDAO,
) {
    val abilityIndex: HashMap<String, AbilityEntity> = hashMapOf<String, AbilityEntity>()
    val eggGroupIndex: HashMap<String, EggGroupEntity> = hashMapOf<String, EggGroupEntity>()
    val typeIndex: HashMap<String, TypeEntity> = hashMapOf<String, TypeEntity>()

    fun findByIDOrNull(id: Int): Pokemon? = pokemonDAO.findByIdOrNull(id)?.toDomain()

    fun findAll(pageable: Pageable): Page<Pokemon> = pokemonDAO.findAll(pageable).map (PokemonEntity::toDomain)

    fun findByName(pageable: Pageable, name: String): Page<Pokemon> =
        pokemonDAO.findByNameContainingIgnoreCase(pageable, name).map ( PokemonEntity::toDomain )

    fun persistPokemon(pokemon: Pokemon): Pokemon {
        val pokemonEntity = pokemon.toEntity()
        return pokemonDAO.save(
            pokemonEntity.copy(
                abilities = pokemonEntity.abilities.map { ability -> findOrCreateAbility(ability) },
                eggGroups = pokemonEntity.eggGroups.map { eggGroup -> findOrCreateEggGroup(eggGroup) },
                types = pokemonEntity.types.map { type -> findOrCreateType(type) }
            )
        ).toDomain()
    }

    private fun findOrCreateAbility(ability: AbilityEntity):AbilityEntity =
        abilityIndex.get(ability.name) ?:
        abilityIndex.put(ability.name, abilityDAO.findByName(ability.name) ?:
        abilityDAO.save(ability)) ?:
        abilityIndex.get(ability.name)!!

    private fun findOrCreateEggGroup(eggGroup: EggGroupEntity):EggGroupEntity =
        eggGroupIndex.get(eggGroup.name) ?:
        eggGroupIndex.put(eggGroup.name, eggGroupDAO.findByName(eggGroup.name) ?:
        eggGroupDAO.save(eggGroup)) ?:
        eggGroupIndex.get(eggGroup.name)!!

    private fun findOrCreateType(type: TypeEntity):TypeEntity =
        typeIndex.get(type.name) ?:
        typeIndex.put(type.name, typeDAO.findByName(type.name) ?:
        typeDAO.save(type)) ?:
        typeIndex.get(type.name)!!



    // Load Pokemon from csv, convert to entities then save to DB
    init {
        if (pokemonDAO.count().toInt() == 0) {
            val pokedexCSV = PokemonDataInitializer().initialize()
            pokedexCSV.forEach { pokemon ->
                persistPokemon(CSVPokemonMap(pokemon).toPokemon()) }
        }
    }

}