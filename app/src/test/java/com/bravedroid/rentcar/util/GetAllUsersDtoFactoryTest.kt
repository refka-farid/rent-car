package com.bravedroid.rentcar.util

import org.junit.Assert
import org.junit.Test

class GetAllUsersDtoFactoryTest {

    @Test
    fun testGetAllUsersDtoFactory() {
        val factory: GetAllUsersDtoFactoryImpl = GetAllUsersDtoFactoryImpl()
        val usersList = factory.create()
        Assert.assertEquals(8, usersList.size)
    }
}
