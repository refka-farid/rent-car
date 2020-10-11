package com.bravedroid.rentcar.flow.infrastructure.persistence.fake

import android.os.Build
import androidx.annotation.RequiresApi
import com.bravedroid.rentcar.flow.infrastructure.persistence.UserDao
import com.bravedroid.rentcar.shared.domain.User
import com.bravedroid.rentcar.shared.infrastructure.persistence.entities.UserEntity
import com.bravedroid.rentcar.shared.infrastructure.persistence.entities.UserEntity.Companion.toUserEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UserDaoInstance(
    private val db: MutableMap<Long, UserEntity> = mutableMapOf(),
) : UserDao {
    override fun delete(user: User): Flow<Boolean> = flow {
        delay(1_000)
        val idUser = user.id
        if (db.containsKey(idUser)) {
            val deletedUser = db.remove(idUser)
            emit(deletedUser != null)
        } else {
            emit(false)
        }
    }

    override fun get(idNetwork: Long): Flow<UserEntity?> = flow {
        delay(1_000)
        emit(db[idNetwork])
    }

    @Suppress("TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun insertOrUpdate(user: User): Flow<UserEntity> = flow {
        delay(1_000)
        if (db.contains(user.id)) {
            db.replace(user.id, db[user.id]!!, user.toUserEntity())
            emit(db[user.id]!!)
        } else {
            var key = (db.keys.lastOrNull() ?: -1).toLong()
            val newId = ++key
            db[newId] = user.toUserEntity().copy(id = newId)
            emit(db[newId]!!)
        }
    }
}
