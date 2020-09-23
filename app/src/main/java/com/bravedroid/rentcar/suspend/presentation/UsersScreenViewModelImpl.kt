package com.bravedroid.rentcar.suspend.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bravedroid.rentcar.shared.domain.User
import com.bravedroid.rentcar.shared.presentation.UserUiModel
import com.bravedroid.rentcar.shared.presentation.UsersScreenViewModel
import com.bravedroid.rentcar.suspend.domain.GetAllUsersUseCase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class UsersScreenViewModelImpl(
    private val parentCoroutineContext: CoroutineContext,
    private val getAllUsersUseCase: GetAllUsersUseCase,
) : ViewModel(), CoroutineScope, UsersScreenViewModel {

    private val supervisorJob = SupervisorJob(parentCoroutineContext[Job])
    override val coroutineContext: CoroutineContext
        get() = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("Exception occurred in viewModel ${throwable.message}  \n in $coroutineContext")
        } + parentCoroutineContext + supervisorJob

    private val _allUsers = MutableLiveData<List<UserUiModel>>()
   override val allUsers: LiveData<List<UserUiModel>> = _allUsers

   override fun loadContent() {
        launch(coroutineContext) {
            try {
                getAllUsersUseCase().mapToUserUiModelList()
                    .let {
                        _allUsers.value = it
                    }

                supervisorJob.complete()
            } catch (e: Exception) {
                listOf(
                    User(100, "James", 2, "PD Mahboul", "1970"),
                    User(101, "Max", 2, "PD Za3ben", "1971"),
                    User(102, "Bill", 2, "PD Mezien", "1972"),
                )
            }
        }
    }

    private fun List<User>.mapToUserUiModelList(): List<UserUiModel> =
        map { UserUiModel(it.id, it.name) }

    override fun onCleared() {
        super.onCleared()
        supervisorJob.cancel()
    }
}



