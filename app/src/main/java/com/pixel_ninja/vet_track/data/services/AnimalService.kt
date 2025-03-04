package com.pixel_ninja.vet_track.data.services

import com.pixel_ninja.vet_track.data.dao.AnimalDao
import com.pixel_ninja.vet_track.data.model.AnimalEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AnimalService (private val animalDao: AnimalDao) {
    suspend fun addAnimal(animal: AnimalEntity) {
        animalDao.update(animal)
    }



    suspend fun deleteAnimal(animal: AnimalEntity){
        animalDao.deleteAnimal(animal)
    }

    fun researchByBreed(breed : String) : Flow<List<AnimalEntity>>{
        return animalDao.getAnimals().map { animals ->
            animals.filter { it.breed.equals(breed, ignoreCase = true) } }

    }

    fun researchByAge(age : Int) : Flow<List<AnimalEntity>> {
        return animalDao.getAnimals().map { animals ->
            animals.filter { it.age == age }
        }
    }
}