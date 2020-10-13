package com.bravedroid.rentcar.shared.infrastructure.network.dto.cusomers

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CustomerDto(
    @Json(name = "request") val request: String?,
    @Json(name = "count") val count: String?,
    @Json(name = "customers") val proAndNormalCustomers: ProAndNormalCustomersDto?
)
