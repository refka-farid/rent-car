package com.bravedroid.rentcar.shared.infrastructure.network.dto.users


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DescriptionDto(
    @Json(name = "bio")
    val bio: String?,
    @Json(name = "address")
    val address: List<AddressDto>?,
)
