package com.bravedroid.rentcar.suspend.infrastructure.repository

import com.bravedroid.rentcar.shared.domain.User
import com.bravedroid.rentcar.shared.infrastructure.network.dto.users.UserDto
import com.bravedroid.rentcar.suspend.domain.UserRepository
import com.bravedroid.rentcar.suspend.infrastructure.network.ApiService
import com.bravedroid.rentcar.suspend.infrastructure.persistence.UserDao

/* the role of mapper is to transfer a data structure to another data structure
* in the clean architecture the mapper should not be in domain layer  */
class UserRepositoryImpl(
    private val apiService: ApiService,
    userDao: UserDao,
) : UserRepository {
    override suspend fun getAll(): List<User> {
        val allUsers: List<UserDto> = apiService.getAllUsers()
        val userList: MutableList<User> = mutableListOf()
        allUsers.forEach {
            userList.add(
                User(
                    it.id?.toLong() ?: -1,
                    it.fName ?: "",
                    it.sex ?: -1,
                    it.description?.bio ?: "",
                    it.bDate ?: ""
                )
            )
        }
        return userList
    }
}
