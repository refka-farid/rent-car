package com.bravedroid.rentcar.shared.presentation.users

import androidx.lifecycle.LiveData

interface UsersScreenViewModel {
    val allUsers: LiveData<List<UserUiModel>>
    fun loadContent()
}
