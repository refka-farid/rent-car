package com.bravedroid.rentcar.flow.infrastructure.network.stub

import com.bravedroid.rentcar.flow.infrastructure.network.ApiServiceFlow
import com.bravedroid.rentcar.shared.infrastructure.network.dto.users.UserDto
import com.bravedroid.rentcar.shared.infrastructure.network.stub.GetAllUsersDtoFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApiServiceFlowInstance(private val getAllUsersDtoFactory: GetAllUsersDtoFactory) : ApiServiceFlow {
    override fun getAllUsers(): Flow<List<UserDto>> =
        flow<List<UserDto>> {
            delay(3_000)
            emit(getAllUsersDtoFactory.create())
        }
}
