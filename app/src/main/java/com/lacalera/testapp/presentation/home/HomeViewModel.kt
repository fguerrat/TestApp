package com.lacalera.testapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lacalera.testapp.data.common.Resource
import com.lacalera.testapp.domain.usecase.characters.GetCharactersApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersApiUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeState>(HomeState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<HomeEffect>(extraBufferCapacity = 1)
    val uiEffect = _uiEffect.asSharedFlow()

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadCharacters -> {
                fetchCharacters()
            }

            is HomeIntent.ClickCharacter -> {
                clickCharacter(intent.characterId)
            }
        }
    }

    private fun clickCharacter(characterId: Int) {
        viewModelScope.launch {
            _uiEffect.emit(HomeEffect.NavigateToCharacterDetail(characterId))
        }
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            _uiState.value = HomeState.Loading
            when (val result = getCharactersUseCase()) {
                is Resource.Success -> _uiState.value = HomeState.Success(result.data)

                is Resource.Error -> _uiState.value = HomeState.Error(result.message)

                else -> _uiState.value = HomeState.Error("Unknown error occurred")
            }
        }
    }

}
