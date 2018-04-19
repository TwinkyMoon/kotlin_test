package com.uptotech.kotlint.kotlintest

import com.uptotech.kotlint.kotlintest.ui.MainActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AppModule::class, NetModule::class))
interface AppComponent {

    fun inject(app: App)

    fun inject(activity: MainActivity)

}
