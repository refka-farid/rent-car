package com.bravedroid.rentcar.suspend.infrastructure.repository

import com.bravedroid.rentcar.shared.domain.Customer
import com.bravedroid.rentcar.suspend.domain.customers.CustomerRepository
import com.bravedroid.rentcar.suspend.infrastructure.network.ApiService
import com.bravedroid.rentcar.suspend.infrastructure.persistence.UserDao

class CustomerRepositoryImpl(
    private val apiService: ApiService,
    userDao: UserDao,
) : CustomerRepository {

    override suspend fun getAll(): List<Customer> {
        val allCustomers = apiService.getAllCustomers()
        val customerList = mutableListOf<Customer>()
        allCustomers.proAndNormalCustomers?.pro?.forEach { it ->
            customerList.add(
                Customer(
                    id = (it.id ?: -1).toLong(),
                    name = it.name ?: "",
                    sex = getSexType(it.sex),
                    biography = "",
                    birthday = ""
                )
            )
        }
        allCustomers.proAndNormalCustomers?.normal?.forEach { it ->
            customerList.add(
                Customer(
                    id = (it.id ?: -1).toLong(),
                    name = it.name ?: "",
                    sex = getSexType(it.sex),
                    biography = "",
                    birthday = ""
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
