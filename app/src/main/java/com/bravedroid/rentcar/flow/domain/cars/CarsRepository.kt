package com.bravedroid.rentcar.flow.domain.cars

import com.bravedroid.rentcar.shared.domain.Car
import com.bravedroid.rentcar.shared.infrastructure.network.dto.cars.AllCarsDto
import kotlinx.coroutines.flow.Flow

interface CarsRepository {
    fun getAll(): Flow<List<Car>>
}
