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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SoinScreen() {
    var showPopup by remember { mutableStateOf(false) }
    var soinName by remember { mutableStateOf("") }
    var soinDescription by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(soins) { soin ->
                SoinCard(soin = soin)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        FloatingActionButton(
            onClick = { showPopup = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Ajouter un soin",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    if (showPopup) {
        AlertDialog(
            onDismissRequest = { showPopup = false },
            title = { Text("Ajouter un soin") },
            text = {
                Column {
                    TextField(
                        value = soinName,
                        onValueChange = { soinName = it },
                        label = { Text("Nom du soin") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = soinDescription,
                        onValueChange = { soinDescription = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val newSoin = Soin(
                            id = soins.size + 1,
                            name = soinName,
                            description = soinDescription
                        )
                        soins.add(newSoin)
                        showPopup = false
                    }
                ) {
                    Text("Enregistrer")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPopup = false }) {
                    Text("Annuler")
                }
            }
        )
    }
}

data class Soin(
    val id: Int,
    val name: String,
    val description: String
)

val soins = mutableListOf(
    Soin(
        id = 1,
        name = "Nettoyage des plaies",
        description = "Désinfection et nettoyage des plaies ouvertes."
    ),
    Soin(
        id = 2,
        name = "Traitement antiparasitaire",
        description = "Élimination des parasites internes et externes."
    )
)

@Composable
fun SoinCard(soin: Soin) {
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
                text = soin.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = soin.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}