package com.bravedroid.rentcar.shared.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class UsersScreenFragmentTest {
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var usersScreenViewModelMock: UsersScreenViewModel
    private lateinit var sut: UsersScreenFragment

    @Before
    fun setUp() {
        usersScreenViewModelMock = mock(UsersScreenViewModel::class.java)
        sut = UsersScreenFragment(usersScreenViewModelMock)
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun showUsersTest1() {
        //arrange
        val uiModelList = listOf(UserUiModel(1000, "jucky"))
        val allUsersLiveDataMock = mock(LiveData::class.java) as LiveData<List<UserUiModel>>
        `when`(allUsersLiveDataMock.value).thenReturn(uiModelList)
        `when`(usersScreenViewModelMock.allUsers).thenReturn(allUsersLiveDataMock)
        //act
        sut.showUsers()
        //assert
        verify(usersScreenViewModelMock).loadContent()

        val argumentCaptor: ArgumentCaptor<Observer<List<UserUiModel>>> =
            ArgumentCaptor.forClass(Observer::class.java) as ArgumentCaptor<Observer<List<UserUiModel>>>

        verify(usersScreenViewModelMock.allUsers).observe(eq(sut), argumentCaptor.capture())
        assertThat(argumentCaptor.value).isInstanceOf(Observer::class.java)
    }

    @Suppress("UNCHECKED_CAST")
    @Test
    fun showUsersTest2() {
        //arrange
        val uiModelList = listOf(UserUiModel(1000, "jucky"))
        val allUsersLiveDataMock = mock(LiveData::class.java) as LiveData<List<UserUiModel>>
        `when`(allUsersLiveDataMock.value).thenReturn(uiModelList)
        `when`(usersScreenViewModelMock.allUsers).thenReturn(allUsersLiveDataMock)
        //act
        sut.showUsers()
        //assert
        verify(usersScreenViewModelMock).loadContent()
        verify(usersScreenViewModelMock.allUsers).observe(eq(sut), argThat { it is Observer })
    }


    @Suppress("UNCHECKED_CAST")
    @Test
    fun showUsersTest3() {
        //arrange
        val uiModelList = listOf(UserUiModel(1000, "jucky"))
        val allUsersLiveDataFake = MutableLiveData(uiModelList)
        `when`(usersScreenViewModelMock.allUsers).thenReturn(allUsersLiveDataFake)

        //act
        sut.showUsers()
        //assert
        verify(usersScreenViewModelMock).loadContent()
        verify(usersScreenViewModelMock).allUsers
    }
}
