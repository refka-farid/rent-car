package com.bravedroid.rentcar.suspend.infrastructure.network.stub

import com.bravedroid.rentcar.shared.infrastructure.network.dto.users.UserDto
import com.bravedroid.rentcar.shared.infrastructure.network.stub.GetAllUsersDtoFactory
import com.bravedroid.rentcar.suspend.infrastructure.network.ApiService
import kotlinx.coroutines.delay

class ApiServiceInstance(private val getAllUsersDtoFactory: GetAllUsersDtoFactory) : ApiService {
    override suspend fun getAllUsers(): List<UserDto> {
        delay(3_000)
        return getAllUsersDtoFactory.create()
    }
}
