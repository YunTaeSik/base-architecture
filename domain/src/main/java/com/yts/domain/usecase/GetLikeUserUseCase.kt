package com.yts.domain.usecase

import com.yts.domain.repository.UserRepository

class GetLikeUserUseCase(private val userRepository: UserRepository) {

    suspend  fun invoke(userId: Int): Boolean? = userRepository.isLike(userId)
}