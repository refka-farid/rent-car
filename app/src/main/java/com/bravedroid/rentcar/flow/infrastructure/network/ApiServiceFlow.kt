package com.bravedroid.rentcar.flow.infrastructure.network

import com.bravedroid.rentcar.shared.infrastructure.network.dto.users.UserDto
import kotlinx.coroutines.flow.Flow

interface ApiServiceFlow {
    fun getAllUsers(): Flow<List<UserDto>>
}
