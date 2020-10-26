package com.bravedroid.rentcar.shared.infrastructure.network.dto.cars


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllCarsDto(
    @Json(name = "total") val total: Int?,
    @Json(name = "items") val cars: List<CarDto>?
)
