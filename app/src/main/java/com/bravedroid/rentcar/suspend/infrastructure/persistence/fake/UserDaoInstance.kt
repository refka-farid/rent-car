package com.bravedroid.rentcar.suspend.infrastructure.persistence.fake

import android.os.Build
import androidx.annotation.RequiresApi
import com.bravedroid.rentcar.shared.domain.User
import com.bravedroid.rentcar.shared.infrastructure.persistence.entities.UserEntity
import com.bravedroid.rentcar.shared.infrastructure.persistence.entities.UserEntity.Companion.toUserEntity
import com.bravedroid.rentcar.suspend.infrastructure.persistence.UserDao
import kotlinx.coroutines.delay

//data structure

class UserDaoInstance(
    private val db: MutableMap<Long, UserEntity> = mutableMapOf()
) : UserDao {

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

    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun insertOrUpdate(user: User): UserEntity {
        delay(1_000)
        if (db.contains(user.id)) {
            db.replace(user.id, db[user.id]!!, user.toUserEntity())
        } else {
            var key = (db.keys.lastOrNull() ?: -1).toLong()
            val newId = ++key
            db[newId] = user.toUserEntity().copy(id = newId)
        }
        return user.toUserEntity()
    }
}
