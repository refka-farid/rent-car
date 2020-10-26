package com.bravedroid.rentcar.flow.infrastructure.repository

import com.bravedroid.rentcar.flow.domain.cars.CarsRepository
import com.bravedroid.rentcar.flow.infrastructure.network.stub.ApiServiceFlowInstance
import com.bravedroid.rentcar.shared.domain.Car
import com.bravedroid.rentcar.shared.infrastructure.network.dto.cars.AllCarsDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CarsRepositoryImpl(
    private val apiServiceInstance: ApiServiceFlowInstance
) : CarsRepository {
    override fun getAll(): Flow<List<Car>> = apiServiceInstance.getAllCars()
        .map {
            createListOfCars(it)
        }

    private fun createListOfCars(allCarsDto: AllCarsDto): List<Car> {
        val carList = mutableListOf<Car>()
        allCarsDto.cars?.forEach {
            carList.add(Car(it.id, it.km, it.model?.name, it.model?.homeFactory))
        }
        return carList
    }
}
