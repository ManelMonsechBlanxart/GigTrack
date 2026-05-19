package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.gestio.AssignacioMaterial
import model.gestio.Esdeveniment
import viewmodel.ClientViewModel
import viewmodel.EsdevenimentViewModel
import viewmodel.MaterialViewModel
import java.time.LocalDate
import java.time.format.DateTimeParseException

@Composable
fun EsdevenimentScreen(
    eventVM: EsdevenimentViewModel,
    clientVM: ClientViewModel,
    materialVM: MaterialViewModel
) {
    var idEditant by remember { mutableStateOf<String?>(null) }
    var titol by remember { mutableStateOf("") }
    var lloc by remember { mutableStateOf("") }
    var data by remember { mutableStateOf("") }
    var preu by remember { mutableStateOf("") }

    var clientSeleccionat by remember { mutableStateOf("") }
    var desplegatClient by remember { mutableStateOf(false) }

    var materialSeleccionat by remember { mutableStateOf("") }
    var quantitatMaterial by remember { mutableStateOf("") }
    var desplegatMaterial by remember { mutableStateOf(false) }

    var missatgeError by remember { mutableStateOf("") }

    val assignacionsSeleccionades = remember { mutableStateListOf<AssignacioMaterial>() }

    Column(Modifier.padding(16.dp)) {

        Text("Esdeveniments", style = MaterialTheme.typography.h5)

        if (missatgeError.isNotBlank()) {
            Text("Error: $missatgeError")
            Spacer(Modifier.height(8.dp))
        }

        OutlinedTextField(titol, { titol = it }, label = { Text("Títol") })
        OutlinedTextField(lloc, { lloc = it }, label = { Text("Lloc") })
        OutlinedTextField(data, { data = it }, label = { Text("Data YYYY-MM-DD") })
        OutlinedTextField(preu, { preu = it }, label = { Text("Preu (€)") })

        Spacer(Modifier.height(8.dp))

        Text("Client:")

        Button(onClick = { desplegatClient = true }) {
            val clientNom = clientVM.clients.find { it.id == clientSeleccionat }?.nom
                ?: "Seleccionar client"
            Text(clientNom)
        }

        DropdownMenu(
            expanded = desplegatClient,
            onDismissRequest = { desplegatClient = false }
        ) {
            clientVM.clients.forEach { client ->
                DropdownMenuItem(onClick = {
                    clientSeleccionat = client.id
                    desplegatClient = false
                }) {
                    Text(client.nom)
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Text("Material necessari:")

        Row {
            Button(onClick = { desplegatMaterial = true }) {
                val nomMaterial = materialVM.materials.find { it.id == materialSeleccionat }?.nom
                    ?: "Seleccionar material"
                Text(nomMaterial)
            }

            Spacer(Modifier.width(8.dp))

            OutlinedTextField(
                value = quantitatMaterial,
                onValueChange = { quantitatMaterial = it },
                label = { Text("Quantitat") }
            )

            Spacer(Modifier.width(8.dp))

            Button(onClick = {
                val material = materialVM.materials.find { it.id == materialSeleccionat }
                val quantitatNecessaria = quantitatMaterial.toIntOrNull() ?: 0

                if (material == null) {
                    missatgeError = "Cal seleccionar un material"
                    return@Button
                }

                if (quantitatNecessaria <= 0) {
                    missatgeError = "La quantitat ha de ser major que 0"
                    return@Button
                }

                if (quantitatNecessaria > material.quantitat) {
                    missatgeError = "No hi ha prou unitats disponibles"
                    return@Button
                }

                val existent = assignacionsSeleccionades.indexOfFirst { it.materialId == material.id }

                if (existent >= 0) {
                    assignacionsSeleccionades[existent] =
                        AssignacioMaterial(material.id, quantitatNecessaria)
                } else {
                    assignacionsSeleccionades.add(
                        AssignacioMaterial(material.id, quantitatNecessaria)
                    )
                }

                materialSeleccionat = ""
                quantitatMaterial = ""
                missatgeError = ""
            }) {
                Text("Afegir")
            }
        }

        DropdownMenu(
            expanded = desplegatMaterial,
            onDismissRequest = { desplegatMaterial = false }
        ) {
            materialVM.materials.forEach { material ->
                DropdownMenuItem(onClick = {
                    materialSeleccionat = material.id
                    desplegatMaterial = false
                }) {
                    Text("${material.nom} - disponibles: ${material.quantitat}")
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Text("Materials afegits:")

        assignacionsSeleccionades.forEach { assignacio ->
            val material = materialVM.materials.find { it.id == assignacio.materialId }

            if (material != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${material.nom} x${assignacio.quantitat}")

                    Button(onClick = {
                        assignacionsSeleccionades.remove(assignacio)
                    }) {
                        Text("Treure")
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            try {
                if (titol.isBlank()) {
                    missatgeError = "El títol no pot estar buit"
                    return@Button
                }

                if (lloc.isBlank()) {
                    missatgeError = "El lloc no pot estar buit"
                    return@Button
                }

                if (data.isBlank()) {
                    missatgeError = "Cal indicar una data"
                    return@Button
                }

                val dataParsed = try {
                    LocalDate.parse(data)
                } catch (e: DateTimeParseException) {
                    missatgeError = "Format de la data incorrecte, utilitza: YYYY-MM-DD"
                    return@Button
                }

                if (clientSeleccionat.isBlank()) {
                    missatgeError = "Cal seleccionar un client"
                    return@Button
                }

                val id = idEditant ?: "E${System.currentTimeMillis()}"

                val event = Esdeveniment(
                    id = id,
                    titol = titol,
                    data = dataParsed,
                    lloc = lloc,
                    cachet = preu.toDoubleOrNull() ?: 0.0,
                    clientId = clientSeleccionat,
                    assignacionsMaterial = assignacionsSeleccionades.toMutableList()
                )

                eventVM.guardarEsdeveniment(event)

                idEditant = null
                titol = ""
                lloc = ""
                data = ""
                preu = ""
                clientSeleccionat = ""
                materialSeleccionat = ""
                quantitatMaterial = ""
                assignacionsSeleccionades.clear()
                missatgeError = ""

            } catch (e: Exception) {
                missatgeError = e.message ?: "Error desconegut"
            }
        }) {
            Text(if (idEditant == null) "Crear esdeveniment" else "Guardar canvis")
        }

        Spacer(Modifier.height(16.dp))

        Text("Llista d'esdeveniments:")

        LazyColumn {
            items(eventVM.events) { event ->
                val client = clientVM.clients.find { it.id == event.clientId }?.nom
                    ?: "Sense client"

                val nomsMaterials = event.assignacionsMaterial.mapNotNull { assignacio ->
                    val material = materialVM.materials.find { it.id == assignacio.materialId }
                    material?.let { "${it.nom} x${assignacio.quantitat}" }
                }

                Column(Modifier.padding(bottom = 12.dp)) {
                    Text("${event.resum()} - Client: $client - Preu: ${event.cachet}€")

                    if (nomsMaterials.isEmpty()) {
                        Text("Materials: cap material assignat")
                    } else {
                        Text("Materials: ${nomsMaterials.joinToString(", ")}")
                    }

                    Row {
                        Button(onClick = {
                            idEditant = event.id
                            titol = event.titol
                            lloc = event.lloc
                            data = event.data.toString()
                            preu = event.cachet.toString()
                            clientSeleccionat = event.clientId
                            materialSeleccionat = ""
                            quantitatMaterial = ""
                            assignacionsSeleccionades.clear()
                            assignacionsSeleccionades.addAll(event.assignacionsMaterial)
                            missatgeError = ""
                        }) {
                            Text("Editar")
                        }

                        Spacer(Modifier.width(8.dp))

                        Button(onClick = {
                            eventVM.eliminarEsdeveniment(event.id)
                        }) {
                            Text("Eliminar")
                        }
                    }
                }
            }
        }
    }
}