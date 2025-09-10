package com.lacalera.testapp.data.repository

import com.lacalera.testapp.data.local.dao.CharacterDao
import com.lacalera.testapp.data.local.model.toDomain
import com.lacalera.testapp.data.local.model.toEntity
import com.lacalera.testapp.data.remote.model.CharacterResponse
import com.lacalera.testapp.data.remote.model.ResponseGenericDTO
import com.lacalera.testapp.data.remote.service.RickandMortyServiceApi
import com.lacalera.testapp.domain.model.Character
import com.lacalera.testapp.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val rickandMortyServiceApi: RickandMortyServiceApi,
    private val characterDao: CharacterDao
) : CharacterRepository {

    override suspend fun getCharactersByApi(
        page: Int,
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): ResponseGenericDTO<CharacterResponse> {
        return rickandMortyServiceApi.getCharacters(
            page = page,
            name = name,
            status = status,
            species = species,
            type = type,
            gender = gender
        )
    }

    override suspend fun getCharactersByLocal(): List<Character> {
        return characterDao.getTaskPending().map { it.toDomain() }
    }

    override suspend fun saveCharacters(characters: List<Character>) {
        characterDao.insertAll(characters.map { it.toEntity() })
    }
}
