package com.lacalera.testapp.presentation.home

import com.lacalera.testapp.domain.model.Character

sealed interface HomeState {

    data class Success(val listCharacters: List<Character>) : HomeState
    data class Error(val message: String) : HomeState
    data object Loading : HomeState

}