package com.bravedroid.rentcar.shared.infrastructure.network.dto.users


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "f_name")
    val fName: String?,
    @Json(name = "l_name")
    val lName: String?,
    @Json(name = "age")
    val age: Int?,
    @Json(name = "b_date")
    val bDate: String?,
    @Json(name = "role")
    val role: String?,
    @Json(name = "email")
    val email: String?,
    @Json(name = "height")
    val height: Int?,
    @Json(name = "weight")
    val weight: Int?,
    @Json(name = "sex")
    val sex: Int?,
    @Json(name = "active")
    val active: Boolean?,
    @Json(name = "description")
    val description: DescriptionDto?,
    @Json(name = "transactions")
    val transactions: List<TransactionDto>?,
    @Json(name = "car")
    val car: CarDto?,
)
