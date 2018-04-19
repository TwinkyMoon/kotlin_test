package com.uptotech.kotlint.kotlintest

import android.app.Application

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                //AppModule is required due to Application instance is created and further initialization of NetModule was possible
                .appModule(AppModule(this))
                //.androidModule(AndroidModule(this))
                .netModule(NetModule())
                .build()
    }

    fun appComponent(): AppComponent = appComponent
}