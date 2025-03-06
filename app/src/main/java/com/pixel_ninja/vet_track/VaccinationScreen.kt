package com.pixel_ninja.vet_track

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
@Composable
fun VaccinationScreen() {
    // État pour contrôler l'affichage du popup
    var showPopup by remember { mutableStateOf(false) }

    // État pour les champs du formulaire
    var vaccinationName by remember { mutableStateOf("") }
    var vaccinationDescription by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        // Liste des vaccinations
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
                        // Ajouter la vaccination à la liste
                        val newVaccination = Vaccination(
                            id = vaccinations.size + 1, // Générer un nouvel ID
                            name = vaccinationName,
                            description = vaccinationDescription
                        )
                        vaccinations.add(newVaccination) // Ajouter à la liste
                        showPopup = false // Fermer le popup
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

data class Vaccination(
    val id: Int,
    val name: String,
    val description: String
)

val vaccinations = mutableListOf(
    Vaccination(
        id = 1,
        name = "Vaccin contre la grippe",
        description = "Protège contre les souches courantes de la grippe."
    ),
    Vaccination(
        id = 2,
        name = "Vaccin contre la rage",
        description = "Nécessaire pour les animaux exposés à la rage."
    )
)

@Composable
fun VaccinationCard(vaccination: Vaccination) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Nom de la vaccination
            Text(
                text = vaccination.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Description de la vaccination
            Text(
                text = vaccination.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}