package com.example.hobbyrentmobile.data.api

import com.example.hobbyrentmobile.data.models.Equipment
import com.example.hobbyrentmobile.data.models.Rental
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("api/equipment")
    suspend fun getEquipment(): List<Equipment>

    @POST("api/rentals/start")
    suspend fun startRental(
        @Query("equipmentId") equipmentId: Long,
        @Query("userId") userId: Long,
        @Query("endIsoDate") endIsoDate: String
    ): Rental
}