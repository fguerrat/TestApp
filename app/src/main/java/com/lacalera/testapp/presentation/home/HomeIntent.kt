package com.lacalera.testapp.presentation.home

sealed interface HomeIntent {
    data object LoadCharacters : HomeIntent

    data class ClickCharacter(val characterId: Int) : HomeIntent
}