package com.bravedroid.rentcar.shared.presentation

import androidx.lifecycle.LiveData

interface CustomersScreenViewModel {
    val allCustomers: LiveData<List<CustomerUiModel>>
    fun loadContent()
}
