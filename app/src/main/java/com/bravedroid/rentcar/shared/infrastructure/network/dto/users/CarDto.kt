package com.bravedroid.rentcar.shared.infrastructure.network.dto.users


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CarDto(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "model")
    val model: String?,
    @Json(name = "obtention")
    val obtention: String?,
)
