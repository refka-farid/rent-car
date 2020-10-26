package com.bravedroid.rentcar.flow.domain.users

class GetAllUsersUseCase(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.getAll()
}
