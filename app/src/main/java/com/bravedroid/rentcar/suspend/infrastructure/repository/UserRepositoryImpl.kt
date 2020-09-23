package com.bravedroid.rentcar.suspend.infrastructure.repository

import com.bravedroid.rentcar.shared.domain.User
import com.bravedroid.rentcar.suspend.domain.UserRepository
import com.bravedroid.rentcar.suspend.infrastructure.network.ApiService
import com.bravedroid.rentcar.suspend.infrastructure.network.UserService
import com.bravedroid.rentcar.suspend.infrastructure.persistence.UserDao

/* the role of mapper is to transfer a data structure to another data structure
* in the clean architecture the mapper should not be in domain layer  */
class UserRepositoryImpl(
  //private val userService: UserService,
  private val apiService: ApiService,
  userDao: UserDao,
) : UserRepository {
  override suspend fun getAll(): List<User> {
    val allUsers = apiService.getAllUsers()
    TODO("Not yet implemented because i don't know how map a userDtoList to a list of User," +
            " i will look to the commeted code below and the json ")
  }
//  override suspend fun getAll(): List<User> {
//    val userDtoList = userService.getAllUsers()
//    val userList: MutableList<User> = mutableListOf()
//    userDtoList.forEach { userDto ->
//      if (userDto.idNetwork != null) {
//        val userInfoDto = userService.getUserInfo(userDto.idNetwork)
//        userList += User(
//          userDto.idNetwork,
//          userDto.name ?: "",
//          userDto.sex ?: -1,
//          userInfoDto.birthday ?: "",
//          userInfoDto.bio ?: ""
//        )
//      }
//    }
//    return userList
//  }
}
