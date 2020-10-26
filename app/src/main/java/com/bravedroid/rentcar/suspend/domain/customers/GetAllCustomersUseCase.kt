package com.bravedroid.rentcar.suspend.domain.customers

class GetAllCustomersUseCase(private val customerRepository: CustomerRepository) {
    suspend operator fun invoke() = customerRepository.getAll()
}
