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
import com.pixel_ninja.vet_track.data.model.BirthEntity
import com.pixel_ninja.vet_track.viewModel.BirthTrackViewModel
import java.time.LocalDate

@Composable
fun BirthScreen(viewModel: BirthTrackViewModel = viewModel()) {
    var showPopup by remember { mutableStateOf(false) }
    var animalName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    // Observer les changements de la liste des naissances
    val births by viewModel.births.observeAsState(emptyList())

    // Charger les naissances au démarrage
    LaunchedEffect(Unit) {
        viewModel.getAllBirths()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(births) { birth ->
                BirthCard(birth = birth, onDelete = { viewModel.deleteBirth(birth) })
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
                contentDescription = "Ajouter une naissance",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    // Pop-up pour ajouter une naissance
    if (showPopup) {
        AlertDialog(
            onDismissRequest = { showPopup = false },
            title = { Text("Ajouter une naissance") },
            text = {
                Column {
                    TextField(
                        value = animalName,
                        onValueChange = { animalName = it },
                        label = { Text("Nom de l'animal") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = birthDate,
                        onValueChange = { birthDate = it },
                        label = { Text("Date de naissance") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val newBirth = BirthEntity(
                            animalName = animalName,
                            birthDate = LocalDate.parse(birthDate),  // Conversion de la date
                            description = description
                        )
                        viewModel.createBirth(newBirth)  // Appeler la fonction du ViewModel pour ajouter la naissance
                        animalName = ""  // Réinitialiser les champs
                        birthDate = ""
                        description = ""
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
fun BirthCard(birth: BirthEntity, onDelete: () -> Unit) {
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
                text = "Naissance de ${birth.animalName} - ${birth.birthDate}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Description: ${birth.description}",
                style = MaterialTheme.typography.bodyMedium
            )

            // Bouton de suppression
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Supprimer")
            }
        }
    }
}
