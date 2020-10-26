package com.bravedroid.rentcar.suspend.domain.users

import com.bravedroid.rentcar.shared.domain.User

interface UserRepository {
    suspend fun getAll(): List<User>
}
