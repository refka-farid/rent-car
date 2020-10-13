package com.bravedroid.rentcar.shared.infrastructure.network.dto.cusomers

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NormalDto(
    @Json(name = "id") val id: Int?,
    @Json(name = "name") val name: String?,
    @Json(name = "sex") val sex: Boolean?,
    @Json(name = "date") val date: String?,
    @Json(name = "email") val email: String?,
    @Json(name = "agency") val agency: List<String>?,
    @Json(name = "adviser") val adviser: AdviserDto?
)
