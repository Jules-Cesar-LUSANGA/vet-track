package com.pixel_ninja.vet_track

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.pixel_ninja.vet_track.partials.BottomNavigationBar
import com.pixel_ninja.vet_track.partials.TopBar
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "animaux",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("animaux") { AnimalScreen()  }
            composable("vaccination") { VaccinationScreen() }
            composable("soins") { SoinScreen() }
        }
    }
}
