package com.pixel_ninja.vet_track.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.pixel_ninja.vet_track.data.model.CareEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CareDao {
    @Upsert
    suspend fun update(care: CareEntity)

    @Query("SELECT * FROM cares ORDER BY date DESC")
    fun getCares(): Flow<List<CareEntity>>

    @Query("SELECT * FROM cares WHERE id = :careId LIMIT 1")
    suspend fun getCareById(careId: Long): CareEntity?

    @Delete
    suspend fun deleteCare(care: CareEntity)

}