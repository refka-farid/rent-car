package com.bravedroid.rentcar.flow.domain.customers

class GetAllCustomersUseCase(private val customerRepository: CustomerRepository) {
    operator fun invoke() = customerRepository.getAll()
}
