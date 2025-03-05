package com.pixel_ninja.vet_track.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "births", indices = [Index(value = ["matherId", "fatherId"])])
data class BirthEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val matherId: Long,
    val fatherId: Long,
    val birthDay: LocalDate,
    val nbrWeight: Int,
    val date: LocalDate,

)
