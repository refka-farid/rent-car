package com.bravedroid.rentcar.suspend.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.bravedroid.rentcar.shared.domain.User
import com.bravedroid.rentcar.shared.presentation.UserUiModel
import com.bravedroid.rentcar.suspend.domain.GetAllUsersUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import kotlin.coroutines.CoroutineContext

@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
class UsersScreenViewModelImplTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
//    @Rule
//    @JvmField
//    var coroutineTestRule = CoroutineTestRule()

    private lateinit var parentCoroutineContext: CoroutineContext
    private lateinit var getAllUsersUseCaseMock: GetAllUsersUseCase
    private lateinit var sut: UsersScreenViewModelImpl

    @Before
    fun setUp() {
        //parentCoroutineContext = coroutineTestRule.testDispatcher
        parentCoroutineContext = testDispatcher
        getAllUsersUseCaseMock = mock(GetAllUsersUseCase::class.java)
        sut = UsersScreenViewModelImpl(parentCoroutineContext, getAllUsersUseCaseMock)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `loadContent should invoke getAllUsersUseCase#invoke`() = runBlockingTest {
        val user = User(100, "", 1, "some biographie", "01/09/1999")
        val listOfUser: List<User> = listOf(
            user.copy(200, "SIHEM"),
            user.copy(300, "SALMA"),
            user.copy(400, "ALMA"),
        )
        sut.loadContent()
        `when`(getAllUsersUseCaseMock.invoke()).thenReturn(listOfUser)
        verify(getAllUsersUseCaseMock).invoke()
    }

    @Test
    fun `loadContent should update sut#allUsers`() = runBlockingTest {
        //arrange
        val user = User(100, "", 1, "some biographie", "01/09/1999")
        val listOfUser: List<User> = listOf(
            user.copy(200, "SIHEM"),
            user.copy(300, "SALMA"),
            user.copy(400, "ALMA"),
        )
        `when`(getAllUsersUseCaseMock.invoke()).thenReturn(listOfUser)
        val observerMock = mock(Observer::class.java) as Observer<List<UserUiModel>>
        val uiModelList = listOf(
            UserUiModel(200, "SIHEM"),
            UserUiModel(300, "SALMA"),
            UserUiModel(400, "ALMA")
        )

        //act
        sut.loadContent()
        sut.allUsers.observeForever(observerMock)

        // assert
        verify(observerMock).onChanged(uiModelList)
        sut.allUsers.removeObserver(observerMock)
    }


    @Test
    fun `loadContent should update sut#allUsers with predefined list in case of error`() =
        runBlockingTest {
            //arrange
            `when`(getAllUsersUseCaseMock.invoke()).thenThrow(RuntimeException())
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
            verify(observerMock).onChanged(uiModelList)
            sut.allUsers.removeObserver(observerMock)
        }

    @Test
    fun `loadContent should update sut#allUsers non Raw data `() = runBlockingTest {
        //arrange
        val user = User(100, "", 1, "some biographie", "01/09/1999")
        val listOfUser: List<User> = listOf(
            user.copy(200, "SIHEM"),
            user.copy(300, "SALMA"),
            user.copy(400, "ALMA"),
        )
        `when`(getAllUsersUseCaseMock.invoke()).thenReturn(listOfUser)
        val observerMock = mock(Observer::class.java) as Observer<List<UserUiModel>>
        val uiModelList = listOf(
            UserUiModel(200, "SIHEM"),
            UserUiModel(300, "SALMA"),
            UserUiModel(400, "ALMA")
        )

        //act
        sut.loadContent()
        sut.allUsers.observeForever(observerMock)

        // assert
        verify(observerMock).onChanged(eq(uiModelList))
        sut.allUsers.removeObserver(observerMock)
    }

    @Test
    fun `loadContent should update sut#allUsers non raw data custom matcher`() = runBlockingTest {
        //arrange
        val user = User(100, "", 1, "some biographie", "01/09/1999")
        val listOfUser: List<User> = listOf(
            user.copy(200, "SIHEM"),
            user.copy(300, "SALMA"),
            user.copy(400, "ALMA"),
        )
        `when`(getAllUsersUseCaseMock.invoke()).thenReturn(listOfUser)
        val observerMock = mock(Observer::class.java) as Observer<List<UserUiModel>>
        val uiModelList = listOf(
            UserUiModel(200, "SIHEM"),
            UserUiModel(300, "SALMA"),
            UserUiModel(400, "ALMA")
        )

        //act
        sut.loadContent()
        sut.allUsers.observeForever(observerMock)

        // assert
        verify(observerMock).onChanged(argThat { it.size == uiModelList.size })
        sut.allUsers.removeObserver(observerMock)
    }

    @Test
    fun `loadContent should update sut#allUsers non raw data argumentCaptor `() = runBlockingTest {
        //arrange
        val user = User(100, "", 1, "some biographie", "01/09/1999")
        val listOfUser: List<User> = listOf(
            user.copy(200, "SIHEM"),
            user.copy(300, "SALMA"),
            user.copy(400, "ALMA"),
        )
        `when`(getAllUsersUseCaseMock.invoke()).thenReturn(listOfUser)
        val observerMock = mock(Observer::class.java) as Observer<List<UserUiModel>>
        val uiModelList = listOf(
            UserUiModel(200, "SIHEM"),
            UserUiModel(300, "SALMA"),
            UserUiModel(400, "ALMA")
        )

        //act
        sut.loadContent()
        sut.allUsers.observeForever(observerMock)

        val argumentCaptor: ArgumentCaptor<List<UserUiModel>> =
            ArgumentCaptor.forClass(List::class.java) as ArgumentCaptor<List<UserUiModel>>

        // assert
        verify(observerMock).onChanged(argumentCaptor.capture())
        assertThat(argumentCaptor.value).isEqualTo(uiModelList)
        sut.allUsers.removeObserver(observerMock)
    }
}
