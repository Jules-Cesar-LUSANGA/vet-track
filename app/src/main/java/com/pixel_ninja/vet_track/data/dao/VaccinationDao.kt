package com.pixel_ninja.vet_track.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.pixel_ninja.vet_track.data.model.VaccinationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VaccinationDao {
    @Upsert
    suspend fun update(vaccination: VaccinationEntity)

    @Query("SELECT * FROM vaccinations ORDER BY name ASC")
    fun getVaccinations(): Flow<List<VaccinationEntity>>

    @Delete
    suspend fun deleteVaccination(vaccination: VaccinationEntity)
}