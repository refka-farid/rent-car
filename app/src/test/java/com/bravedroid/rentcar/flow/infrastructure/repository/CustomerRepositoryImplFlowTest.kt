package com.bravedroid.rentcar.flow.infrastructure.repository

import com.bravedroid.rentcar.flow.infrastructure.network.ApiServiceFlow
import com.bravedroid.rentcar.flow.infrastructure.persistence.UserDao
import com.bravedroid.rentcar.shared.domain.Customer
import com.bravedroid.rentcar.shared.infrastructure.network.dto.cusomers.*
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class CustomerRepositoryImplFlowTest {
    private lateinit var sut: CustomerRepositoryImpl
    private lateinit var apiService: ApiServiceFlow
    private lateinit var userDao: UserDao

    @Before
    fun setUp() {
        apiService = mock(ApiServiceFlow::class.java)
        userDao = mock(UserDao::class.java)
        sut = CustomerRepositoryImpl(apiService, userDao)
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
        `when`(apiService.getAllCustomers()).thenReturn(flowOf(expectedCustomerDto))
        val result = sut.getAll().first()
        verify(apiService).getAllCustomers()
        assertThat(result).isEqualTo(expectedList)
    }
}
