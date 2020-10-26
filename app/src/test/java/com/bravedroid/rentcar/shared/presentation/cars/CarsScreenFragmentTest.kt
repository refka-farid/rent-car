package com.bravedroid.rentcar.shared.presentation.cars

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class CarsScreenFragmentTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sut: CarsScreenFragment
    private lateinit var carScreenViewModelMock: CarScreenViewModel

    @Before
    fun setUp() {
        carScreenViewModelMock = mock(CarScreenViewModel::class.java)
        sut = CarsScreenFragment(carScreenViewModelMock)
    }

    @Test
    fun onCleared() {
        val uiModelList = listOf(CarUiModel("1000", "FIAt"))
        val allCarsLiveDataFake = MutableLiveData(uiModelList)
        `when`(carScreenViewModelMock.allCars).thenReturn(allCarsLiveDataFake)

        val result = sut.showCars()
        verify(carScreenViewModelMock).loadContent()
        verify(carScreenViewModelMock).allCars
    }
}
