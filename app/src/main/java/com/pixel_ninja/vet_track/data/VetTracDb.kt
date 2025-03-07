package com.pixel_ninja.vet_track.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pixel_ninja.vet_track.data.dao.AnimalDao
import com.pixel_ninja.vet_track.data.dao.BirthDao
import com.pixel_ninja.vet_track.data.dao.CareDao
import com.pixel_ninja.vet_track.data.dao.SaleDao
import com.pixel_ninja.vet_track.data.model.AnimalEntity
import com.pixel_ninja.vet_track.data.model.BirthEntity
import com.pixel_ninja.vet_track.data.model.CareEntity
import com.pixel_ninja.vet_track.data.model.SaleEntity
import com.pixel_ninja.vet_track.data.Converters
import com.pixel_ninja.vet_track.data.dao.VaccinationDao

@Database(
    entities = [AnimalEntity::class, BirthEntity::class, CareEntity::class, SaleEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class VetTracDb : RoomDatabase() {

    abstract fun animalDao(): AnimalDao
    abstract fun birthDao(): BirthDao
    abstract fun careDao(): CareDao
    abstract fun saleDao(): SaleDao
    abstract fun vaccinationDao() : VaccinationDao

    companion object {
        @Volatile
        private var INSTANCE: VetTracDb? = null

        fun getDatabase(context: Context): VetTracDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VetTracDb::class.java,
                    "VetTracDb.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
