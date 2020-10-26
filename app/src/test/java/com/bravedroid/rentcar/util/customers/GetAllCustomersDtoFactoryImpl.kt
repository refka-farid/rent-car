package com.bravedroid.rentcar.util.customers

import com.bravedroid.rentcar.shared.infrastructure.network.dto.cusomers.CustomerDto
import com.bravedroid.rentcar.shared.infrastructure.network.stub.GetAllCustomersDtoFactory
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class GetAllCustomersDtoFactoryImpl : GetAllCustomersDtoFactory {
    override fun create(): CustomerDto {
        val json = this::class.java.getResource("/GET_all_cutomers.json")!!.readText()
        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<CustomerDto> = moshi.adapter(CustomerDto::class.java)
        val mutableListOfCustomerDto = jsonAdapter.fromJson(json)
        return mutableListOfCustomerDto!!
    }
}
