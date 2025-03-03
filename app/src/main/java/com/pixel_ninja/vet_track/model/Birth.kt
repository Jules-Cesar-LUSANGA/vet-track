package com.pixel_ninja.vet_track.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "births", foreignKeys = [ForeignKey(
    entity = Animal::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("motherAnimalId"),
    onDelete = ForeignKey.CASCADE
)])

data class Birth(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val motherAnimalId: Long,  // clé étrangère vers l'animal mère
    val birthDate: String,
    val babyAnimalDetails: String
)
