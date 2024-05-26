package com.dylanmiska.pokemonAPI.persistence.entities

import com.dylanmiska.pokemonAPI.domain.Trainer
import javax.persistence.*

@Table(name="trainers")
@Entity
data class TrainerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int? = null,
    @Column(unique = true, name = "trainer_username")
    val trainerUsername: String,
    @Column(name = "trainer_password_hash")
    val trainerPasswordHash: String,
)

fun TrainerEntity.toDomain(): Trainer {
    return Trainer(
        trainerUsername = trainerUsername,
        trainerPasswordHash = trainerPasswordHash,
    )
}