package com.pixel_ninja.vet_track.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "animals")
data class AnimalEntity(
    @PrimaryKey(autoGenerate = true)
    val  id : Long = 0,
    val name : String,
    val breed: String,
    val age: Int,
    val healthStatus: String,
    val photoUri: String,
    val date: LocalDate,
)
