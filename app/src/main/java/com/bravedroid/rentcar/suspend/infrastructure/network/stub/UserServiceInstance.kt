package com.bravedroid.rentcar.suspend.infrastructure.network.stub

import com.bravedroid.rentcar.suspend.infrastructure.network.UserDto
import com.bravedroid.rentcar.suspend.infrastructure.network.UserInfoDto
import com.bravedroid.rentcar.suspend.infrastructure.network.UserService
import kotlinx.coroutines.delay

//stub , mocked user service implementation just to test
class UserServiceInstance : UserService {
    override suspend fun getAllUsers(): List<UserDto> =
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
            ), UserDto(
                15,
                "Soumaya",
                0
            )
        )

    override suspend fun getUserInfo(idNetwork: Long): UserInfoDto {
        delay(2_000)
        return when (idNetwork) {
            11L -> UserInfoDto("Amer is the grand father of Mohsen")
            12L -> UserInfoDto("American singer, was maried to justin timberlake ")
            13L -> UserInfoDto(birthday = "01/01/2020")
            14L -> UserInfoDto("Zahia is his mother, his knonw as faysoul")
            15L -> UserInfoDto("01/01/1980")
            else -> throw IllegalArgumentException("")
        }
    }
}
