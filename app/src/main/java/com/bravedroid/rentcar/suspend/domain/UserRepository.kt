package com.bravedroid.rentcar.suspend.domain

import com.bravedroid.rentcar.shared.domain.User

interface UserRepository {
    suspend fun getAll(): List<User>
}
