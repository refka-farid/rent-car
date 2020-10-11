package com.bravedroid.rentcar.flow.domain

class GetAllUsersUseCase(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.getAll()
}
