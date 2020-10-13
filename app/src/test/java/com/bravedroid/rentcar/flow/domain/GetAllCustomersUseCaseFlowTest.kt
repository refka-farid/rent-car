package com.bravedroid.rentcar.flow.domain

import com.bravedroid.rentcar.shared.domain.Customer
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class GetAllCustomersUseCaseFlowTest {
    private lateinit var customerRepositoryMock: CustomerRepository
    private lateinit var sut: GetAllCustomersUseCase

    @Before
    fun setUp() {
        customerRepositoryMock = mock(CustomerRepository::class.java)
        sut = GetAllCustomersUseCase(customerRepositoryMock)
    }

    @Test
    operator fun invoke() = runBlockingTest {
        val customer = Customer(
            100, "james", 1, "", ""
        )
        val expectedList = listOf(
            customer,
            customer.copy(200, "julia", 0),
            customer.copy(300, "houlia", 0),
        )

        `when`(customerRepositoryMock.getAll()).thenReturn(flowOf(expectedList))
        val result = sut.invoke().first()
        verify(customerRepositoryMock).getAll()
        Truth.assertThat(result).isEqualTo(expectedList)
    }
}
