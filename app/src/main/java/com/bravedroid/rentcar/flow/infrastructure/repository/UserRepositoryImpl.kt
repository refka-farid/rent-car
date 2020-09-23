package com.bravedroid.rentcar.flow.infrastructure.repository

import com.bravedroid.rentcar.flow.domain.UserRepository
import com.bravedroid.rentcar.flow.infrastructure.network.ApiServiceFlow
import com.bravedroid.rentcar.flow.infrastructure.persistence.UserDao
import com.bravedroid.rentcar.shared.domain.User
import kotlinx.coroutines.flow.Flow

//* the role of mapper is to transfer a data structure to another data structure
//* in the clean architecture the mapper should not be in domain layer  */
class UserRepositoryImpl(
    //private val userService: UserService,
    private val apiServiceFlow: ApiServiceFlow,
    private val userDao: UserDao,
) : UserRepository {
    override fun getAll(): Flow<List<User>> {
        val x = apiServiceFlow.getAllUsers()
        TODO("Not yet implemented")
    }
//    override fun getAll(): Flow<List<User>> = userService.getAllUsers()
//        .flatMapConcat { userDtoList ->
//            flow<List<User>> {
//                emit(createUserList(userDtoList))
//            }
//        }

//    private suspend fun createUserList(userDtoList: List<UserDto>): MutableList<User> {
//        val userList: MutableList<User> = mutableListOf()
//        userDtoList.forEach { userDto ->
//            if (userDto.idNetwork != null) {
//                val userInfoDto = userService.getUserInfo(userDto.idNetwork).first()
//                userList += User(
//                  userDto.idNetwork,
//                  userDto.name ?: "",
//                  userDto.sex ?: -1,
//                  userInfoDto.birthday ?: "",
//                  userInfoDto.bio ?: ""
//                )
//            }
//        }
//        return userList
//    }
}
