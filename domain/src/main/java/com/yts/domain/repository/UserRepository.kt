package com.yts.domain.repository

import com.yts.domain.entity.User
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope

interface UserRepository {

    fun getUsers(
        scope: CoroutineScope,
        token: String,
        q: String,
        per_page: Int,
        page: Int
    ): Observable<*>

    suspend fun getLikeUsers(query: String): MutableList<User>

    suspend fun like(user: User)

    suspend fun isLike(userId: Int): Boolean

    suspend fun deleteUser(user: User)
}