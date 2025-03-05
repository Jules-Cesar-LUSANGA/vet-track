package com.pixel_ninja.vet_track.data.services

import com.pixel_ninja.vet_track.data.dao.CareDao
import com.pixel_ninja.vet_track.data.model.CareEntity
import kotlinx.coroutines.flow.Flow

class CareService (private val careDao: CareDao)  {

    suspend fun addCare(care: CareEntity){
        careDao.update(care)
    }

    suspend fun deleteCare(care: CareEntity){
        careDao.deleteCare(care)
    }

    fun  getAllCares() : Flow<List<CareEntity>> {
        return careDao.getCares()
    }
    suspend fun getCareById(careId : Long) : CareEntity?{
        return careDao.getCareById(careId)
    }
}