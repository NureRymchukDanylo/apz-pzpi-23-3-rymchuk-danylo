package com.example.hobbyrentmobile.data.models

data class Equipment(
    val id: Long,
    val title: String,
    val category: String,
    val hourlyRate: Double,
    val status: String,
    val owner: User? = null
)

data class User(
    val id: Long,
    val username: String,
    val email: String,
    val role: String
)

data class Rental(
    val id: Long,
    val equipment: Equipment,
    val tenant: User,
    val startDate: String,
    val endDate: String,
    val totalPrice: Double,
    val status: String
)