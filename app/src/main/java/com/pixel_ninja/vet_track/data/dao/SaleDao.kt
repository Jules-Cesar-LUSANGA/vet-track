package com.pixel_ninja.vet_track.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SaleDao {
    @Upsert
    suspend fun update(sale: SaleDao)

    @Query("SELECT * FROM sales ORDER BY date DESC")
    fun getSales(): Flow<List<SaleDao>>

    @Query("SELECT * FROM sales WHERE id = :saleId LIMIT 1")
    suspend fun getSaleById(saleId: Long): SaleDao?

    @Delete
    suspend fun deleteSales(sales: List<SaleDao>)

    @Delete
    suspend fun deleteSale(sale: SaleDao)

}