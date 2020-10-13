package com.bravedroid.rentcar.util

import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class GetAllCustomersDtoFactoryImplTest {

    @Test
    fun testGetAllCustomersDtoFactory() {
        val factory = GetAllCustomersDtoFactoryImpl()
        val customersList = factory.create()
        Assert.assertEquals(4,customersList.proAndNormalCustomers?.normal?.size)
        Assert.assertEquals(9,customersList.proAndNormalCustomers?.pro?.size)
    }
}
