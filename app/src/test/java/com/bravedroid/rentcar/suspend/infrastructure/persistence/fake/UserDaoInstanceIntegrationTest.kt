package com.bravedroid.rentcar.suspend.infrastructure.persistence.fake

import com.bravedroid.rentcar.shared.domain.User
import com.bravedroid.rentcar.shared.infrastructure.persistence.entities.UserEntity
import com.bravedroid.rentcar.shared.infrastructure.persistence.entities.UserEntity.Companion.toUserEntity
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import java.sql.Date

@ExperimentalCoroutinesApi
class UserDaoInstanceIntegrationTest {
    private lateinit var db: MutableMap<Long, UserEntity>
    private lateinit var sut: UserDaoInstance

    @Before
    fun setUp() {
        val userEntity = UserEntity(100L, "hassan", 1, Date(11111111L))
        db = mutableMapOf(
            Pair(100L, userEntity),
            Pair(200L, userEntity.copy(id = 200L)),
            Pair(300L, userEntity.copy(id = 300L)),
            Pair(400L, userEntity.copy(id = 400L)),
            Pair(500L, userEntity.copy(id = 500L)),
            Pair(600L, userEntity.copy(id = 600L)),
            Pair(700L, userEntity.copy(id = 700L)),
        )
        sut = UserDaoInstance(db)
    }

    @Test
    fun `when db contains user userDao should delete it`() = runBlockingTest {
        val user = User(100L, "hassan", 1, "", "19/9/1999")
        val result = sut.delete(user)
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `when db doesn't contains user userDao should return null `() = runBlockingTest {
        val user = User(111L, "hassan", 1, "", "19/9/1999")
        val result = sut.delete(user)
        Truth.assertThat(result).isFalse()
    }

    @Test
    fun `when db contains user UserDao should get it `() = runBlockingTest {
        val userEntity = UserEntity(100L, "hassan", 1, Date(11111111L))
        val result = sut.get(100L)
        Truth.assertThat(result).isEqualTo(userEntity)
    }

    @Test
    fun `when db doesn't contains user UserDao should get null `() = runBlockingTest {
        val result = sut.get(111)
        Truth.assertThat(result).isEqualTo(null)
    }

    @Test
    fun `when db contains user UserDao should update it `() = runBlockingTest {
        val user = User(700L, "omar", 1, "", "2020-10-08")
        val userEntity = user.toUserEntity()
        val result = sut.insertOrUpdate(user)
        Truth.assertThat(result == userEntity).isTrue()
    }

    @Test
    fun `when db doesn't contains user UserDao should create it `() = runBlockingTest {
        val user = User(701L, "omar", 1, "", "2020-10-08")
        val userEntity = user.toUserEntity()
        val result = sut.insertOrUpdate(user)
        Truth.assertThat(result == userEntity).isTrue()
    }
}
