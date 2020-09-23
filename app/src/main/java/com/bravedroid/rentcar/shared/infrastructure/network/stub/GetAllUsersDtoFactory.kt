package com.bravedroid.rentcar.shared.infrastructure.network.stub

import com.bravedroid.rentcar.shared.infrastructure.network.dto.users.UserDto

interface GetAllUsersDtoFactory {
    fun create(): List<UserDto>
}
