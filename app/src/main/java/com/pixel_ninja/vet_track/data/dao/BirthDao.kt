package com.pixel_ninja.vet_track.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.pixel_ninja.vet_track.data.model.BirthEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BirthDao {
    @Upsert
    suspend fun update(birth: BirthEntity)

    @Query("SELECT * FROM births ORDER BY date DESC")
    fun getBirths(): Flow<List<BirthEntity>>



    @Query("SELECT * FROM births WHERE id = :birthId LIMIT 1")
    suspend fun getBirthById(birthId: Long): BirthEntity?

    @Delete
    suspend fun deleteBirth(birth: BirthEntity)
}