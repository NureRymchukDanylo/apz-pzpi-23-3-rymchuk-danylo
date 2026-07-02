package com.example.hobbyrentmobile.data.repository

import com.example.hobbyrentmobile.data.api.RetrofitClient
import com.example.hobbyrentmobile.data.models.Equipment
import com.example.hobbyrentmobile.data.models.Rental

class EquipmentRepository {

    private val apiService = RetrofitClient.apiService

    suspend fun getEquipmentList(): List<Equipment> {
        return apiService.getEquipment()
    }

    suspend fun rentEquipment(equipmentId: Long, userId: Long, endDate: String): Rental {
        return apiService.startRental(equipmentId, userId, endDate)
    }
}