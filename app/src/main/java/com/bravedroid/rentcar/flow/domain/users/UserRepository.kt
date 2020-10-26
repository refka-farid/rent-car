package com.bravedroid.rentcar.flow.domain.users

import com.bravedroid.rentcar.shared.domain.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getAll(): Flow<List<User>>
}
