package com.bravedroid.rentcar.shared.infrastructure.network.stub

import com.bravedroid.rentcar.shared.infrastructure.network.dto.cusomers.CustomerDto

interface GetAllCustomersDtoFactory {
    fun create(): CustomerDto
}
