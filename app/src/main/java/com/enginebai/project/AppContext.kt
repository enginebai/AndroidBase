package com.enginebai.project

import com.enginebai.base.BaseApplication
import com.enginebai.project.di.networkModule
import com.enginebai.project.di.appModule
import com.enginebai.project.di.logModule

class AppContext : BaseApplication() {
    override fun defineDependencies() = listOf(networkModule, logModule, appModule)
}