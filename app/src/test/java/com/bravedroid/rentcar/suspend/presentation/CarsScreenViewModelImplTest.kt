package com.bravedroid.rentcar.suspend.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bravedroid.rentcar.suspend.domain.cars.GetAllCarsUseCase
import com.bravedroid.rentcar.shared.domain.Car
import com.bravedroid.rentcar.shared.presentation.cars.CarUiModel
import com.bravedroid.rentcar.shared.presentation.customers.CustomerUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class CarsScreenViewModelImplTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var parentCoroutineContext: CoroutineContext
    private lateinit var getAllCarsUseCaseMock: GetAllCarsUseCase
    private lateinit var sut: CarsScreenViewModelImpl

    @Before
    fun setUp() {
        parentCoroutineContext = testDispatcher
        getAllCarsUseCaseMock = mock(GetAllCarsUseCase::class.java)
        sut = CarsScreenViewModelImpl(parentCoroutineContext, getAllCarsUseCaseMock)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `loadContent should invoke getAllCarsUseCase#invoke`() = runBlockingTest {
        val car = Car("100", 50, "Symbole", "Renault")
        val expectedCarsList = listOf(
            car,
            car.copy("200", 70, "nissan micra"),
            car.copy("300", 10, "nissan micra"),
            car.copy("400", 20, "nissan micra"),
        )
        `when`(getAllCarsUseCaseMock.invoke()).thenReturn(expectedCarsList)
        val result = sut.loadContent()
        verify(getAllCarsUseCaseMock).invoke()
    }

    @Test
    fun `loadContent should update sut#allCars`() = runBlockingTest {
        val car = Car("100", 50, "Symbole", "Renault")
        val expectedCarsList = listOf(
            car,
            car.copy("200", 70, "nissan micra"),
            car.copy("300", 10, "nissan micra"),
            car.copy("400", 20, "nissan micra"),
        )
        `when`(getAllCarsUseCaseMock.invoke()).thenReturn(expectedCarsList)
        val observerMock = mock(Observer::class.java) as Observer<List<CarUiModel>>
        val carUiModelList = listOf(
            CarUiModel("100", "Symbole"),
            CarUiModel("200", "nissan micra"),
            CarUiModel("300", "nissan micra"),
            CarUiModel("400", "nissan micra"),
        )
        sut.allCars.observeForever(observerMock)
        sut.loadContent()
        verify(observerMock).onChanged(carUiModelList)
        sut.allCars.removeObserver(observerMock)
    }

    @Test
    fun `loadContent should update sut#allCars with predefined list in case of error`() =
        runBlockingTest {
            `when`(getAllCarsUseCaseMock.invoke()).thenThrow(IllegalArgumentException("exception from test"))
            val observerMock = mock(Observer::class.java) as Observer<List<CarUiModel>>
            val carUiModelList = listOf(
                CarUiModel("111--A", "Symbole"),
                CarUiModel("111--A", "Symbole"),
                CarUiModel("111--A", "Symbole"),
            )
            sut.allCars.observeForever(observerMock)
            sut.loadContent()

            verify(observerMock).onChanged(carUiModelList)
            sut.allCars.removeObserver(observerMock)
        }

    @Test fun `loadContent should not update sut#allCars with predefined list in case of non known error`() =
        runBlockingTest {
            `when`(getAllCarsUseCaseMock.invoke()).then { throw RuntimeException("mock error message") }
            val observerMock = mock(Observer::class.java) as Observer<List<CarUiModel>>
            val carUiModelList = listOf(
                CarUiModel("111--A", "Symbole"),
                CarUiModel("111--A", "Symbole"),
                CarUiModel("111--A", "Symbole"),
            )
            sut.allCars.observeForever(observerMock)
            sut.loadContent()

            verify(observerMock, never()).onChanged(argThat {
                it == carUiModelList
            })
            sut.allCars.removeObserver(observerMock)
        }

}
