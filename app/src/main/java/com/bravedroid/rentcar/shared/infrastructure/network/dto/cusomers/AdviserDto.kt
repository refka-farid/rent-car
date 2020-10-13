package com.bravedroid.rentcar.shared.infrastructure.network.dto.cusomers

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AdviserDto(
    @Json(name = "id") val id: Int?
)
