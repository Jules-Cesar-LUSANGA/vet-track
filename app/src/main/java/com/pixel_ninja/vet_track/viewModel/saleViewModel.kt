package com.pixel_ninja.vet_track.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pixel_ninja.vet_track.data.VetTracDb
import com.pixel_ninja.vet_track.data.model.SaleEntity
import com.pixel_ninja.vet_track.data.services.SaleService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SaleViewModel(context: Context) : ViewModel() {
    private val db = VetTracDb.getDatabase(context)
    private val saleService = SaleService(db.saleDao())

    private val _sales = MutableLiveData<List<SaleEntity>>()
    val sales: LiveData<List<SaleEntity>> get() = _sales

    private val _sale = MutableLiveData<SaleEntity?>()
    val sale: LiveData<SaleEntity?> get() = _sale

    fun createSale(sale: SaleEntity) {
        viewModelScope.launch {
            saleService.addSale(sale)
            getAllSales() // Rafraîchir la liste après l'ajout
        }
    }

    fun getAllSales() {
        viewModelScope.launch {
            saleService.getAllSales().collect {
                _sales.value = it
            }
        }
    }

    fun deleteSale(sale: SaleEntity) {
        viewModelScope.launch {
            saleService.deleteSale(sale)
        }
    }

    fun getSaleById(saleId: Long) {
        viewModelScope.launch {
            val result = saleService.getSaleById(saleId)
            _sale.postValue(result)
        }
    }
}
