package com.gigtrack.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.gson.*

data class ApiClient(
    val id: String,
    val nom: String,
    val telefon: String,
    val email: String
)

data class ApiMaterial(
    val id: String,
    val nom: String,
    val marca: String,
    val model: String,
    val preuPerUnitat: Double,
    val quantitat: Int
)

data class ApiEsdeveniment(
    val id: String,
    val titol: String,
    val data: String,
    val lloc: String,
    val cachet: Double,
    val clientId: String
)

class ApiRepository {

    private val baseUrl = "http://192.168.20.152:3000"

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            gson()
        }
    }

    suspend fun obtenirClients(): List<ApiClient> {
        return client.get("$baseUrl/clients").body()
    }

    suspend fun obtenirMaterials(): List<ApiMaterial> {
        return client.get("$baseUrl/materials").body()
    }

    suspend fun obtenirEsdeveniments(): List<ApiEsdeveniment> {
        return client.get("$baseUrl/esdeveniments").body()
    }
}