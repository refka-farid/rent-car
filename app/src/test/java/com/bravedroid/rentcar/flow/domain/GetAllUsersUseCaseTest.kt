package com.bravedroid.rentcar.flow.domain

import com.bravedroid.rentcar.flow.domain.users.GetAllUsersUseCase
import com.bravedroid.rentcar.flow.domain.users.UserRepository
import com.bravedroid.rentcar.shared.domain.User
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class GetAllUsersUseCaseTest {
    private lateinit var userRepositoryMock: UserRepository
    private lateinit var sut: GetAllUsersUseCase

    @Before
    fun setUp() {
        userRepositoryMock = Mockito.mock(UserRepository::class.java)
        sut = GetAllUsersUseCase(userRepositoryMock)
    }

    @Test
    operator fun invoke() = runBlockingTest {
        val user = User(100, "", 1, "some biographie", "01/09/1999")
        val listOfUser: List<User> = listOf(
            user.copy(200, "SIHEM"),
            user.copy(300, "SALMA"),
            user.copy(400, "ALMA"),
        )

        Mockito.`when`(userRepositoryMock.getAll()).thenReturn(flowOf(listOfUser))
        val listOfUsersResult = sut.invoke()
        Mockito.verify(userRepositoryMock).getAll()
        Truth.assertThat(listOfUsersResult.first()).isEqualTo(listOfUser)
    }
}

