package com.bravedroid.rentcar.suspend.infrastructure.repository

import com.bravedroid.rentcar.shared.domain.Car
import com.bravedroid.rentcar.suspend.domain.cars.CarsRepository
import com.bravedroid.rentcar.suspend.infrastructure.network.stub.ApiServiceInstance

class CarsRepositoryImpl(
    private val apiServiceInstance: ApiServiceInstance
) : CarsRepository {
    override suspend fun getAll(): List<Car> {
        val allCarsDto = apiServiceInstance.getAllCars()
        val allCars = mutableListOf<Car>()
        allCarsDto.cars?.forEach {
            allCars.add(
                Car(it.id, it.km, it.model?.name, it.model?.homeFactory)
            )
        }
        return allCars
    }
}
