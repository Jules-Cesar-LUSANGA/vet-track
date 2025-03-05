package com.pixel_ninja.vet_track.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pixel_ninja.vet_track.data.VetTracDb
import com.pixel_ninja.vet_track.data.model.CareEntity
import com.pixel_ninja.vet_track.data.services.CareService
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CareViewModel (context : Context) : ViewModel() {
    private val db = VetTracDb.getDatabase(context)
    private val careService = CareService(db.careDao())

    private   val _cares = MutableLiveData<List<CareEntity>>()
   val cares :  MutableLiveData<List<CareEntity>> get() = _cares

    private val _care = MutableLiveData<CareEntity?>()
    val care :  MutableLiveData<CareEntity?> get() = _care

    fun createCare(care : CareEntity) {
        viewModelScope.launch {
            careService.addCare(care)
        }
    }

    fun deletedCare(care : CareEntity) {
        viewModelScope.launch {
            careService.deleteCare(care)
        }
    }
    fun getAllCare() {
        viewModelScope.launch {
            careService.getAllCares().collect{
                _cares.value = it
            }
        }
    }

    fun getOneCare(careId : Long) {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                careService.getCareById(careId)
            }
            _care.postValue(result)
        }
    }
}