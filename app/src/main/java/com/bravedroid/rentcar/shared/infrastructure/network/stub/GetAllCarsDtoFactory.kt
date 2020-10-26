package com.bravedroid.rentcar.shared.infrastructure.network.stub

import com.bravedroid.rentcar.shared.infrastructure.network.dto.cars.AllCarsDto

interface GetAllCarsDtoFactory {
    fun create(): AllCarsDto
}
