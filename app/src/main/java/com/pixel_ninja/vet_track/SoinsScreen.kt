package com.pixel_ninja.vet_track

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.libraries.places.api.model.LocalDate
import com.pixel_ninja.vet_track.data.model.CareEntity
import com.pixel_ninja.vet_track.viewModel.CareViewModel


@Composable
fun SoinScreen(viewModel: CareViewModel = viewModel()) {
    var showPopup by remember { mutableStateOf(false) }
    var soinDescription by remember { mutableStateOf("") }
    var soinType by remember { mutableStateOf("") }

    // Observer les changements de la liste de soins
    val cares by viewModel.cares.observeAsState(emptyList())

    // Charger les soins au démarrage
    LaunchedEffect(Unit) {
        viewModel.getAllCare()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(cares) { soin ->
                SoinCard(soin = soin, onDelete = { viewModel.deletedCare(soin) })
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

    // Pop-up pour ajouter un soin
    if (showPopup) {
        AlertDialog(
            onDismissRequest = { showPopup = false },
            title = { Text("Ajouter un soin") },
            text = {
                Column {
                    TextField(
                        value = soinType,
                        onValueChange = { soinType = it },
                        label = { Text("Type de soin") },
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
                        val newSoin = CareEntity(
                            animalId = 1,  // Remplacez par l'ID de l'animal approprié
                            date = java.time.LocalDate.now(),  // Vous pouvez définir la date actuelle ou une autre valeur
                            type = soinType,
                            description = soinDescription
                        )
                        viewModel.createCare(newSoin)  // Appeler la fonction du ViewModel pour ajouter le soin
                        soinDescription = ""  // Réinitialiser les champs
                        soinType = ""
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


@Composable
fun SoinCard(soin: CareEntity, onDelete: () -> Unit) {
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
                text = soin.type,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = soin.description,
                style = MaterialTheme.typography.bodyMedium
            )

            // Bouton de suppression
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Supprimer")
            }
        }
    }
}
