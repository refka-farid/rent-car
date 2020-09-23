package com.bravedroid.rentcar.flow.infrastructure.network.stub

import com.bravedroid.rentcar.flow.infrastructure.network.UserDto
import com.bravedroid.rentcar.flow.infrastructure.network.UserInfoDto
import com.bravedroid.rentcar.flow.infrastructure.network.UserService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart

//stub , mocked user service implementation just to test
class UserServiceInstance : UserService {
    override fun getAllUsers(): Flow<List<UserDto>> =
        flowOf(
            listOf(
                UserDto(
                    11,
                    "Amer",
                    1
                ),
                UserDto(
                    12,
                    "Britny"
                ),
                UserDto(
                    13,
                    "Maher",
                    1
                ),
                UserDto(
                    14,
                    "Faysal",
                    1
                ),
                UserDto(
                    15,
                    "Soumaya",
                    0
                ),
//            UserDto(
//              16,
//              "Karim",
//              0
//            ),

            )
        ).onStart {
            delay(5_000)
        }

    override fun getUserInfo(idNetwork: Long): Flow<UserInfoDto> {
        return flow {
            delay(2_000)
            when (idNetwork) {
                11L -> emit(UserInfoDto("Amer is the grand father of Mohsen"))
                12L -> emit(UserInfoDto("American singer, was maried to justin timberlake "))
                13L -> emit(UserInfoDto(birthday = "01/01/2020"))
                14L -> emit(UserInfoDto("Zahia is his mother, his knonw as faysoul"))
                15L -> emit(UserInfoDto("01/01/1980"))
                else -> throw IllegalArgumentException("404 :p")
            }
        }
    }
}
