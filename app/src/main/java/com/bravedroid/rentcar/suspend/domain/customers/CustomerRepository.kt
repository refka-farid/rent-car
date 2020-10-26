package com.bravedroid.rentcar.suspend.domain.customers

import com.bravedroid.rentcar.shared.domain.Customer

interface CustomerRepository {
    suspend fun getAll(): List<Customer>
}
