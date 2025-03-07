package com.pixel_ninja.vet_track

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pixel_ninja.vet_track.data.model.VaccinationEntity
import com.pixel_ninja.vet_track.viewModel.VaccinationViewModel

@Composable
fun VaccinationScreen(viewModel: VaccinationViewModel) {
    val vaccinations by viewModel.vaccins.observeAsState(emptyList())

    var showPopup by remember { mutableStateOf(false) }
    var vaccinationName by remember { mutableStateOf("") }
    var vaccinationDescription by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.getAllsVaccination() // Charger les données au démarrage
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Liste des vaccinations dynamiques
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(vaccinations) { vaccination ->
                VaccinationCard(vaccination = vaccination)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // FloatingActionButton pour ajouter une vaccination
        FloatingActionButton(
            onClick = { showPopup = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Ajouter une vaccination",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    // Popup pour ajouter une vaccination
    if (showPopup) {
        AlertDialog(
            onDismissRequest = { showPopup = false },
            title = { Text("Ajouter une vaccination") },
            text = {
                Column {
                    TextField(
                        value = vaccinationName,
                        onValueChange = { vaccinationName = it },
                        label = { Text("Nom de la vaccination") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = vaccinationDescription,
                        onValueChange = { vaccinationDescription = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val newVaccination = VaccinationEntity(
                            name = vaccinationName,
                            description = vaccinationDescription
                        )
                        viewModel.createVaccination(newVaccination) // Sauvegarde dans la BDD
                        showPopup = false
                    }
                ) {
                    Text("Enregistrer")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showPopup = false }
                ) {
                    Text("Annuler")
                }
            }
        )
    }
}

@Composable
fun VaccinationCard(vaccination: VaccinationEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = vaccination.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = vaccination.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
