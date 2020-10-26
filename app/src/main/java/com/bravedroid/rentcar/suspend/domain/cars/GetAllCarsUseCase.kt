package com.bravedroid.rentcar.suspend.domain.cars

class GetAllCarsUseCase(private val carsRepository: CarsRepository) {
    suspend operator fun invoke() = carsRepository.getAll()

}
