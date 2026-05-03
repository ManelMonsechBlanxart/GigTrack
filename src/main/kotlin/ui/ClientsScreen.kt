package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.gestio.Client
import viewmodel.ClientViewModel

@Composable
fun ClientScreen(viewModel: ClientViewModel) {

    var nom by remember { mutableStateOf("") }
    var telefon by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {

        Text("Clients", style = MaterialTheme.typography.h5)

        OutlinedTextField(nom, { nom = it }, label = { Text("Nom") })
        OutlinedTextField(telefon, { telefon = it }, label = { Text("Telèfon") })
        OutlinedTextField(email, { email = it }, label = { Text("Email") })

        Button(onClick = {
            val client = Client(
                "C${System.currentTimeMillis()}",
                nom,
                telefon,
                email
            )
            viewModel.guardarClient(client)
            nom = ""; telefon = ""; email = ""
        }) {
            Text("Afegir")
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(viewModel.clients) { c ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(c.resum())
                    Button(onClick = { viewModel.eliminarClient(c.id) }) {
                        Text("Eliminar")
                    }
                }
            }
        }
    }
}