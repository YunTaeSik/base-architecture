package com.yts.domain.usecase

import com.yts.domain.entity.User
import com.yts.domain.repository.UserRepository

class GetLikeUsersUseCase(private val userRepository: UserRepository) {

    suspend fun invoke(query: String): MutableList<User> = userRepository.getLikeUsers(query)
}