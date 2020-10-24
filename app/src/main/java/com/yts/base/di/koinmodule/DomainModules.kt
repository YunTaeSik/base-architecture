package com.yts.base.di.koinmodule

import com.yts.base.util.Const
import com.yts.data.repository.UserRepositoryImp
import com.yts.domain.repository.UserRepository
import com.yts.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {
    single<UserRepository> { UserRepositoryImp(get(), get()) }

    single { GetTokenUseCase(Const.GITHUB_TOKEN) }
    single { GetUsersUseCase(get()) }

    single { UpdateUserUseCase(get()) }
    single { GetLikeUserUseCase(get()) }
    single { GetLikeUsersUseCase(get()) }
    single { DeleteUserUseCase(get()) }
}
