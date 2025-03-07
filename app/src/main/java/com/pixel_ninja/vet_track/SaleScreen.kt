package com.pixel_ninja.vet_track

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.TextField
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pixel_ninja.vet_track.data.model.SaleEntity
import com.pixel_ninja.vet_track.viewModel.SaleViewModel
import java.time.LocalDate

@Composable
fun SaleScreen(viewModel: SaleViewModel) {
    val sales = viewModel.sales.value

    // UI pour ajouter une vente
    var animalId = TextFieldValue("")
    var price = TextFieldValue("")
    var date = TextFieldValue(LocalDate.now().toString())

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        // Titre
        Text(
            text = "Suivi des Ventes",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        // Formulaire pour ajouter une vente
        OutlinedTextField(
            value = animalId,
            onValueChange = { animalId = it },
            label = { Text("ID de l'animal") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Prix de la vente") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date de vente") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        // Bouton pour ajouter la vente
        Button(
            onClick = {
                val saleEntity = SaleEntity(
                    animalId = animalId.text.toLong(),
                    price = price.text.toDouble(),
                    date = LocalDate.parse(date.text)
                )
                viewModel.createSale(saleEntity)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ajouter la vente")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Affichage de la liste des ventes
        Text(
            text = "Historique des Ventes",
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Liste des ventes
        sales?.forEach { sale ->
            Text(text = "Animal ID: ${sale.animalId}, Prix: ${sale.price}, Date: ${sale.date}")
        }
    }
}
