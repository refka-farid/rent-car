package com.bravedroid.rentcar.flow.infrastructure.persistence

import com.bravedroid.rentcar.shared.domain.User
import com.bravedroid.rentcar.shared.infrastructure.persistence.entities.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserDao {
    fun get(idNetwork: Long): Flow<UserEntity?>
    fun insertOrUpdate(user: User): Flow<UserEntity>
    fun delete(user: User): Flow<Boolean>

}
