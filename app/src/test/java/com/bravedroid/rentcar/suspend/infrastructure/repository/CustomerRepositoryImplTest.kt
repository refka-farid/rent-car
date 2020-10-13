package com.bravedroid.rentcar.suspend.infrastructure.repository

import com.bravedroid.rentcar.shared.domain.Customer
import com.bravedroid.rentcar.shared.infrastructure.network.dto.cusomers.*
import com.bravedroid.rentcar.suspend.infrastructure.network.ApiService
import com.bravedroid.rentcar.suspend.infrastructure.persistence.UserDao
import com.google.common.truth.ExpectFailure.assertThat
import com.google.common.truth.Truth
import com.google.common.truth.Truth.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class CustomerRepositoryImplTest {
    private lateinit var sut: CustomerRepositoryImpl
    private lateinit var apiService: ApiService
    private lateinit var userDao: UserDao

    @Before
    fun setUp() {
        apiService = Mockito.mock(ApiService::class.java)
        userDao = Mockito.mock(UserDao::class.java)
        sut = CustomerRepositoryImpl(apiService,userDao)
    }

    @Test
    fun getAll() = runBlockingTest {
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
                        300,
                        "houlia",
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
        val customer = Customer(
            100, "james", 1, "", ""
        )
        val expectedList = listOf(
            customer,
            customer.copy(200, "julia", 0),
            customer.copy(300, "houlia", 0),
        )
        `when`(apiService.getAllCustomers()).thenReturn(expectedCustomerDto)
        val result = sut.getAll()
        verify(apiService).getAllCustomers()
        assertThat(result).isEqualTo(expectedList)
    }
}
