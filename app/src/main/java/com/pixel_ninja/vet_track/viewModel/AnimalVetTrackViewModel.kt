package com.pixel_ninja.vet_track.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pixel_ninja.vet_track.data.VetTracDb
import com.pixel_ninja.vet_track.data.model.AnimalEntity
import com.pixel_ninja.vet_track.data.services.AnimalService
import kotlinx.coroutines.launch

class AnimalVetTrackViewModel (context: Context) : ViewModel()   {
    private val db: VetTracDb = VetTracDb.getDatabase(context)
    private val animalService = AnimalService(db.animalDao())

    // LiveData pour observer la liste des animaux
    private val _animals = MutableLiveData<List<AnimalEntity>>()
    val animals: LiveData<List<AnimalEntity>> get() = _animals

    fun setAnimals(animalList: List<AnimalEntity>) {
        _animals.value = animalList
    }

    fun createAnimal(animal: AnimalEntity) {
        viewModelScope.launch {
            animalService.addAnimal(animal)
        }
    }

    fun deletedAnimal(animal: AnimalEntity) {
        viewModelScope.launch {
            animalService.deleteAnimal(animal)
        }
    }

    fun searchByBreed(breed: String) {
        viewModelScope.launch {
            animalService.researchByBreed(breed).collect { result ->
                _animals.value = result
            }
        }
    }

    fun searchByAge(age: Int) {
        viewModelScope.launch {
            animalService.researchByAge(age).collect { result ->
                _animals.value = result
            }
        }
    }
}