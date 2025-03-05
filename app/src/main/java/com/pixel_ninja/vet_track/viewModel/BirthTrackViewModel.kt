package com.pixel_ninja.vet_track.viewModel

import android.content.Context
import androidx.lifecycle.*
import com.pixel_ninja.vet_track.data.VetTracDb
import com.pixel_ninja.vet_track.data.model.BirthEntity
import com.pixel_ninja.vet_track.data.services.BirthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BirthTrackViewModel(context: Context) : ViewModel() {
    private val db = VetTracDb.getDatabase(context)
    private val birthService = BirthService(db.birthDao())

    private val _births = MutableLiveData<List<BirthEntity>>()
    val births: LiveData<List<BirthEntity>> get() = _births  // Exposé en tant que LiveData

    private val _birth = MutableLiveData<BirthEntity?>()
    val birth: LiveData<BirthEntity?> get() = _birth  // LiveData pour une seule naissance

    fun createBirth(birth: BirthEntity) {
        viewModelScope.launch {
            birthService.addBirth(birth)
        }
    }

    fun deleteBirth(birth: BirthEntity) {
        viewModelScope.launch {
            birthService.deleteBirth(birth)
        }
    }

    fun getAllBirths() {
        viewModelScope.launch {
            birthService.getAllBirth().collect {
                _births.postValue(it)
            }
        }
    }

    fun getOneBirth(birthId: Long) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                birthService.getBirthById(birthId)
            }
            _birth.postValue(result)
        }
    }

}
