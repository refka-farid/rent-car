package com.bravedroid.rentcar.util.users

import org.junit.Assert
import org.junit.Test

class GetAllUsersDtoFactoryImlTest {

    @Test
    fun testGetAllUsersDtoFactory() {
        val factory = GetAllUsersDtoFactoryImpl()
        val usersList = factory.create()
        Assert.assertEquals(8, usersList.size)
    }
}
