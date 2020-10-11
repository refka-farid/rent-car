package com.bravedroid.rentcar.flow.infrastructure.repository

import com.bravedroid.rentcar.flow.infrastructure.network.ApiServiceFlow
import com.bravedroid.rentcar.flow.infrastructure.persistence.UserDao
import com.bravedroid.rentcar.shared.domain.User
import com.bravedroid.rentcar.shared.infrastructure.network.dto.users.DescriptionDto
import com.bravedroid.rentcar.shared.infrastructure.network.dto.users.UserDto
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class UserRepositoryImplFlowTest {
    private lateinit var sut: UserRepositoryImpl
    private lateinit var apiService: ApiServiceFlow
    private lateinit var userDao: UserDao

    @Before
    fun setUp() {
        apiService = Mockito.mock(ApiServiceFlow::class.java)
        userDao = Mockito.mock(UserDao::class.java)
        sut = UserRepositoryImpl(
            apiService, userDao
        )
    }

    @Test
    fun getAll() {
        runBlockingTest {
            val userDto = UserDto(
                id = 200,
                fName = "SIHEM",
                lName = "SESSI",
                age = 20,
                "01/09/1999",
                null,
                null,
                null,
                null,
                1,
                null,
                DescriptionDto("some biographie", null),
                null,
                null,
            )

            val listOfUsersDto: List<UserDto> = listOf(
                userDto.copy(
                    id = 200,
                    fName = "SIHEM",
                ),
                userDto.copy(
                    id = 300,
                    fName = "SALMA",
                ),
                userDto.copy(
                    id = 400,
                    fName = "ALMA",
                )
            )

            val user = User(100, "sala7", 1, "some biographie", "01/09/1999")
            val listOfUser: List<User> = listOf(
                user.copy(200, "SIHEM"),
                user.copy(300, "SALMA"),
                user.copy(400, "ALMA"),
            )

            Mockito.`when`(apiService.getAllUsers()).thenReturn(flowOf(listOfUsersDto))
            val listOfUsersResult = sut.getAll()
            verify(apiService).getAllUsers()//test collaboration
            Truth.assertThat(listOfUsersResult.first()).isEqualTo(listOfUser)
        }
    }
}
