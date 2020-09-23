package com.bravedroid.rentcar.flow.presentation

//import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bravedroid.rentcar.flow.domain.GetAllUsersUseCase
import com.bravedroid.rentcar.shared.domain.User
import com.bravedroid.rentcar.shared.presentation.UserUiModel
import com.bravedroid.rentcar.shared.presentation.UsersScreenViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlin.coroutines.CoroutineContext

class UsersScreenViewModelImpl(
    // this will be removed in a real application android , will be replaced by viewModelScope
    private val parentCoroutineContext: CoroutineContext,
    private val getAllUsersUseCase: GetAllUsersUseCase,
) : ViewModel(), CoroutineScope , UsersScreenViewModel {

    private val supervisorJob = SupervisorJob(parentCoroutineContext[Job])
    override val coroutineContext: CoroutineContext
        get() = CoroutineExceptionHandler { coroutineContext, throwable ->
            println("Exception occurred in viewModel ${throwable.message}  \n in $coroutineContext")
        } + parentCoroutineContext + supervisorJob

    private val _allUsers = MutableLiveData<List<UserUiModel>>()
    override val allUsers: LiveData<List<UserUiModel>> = _allUsers


    override fun loadContent() {
        // in real android application we will use this
        // viewModelScope.launch {
        //GlobalScope.launch (context){
        launch(coroutineContext) {
            getAllUsersUseCase()
                .onEach {
                    println("event is intercepted ")
                }
                .catch { e ->
                    if (e is IllegalArgumentException) emit(
                        listOf(
                            User(100, "James", 2, "PD Mahboul", "1970"),
                            User(101, "Max", 2, "PD Za3ben", "1971"),
                            User(102, "Bill", 2, "PD Mezien", "1972"),
                        )
                    )
                }.collect { users ->
                    users.mapToUserUiModelList()
                        .let {
                            _allUsers.value = it
                        }
                    supervisorJob.complete()
                }
            //}
        }
    }

    private fun List<User>.mapToUserUiModelList(): List<UserUiModel> =
        map { UserUiModel(it.id, it.name) }


    override fun onCleared() {
        super.onCleared()
        supervisorJob.cancel()
    }
}
