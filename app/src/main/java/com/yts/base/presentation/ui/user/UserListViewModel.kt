package com.yts.base.presentation.ui.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.yts.base.presentation.base.BaseViewModel
import com.yts.domain.entity.User
import com.yts.domain.usecase.*
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class UserListViewModel @ViewModelInject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val getUsersUseCase: GetUsersUseCase,
    private val getLikeUserUseCase: GetLikeUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : BaseViewModel() {
    private val searchDelayTime = 1000L
    private var searchDelayDisposable: Disposable? = null
    private var lastSearchQuery: String? = null

    private var _userList = MutableLiveData<PagingData<User>>()
    val userList: LiveData<PagingData<User>> get() = _userList

    private var _isEmptyQuery = MutableLiveData<Boolean>()
    val isEmptyQuery: LiveData<Boolean> get() = _isEmptyQuery

    init {
        _isEmptyQuery.value = true
    }

    fun loadingUserList(isLoading: Boolean) {
        _isLoading.postValue(isLoading)
    }

    private fun checkDuplicateQuery(query: String): Boolean {
        return if (lastSearchQuery == query) {
            true
        } else {
            lastSearchQuery = query
            false
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun getUserList(query: String) {

        if (checkDuplicateQuery(query)) {
            return
        }
        searchDelayDisposable?.dispose()
        _isLoading.value = false

        if (query.isNotEmpty()) {
            searchDelayDisposable = addDisposable(
                Observable.concat(
                    Observable.timer(searchDelayTime, TimeUnit.MILLISECONDS),
                    getUsersUseCase.invoke(
                        scope = viewModelScope,
                        token = getTokenUseCase.invoke(),
                        q = query,
                        per_page = 30,
                        startPage = 1
                    )
                ), onSuccess = {
                    if (it is PagingData<*>) {
                        _userList.value = it as PagingData<User>
                    }
                }, onError = {
                    commonError(it)
                })
        } else {
            _userList.value = PagingData.empty()
        }
        _isEmptyQuery.value = query.isEmpty()
    }

    suspend fun getLikeUser(userId: Int): Boolean {
        return getLikeUserUseCase.invoke(userId) ?: false
    }

    fun checkLikeUser(checked: Boolean, user: User) {
        viewModelScope.launch {
            user.like = checked
            updateUserUseCase.invoke(user)
        }
    }


}