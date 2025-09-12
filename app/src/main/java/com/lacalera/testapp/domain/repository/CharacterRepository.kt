package com.lacalera.testapp.domain.repository

import com.lacalera.testapp.data.remote.model.CharacterResponse
import com.lacalera.testapp.data.remote.model.ResponseGenericDTO
import com.lacalera.testapp.domain.model.Character

interface CharacterRepository {
    suspend fun getCharactersByApi(
        page: Int = 1,
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): ResponseGenericDTO<CharacterResponse>

    suspend fun getCharactersByLocal(): List<Character>

    suspend fun saveCharacters(characters: List<Character>)
}
