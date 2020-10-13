package com.bravedroid.rentcar.shared.presentation

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer

class CustomersScreenFragment(
    private val customersScreenViewModel: CustomersScreenViewModel
) : LifecycleOwner {
    private val lifecycle = LifecycleRegistry(this)

    init {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun getLifecycle(): Lifecycle = lifecycle

    fun showCustomers() {
        customersScreenViewModel.loadContent()
        customersScreenViewModel.allCustomers.observe(this, Observer {
            println("all customers are the following :")
            it.forEach(::println)
        })
    }
}
