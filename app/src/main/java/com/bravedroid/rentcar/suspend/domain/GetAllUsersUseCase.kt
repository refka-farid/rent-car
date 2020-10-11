package com.bravedroid.rentcar.suspend.domain

class GetAllUsersUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.getAll()
}
