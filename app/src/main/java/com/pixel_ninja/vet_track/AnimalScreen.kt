package com.pixel_ninja.vet_track

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.File


data class Animal(
    val id: Int,
    val name: String,
    val breed: String,
    val age: Int,
    val healthStatus: String,
    val imagePath: String // Chemin d'accès local ou URI de l'image
)

val animals = listOf(
    Animal(
        id = 1,
        name = "Bella",
        breed = "Vache Holstein",
        age = 3,
        healthStatus = "Bonne santé",
        imagePath = "/storage/emulated/0/DCIM/bella.jpg" // Exemple de chemin local
    ),
    Animal(
        id = 2,
        name = "Max",
        breed = "Mouton Suffolk",
        age = 2,
        healthStatus = "En traitement",
        imagePath = "/storage/emulated/0/DCIM/max.jpg" // Exemple de chemin local
    ),
    Animal(
        id = 3,
        name = "Luna",
        breed = "Chèvre Alpine",
        age = 4,
        healthStatus = "Bonne santé",
        imagePath = "/storage/emulated/0/DCIM/luna.jpg" // Exemple de chemin local
    )
)

@Composable
fun AnimalScreen() {
    // État pour stocker la valeur de la recherche
    var searchQuery by remember { mutableStateOf("") }

    // État pour contrôler l'affichage du popup
    var showPopup by remember { mutableStateOf(false) }

    // Liste filtrée des animaux
    val filteredAnimals = animals.filter { animal ->
        animal.name.contains(searchQuery, ignoreCase = true) ||
                animal.breed.contains(searchQuery, ignoreCase = true) ||
                animal.age.toString().contains(searchQuery, ignoreCase = true)
    }

    // État pour les champs du formulaire
    var animalName by remember { mutableStateOf("") }
    var animalBreed by remember { mutableStateOf("") }
    var animalAge by remember { mutableStateOf("") }
    var animalHealthStatus by remember { mutableStateOf("") }
    var animalImageUri by remember { mutableStateOf<Uri?>(null) }

    // Context pour lancer l'ImagePicker
    val context = LocalContext.current

    // Launcher pour l'ImagePicker
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            animalImageUri = uri // Stocker l'URI de l'image sélectionnée
        }
    )


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Barre de recherche
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                placeholder = { Text("Nom, race ou âge") },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Rechercher"
                    )
                },
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            // Afficher la liste des animaux ou un message si aucun animal n'est trouvé
            if (filteredAnimals.isNotEmpty()) {
                LazyColumn {
                    items(filteredAnimals) { animal ->
                        AnimalCard(animal = animal)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            } else {
                // Afficher un message si aucun animal n'est trouvé
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Aucun animal trouvé",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }

        // FloatingActionButton pour ajouter un animal
        FloatingActionButton(
            onClick = { showPopup = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Ajouter un animal",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }

    // Popup pour enregistrer un animal
    if (showPopup) {
        AlertDialog(
            onDismissRequest = { showPopup = false },
            title = { Text("Ajouter un animal") },
            text = {
                Column {
                    TextField(
                        value = animalName,
                        onValueChange = { animalName = it },
                        label = { Text("Nom") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = animalBreed,
                        onValueChange = { animalBreed = it },
                        label = { Text("Race") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = animalAge,
                        onValueChange = { animalAge = it },
                        label = { Text("Âge") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = animalHealthStatus,
                        onValueChange = { animalHealthStatus = it },
                        label = { Text("État de santé") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Bouton pour sélectionner une image
                    Button(
                        onClick = { imagePickerLauncher.launch("image/*") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Sélectionner une image")
                    }

                    // Afficher l'image sélectionnée
                    if (animalImageUri != null) {
                        Spacer(modifier = Modifier.height(16.dp))
                        val bitmap = rememberImageBitmapFromUri(context, animalImageUri!!)
                        if (bitmap != null) {
                            Image(
                                bitmap = bitmap,
                                contentDescription = "Image de l'animal",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .clip(MaterialTheme.shapes.medium),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Text("Impossible de charger l'image", color = Color.Red)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Ajouter l'animal à la liste
                        val newAnimal = Animal(
                            id = animals.size + 1, // Générer un nouvel ID
                            name = animalName,
                            breed = animalBreed,
                            age = animalAge.toIntOrNull() ?: 0,
                            healthStatus = animalHealthStatus,
                            imagePath = animalImageUri.toString() // Stocker l'URI de l'image
                        )

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
@Composable
fun AnimalCard(animal: Animal) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Image de l'animal (chargée à partir du chemin local)
            val imageBitmap = rememberImageBitmapFromPath(animal.imagePath)
            if (imageBitmap != null) {
                Image(
                    bitmap = imageBitmap,
                    contentDescription = "Photo de ${animal.name}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Afficher un placeholder si l'image n'est pas trouvée
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Image non disponible", color = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Nom de l'animal
            Text(
                text = animal.name,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            // Race de l'animal
            Text(
                text = "Race : ${animal.breed}",
                style = MaterialTheme.typography.bodyMedium
            )

            // Âge de l'animal
            Text(
                text = "Âge : ${animal.age} ans",
                style = MaterialTheme.typography.bodyMedium
            )

            // État de santé
            Text(
                text = "Santé : ${animal.healthStatus}",
                style = MaterialTheme.typography.bodyMedium,
                color = when (animal.healthStatus) {
                    "Bonne santé" -> Color.Green
                    "En traitement" -> Color.Yellow
                    else -> Color.Red
                }
            )
        }
    }
}

@Composable
fun rememberImageBitmapFromPath(imagePath: String): ImageBitmap? {
    return remember(imagePath) {
        val file = File(imagePath)
        if (file.exists()) {
            val bitmap = BitmapFactory.decodeFile(file.absolutePath)
            bitmap?.asImageBitmap()
        } else {
            null
        }
    }
}


@Composable
fun rememberImageBitmapFromUri(context: Context, uri: Uri): ImageBitmap? {
    return remember(uri) {
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            bitmap?.asImageBitmap()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}