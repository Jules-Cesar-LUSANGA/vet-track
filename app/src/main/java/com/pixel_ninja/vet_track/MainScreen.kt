package com.pixel_ninja.vet_track

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.pixel_ninja.vet_track.partials.BottomNavigationBar
import com.pixel_ninja.vet_track.partials.TopBar
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.pixel_ninja.vet_track.viewModel.AnimalVetTrackViewModel
import com.pixel_ninja.vet_track.viewModel.BirthTrackViewModel
import com.pixel_ninja.vet_track.viewModel.CareViewModel
import com.pixel_ninja.vet_track.viewModel.VaccinationViewModel

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val animalVetTrackViewModel: AnimalVetTrackViewModel = viewModel()
    val soinViewModel: CareViewModel = viewModel()
    val vaccinationViewModel: VaccinationViewModel = viewModel()
    val birthTrackViewModel: BirthTrackViewModel = viewModel()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "animaux",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("animaux") { AnimalScreen(viewModel = animalVetTrackViewModel)  }
            composable("vaccination") { VaccinationScreen(viewModel = vaccinationViewModel) }
            composable("soins") { SoinScreen(viewModel = soinViewModel) }
            composable("naissances") { BirthScreen(viewModel = birthTrackViewModel) }
        }
    }
}
