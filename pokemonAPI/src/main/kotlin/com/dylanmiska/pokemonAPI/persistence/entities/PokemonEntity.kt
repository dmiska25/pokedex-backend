package com.dylanmiska.pokemonAPI.persistence.entities

import com.dylanmiska.pokemonAPI.domain.Pokemon
import com.dylanmiska.pokemonAPI.domain.Stats
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import javax.persistence.*

@Table(name = "pokemon")
@Entity
data class PokemonEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val name: String,
    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(
        name = "pokemon_types",
        joinColumns = [
            JoinColumn(name = "pokemon_id")
        ],
        inverseJoinColumns = [
            JoinColumn(name = "type_id")
        ]
    )
    val types: List<TypeEntity>,
    val height: Double,
    val weight: Double,
    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(
        name = "pokemon_abilities",
        joinColumns = [
            JoinColumn(name = "pokemon_id")
        ],
        inverseJoinColumns = [
            JoinColumn(name = "ability_id")
        ]
    )
    val abilities: List<AbilityEntity>,
    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(
        name = "pokemon_egg_groups",
        joinColumns = [
            JoinColumn(name = "pokemon_id")
        ],
        inverseJoinColumns = [
            JoinColumn(name = "egg_group_id")
        ]
    )
    val eggGroups: List<EggGroupEntity>,
    //stats
    val HP: Int,
    val attack: Int,
    val defense: Int,
    val speed: Int,
    @Column(name = "special_attack")
    val specialAttack: Int,
    @Column(name = "special_defense")
    val specialDefense: Int,
    //end stats
    val genus: String,
    val description: String,
    val image: String
)

fun PokemonEntity.toDomain(): Pokemon = Pokemon(
    id = id,
    name  = name,
    types = types.map { typeEntity -> typeEntity.toDomain() },
    height = height,
    weight = weight,
    abilities = abilities.map { ability -> ability.toDomain() },
    eggGroups = eggGroups.map { eggGroup -> eggGroup.toDomain() },
    stats = Stats(
        HP = HP,
        attack = attack,
        defense = defense,
        speed = speed,
        specialAttack = specialAttack,
        specialDefense = specialDefense,
    ),
    genus = genus,
    description = description,
    image = image
)