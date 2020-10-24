package com.yts.base.di.koinmodule

import com.yts.base.presentation.ui.MainViewModel
import com.yts.base.presentation.ui.like.LikeUserListViewModel
import com.yts.base.presentation.ui.like.LikeUserListAdapter
import com.yts.base.presentation.ui.user.UserListPagingAdapter
import com.yts.base.presentation.ui.user.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel {
        MainViewModel()
    }

    viewModel {
        UserListViewModel(get(), get(), get(), get())
    }
    viewModel {
        LikeUserListViewModel(get(), get())
    }

    factory { (model: UserListViewModel) -> UserListPagingAdapter(model) }
    factory { (model: LikeUserListViewModel) -> LikeUserListAdapter(model) }

}

