package com.bravedroid.rentcar.flow.infrastructure.network

import com.bravedroid.rentcar.shared.infrastructure.network.dto.cars.AllCarsDto
import com.bravedroid.rentcar.shared.infrastructure.network.dto.cusomers.CustomerDto
import com.bravedroid.rentcar.shared.infrastructure.network.dto.users.UserDto
import kotlinx.coroutines.flow.Flow

interface ApiServiceFlow {
    fun getAllUsers(): Flow<List<UserDto>>
    fun getAllCustomers(): Flow<CustomerDto>
    fun getAllCars(): Flow<AllCarsDto>
}
