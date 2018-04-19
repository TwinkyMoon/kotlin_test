package com.uptotech.kotlint.kotlintest

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class AppModule(private val app: App) {

    @Provides
    @Singleton
    fun provideApplication() = app
}