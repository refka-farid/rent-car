package com.bravedroid.rentcar.suspend.domain

import com.bravedroid.rentcar.shared.domain.User
import com.bravedroid.rentcar.shared.infrastructure.network.dto.users.DescriptionDto
import com.bravedroid.rentcar.shared.infrastructure.network.dto.users.UserDto
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class GetAllUsersUseCaseTest {
    private lateinit var userRepositoryMock: UserRepository
    private lateinit var sut: GetAllUsersUseCase

    @Before
    fun setUp() {
        userRepositoryMock = mock(UserRepository::class.java)
        sut = GetAllUsersUseCase(userRepositoryMock)
    }

    @Test
    operator fun invoke() {
        runBlockingTest {
            val user = User(100, "", 1, "some biographie", "01/09/1999")
            val listOfUser: List<User> = listOf(
                user.copy(200, "SIHEM"),
                user.copy(300, "SALMA"),
                user.copy(400, "ALMA"),
            )

            Mockito.`when`(userRepositoryMock.getAll()).thenReturn(listOfUser)
            val listOfUsersResult = sut.invoke()
            verify(userRepositoryMock).getAll()
            Truth.assertThat(listOfUsersResult).isEqualTo(listOfUser)
        }
    }
}
