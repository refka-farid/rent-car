package com.bravedroid.rentcar.flow.infrastructure.network

data class UserDto(
  val idNetwork: Long? = null,
  val name: String? = null,
  val sex: Int? = null, //0 female, 1 male, 2 unknown
)
