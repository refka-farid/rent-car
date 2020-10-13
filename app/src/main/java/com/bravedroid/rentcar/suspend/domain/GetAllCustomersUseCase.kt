package com.bravedroid.rentcar.suspend.domain

class GetAllCustomersUseCase(private val customerRepository: CustomerRepository) {
    suspend operator fun invoke() = customerRepository.getAll()
}
