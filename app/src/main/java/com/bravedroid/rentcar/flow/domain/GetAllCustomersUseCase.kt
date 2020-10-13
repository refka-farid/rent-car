package com.bravedroid.rentcar.flow.domain

class GetAllCustomersUseCase(private val customerRepository: CustomerRepository) {
    operator fun invoke() = customerRepository.getAll()
}
