package com.lacalera.testapp.domain.usecase.characters

import com.lacalera.testapp.data.common.Resource
import com.lacalera.testapp.data.remote.model.CharacterResponse
import com.lacalera.testapp.data.remote.model.ResponseGenericDTO
import com.lacalera.testapp.domain.model.Character
import com.lacalera.testapp.domain.model.toDomain
import com.lacalera.testapp.domain.repository.CharacterRepository
import javax.inject.Inject


class GetCharactersApiUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
){
    suspend operator fun invoke(
        page: Int = 1,
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): Resource<List<Character>> {
        return try {
            val data = characterRepository.getCharactersByLocal()
            if(data.isNotEmpty()){
                return Resource.Success(data)
            }
            val response = characterRepository.getCharactersByApi(
                page = page,
                name = name,
                status = status,
                species = species,
                type = type,
                gender = gender
            )
            if(response.isValid && response.data != null){
                val characterList: List<Character> = response.data.results.map { it.toDomain() }
                return Resource.Success(characterList)
            }else{
                Resource.Error("Error al obtener los personajes")
            }

        }catch (e: Exception) {
            Resource.Error(e.message!!)
        }
    }
}