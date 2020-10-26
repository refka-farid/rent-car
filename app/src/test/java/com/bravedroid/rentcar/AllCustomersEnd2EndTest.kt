package com.bravedroid.rentcar

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bravedroid.rentcar.flow.domain.customers.GetAllCustomersUseCase
import com.bravedroid.rentcar.flow.infrastructure.network.ApiServiceFlow
import com.bravedroid.rentcar.flow.infrastructure.network.stub.ApiServiceFlowInstance
import com.bravedroid.rentcar.flow.infrastructure.persistence.UserDao
import com.bravedroid.rentcar.flow.infrastructure.persistence.fake.UserDaoInstance
import com.bravedroid.rentcar.flow.presentation.CustomersScreenViewModelImpl
import com.bravedroid.rentcar.shared.presentation.customers.CustomersScreenFragment
import com.bravedroid.rentcar.shared.presentation.customers.CustomersScreenViewModel
import com.bravedroid.rentcar.suspend.infrastructure.network.ApiService
import com.bravedroid.rentcar.suspend.infrastructure.network.stub.ApiServiceInstance
import com.bravedroid.rentcar.suspend.infrastructure.repository.CustomerRepositoryImpl
import com.bravedroid.rentcar.util.cars.GetAllCarsDtoFactoryImpl
import com.bravedroid.rentcar.util.customers.GetAllCustomersDtoFactoryImpl
import com.bravedroid.rentcar.util.users.GetAllUsersDtoFactoryImpl
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.bravedroid.rentcar.flow.infrastructure.repository.CustomerRepositoryImpl as CustomerRepositoryImplFlow
import com.bravedroid.rentcar.suspend.domain.customers.GetAllCustomersUseCase as GetAllCustomersUseCaseSuspend
import com.bravedroid.rentcar.suspend.infrastructure.persistence.UserDao as UserDaoSuspend
import com.bravedroid.rentcar.suspend.infrastructure.persistence.fake.UserDaoInstance as UserDaoInstanceSuspend
import com.bravedroid.rentcar.suspend.presentation.CustomersScreenViewModelImpl as CustomersScreenViewModelImplSuspend

@RunWith(JUnitParamsRunner::class)
class AllCustomersEnd2EndTest {
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
        val customersScreenViewModel: CustomersScreenViewModel =
            when (asynchronousStrategy) {
                AsynchronousStrategy.REACTIVE -> {
                    val userDao: UserDao = UserDaoInstance()
                    val getAllUsersDtoFactoryImpl = GetAllUsersDtoFactoryImpl()
                    val getAllCustomersDtoFactoryImpl = GetAllCustomersDtoFactoryImpl()
                    val getAllCarsDtoFactoryImpl = GetAllCarsDtoFactoryImpl()
                    val apiService: ApiServiceFlow = ApiServiceFlowInstance(
                        getAllUsersDtoFactoryImpl,
                        getAllCustomersDtoFactoryImpl,
                        getAllCarsDtoFactoryImpl,
                    )
                    val customerRepository = CustomerRepositoryImplFlow(apiService, userDao)
                    val getAllCustomersUseCase = GetAllCustomersUseCase(customerRepository)
                    CustomersScreenViewModelImpl(coroutineContext, getAllCustomersUseCase)
                }
                AsynchronousStrategy.COROUTINE -> {
                    val userDao: UserDaoSuspend = UserDaoInstanceSuspend()
                    val getAllUsersDtoFactoryImpl = GetAllUsersDtoFactoryImpl()
                    val getAllCustomersDtoFactoryImpl = GetAllCustomersDtoFactoryImpl()
                    val getAllCarsDtoFactoryImpl = GetAllCarsDtoFactoryImpl()
                    val apiService: ApiService = ApiServiceInstance(
                        getAllUsersDtoFactoryImpl,
                        getAllCustomersDtoFactoryImpl,
                        getAllCarsDtoFactoryImpl
                    )
                    val customerRepository = CustomerRepositoryImpl(apiService, userDao)
                    val getAllUsersUseCase = GetAllCustomersUseCaseSuspend(customerRepository)
                    CustomersScreenViewModelImplSuspend(coroutineContext, getAllUsersUseCase)
                }
                AsynchronousStrategy.CALLBACK -> TODO("not supporting callback yet")
            }

        val fragment = CustomersScreenFragment(customersScreenViewModel)
        fragment.showCustomers()
    }
}
