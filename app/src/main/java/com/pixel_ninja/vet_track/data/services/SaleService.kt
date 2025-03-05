package com.pixel_ninja.vet_track.data.services

import com.pixel_ninja.vet_track.data.dao.SaleDao
import com.pixel_ninja.vet_track.data.model.SaleEntity
import kotlinx.coroutines.flow.Flow

class SaleService (private val saleDao: SaleDao) {
    suspend fun addSale(sale: SaleEntity){
        saleDao.update(sale)
    }
    suspend fun deleteSale(sale: SaleEntity){
        saleDao.deleteSale(sale)
    }
    fun getAllSales() : Flow<List<SaleEntity>> {
        return saleDao.getSales()
    }

    suspend fun getSaleById(saleId : Long) : SaleEntity? {
        return saleDao.getSaleById(saleId)
    }
}