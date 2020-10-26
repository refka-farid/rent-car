package com.bravedroid.rentcar.util.cars

import com.bravedroid.rentcar.shared.infrastructure.network.dto.cars.AllCarsDto
import com.bravedroid.rentcar.shared.infrastructure.network.stub.GetAllCarsDtoFactory
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

class GetAllCarsDtoFactoryImpl : GetAllCarsDtoFactory {
    override fun create(): AllCarsDto {
        val json = this::class.java.getResource("/GET_all_cars.json")!!.readText()
        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<AllCarsDto> = moshi.adapter(AllCarsDto::class.java)
        val mutableListOfAllCarsDto = jsonAdapter.fromJson(json)
        return mutableListOfAllCarsDto!!
    }
}
