package com.jbpi.test02

import android.app.Application
import com.jbpi.test02.di.ApplicationComponent
import com.jbpi.test02.di.ApplicationModule
import com.jbpi.test02.di.DaggerApplicationComponent

class MyApplication : Application() {
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}
