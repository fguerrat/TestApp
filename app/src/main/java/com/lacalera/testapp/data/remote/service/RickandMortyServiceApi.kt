package com.lacalera.testapp.data.remote.service

import android.content.Context
import android.util.Log
import com.lacalera.testapp.data.remote.apis.RickandMortyApi
import com.lacalera.testapp.data.remote.model.CharacterResponse
import com.lacalera.testapp.data.remote.model.ResponseGenericDTO
import com.lacalera.testapp.data.remote.utils.handleApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.Response

class RickandMortyServiceApi(
    private val context: Context,
    private val rickandMortyApi: RickandMortyApi

) {
    suspend fun getCharacters(
        page: Int = 1,
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): ResponseGenericDTO<CharacterResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = rickandMortyApi.getCharacters(
                    page = page,
                    name = name,
                    status = status,
                    species = species,
                    type = type,
                    gender = gender
                )
                
                ResponseGenericDTO(
                    data = response.body(),
                    error = if (!response.isSuccessful) "Error al obtener los personajes" else null,
                    isValid = response.isSuccessful
                )
            } catch (e: Exception) {
                Log.e("RickandMortyServiceApi", "Error in getCharacters", e)
                ResponseGenericDTO(
                    data = null,
                    error = e.message ?: "Error desconocido al obtener los personajes",
                    isValid = false
                )
            }
        }
    }
    }
