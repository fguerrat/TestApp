package com.lacalera.testapp.domain.model

import com.lacalera.testapp.data.remote.model.CharacterDTO
import com.lacalera.testapp.data.remote.model.CharacterResponse

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val image: String,
    val url: String,
    val created: String
)

fun CharacterDTO.toDomain(): Character {
    return Character(
        id = this.id,
        name = this.name,
        status = this.status,
        species = this.species,
        type = this.type,
        gender = this.gender,
        image = this.image,
        url = this.url,
        created = this.created
    )
}