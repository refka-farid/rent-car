package com.bravedroid.rentcar.flow.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bravedroid.rentcar.flow.domain.GetAllUsersUseCase
import com.bravedroid.rentcar.shared.domain.User
import com.bravedroid.rentcar.shared.presentation.UserUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito
import org.mockito.Mockito.*
import kotlin.IllegalArgumentException
import kotlin.coroutines.CoroutineContext

@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
class UsersScreenViewModelImplTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
    private val user = User(100, "", 1, "some biographie", "01/09/1999")
    private val listOfUser: List<User> = listOf(
        user.copy(200, "SIHEM"),
        user.copy(300, "SALMA"),
        user.copy(400, "ALMA"),
    )
    private val uiModelList = listOf(
        UserUiModel(200, "SIHEM"),
        UserUiModel(300, "SALMA"),
        UserUiModel(400, "ALMA")
    )
    private lateinit var parentCoroutineContext: CoroutineContext
    private lateinit var getAllUsersUseCaseMock: GetAllUsersUseCase
    private lateinit var sut: UsersScreenViewModelImpl

    @Before
    fun setUp() {
        parentCoroutineContext = testDispatcher
        getAllUsersUseCaseMock = mock(GetAllUsersUseCase::class.java)
        sut = UsersScreenViewModelImpl(parentCoroutineContext, getAllUsersUseCaseMock)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `loadContent should invoke getAllUsersUseCase#invoke`() {
        // arrange
        `when`(getAllUsersUseCaseMock.invoke()).thenReturn(flowOf(listOfUser))
        // act
        sut.loadContent()
        // assert
        verify(getAllUsersUseCaseMock).invoke()
    }

    @Test
    fun `loadContent should update sut#allUsers`() {
        //arrange
        `when`(getAllUsersUseCaseMock.invoke()).thenReturn(flowOf(listOfUser))
        val observerMock = mock(Observer::class.java) as Observer<List<UserUiModel>>

        //act
        sut.loadContent()
        sut.allUsers.observeForever(observerMock)

        //assert
        verify(observerMock).onChanged(uiModelList)
        sut.allUsers.removeObserver(observerMock)
    }

    @Test
    fun `loadContent should update sut#allUsers with predefined list in case of error`() =  runBlockingTest {

        //arrange
        `when`(getAllUsersUseCaseMock.invoke()).thenReturn(
            flow {
                throw IllegalArgumentException("mock error message")
            }
        )
        val observerMock = mock(Observer::class.java) as Observer<List<UserUiModel>>
        val uiModelList = listOf(
            UserUiModel(100, "James"),
            UserUiModel(101, "Max"),
            UserUiModel(102, "Bill"),
        )

        //act
        sut.allUsers.observeForever(observerMock)
        sut.loadContent()

        // assert
        verify(observerMock).onChanged(argThat {
          it==uiModelList
        })
        sut.allUsers.removeObserver(observerMock)
    }

    @Test
    fun `loadContent should not update sut#allUsers with predefined list in case of non known error`() =  runBlockingTest {

        //arrange
        `when`(getAllUsersUseCaseMock.invoke()).thenReturn(
            flow {
                throw RuntimeException("mock error message")
            }
        )
        val observerMock = mock(Observer::class.java) as Observer<List<UserUiModel>>
        val uiModelList = listOf(
            UserUiModel(100, "James"),
            UserUiModel(101, "Max"),
            UserUiModel(102, "Bill"),
        )

        //act
        sut.allUsers.observeForever(observerMock)
        sut.loadContent()

        // assert
        verify(observerMock, never()).onChanged(argThat {
          it==uiModelList
        })
        sut.allUsers.removeObserver(observerMock)
    }
}
