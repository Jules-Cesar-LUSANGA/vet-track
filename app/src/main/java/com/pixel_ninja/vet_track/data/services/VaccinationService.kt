package com.pixel_ninja.vet_track.data.services

import com.pixel_ninja.vet_track.data.dao.VaccinationDao
import com.pixel_ninja.vet_track.data.model.AnimalEntity
import com.pixel_ninja.vet_track.data.model.BirthEntity
import com.pixel_ninja.vet_track.data.model.VaccinationEntity
import kotlinx.coroutines.flow.Flow

class VaccinationService (private val vaccinationDao: VaccinationDao) {
    suspend fun newVaccination(vaccin: VaccinationEntity) {
       vaccinationDao.update(vaccin)
    }

    suspend fun deleteVaccin(vaccin:  VaccinationEntity){
        vaccinationDao.deleteVaccination(vaccin)
    }

    fun getAllVaccination() : Flow<List<VaccinationEntity>> {
        return vaccinationDao.getVaccinations()
    }
}