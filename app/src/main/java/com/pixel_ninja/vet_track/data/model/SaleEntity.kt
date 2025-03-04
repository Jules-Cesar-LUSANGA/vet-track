package com.pixel_ninja.vet_track.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "sales", indices = [Index(value = ["animalId"])] )
data class SaleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val animalId: Long,
    val date: LocalDate,
    val price: Double,

)
