package com.yts.base.di.koinmodule

import org.koin.dsl.module

val domainModule = module {
    /*   single<SearchRepository> { SearchRepositoryImp(get()) }
       single<GetBooksUseCase> { GetBooksUseCase(get()) }
       single<GetTokenUseCase> { GetTokenUseCase(Const.REST_API_KEY) }*/
}
