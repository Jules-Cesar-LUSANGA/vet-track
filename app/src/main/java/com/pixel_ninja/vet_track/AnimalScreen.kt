package com.pixel_ninja.vet_track

import android.net.Uri

import androidx.compose.ui.graphics.asImageBitmap

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pixel_ninja.vet_track.data.model.AnimalEntity
import com.pixel_ninja.vet_track.partials.utils.toBitmap
import com.pixel_ninja.vet_track.viewModel.AnimalVetTrackViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AnimalScreen(viewModel: AnimalVetTrackViewModel) {
    // État pour stocker la valeur de la recherche
    var searchQuery by remember { mutableStateOf("") }

    // Liste filtrée des animaux
    val filteredAnimals by viewModel.animals.observeAsState(emptyList())

    // Gestion des permissions et des résultats
    val context = LocalContext.current
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    // Gérer la prise de photo avec la caméra
    val takePhotoLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
        if (isSuccess && photoUri != null) {
            // Logic to handle the taken photo (save it to DB or use it)
            // Ex: viewModel.saveAnimalPhoto(photoUri)
        }
    }

    // Gérer la sélection d'une image depuis la galerie
    val pickImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        photoUri = uri
        uri?.let {
            // Logic to handle the selected image (save it to DB or use it)
            // Ex: viewModel.saveAnimalPhoto(uri)
        }
    }

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
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Rechercher") }
            )

            // Afficher la liste des animaux
            if (filteredAnimals.isNotEmpty()) {
                LazyColumn {
                    items(filteredAnimals) { animal ->
                        AnimalCard(animal = animal)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            } else {
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

            // Options pour ajouter une photo
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { photoUri?.let { takePhotoLauncher.launch(it) } },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Prendre une photo")
                }

                Button(
                    onClick = { pickImageLauncher.launch("image/*") },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Choisir une photo existante")
                }

                // Afficher la photo si elle est sélectionnée
                photoUri?.let {
                    val bitmap = it.toBitmap(context)

                    // Vérification si le bitmap est non nul et conversion
                    bitmap?.let { validBitmap ->
                        val imageBitmap: ImageBitmap = validBitmap.asImageBitmap()

                        Image(
                            bitmap = imageBitmap,
                            contentDescription = "Image de l'animal",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

        // FloatingActionButton pour ajouter un animal
        FloatingActionButton(
            onClick = { /* Logique d'ajout d'animal */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Ajouter un animal")
        }
    }
}

@Composable
fun AnimalCard(animal: AnimalEntity) {
    // Affichage d'une carte pour chaque animal
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = animal.name, style = MaterialTheme.typography.headlineSmall)
        Text(text = animal.breed, style = MaterialTheme.typography.bodyMedium)
        Text(text = "Âge: ${animal.age}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "État de santé: ${animal.healthStatus}", style = MaterialTheme.typography.bodyMedium)

        // Afficher l'image de l'animal
        if (animal.photoUri.isNotEmpty()) {
            val imageUri = Uri.parse(animal.photoUri)
            val bitmap = imageUri.toBitmap(LocalContext.current) // Obtenez le Bitmap

            // Convertir le Bitmap en ImageBitmap
            val imageBitmap: ImageBitmap = bitmap?.asImageBitmap() ?: ImageBitmap(0, 0)

            Image(
                bitmap = imageBitmap,
                contentDescription = "Image de l'animal",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimalScreenPreview() {
    val context = LocalContext.current
    // Création d'un ViewModel factice
    val fakeViewModel = AnimalVetTrackViewModel(context)

    // Injecter des données fictives via la méthode setAnimals
    val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    fakeViewModel.setAnimals(
        listOf(
            AnimalEntity(
                name = "Rex",
                breed = "Labrador",
                age = 3,
                healthStatus = "En bonne santé",
                photoUri = "https://www.exemple.com/photo_rex.jpg",
                date = currentDate // Ajout de la date
            ),
            AnimalEntity(
                name = "Félix",
                breed = "Chat",
                age = 2,
                healthStatus = "Malade",
                photoUri = "https://www.exemple.com/photo_felix.jpg",
                date = currentDate // Ajout de la date
            )
        )
    )


    // Afficher la prévisualisation avec les données fictives
    AnimalScreen(viewModel = fakeViewModel)
}





