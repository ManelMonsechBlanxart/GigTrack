package com.gigtrack.api

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * ViewModel encarregat de gestionar l'estat de les dades rebudes de l'API.
 *
 * La UI només consulta aquest ViewModel i no accedeix directament ni a l'API
 * ni a MongoDB.
 */
class ApiViewModel : ViewModel() {

    private val repository = ApiRepository()

    var clients by mutableStateOf<List<ApiClient>>(emptyList())
        private set

    var materials by mutableStateOf<List<ApiMaterial>>(emptyList())
        private set

    var esdeveniments by mutableStateOf<List<ApiEsdeveniment>>(emptyList())
        private set

    var carregant by mutableStateOf(false)
        private set

    var error by mutableStateOf("")
        private set

    fun carregarDades() {
        viewModelScope.launch {
            try {
                carregant = true
                error = ""

                clients = repository.obtenirClients()
                materials = repository.obtenirMaterials()
                esdeveniments = repository.obtenirEsdeveniments()

                carregant = false
            } catch (e: Exception) {
                error = e.message ?: "Error desconegut"
                carregant = false
            }
        }
    }
}