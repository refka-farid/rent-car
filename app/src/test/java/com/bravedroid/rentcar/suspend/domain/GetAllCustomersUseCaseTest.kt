package com.bravedroid.rentcar.suspend.domain

import com.bravedroid.rentcar.shared.domain.Customer
import com.bravedroid.rentcar.suspend.domain.customers.CustomerRepository
import com.bravedroid.rentcar.suspend.domain.customers.GetAllCustomersUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class GetAllCustomersUseCaseTest {
    private lateinit var customersRepositoryMock: CustomerRepository
    private lateinit var sut: GetAllCustomersUseCase

    @Before
    fun setUp() {
        customersRepositoryMock = mock(CustomerRepository::class.java)
        sut = GetAllCustomersUseCase(customersRepositoryMock)
    }

    @Test
    operator fun invoke() = runBlockingTest {
        val customer = Customer(100, "james", 1, "", "")
        val expectedList = listOf(
            customer,
            customer.copy(200, "julia", 0),
            customer.copy(300, "houlia", 0),
        )

        `when`(customersRepositoryMock.getAll()).thenReturn(expectedList)
        val listOfCustomersResult = sut.invoke()
        verify(customersRepositoryMock).getAll()
        assertThat(listOfCustomersResult).isEqualTo(expectedList)
    }
}
