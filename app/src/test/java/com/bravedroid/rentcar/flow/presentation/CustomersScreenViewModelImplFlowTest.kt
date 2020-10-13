package com.bravedroid.rentcar.flow.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bravedroid.rentcar.flow.domain.GetAllCustomersUseCase
import com.bravedroid.rentcar.shared.domain.Customer
import com.bravedroid.rentcar.shared.presentation.CustomerUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*
import kotlin.coroutines.CoroutineContext

@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
class CustomersScreenViewModelImplFlowTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var parentCoroutineContext: CoroutineContext
    private lateinit var getAllCustomersUseCaseMock: GetAllCustomersUseCase
    private lateinit var sut: CustomersScreenViewModelImpl


    @Before
    fun setUp() {
        parentCoroutineContext = testDispatcher
        getAllCustomersUseCaseMock = mock(GetAllCustomersUseCase::class.java)
        sut = CustomersScreenViewModelImpl(parentCoroutineContext, getAllCustomersUseCaseMock)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `loadContent should invoke getAllCustomersUseCase#invoke`() = runBlockingTest {
        //arrange
        val customer = Customer(100, "james", 1, "", "")
        val expectedList = listOf(
            customer,
            customer.copy(200, "julia", 0),
            customer.copy(300, "houlia", 0),
        )
        //act
        sut.loadContent()
        `when`(getAllCustomersUseCaseMock.invoke()).thenReturn(flowOf(expectedList))
        //assert
        verify(getAllCustomersUseCaseMock).invoke()
    }

    @Test
    fun `loadContent should update sut#allCustomers`() = runBlockingTest {
        //arrange
        val customer = Customer(100, "james", 1, "", "")
        val expectedList = listOf(
            customer,
            customer.copy(200, "julia", 0),
            customer.copy(300, "houlia", 0),
        )
        `when`(getAllCustomersUseCaseMock.invoke()).thenReturn(flowOf(expectedList))
        val observerMock = mock(Observer::class.java) as Observer<List<CustomerUiModel>>
        val customerUiModelList = listOf<CustomerUiModel>(
            CustomerUiModel(100, "james"),
            CustomerUiModel(200, "julia"),
            CustomerUiModel(300, "houlia"),
        )
        //act
        sut.loadContent()
        sut.allCustomers.observeForever(observerMock)

        //assert
        verify(observerMock).onChanged(customerUiModelList)
        sut.allCustomers.removeObserver(observerMock)
    }

    @Test
    fun `loadContent should update sut#allCustomers with predefined list in case of error`() =
        runBlockingTest {
            `when`(getAllCustomersUseCaseMock.invoke()).thenReturn(
                flow {
                    throw IllegalArgumentException("mock error message")
                }
            )
            val observerMock = mock(Observer::class.java) as Observer<List<CustomerUiModel>>
            val customerUiModelList = listOf(
                CustomerUiModel(100, "James"),
                CustomerUiModel(101, "max"),
                CustomerUiModel(102, "Bill"),
            )

            //act
            sut.allCustomers.observeForever(observerMock)
            sut.loadContent()

            //assert
            verify(observerMock).onChanged(customerUiModelList)
            sut.allCustomers.removeObserver(observerMock)
        }

    @Test
    fun `loadContent should not update sut#allCustomers with predefined list in case of non known error`() =
        runBlockingTest {
            //arrange
            `when`(getAllCustomersUseCaseMock.invoke()).then{throw RuntimeException("mock error message")}
            val observerMock = mock(Observer::class.java) as Observer<List<CustomerUiModel>>
            val customerUiModelList = listOf(
                CustomerUiModel(100, "James"),
                CustomerUiModel(101, "max"),
                CustomerUiModel(102, "Bill"),
            )

            //act
            sut.allCustomers.observeForever(observerMock)
            sut.loadContent()

            //assert
            verify(observerMock, never()).onChanged(argThat {
                it == customerUiModelList
            })
            sut.allCustomers.removeObserver(observerMock)
        }
}
