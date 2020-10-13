package com.bravedroid.rentcar.suspend.infrastructure.network.stub

import com.bravedroid.rentcar.shared.infrastructure.network.dto.cusomers.CustomerDto
import com.bravedroid.rentcar.shared.infrastructure.network.dto.users.UserDto
import com.bravedroid.rentcar.shared.infrastructure.network.stub.GetAllCustomersDtoFactory
import com.bravedroid.rentcar.shared.infrastructure.network.stub.GetAllUsersDtoFactory
import com.bravedroid.rentcar.suspend.infrastructure.network.ApiService
import kotlinx.coroutines.delay

class ApiServiceInstance(
    private val getAllUsersDtoFactory: GetAllUsersDtoFactory,
    private val getAllCustomersDtoFactory: GetAllCustomersDtoFactory
) : ApiService {
    override suspend fun getAllUsers(): List<UserDto> {
        delay(3_000)
        return getAllUsersDtoFactory.create()
    }

    override suspend fun getAllCustomers(): CustomerDto {
        delay(3_000)
        return getAllCustomersDtoFactory.create()
    }
}
