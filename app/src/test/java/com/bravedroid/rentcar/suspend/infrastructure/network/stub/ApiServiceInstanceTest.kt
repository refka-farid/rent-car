package com.bravedroid.rentcar.suspend.infrastructure.network.stub

import com.bravedroid.rentcar.shared.infrastructure.network.dto.users.UserDto
import com.bravedroid.rentcar.shared.infrastructure.network.stub.GetAllUsersDtoFactory
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class ApiServiceInstanceTest {

    private lateinit var sut: ApiServiceInstance
    private lateinit var getAllUsersDtoFactoryMock: GetAllUsersDtoFactory

    @Before
    fun setUp() {
        getAllUsersDtoFactoryMock = mock(GetAllUsersDtoFactory::class.java)
        sut = ApiServiceInstance(getAllUsersDtoFactoryMock)
    }

    @Test
    fun getAllUsersTest() = runBlockingTest {
        //arrange
        val userDto = UserDto(
            id = 200,
            fName = "SIHEM",
            lName = "SESSI",
            age = 20,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
        )

        val listOfUsersDto: List<UserDto> = listOf(
            userDto.copy(
                id = 100,
                fName = "sala7",
                lName = "nejjma",
                age = 99,
            ),
            userDto.copy(
                id = 300,
                fName = "SALMA",
                lName = "BEJI",
                age = 20,
            ),
            userDto.copy(
                id = 400,
                fName = "ALMA",
                lName = "heshmi",
                age = 20,
            )
        )
        `when`(getAllUsersDtoFactoryMock.create()).thenReturn(listOfUsersDto)

        val allUsersResult = sut.getAllUsers()//act

        //assert
        verify(getAllUsersDtoFactoryMock).create()//test collaboration
        assertThat(allUsersResult).isEqualTo(listOfUsersDto)// test output
    }
}
