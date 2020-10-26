package com.bravedroid.rentcar.suspend.infrastructure.network

import com.bravedroid.rentcar.shared.infrastructure.network.dto.cars.AllCarsDto
import com.bravedroid.rentcar.shared.infrastructure.network.dto.cusomers.CustomerDto
import com.bravedroid.rentcar.shared.infrastructure.network.dto.users.UserDto

interface ApiService {
    suspend fun getAllUsers(): List<UserDto>
    //suspend fun getAllCustomers(): List<CustomerDto>
    suspend fun getAllCustomers(): CustomerDto
    suspend fun getAllCars(): AllCarsDto
}
