package com.yts.base.di.hilt

import com.yts.base.presentation.ui.like.LikeUserListAdapter
import com.yts.base.presentation.ui.like.LikeUserListViewModel
import com.yts.base.presentation.ui.user.UserListPagingAdapter
import com.yts.base.presentation.ui.user.UserListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/*
@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    fun providerUserListPagingAdapter(
        model: UserListViewModel
    ): UserListPagingAdapter =
        UserListPagingAdapter(model)

    @Provides
    fun providerLikeUserListAdapter(
        model: LikeUserListViewModel
    ): LikeUserListAdapter =
        LikeUserListAdapter(model)
}*/
