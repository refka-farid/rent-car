package com.bravedroid.rentcar.suspend.domain

import com.bravedroid.rentcar.shared.domain.Customer

interface CustomerRepository {
    suspend fun getAll(): List<Customer>
}
