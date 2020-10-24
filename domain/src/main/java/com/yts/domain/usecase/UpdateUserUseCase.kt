package com.yts.domain.usecase

import com.yts.domain.entity.User
import com.yts.domain.repository.UserRepository

class UpdateUserUseCase(private val userRepository: UserRepository) {
    suspend fun invoke(user: User) = userRepository.like(user)
}