package com.pixel_ninja.vet_track.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "cares", foreignKeys = [ForeignKey(
    entity = Animal::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("animalId"),
    onDelete = ForeignKey.CASCADE
)])
data class Care(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val vaccinationDate: String,
    val careDetails: String,
    val animalId: Long  // clé étrangère vers l'animal
)
