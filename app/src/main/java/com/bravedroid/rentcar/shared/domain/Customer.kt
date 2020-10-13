package com.bravedroid.rentcar.shared.domain

data class Customer(
    val id: Long,
    val name: String,
    val sex: Int,
    val biography: String,
    val birthday: String,
)
