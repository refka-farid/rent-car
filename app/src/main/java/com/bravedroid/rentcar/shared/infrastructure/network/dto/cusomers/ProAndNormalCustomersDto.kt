package com.bravedroid.rentcar.shared.infrastructure.network.dto.cusomers

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProAndNormalCustomersDto(
    @Json(name = "pro") val pro: List<ProDto>?,
    @Json(name = "normal") val normal: List<NormalDto>?
)
