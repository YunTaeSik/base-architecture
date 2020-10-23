package com.yts.base.di.koinmodule

import com.google.gson.Gson
import org.koin.dsl.module

var appModule = module {
    single { Gson() }
}

