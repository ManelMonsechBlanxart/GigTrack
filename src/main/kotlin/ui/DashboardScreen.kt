package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import viewmodel.ClientViewModel
import viewmodel.EsdevenimentViewModel
import viewmodel.MaterialViewModel
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Composable
fun DashboardScreen(
    clientVM: ClientViewModel,
    eventVM: EsdevenimentViewModel,
    materialVM: MaterialViewModel
) {
    val avui = LocalDate.now()

    val proxims = eventVM.events
        .filter { it.data.isAfter(avui) || it.data.isEqual(avui) }
        .sortedBy { it.data }
        .take(5)

    val proximEvent = proxims.firstOrNull()

    Column(Modifier.padding(16.dp)) {

        Text("Dashboard", style = MaterialTheme.typography.h4)

        Spacer(Modifier.height(16.dp))

        Text("Total clients: ${clientVM.clients.size}")
        Text("Total esdeveniments: ${eventVM.events.size}")
        Text("Total material inventariat: ${materialVM.materials.size}")

        Spacer(Modifier.height(16.dp))

        Text("Pròxim esdeveniment destacat:", style = MaterialTheme.typography.h6)

        if (proximEvent != null) {
            val client = clientVM.clients.find { it.id == proximEvent.clientId }?.nom ?: "Sense client"
            val dies = ChronoUnit.DAYS.between(avui, proximEvent.data)

            val materials = proximEvent.assignacionsMaterial.mapNotNull { assignacio ->
                val material = materialVM.materials.find { it.id == assignacio.materialId }
                material?.let { "${it.nom} x${assignacio.quantitat}" }
            }

            Text(proximEvent.titol)
            Text("Client: $client")
            Text("Data: ${proximEvent.data}")
            Text("Falten $dies dies")
            Text("Preu: ${proximEvent.cachet}€")

            if (materials.isEmpty()) {
                Text("Material: cap material assignat")
            } else {
                Text("Material: ${materials.joinToString(", ")}")
            }
        } else {
            Text("No hi ha esdeveniments pròxims.")
        }

        Spacer(Modifier.height(24.dp))

        Text("Pròxims esdeveniments:", style = MaterialTheme.typography.h6)

        if (proxims.isEmpty()) {
            Text("No hi ha esdeveniments pròxims.")
        } else {
            proxims.forEach { event ->
                val client = clientVM.clients.find { it.id == event.clientId }?.nom ?: "Sense client"
                val dies = ChronoUnit.DAYS.between(avui, event.data)

                val materials = event.assignacionsMaterial.mapNotNull { assignacio ->
                    val material = materialVM.materials.find { it.id == assignacio.materialId }
                    material?.let { "${it.nom} x${assignacio.quantitat}" }
                }

                Text("${event.data} - ${event.titol} - $client - falten $dies dies")

                if (materials.isNotEmpty()) {
                    Text("Material: ${materials.joinToString(", ")}")
                }

                Spacer(Modifier.height(8.dp))
            }
        }
    }
}