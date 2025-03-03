package com.pixel_ninja.vet_track.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "sales", foreignKeys = [ForeignKey(
    entity = Animal::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("animalId"),
    onDelete = ForeignKey.CASCADE
)])
data class Sale(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val animalId: Long,  // clé étrangère vers l'animal vendu
    val saleDate: String,
    val buyerDetails: String,
    val salePrice: Double
)
