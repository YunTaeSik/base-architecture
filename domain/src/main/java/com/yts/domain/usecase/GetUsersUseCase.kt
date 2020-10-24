package com.yts.domain.usecase

import com.yts.domain.repository.UserRepository
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope

class GetUsersUseCase(private val userRepository: UserRepository) {
    fun invoke(
        scope: CoroutineScope,
        token: String,
        q: String,
        per_page: Int,
        startPage: Int
    ): Observable<*> = userRepository.getUsers(scope, token, q, per_page, startPage)

}