package com.bravedroid.rentcar.flow.infrastructure.persistence

import com.bravedroid.rentcar.shared.domain.User
import com.bravedroid.rentcar.shared.infrastructure.persistence.entities.UserEntity

interface UserDao {
    suspend fun get(idNetwork: Long): UserEntity?
    suspend fun insertOrUpdate(user: User): UserEntity
    suspend fun delete(user: User): Boolean

}
