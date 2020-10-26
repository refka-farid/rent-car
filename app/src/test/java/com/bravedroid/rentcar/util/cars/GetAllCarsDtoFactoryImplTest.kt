package com.bravedroid.rentcar.util.cars

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetAllCarsDtoFactoryImplTest {
    @Test
    fun create() {
        val factory = GetAllCarsDtoFactoryImpl()
        val carsList = factory.create()
        Assert.assertEquals(9,carsList.cars?.size)
    }
}
