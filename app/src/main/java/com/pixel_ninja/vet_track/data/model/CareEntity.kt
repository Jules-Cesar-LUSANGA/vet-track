package com.pixel_ninja.vet_track.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "cares", indices = [Index(value = ["animalId"])])
data class CareEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val animalId: Long,
    val date: LocalDate,
    val type: String,
    val description: String
)
