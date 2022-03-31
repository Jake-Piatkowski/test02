package com.jbpi.test02.di

import com.jbpi.test02.DemoActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(mainActivity: DemoActivity)
}
