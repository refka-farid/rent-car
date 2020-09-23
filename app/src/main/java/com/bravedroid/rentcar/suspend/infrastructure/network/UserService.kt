package com.bravedroid.rentcar.suspend.infrastructure.network

interface UserService {
    suspend fun getAllUsers(): List<UserDto>
    suspend fun getUserInfo(idNetwork: Long): UserInfoDto
}
