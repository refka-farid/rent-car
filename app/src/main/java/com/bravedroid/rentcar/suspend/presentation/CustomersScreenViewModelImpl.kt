package com.bravedroid.rentcar.suspend.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bravedroid.rentcar.shared.domain.Customer
import com.bravedroid.rentcar.shared.presentation.customers.CustomerUiModel
import com.bravedroid.rentcar.shared.presentation.customers.CustomersScreenViewModel
import com.bravedroid.rentcar.suspend.domain.customers.GetAllCustomersUseCase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class CustomersScreenViewModelImpl(
    private val parentCoroutineContext: CoroutineContext,
    private val getAllCustomersUseCase: GetAllCustomersUseCase,
) : ViewModel(), CoroutineScope, CustomersScreenViewModel {

    private val supervisorJob = SupervisorJob(parentCoroutineContext[Job])
    override val coroutineContext: CoroutineContext
        get() = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("Exception occurred in viewModel ${throwable.message}  \n in $coroutineContext")
        } + parentCoroutineContext + supervisorJob

    private val _allCustomers = MutableLiveData<List<CustomerUiModel>>()
    override val allCustomers: LiveData<List<CustomerUiModel>> = _allCustomers

    override fun loadContent() {
        launch(coroutineContext) {
            _allCustomers.value = try {
                getAllCustomersUseCase().mapToCustomerUiModelList()
            } catch (e: IllegalArgumentException) {
                listOf(
                    Customer(100, "James", 2, "PD Mahboul", "1970"),
                    Customer(101, "Max", 2, "PD Za3ben", "1971"),
                    Customer(102, "Bill", 2, "PD Mezien", "1972"),
                ).mapToCustomerUiModelList()
            }
            supervisorJob.complete()
        }
    }

    private fun List<Customer>.mapToCustomerUiModelList(): List<CustomerUiModel> =
        map { CustomerUiModel(it.id, it.name) }

    override fun onCleared() {
        super.onCleared()
        supervisorJob.cancel()
    }
}
