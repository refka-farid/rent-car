package com.bravedroid.rentcar.shared.infrastructure.network.dto.users


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TransactionDto(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "timestamp")
    val timestamp: Long?,
)
