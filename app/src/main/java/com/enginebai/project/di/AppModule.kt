package com.enginebai.project.di

import com.enginebai.project.utils.ExceptionHandler
import com.google.gson.Gson
import org.koin.dsl.module

val appModule = module {
    single { Gson() }
    single(createdAtStart = true) { ExceptionHandler() }
}