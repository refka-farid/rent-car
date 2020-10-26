package com.bravedroid.rentcar.shared.infrastructure.network.dto.cars


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ModelDto(
    @Json(name = "color") val color: String?,
    @Json(name = "homeFactory") val homeFactory: String?,
    @Json(name = "name") val name: String?,
    @Json(name = "release_date") val releaseDate: Int?
)
