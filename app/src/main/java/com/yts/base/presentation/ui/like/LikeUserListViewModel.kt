package com.yts.base.presentation.ui.like

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yts.base.extension.Event
import com.yts.base.presentation.base.BaseViewModel
import com.yts.domain.entity.User
import com.yts.domain.usecase.DeleteUserUseCase
import com.yts.domain.usecase.GetLikeUsersUseCase
import kotlinx.coroutines.launch

class LikeUserListViewModel @ViewModelInject constructor(
    private val getLikeUsersUseCase: GetLikeUsersUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : BaseViewModel() {

    private var _likeUserList = MutableLiveData<MutableList<User>>()
    val likeUserList: LiveData<MutableList<User>> get() = _likeUserList

    private var _isEmptyLikeUserList = MutableLiveData<Boolean>()
    val isEmptyLikeUserList: LiveData<Boolean> get() = _isEmptyLikeUserList

    private val _deleteLikeUser = MutableLiveData<Event<Unit>>()
    val deleteLikeUser: LiveData<Event<Unit>> = _deleteLikeUser

    init {
        getLikeUserList("")
    }

    fun setEmptyLikeUserList(isEmpty: Boolean) {
        _isEmptyLikeUserList.value = isEmpty
    }

    fun getLikeUserList(query: String) {
        viewModelScope.launch {
            _likeUserList.value = getLikeUsersUseCase.invoke(query)
        }
    }

    fun deleteLikeUser(user: User) {
        viewModelScope.launch {
            deleteUserUseCase.invoke(user)
            _deleteLikeUser.value = Event(Unit)
        }
    }
}