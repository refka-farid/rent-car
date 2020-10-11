package com.bravedroid.rentcar.flow.infrastructure.repository

//import com.bravedroid.rentcar.flow.infrastructure.network.UserDto
import com.bravedroid.rentcar.flow.domain.UserRepository
import com.bravedroid.rentcar.flow.infrastructure.network.ApiServiceFlow
import com.bravedroid.rentcar.flow.infrastructure.persistence.UserDao
import com.bravedroid.rentcar.shared.domain.User
import com.bravedroid.rentcar.shared.infrastructure.network.dto.users.UserDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//* the role of mapper is to transfer a data structure to another data structure
//* in the clean architecture the mapper should not be in domain layer  */
class UserRepositoryImpl(
    private val apiServiceFlow: ApiServiceFlow,
    private val userDao: UserDao,
) : UserRepository {
    override fun getAll(): Flow<List<User>> = apiServiceFlow.getAllUsers()
        .map(::createUserList)

    private fun createUserList(userDtoList: List<UserDto>): List<User> {
        val userList: MutableList<User> = mutableListOf()
        userDtoList.forEach { it ->
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
