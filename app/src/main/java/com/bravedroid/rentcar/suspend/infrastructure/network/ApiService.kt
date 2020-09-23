package com.bravedroid.rentcar.suspend.infrastructure.network

import com.bravedroid.rentcar.shared.infrastructure.network.dto.users.UserDto

interface ApiService {
    suspend fun getAllUsers(): List<UserDto>
}
