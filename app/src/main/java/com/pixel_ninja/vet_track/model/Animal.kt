package com.pixel_ninja.vet_track.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animals")
data class Animal(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val race: String,
    val age: Int,
    val healthStatus: String,
    val photoUri: String
)
