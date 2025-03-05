package com.pixel_ninja.vet_track.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.pixel_ninja.vet_track.data.model.SaleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SaleDao {
    @Upsert
    suspend fun update(sale: SaleEntity)

    @Query("SELECT * FROM sales ORDER BY date DESC")
    fun getSales(): Flow<List<SaleEntity>>

    @Query("SELECT * FROM sales WHERE id = :saleId LIMIT 1")
    suspend fun getSaleById(saleId: Long):SaleEntity?

    @Delete
    suspend fun deleteSale(sale: SaleEntity)

}