package com.pixel_ninja.vet_track.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.pixel_ninja.vet_track.data.model.AnimalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimalDao {

    @Upsert
    suspend fun update(animal: AnimalEntity)

    @Query("SELECT * FROM animals ORDER BY date DESC")
    fun getAnimals() : Flow<List<AnimalEntity>> // Flow is used to observe changes in the database

    @Query("SELECT * FROM animals WHERE id = :animalId LIMIT 1")
    suspend fun getAnimalById(animalId: Long): AnimalEntity?


    @Delete
    suspend fun deleteAnimal(animal: AnimalEntity)



}