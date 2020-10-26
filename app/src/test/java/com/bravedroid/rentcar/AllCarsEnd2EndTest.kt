package com.bravedroid.rentcar

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bravedroid.rentcar.flow.domain.cars.GetAllCarsUseCase
import com.bravedroid.rentcar.flow.infrastructure.network.stub.ApiServiceFlowInstance
import com.bravedroid.rentcar.flow.infrastructure.repository.CarsRepositoryImpl
import com.bravedroid.rentcar.flow.presentation.CarsScreenViewModelImpl
import com.bravedroid.rentcar.shared.presentation.cars.CarScreenViewModel
import com.bravedroid.rentcar.shared.presentation.cars.CarsScreenFragment
import com.bravedroid.rentcar.suspend.infrastructure.network.stub.ApiServiceInstance
import com.bravedroid.rentcar.util.cars.GetAllCarsDtoFactoryImpl
import com.bravedroid.rentcar.util.customers.GetAllCustomersDtoFactoryImpl
import com.bravedroid.rentcar.util.users.GetAllUsersDtoFactoryImpl
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnitParamsRunner::class)
class AllCarsEnd2EndTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private fun provideAsyncStrategyArgs(): Array<Any> =
        arrayOf(
            AsynchronousStrategy.COROUTINE,
            AsynchronousStrategy.REACTIVE
        )

    @Test
    @Parameters(method = "provideAsyncStrategyArgs")
    fun main(asynchronousStrategy: AsynchronousStrategy) = runBlocking {
        println("***=> START ${asynchronousStrategy.name} STRATEGY ")
        val carsScreenViewModel: CarScreenViewModel =
            when (asynchronousStrategy) {
                AsynchronousStrategy.REACTIVE -> {
                    val getAllUsersDtoFactoryImpl = GetAllUsersDtoFactoryImpl()
                    val getAllCustomersDtoFactoryImpl = GetAllCustomersDtoFactoryImpl()
                    val getAllCarsDtoFactoryImpl = GetAllCarsDtoFactoryImpl()
                    val apiService: ApiServiceFlowInstance = ApiServiceFlowInstance(
                        getAllUsersDtoFactoryImpl,
                        getAllCustomersDtoFactoryImpl,
                        getAllCarsDtoFactoryImpl,
                    )
                    val carRepository = CarsRepositoryImpl(apiService)
                    val getAllCarsUseCase = GetAllCarsUseCase(carRepository)
                    CarsScreenViewModelImpl(coroutineContext, getAllCarsUseCase)
                }
                AsynchronousStrategy.COROUTINE -> {
                    val getAllUsersDtoFactoryImpl = GetAllUsersDtoFactoryImpl()
                    val getAllCustomersDtoFactoryImpl = GetAllCustomersDtoFactoryImpl()
                    val getAllCarsDtoFactoryImpl = GetAllCarsDtoFactoryImpl()
                    val apiService: ApiServiceInstance = ApiServiceInstance(
                        getAllUsersDtoFactoryImpl,
                        getAllCustomersDtoFactoryImpl,
                        getAllCarsDtoFactoryImpl,
                    )
                    val carRepository =
                        com.bravedroid.rentcar.suspend.infrastructure.repository.CarsRepositoryImpl(
                            apiService
                        )
                    val getAllCarsUseCase =
                        com.bravedroid.rentcar.suspend.domain.cars.GetAllCarsUseCase(carRepository)
                    com.bravedroid.rentcar.suspend.presentation.CarsScreenViewModelImpl(
                        coroutineContext,
                        getAllCarsUseCase
                    )
                }
                AsynchronousStrategy.CALLBACK -> {
                    TODO()
                }
            }
        val fragment = CarsScreenFragment(carsScreenViewModel)
        fragment.showCars()
    }
}
