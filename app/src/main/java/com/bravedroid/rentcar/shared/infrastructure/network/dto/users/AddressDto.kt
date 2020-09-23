package com.bravedroid.rentcar.shared.infrastructure.network.dto.users


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressDto(
    @Json(name = "country")
    val country: String?,
    @Json(name = "zip")
    val zip: Int?,
    @Json(name = "address")
    val address: String?,
    @Json(name = "tel")
    val tel: String?,
)
