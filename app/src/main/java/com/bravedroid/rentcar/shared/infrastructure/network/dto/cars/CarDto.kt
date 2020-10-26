package com.bravedroid.rentcar.shared.infrastructure.network.dto.cars


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CarDto(
    @Json(name = "id") val id: String?,
    @Json(name = "km") val km: Int?,
    @Json(name = "power") val power: String?,
    @Json(name = "entrance_timestamp") val entranceTimestamp: Long?,
    @Json(name = "model") val model: ModelDto?,
    @Json(name = "img") val img: String?
)
