import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

import model.gestio.Client
import model.gestio.Esdeveniment
import model.material.Material
import repository.JsonRepository
import ui.*
import viewmodel.*

fun main() = application {

    val clientRepo = JsonRepository("data/clients.json", Client::class.java)
    val eventRepo = JsonRepository("data/esdeveniments.json", Esdeveniment::class.java)
    val materialRepo = JsonRepository("data/materials.json", Material::class.java)

    val clientVM = ClientViewModel(clientRepo)
    val eventVM = EsdevenimentViewModel(eventRepo)
    val materialVM = MaterialViewModel(materialRepo)

    Window(onCloseRequest = { exitApplication() }) {

        var pantalla by remember { mutableStateOf("dashboard") }

        MaterialTheme {
            Column {
                Row {
                    Button(onClick = { pantalla = "dashboard" }) { Text("Inici") }
                    Button(onClick = { pantalla = "clients" }) { Text("Clients") }
                    Button(onClick = { pantalla = "events" }) { Text("Esdeveniments") }
                    Button(onClick = { pantalla = "material" }) { Text("Material") }
                }

                when (pantalla) {
                    "dashboard" -> DashboardScreen(clientVM, eventVM, materialVM)
                    "clients" -> ClientScreen(clientVM)
                    "events" -> EsdevenimentScreen(eventVM, clientVM, materialVM)
                    "material" -> MaterialScreen(materialVM)
                }
            }
        }
    }
}