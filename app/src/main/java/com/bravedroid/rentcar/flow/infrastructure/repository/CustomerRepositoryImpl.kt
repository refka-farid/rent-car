package com.bravedroid.rentcar.flow.infrastructure.repository

import com.bravedroid.rentcar.flow.domain.CustomerRepository
import com.bravedroid.rentcar.flow.infrastructure.network.ApiServiceFlow
import com.bravedroid.rentcar.flow.infrastructure.persistence.UserDao
import com.bravedroid.rentcar.shared.domain.Customer
import com.bravedroid.rentcar.shared.infrastructure.network.dto.cusomers.CustomerDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CustomerRepositoryImpl(
    private val apiServiceFlow: ApiServiceFlow,
    private val userDao: UserDao,
) : CustomerRepository {
    override fun getAll(): Flow<List<Customer>> = apiServiceFlow.getAllCustomers()
        .map {
            createCustomerList(it)
        }

    private fun createCustomerList(customerDto: CustomerDto): List<Customer> {
        val customerList: MutableList<Customer> = mutableListOf()
        customerDto.proAndNormalCustomers?.pro?.forEach {
            customerList.add(
                Customer(
                    (it.id ?: -1).toLong(),
                    it.name ?: "",
                    getSexType(it.sex),
                    "",
                    ""
                )
            )
        }
        customerDto.proAndNormalCustomers?.normal?.forEach {
            customerList.add(
                Customer(
                    (it.id ?: -1).toLong(),
                    it.name ?: "",
                    getSexType(it.sex),
                    "",
                    ""
                )
            )
        }
        return customerList
    }

    private fun getSexType(sex: Boolean?): Int =
        if (sex != null) {
            if (sex) {
                1
            } else {
                0
            }
        } else {
            -1
        }
}
