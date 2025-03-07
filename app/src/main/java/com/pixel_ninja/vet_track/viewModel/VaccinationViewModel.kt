package com.pixel_ninja.vet_track.viewModel

import androidx.lifecycle.ViewModel
import com.pixel_ninja.vet_track.data.VetTracDb
import com.pixel_ninja.vet_track.data.services.CareService
import com.pixel_ninja.vet_track.data.services.VaccinationService
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pixel_ninja.vet_track.data.model.CareEntity
import com.pixel_ninja.vet_track.data.model.VaccinationEntity
import kotlinx.coroutines.launch


class VaccinationViewModel (context : Context) : ViewModel() {
    private val db = VetTracDb.getDatabase(context)
    private val vaccinationService= VaccinationService(db.vaccinationDao())

    private   val _vaccins = MutableLiveData<List<VaccinationEntity>>()
    val vaccins : MutableLiveData<List<VaccinationEntity>> get() = _vaccins

    fun createVaccination(vacin: VaccinationEntity) {
        viewModelScope.launch {
           vaccinationService.newVaccination(vacin)
        }
    }

    fun deletedVaccination(vaccin : VaccinationEntity) {
        viewModelScope.launch {
            vaccinationService.deleteVaccin(vaccin)
        }
    }
    fun getAllsVaccination() {
        viewModelScope.launch {
            vaccinationService.getAllVaccination().collect{
                _vaccins.value = it
            }
        }
    }
}