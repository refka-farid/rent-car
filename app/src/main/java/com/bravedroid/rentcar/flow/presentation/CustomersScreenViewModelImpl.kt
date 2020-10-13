package com.bravedroid.rentcar.flow.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bravedroid.rentcar.flow.domain.GetAllCustomersUseCase
import com.bravedroid.rentcar.shared.domain.Customer
import com.bravedroid.rentcar.shared.presentation.CustomerUiModel
import com.bravedroid.rentcar.shared.presentation.CustomersScreenViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
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
            getAllCustomersUseCase()
                .onEach {
                    println("event is intercepted ")
                }
                .catch { e: Throwable ->
                    if (e is IllegalArgumentException) emit(
                        listOf(
                            Customer(100, "James", 1, "", ""),
                            Customer(101, "max", 1, "", ""),
                            Customer(102, "Bill", 1, "", ""),
                        )
                    )
                }.collect { customers: List<Customer> ->
                    customers.mapToCustomerUiModelList()
                        .let {
                            _allCustomers.value = it
                        }
                    supervisorJob.complete()
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        supervisorJob.cancel()
    }

    private fun List<Customer>.mapToCustomerUiModelList(): List<CustomerUiModel> =
        map { CustomerUiModel(it.id, it.name) }
}
