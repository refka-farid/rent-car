package com.bravedroid.rentcar.util.users

import com.bravedroid.rentcar.shared.infrastructure.network.dto.users.UserDto
import com.bravedroid.rentcar.shared.infrastructure.network.stub.GetAllUsersDtoFactory
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class GetAllUsersDtoFactoryImpl : GetAllUsersDtoFactory {
    override fun create(): List<UserDto> {
        val json = this::class.java.getResource("/GET_all_users.json")!!.readText()
        val moshi = Moshi.Builder().build()
        //val jsonAdapter = moshi.adapter(ArrayList<UserDto>::class.java)// this is impossible in java
        val mutableListOfUserDtoType =
            Types.newParameterizedType(MutableList::class.java, UserDto::class.java)
        val jsonAdapter: JsonAdapter<MutableList<UserDto>> = moshi.adapter(mutableListOfUserDtoType)
        val mutableListOfUserDto = jsonAdapter.fromJson(json)
        return mutableListOfUserDto!!.toList()
    }
}
