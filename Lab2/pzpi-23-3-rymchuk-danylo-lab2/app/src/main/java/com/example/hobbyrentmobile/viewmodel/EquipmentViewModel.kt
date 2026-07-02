package com.example.hobbyrentmobile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hobbyrentmobile.data.models.Equipment
import com.example.hobbyrentmobile.data.repository.EquipmentRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EquipmentViewModel : ViewModel() {

    private val repository = EquipmentRepository()

    val equipmentList = androidx.compose.runtime.mutableStateOf<List<Equipment>>(emptyList())
    val isLoading = androidx.compose.runtime.mutableStateOf(true)
    val errorMessage = androidx.compose.runtime.mutableStateOf<String?>(null)

    init {
        fetchEquipment()
    }

    fun fetchEquipment() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            try {
                equipmentList.value = repository.getEquipmentList()
            } catch (e: Exception) {
                errorMessage.value = "Помилка завантаження: ${e.message}"
                Log.e("ViewModel", "Error fetching equipment", e)
            } finally {
                isLoading.value = false
            }
        }
    }

    fun rentEquipment(equipmentId: Long, hours: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val endDate = LocalDateTime.now().plusHours(hours.toLong())
                    .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

                repository.rentEquipment(equipmentId, 1L, endDate)
                onSuccess()
                fetchEquipment()
            } catch (e: Exception) {
                onError("Помилка оренди: ${e.message}")
            }
        }
    }
}