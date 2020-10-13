package com.bravedroid.rentcar.flow.domain

import com.bravedroid.rentcar.shared.domain.Customer
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    fun getAll(): Flow<List<Customer>>
}
