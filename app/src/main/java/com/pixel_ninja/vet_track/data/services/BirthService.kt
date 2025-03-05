package com.pixel_ninja.vet_track.data.services

import com.pixel_ninja.vet_track.data.dao.BirthDao
import com.pixel_ninja.vet_track.data.model.BirthEntity
import kotlinx.coroutines.flow.Flow

class BirthService (private val birthDao: BirthDao) {

    suspend fun addBirth(birth: BirthEntity){
        birthDao.update(birth)
    }

    suspend fun deleteBirth(birth: BirthEntity){
        birthDao.deleteBirth(birth)
    }

    fun getAllBirth() : Flow<List<BirthEntity>> {
        return birthDao.getBirths()
    }
    suspend fun getBirthById(birthId: Long):BirthEntity? {
        return birthDao.getBirthById(birthId)
    }

}