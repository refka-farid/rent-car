package com.bravedroid.rentcar.suspend.domain.cars

import com.bravedroid.rentcar.shared.domain.Car

interface CarsRepository {
    suspend fun getAll(): List<Car>
}
