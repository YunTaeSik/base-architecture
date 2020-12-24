package com.yts.base.di.hilt

import com.yts.base.util.Const
import com.yts.data.repository.UserRepositoryImp
import com.yts.data.source.local.AppDatabase
import com.yts.data.source.remote.SearchService
import com.yts.domain.repository.UserRepository
import com.yts.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideUserRepository(
        dataBase: AppDatabase,
        searchService: SearchService
    ): UserRepository {
        return UserRepositoryImp(dataBase, searchService)
    }

    @Singleton
    @Provides
    fun provideGetTokenUseCase(): GetTokenUseCase = GetTokenUseCase(Const.GITHUB_TOKEN)

    @Singleton
    @Provides
    fun providerGetUsersUseCase(
        userRepository: UserRepository
    ): GetUsersUseCase =
        GetUsersUseCase(userRepository)

    @Singleton
    @Provides
    fun providerUpdateUserUseCase(userRepository: UserRepository): UpdateUserUseCase =
        UpdateUserUseCase(userRepository)

    @Singleton
    @Provides
    fun providerGetLikeUserUseCase(userRepository: UserRepository): GetLikeUserUseCase =
        GetLikeUserUseCase(userRepository)

    @Singleton
    @Provides
    fun providerGetLikeUsersUseCase(
        userRepository: UserRepository
    ): GetLikeUsersUseCase =
        GetLikeUsersUseCase(userRepository)

    @Singleton
    @Provides
    fun providerDeleteUserUseCase(userRepository: UserRepository): DeleteUserUseCase =
        DeleteUserUseCase(userRepository)
}
