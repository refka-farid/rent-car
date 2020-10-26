package com.bravedroid.rentcar.suspend.domain.users

class GetAllUsersUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.getAll()
}
