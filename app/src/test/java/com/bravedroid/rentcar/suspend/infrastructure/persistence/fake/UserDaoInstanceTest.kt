package com.bravedroid.rentcar.suspend.infrastructure.persistence.fake

import com.bravedroid.rentcar.shared.domain.User
import com.bravedroid.rentcar.shared.infrastructure.persistence.entities.UserEntity
import com.bravedroid.rentcar.shared.infrastructure.persistence.entities.UserEntity.Companion.toUserEntity
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

@Suppress("UNCHECKED_CAST", "TYPE_INFERENCE_ONLY_INPUT_TYPES_WARNING")
@ExperimentalCoroutinesApi
class UserDaoInstanceTest {

    private lateinit var sut: UserDaoInstance
    private lateinit var dbMock: MutableMap<Long, UserEntity>

    @Before
    fun setUp() {
        dbMock = Mockito.mock(MutableMap::class.java) as MutableMap<Long, UserEntity>
        sut = UserDaoInstance(dbMock)
    }

    @Test
    fun `when db contains user UserDao should update it `() = runBlockingTest {
        //arrange
        val user1 = User(100L, "", 1, "some biographie", "01/09/1999")
        val userEntity1 = user1.toUserEntity()
        `when`(dbMock.contains(100L)).thenReturn(true)
        `when`(dbMock.get(100L)).thenReturn(userEntity1)
        //act
        val result = sut.insertOrUpdate(user1)
        //assert
        Truth.assertThat(result).isEqualTo(userEntity1)
    }

    @Test
    fun `when db doesn't contains user UserDao should create it `() = runBlockingTest {
        //arrange
        val user2 = User(101L, "", 1, "some biographie", "01/09/1999")
        val userEntity2 = user2.toUserEntity()

        `when`(dbMock.contains(101L)).thenReturn(true)
        `when`(dbMock[101L]).thenReturn(userEntity2)
        `when`(dbMock.keys).thenReturn(mutableSetOf(100L))
        `when`(dbMock.put(101L, userEntity2)).thenReturn(userEntity2)

        //act
        val result1 = sut.insertOrUpdate(user2)
        //assert
        Truth.assertThat(result1).isEqualTo(userEntity2)

    }

    @Test
    fun `when db contains user UserDao should get it `() = runBlockingTest {
        val user = User(100, "", 1, "some biographie", "01/09/1999")
        val userEntity: UserEntity? = user.toUserEntity()
        `when`(dbMock.get(100L)).thenReturn(userEntity)
        val result = sut.get(100L)
        Truth.assertThat(result).isEqualTo(userEntity)
    }

    @Test
    fun `when db doesn't contains user UserDao should get null `() = runBlockingTest {
        `when`(dbMock.get(500L)).thenReturn(null)
        val result = sut.get(500L)
        Truth.assertThat(result).isEqualTo(null)
    }

    @Test
    fun `when db contains user UserDao should delete it `() = runBlockingTest {
        val user1 = User(200, "", 1, "some biographie", "01/09/1999")
        val userEntity = user1.toUserEntity()
        `when`(dbMock.containsKey(200)).thenReturn(true)
        `when`(dbMock.remove(200)).thenReturn(userEntity)
        //act
        val result = sut.delete(user1)
        Truth.assertThat(result).isEqualTo(true)
    }

    @Test
    fun `when db doesn't contains user UserDao should return false `() = runBlockingTest {
        val user1 = User(200, "", 1, "some biographie", "01/09/1999")
        `when`(dbMock.containsKey(200)).thenReturn(false)
        //act
        val result = sut.delete(user1)
        Truth.assertThat(result).isEqualTo(false)
    }
}
