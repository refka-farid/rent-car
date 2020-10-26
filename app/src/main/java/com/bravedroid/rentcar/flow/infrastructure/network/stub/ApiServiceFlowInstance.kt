package com.bravedroid.rentcar.flow.infrastructure.network.stub

import com.bravedroid.rentcar.flow.infrastructure.network.ApiServiceFlow
import com.bravedroid.rentcar.shared.infrastructure.network.dto.cars.AllCarsDto
import com.bravedroid.rentcar.shared.infrastructure.network.dto.cusomers.CustomerDto
import com.bravedroid.rentcar.shared.infrastructure.network.dto.users.UserDto
import com.bravedroid.rentcar.shared.infrastructure.network.stub.GetAllCarsDtoFactory
import com.bravedroid.rentcar.shared.infrastructure.network.stub.GetAllCustomersDtoFactory
import com.bravedroid.rentcar.shared.infrastructure.network.stub.GetAllUsersDtoFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApiServiceFlowInstance(
    private val getAllUsersDtoFactory: GetAllUsersDtoFactory,
    private val getAllCustomersDtoFactory: GetAllCustomersDtoFactory,
    private val getAllCarsDtoFactory: GetAllCarsDtoFactory,
) : ApiServiceFlow {
    override fun getAllUsers(): Flow<List<UserDto>> =
        flow<List<UserDto>> {
            delay(3_000)
            emit(getAllUsersDtoFactory.create())
        }

    override fun getAllCustomers(): Flow<CustomerDto> =
        flow<CustomerDto> {
            delay(3_000)
            emit(getAllCustomersDtoFactory.create())
        }

    override fun getAllCars(): Flow<AllCarsDto> =
        flow<AllCarsDto> {
            delay(3_000)
            emit(getAllCarsDtoFactory.create())
        }
}
