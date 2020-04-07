package com.prodan.currency.di.modules

import android.content.Context
import com.prodan.currency.ApplicationController
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun providesAppContext() : Context = ApplicationController.instance.applicationContext
}