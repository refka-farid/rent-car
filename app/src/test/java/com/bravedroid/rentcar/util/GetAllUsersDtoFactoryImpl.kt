package com.bravedroid.rentcar.util

import com.bravedroid.rentcar.shared.infrastructure.network.dto.users.UserDto
import com.bravedroid.rentcar.shared.infrastructure.network.stub.GetAllUsersDtoFactory
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class GetAllUsersDtoFactoryImpl : GetAllUsersDtoFactory {
    override fun create(): List<UserDto> {
        val json = this::class.java.getResource("/GET_all_users.json")!!.readText()
        val moshi = Moshi.Builder().build()
        val mutableListOfUserDtoType =
            Types.newParameterizedType(MutableList::class.java, UserDto::class.java)
        val jsonAdapter: JsonAdapter<MutableList<UserDto>> = moshi.adapter(mutableListOfUserDtoType)
        //val jsonAdapter = moshi.adapter(GetAllUsersDto::class.java)
        val mutableListOfUserDto = jsonAdapter.fromJson(json)
        //val getAllUsersDto = jsonAdapter.fromJson(json)
        //println(getAllUsersDto)
        println(mutableListOfUserDto)
        return mutableListOfUserDto!!.toList()
    }
}
