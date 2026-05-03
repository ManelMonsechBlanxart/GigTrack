package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.base.EstatMaterial
import model.material.*
import viewmodel.MaterialViewModel

@Composable
fun MaterialScreen(viewModel: MaterialViewModel) {

    var idEditant by remember { mutableStateOf<String?>(null) }
    var tipus by remember { mutableStateOf("Altaveu") }
    var nom by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var preu by remember { mutableStateOf("") }
    var quantitat by remember { mutableStateOf("") }
    var polzades by remember { mutableStateOf("") }
    var potencia by remember { mutableStateOf("") }
    var actiu by remember { mutableStateOf(true) }
    var desplegat by remember { mutableStateOf(false) }
    var cerca by remember { mutableStateOf("") }
    var missatgeError by remember { mutableStateOf("") }

    val materialsFiltrats = viewModel.materials.filter {
        it.nom.contains(cerca, ignoreCase = true) ||
                it.marca.contains(cerca, ignoreCase = true) ||
                it.model.contains(cerca, ignoreCase = true) ||
                it.getTipus().contains(cerca, ignoreCase = true)
    }

    Column(Modifier.padding(16.dp)) {

        Text("Material", style = MaterialTheme.typography.h5)

        if (missatgeError.isNotBlank()) {
            Text("Error: $missatgeError")
            Spacer(Modifier.height(8.dp))
        }

        Button(onClick = { desplegat = true }) {
            Text("Tipus: $tipus")
        }

        DropdownMenu(expanded = desplegat, onDismissRequest = { desplegat = false }) {
            listOf("Altaveu", "Subwoofer", "Controladora", "Reproductor", "CapMovil", "Wash").forEach {
                DropdownMenuItem(onClick = {
                    tipus = it
                    desplegat = false
                }) {
                    Text(it)
                }
            }
        }

        OutlinedTextField(nom, { nom = it }, label = { Text("Nom") })
        OutlinedTextField(marca, { marca = it }, label = { Text("Marca") })
        OutlinedTextField(model, { model = it }, label = { Text("Model") })
        OutlinedTextField(preu, { preu = it }, label = { Text("Preu per unitat") })
        OutlinedTextField(quantitat, { quantitat = it }, label = { Text("Quantitat disponible") })

        if (tipus == "Altaveu" || tipus == "Subwoofer") {
            OutlinedTextField(polzades, { polzades = it }, label = { Text("Polzades") })
            Row {
                Checkbox(checked = actiu, onCheckedChange = { actiu = it })
                Text("Actiu")
            }
        }

        if (tipus == "CapMovil" || tipus == "Wash") {
            OutlinedTextField(potencia, { potencia = it }, label = { Text("Potència W") })
        }

        Button(onClick = {
            try {
                if (nom.isBlank()) {
                    missatgeError = "El nom no pot estar buit"
                    return@Button
                }

                if (marca.isBlank()) {
                    missatgeError = "La marca no pot estar buida"
                    return@Button
                }

                if (model.isBlank()) {
                    missatgeError = "El model no pot estar buit"
                    return@Button
                }

                val id = idEditant ?: "M${System.currentTimeMillis()}"
                val preuDouble = preu.toDoubleOrNull() ?: 0.0
                val quantitatInt = quantitat.toIntOrNull() ?: 1

                val material = when (tipus) {
                    "Altaveu" -> Altaveu(
                        id, nom, marca, model, preuDouble, true,
                        EstatMaterial.DISPONIBLE, quantitatInt,
                        polzades.toIntOrNull() ?: 12, actiu
                    )

                    "Subwoofer" -> Subwoofer(
                        id, nom, marca, model, preuDouble, true,
                        EstatMaterial.DISPONIBLE, quantitatInt,
                        polzades.toIntOrNull() ?: 18, actiu
                    )

                    "Controladora" -> Controladora(
                        id, nom, marca, model, preuDouble, true,
                        EstatMaterial.DISPONIBLE, quantitatInt
                    )

                    "Reproductor" -> Reproductor(
                        id, nom, marca, model, preuDouble, true,
                        EstatMaterial.DISPONIBLE, quantitatInt
                    )

                    "CapMovil" -> CapMovil(
                        id, nom, marca, model, preuDouble, true,
                        EstatMaterial.DISPONIBLE, quantitatInt,
                        potencia.toIntOrNull() ?: 0
                    )

                    else -> Wash(
                        id, nom, marca, model, preuDouble, true,
                        EstatMaterial.DISPONIBLE, quantitatInt,
                        potencia.toIntOrNull() ?: 0
                    )
                }

                viewModel.guardarMaterial(material)

                idEditant = null
                nom = ""
                marca = ""
                model = ""
                preu = ""
                quantitat = ""
                polzades = ""
                potencia = ""
                actiu = true
                missatgeError = ""

            } catch (e: Exception) {
                missatgeError = e.message ?: "Error desconegut"
            }
        }) {
            Text(if (idEditant == null) "Afegir material" else "Guardar canvis")
        }

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = cerca,
            onValueChange = { cerca = it },
            label = { Text("Cercar material") }
        )

        Spacer(Modifier.height(8.dp))

        Text("Material inventariat: ${materialsFiltrats.size}")

        LazyColumn {
            items(materialsFiltrats) { material ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(material.resum())

                    Row {
                        Button(onClick = {
                            idEditant = material.id
                            tipus = material.getTipus()
                            nom = material.nom
                            marca = material.marca
                            model = material.model
                            preu = material.preuPerUnitat.toString()
                            quantitat = material.quantitat.toString()
                            missatgeError = ""

                            if (material is Altaveu) {
                                polzades = material.polzades.toString()
                                actiu = material.actiu
                            }

                            if (material is Subwoofer) {
                                polzades = material.polzades.toString()
                                actiu = material.actiu
                            }

                            if (material is MaterialLlum) {
                                potencia = material.potenciaW.toString()
                            }
                        }) {
                            Text("Editar")
                        }

                        Button(onClick = { viewModel.eliminarMaterial(material.id) }) {
                            Text("Eliminar")
                        }
                    }
                }
            }
        }
    }
}