package com.pixel_ninja.vet_track.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "births")
data class BirthEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val animalName: String,  // Nom de l'animal
    val birthDate: LocalDate, // Date de naissance
    val description: String   // Description
)

