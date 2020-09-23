package com.bravedroid.rentcar.flow.domain

import com.bravedroid.rentcar.shared.domain.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
  fun getAll(): Flow<List<User>>
}
