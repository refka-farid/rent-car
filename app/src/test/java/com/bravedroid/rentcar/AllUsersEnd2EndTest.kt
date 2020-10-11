package com.bravedroid.rentcar

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bravedroid.rentcar.AsynchronousStrategy.*
import com.bravedroid.rentcar.flow.infrastructure.network.ApiServiceFlow
import com.bravedroid.rentcar.flow.infrastructure.network.stub.ApiServiceFlowInstance
import com.bravedroid.rentcar.shared.presentation.UsersScreenFragment
import com.bravedroid.rentcar.suspend.domain.GetAllUsersUseCase
import com.bravedroid.rentcar.suspend.domain.UserRepository
import com.bravedroid.rentcar.suspend.infrastructure.network.ApiService
import com.bravedroid.rentcar.suspend.infrastructure.network.stub.ApiServiceInstance
import com.bravedroid.rentcar.suspend.infrastructure.persistence.UserDao
import com.bravedroid.rentcar.suspend.infrastructure.persistence.fake.UserDaoInstance
import com.bravedroid.rentcar.suspend.infrastructure.repository.UserRepositoryImpl
import com.bravedroid.rentcar.suspend.presentation.UsersScreenViewModelImpl
import com.bravedroid.rentcar.util.GetAllUsersDtoFactoryImpl
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.bravedroid.rentcar.flow.domain.GetAllUsersUseCase as GetAllUsersUseCaseFlow
import com.bravedroid.rentcar.flow.infrastructure.persistence.UserDao as UserDaoFlow
import com.bravedroid.rentcar.flow.infrastructure.persistence.fake.UserDaoInstance as UserDaoInstanceFlow
import com.bravedroid.rentcar.flow.infrastructure.repository.UserRepositoryImpl as UserRepositoryImplFlow
import com.bravedroid.rentcar.flow.presentation.UsersScreenViewModelImpl as UsersScreenViewModelFlow


@RunWith(JUnitParamsRunner::class)
class AllUsersEnd2EndTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private fun provideAsyncStrategyArgs(): Array<Any> =
        arrayOf(
            COROUTINE,
            REACTIVE
        )

    @Test
    @Parameters(method = "provideAsyncStrategyArgs")
    fun main(asynchronousStrategy: AsynchronousStrategy) = runBlocking {
        println("***=> START ${asynchronousStrategy.name} STRATEGY ")
        val usersScreenViewModel =
            when (asynchronousStrategy) {
                REACTIVE -> {
                    val userDao: UserDaoFlow = UserDaoInstanceFlow()
                    val getAllUsersDtoFactoryImpl=GetAllUsersDtoFactoryImpl()
                    val apiService: ApiServiceFlow = ApiServiceFlowInstance(getAllUsersDtoFactoryImpl)
                    val userRepository = UserRepositoryImplFlow(apiService, userDao)
                    val getAllUsersUseCase = GetAllUsersUseCaseFlow(userRepository)
                    UsersScreenViewModelFlow(coroutineContext, getAllUsersUseCase)
                }
                COROUTINE -> {
                    val userDao: UserDao = UserDaoInstance()
                    val getAllUsersDtoFactoryImpl=GetAllUsersDtoFactoryImpl()
                    val apiService: ApiService = ApiServiceInstance(getAllUsersDtoFactoryImpl)
                    val userRepository: UserRepository = UserRepositoryImpl(apiService,userDao)
                    val getAllUsersUseCase = GetAllUsersUseCase(userRepository)
                    UsersScreenViewModelImpl(coroutineContext, getAllUsersUseCase)
                }
                CALLBACK -> TODO("not supporting callback yet")
            }

        val fragment = UsersScreenFragment(usersScreenViewModel)
        fragment.showUsers()
    }
}

enum class AsynchronousStrategy {
    REACTIVE,
    COROUTINE,
    CALLBACK;
}
