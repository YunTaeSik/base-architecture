package com.yts.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.observable
import com.yts.data.mapper.map
import com.yts.data.repository.page.UsersPagingSource
import com.yts.data.source.local.AppDatabase
import com.yts.data.source.remote.SearchService
import com.yts.data.table.UserTable
import com.yts.domain.entity.User
import com.yts.domain.repository.UserRepository
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope

class UserRepositoryImp(
    private val dataBase: AppDatabase,
    private val searchService: SearchService
) : UserRepository {
    override fun getUsers(
        scope: CoroutineScope,
        token: String,
        q: String,
        per_page: Int,
        page: Int
    ): Observable<*> = Pager(
        PagingConfig(
            pageSize = per_page,
            enablePlaceholders = true
        )
    ) {
        UsersPagingSource(searchService, token, q, per_page, page)
    }.observable.cachedIn(scope)


    override suspend fun getLikeUsers(query: String): MutableList<User> {
        return dataBase.userDao.load(query).map()
    }


    override suspend fun like(user: User) = dataBase.userDao.insert(user.map())

    override suspend fun isLike(userId: Int): Boolean = dataBase.userDao.load(userId)?.like ?: false

    override suspend fun deleteUser(user: User) = dataBase.userDao.delete(user.map())

}