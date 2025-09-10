package com.lacalera.testapp.presentation.home

sealed class HomeEffect {

    data class NavigateToCharacterDetail(val characterId: Int) : HomeEffect()

}