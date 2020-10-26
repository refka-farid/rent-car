package com.bravedroid.rentcar.suspend.infrastructure.network.stub

import com.bravedroid.rentcar.shared.infrastructure.network.dto.cars.AllCarsDto
import com.bravedroid.rentcar.shared.infrastructure.network.dto.cars.CarDto
import com.bravedroid.rentcar.shared.infrastructure.network.dto.cars.ModelDto
import com.bravedroid.rentcar.shared.infrastructure.network.dto.cusomers.*
import com.bravedroid.rentcar.shared.infrastructure.network.dto.users.UserDto
import com.bravedroid.rentcar.shared.infrastructure.network.stub.GetAllCarsDtoFactory
import com.bravedroid.rentcar.shared.infrastructure.network.stub.GetAllCustomersDtoFactory
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
    private lateinit var getAllCustomersDtoFactoryMock: GetAllCustomersDtoFactory
    private lateinit var getAllCarsDtoFactoryMock: GetAllCarsDtoFactory

    @Before
    fun setUp() {
        getAllUsersDtoFactoryMock = mock(GetAllUsersDtoFactory::class.java)
        getAllCustomersDtoFactoryMock = mock(GetAllCustomersDtoFactory::class.java)
        getAllCarsDtoFactoryMock = mock(GetAllCarsDtoFactory::class.java)
        sut = ApiServiceInstance(
            getAllUsersDtoFactoryMock,
            getAllCustomersDtoFactoryMock,
            getAllCarsDtoFactoryMock
        )
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

    @Test
    fun getAllCustomersTest() = runBlockingTest {
        val expectedCustomerDto = CustomerDto(
            "get/allCustomers",
            "4",
            ProAndNormalCustomersDto(
                listOf(
                    ProDto(
                        100,
                        "james",
                        true,
                        "10/09/1990",
                        "no mail",
                        listOf(
                            "agency 1", "agency 2", "agency 3"
                        ), 888
                    ),
                    ProDto(
                        200,
                        "julia",
                        false,
                        "10/01/1980",
                        "no mail",
                        listOf(
                            "agency 1", "agency 2", "agency 3"
                        ), 555
                    )
                ),
                listOf(
                    NormalDto(
                        200,
                        "julia",
                        false,
                        "10/01/1980",
                        "no mail",
                        listOf(
                            "agency 1", "agency 2", "agency 3"
                        ),
                        AdviserDto(1)
                    )
                )
            )
        )
        `when`(getAllCustomersDtoFactoryMock.create()).thenReturn(expectedCustomerDto)
        val result = sut.getAllCustomers()

        verify(getAllCustomersDtoFactoryMock).create()
        assertThat(result).isEqualTo(expectedCustomerDto)
    }

    @Test
    fun getAllCarsTest() = runBlockingTest {
        val carsDto = AllCarsDto(
            3, listOf(
                CarDto(
                    "111--A",
                    20,
                    "5",
                    100000L,
                    ModelDto(
                        "blue",
                        "Renault",
                        "Symbole",
                        2012
                    ),
                    "data:image/jpeg;base64,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"
                ),
                CarDto(
                    "111--A",
                    20,
                    "5",
                    100000L,
                    ModelDto(
                        "blue",
                        "Renault",
                        "Symbole",
                        2012
                    ),
                    "data:image/jpeg;base64,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"
                ), CarDto(
                    "111--A",
                    20,
                    "5",
                    100000L,
                    ModelDto(
                        "blue",
                        "Renault",
                        "Symbole",
                        2012
                    ),
                    "data:image/jpeg;base64,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"
                )
            )
        )
        `when`(getAllCarsDtoFactoryMock.create()).thenReturn(carsDto)
        val result = sut.getAllCars()

        verify(getAllCarsDtoFactoryMock).create()
        assertThat(result).isEqualTo(carsDto)
    }
}
