package com.bravedroid.rentcar.flow.infrastructure.persistence.fake

import com.bravedroid.rentcar.flow.infrastructure.persistence.UserDao
import com.bravedroid.rentcar.shared.domain.User
import com.bravedroid.rentcar.shared.infrastructure.persistence.entities.UserEntity
import com.bravedroid.rentcar.shared.infrastructure.persistence.entities.UserEntity.Companion.toUserEntity
import kotlinx.coroutines.delay

class UserDaoInstance : UserDao {
    private val db: MutableMap<Long, UserEntity> = mutableMapOf()

    override suspend fun delete(user: User): Boolean {
        delay(1_000)
        val idUser = user.id
        return if (db.containsKey(idUser)) {
            val deletedUser = db.remove(idUser)
            return deletedUser != null
        } else {
            false
        }
    }

    override suspend fun get(idNetwork: Long): UserEntity? {
        delay(1_000)
        return db[idNetwork]
    }

    override suspend fun insertOrUpdate(user: User): UserEntity {
        var isUpdated = false
        delay(1_000)

        if (db.containsValue(user.id)) {
            isUpdated = db.replace(user.id, db[user.id]!!, user.toUserEntity())
        }

        if (!isUpdated) {
            var key = (db.keys.lastOrNull() ?: -1).toLong()
            val newId = ++key
            db.put(newId, user.toUserEntity(newId))!!
        }
        return user.toUserEntity()
    }
}
