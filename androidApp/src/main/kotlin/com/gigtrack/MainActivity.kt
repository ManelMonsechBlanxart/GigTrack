package com.gigtrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gigtrack.api.ApiViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                PantallaApiGigTrack()
            }
        }
    }
}

@Composable
fun PantallaApiGigTrack(
    viewModel: ApiViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.carregarDades()
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "GigTrack - API REST + MongoDB",
            style = MaterialTheme.typography.h5
        )

        Spacer(Modifier.height(12.dp))

        if (viewModel.carregant) {
            Text("Carregant dades des de l'API...")
        }

        if (viewModel.error.isNotBlank()) {
            Text("Error connectant amb l'API:")
            Text(viewModel.error)
        }

        if (!viewModel.carregant && viewModel.error.isBlank()) {
            Text("Clients: ${viewModel.clients.size}")
            Text("Materials: ${viewModel.materials.size}")
            Text("Esdeveniments: ${viewModel.esdeveniments.size}")
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {

            item {
                Text("Clients", style = MaterialTheme.typography.h6)
            }

            items(viewModel.clients) { client ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(client.nom, style = MaterialTheme.typography.h6)
                        Text(client.email)
                        Text(client.telefon)
                    }
                }
            }

            item {
                Spacer(Modifier.height(16.dp))
                Text("Material", style = MaterialTheme.typography.h6)
            }

            items(viewModel.materials) { material ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(material.nom, style = MaterialTheme.typography.h6)
                        Text("${material.marca} ${material.model}")
                        Text("Preu: ${material.preuPerUnitat}€")
                        Text("Quantitat: ${material.quantitat}")
                    }
                }
            }

            item {
                Spacer(Modifier.height(16.dp))
                Text("Esdeveniments", style = MaterialTheme.typography.h6)
            }

            items(viewModel.esdeveniments) { event ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(event.titol, style = MaterialTheme.typography.h6)
                        Text(event.data)
                        Text(event.lloc)
                        Text("Preu: ${event.cachet}€")
                        Text("Client ID: ${event.clientId}")
                    }
                }
            }
        }
    }
}