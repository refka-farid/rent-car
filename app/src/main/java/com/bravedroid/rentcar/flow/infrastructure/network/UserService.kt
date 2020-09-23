package com.bravedroid.rentcar.flow.infrastructure.network

import kotlinx.coroutines.flow.Flow

interface UserService {
    fun getAllUsers(): Flow<List<UserDto>>
    fun getUserInfo(idNetwork: Long): Flow<UserInfoDto>
}
