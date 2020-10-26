package com.bravedroid.rentcar.shared.presentation.customers

import androidx.lifecycle.LiveData

interface CustomersScreenViewModel {
    val allCustomers: LiveData<List<CustomerUiModel>>
    fun loadContent()
}
