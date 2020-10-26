package com.bravedroid.rentcar.shared.presentation.users

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

class UsersScreenFragment(private val usersScreenViewModel: UsersScreenViewModel) :
    LifecycleOwner {
    private val lifecycle = LifecycleRegistry(this)

    init {
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun getLifecycle(): Lifecycle = lifecycle

    fun showUsers() {
        usersScreenViewModel.loadContent()
        usersScreenViewModel.allUsers.observe(this) {
           println("all users are the following : ")
           it.forEach(::println)
        }
    }
}
