package com.bravedroid.rentcar.shared.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class CustomersScreenFragmentTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var customersScreenViewModelMock: CustomersScreenViewModel
    private lateinit var sut: CustomersScreenFragment

    @Before
    fun setUp() {
        customersScreenViewModelMock = mock(CustomersScreenViewModel::class.java)
        sut = CustomersScreenFragment(customersScreenViewModelMock)
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun showUsersTest3() {
        //arrange
        val uiModelList = listOf(CustomerUiModel(1000, "jucky"))
        val allCustomersLiveDataFake = MutableLiveData(uiModelList)
        `when`(customersScreenViewModelMock.allCustomers).thenReturn(allCustomersLiveDataFake)
        //act
        sut.showCustomers()
        //assert
        verify(customersScreenViewModelMock).loadContent()
        verify(customersScreenViewModelMock).allCustomers
    }
}
