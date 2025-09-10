package com.lacalera.testapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lacalera.testapp.data.common.Resource
import com.lacalera.testapp.domain.usecase.characters.GetCharactersApiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersApiUseCase
) : ViewModel() {
// Pagina de vista previa https://rickandmorty-api-jpl.netlify.app/characters/

    //En readme se adjunta el postman del api para la prueba
    //Aplicar el patron de diseño ideal para la aplicacion, consumiendo todos los endpoints del servicio

}
