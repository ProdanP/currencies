package com.prodan.currency.di

import com.prodan.currency.ApplicationController
import com.prodan.currency.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, RepositoriesModule::class,
    ActivitiesModule::class,
    PresentersModule::class,
    InteractorsModule::class,
    AndroidSupportInjectionModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(instance : ApplicationController) : Builder

        fun build() : AppComponent
    }

    fun inject(application : ApplicationController)
}