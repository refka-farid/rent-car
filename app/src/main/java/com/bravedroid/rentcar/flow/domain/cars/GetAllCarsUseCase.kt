package com.bravedroid.rentcar.flow.domain.cars

class GetAllCarsUseCase(private val carsRepository: CarsRepository) {
    operator fun invoke() = carsRepository.getAll()
}
