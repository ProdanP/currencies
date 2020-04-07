package com.prodan.currency

import android.app.Activity
import android.app.Application
import com.prodan.currency.data.repositories.local_db.ApplicationDatabase
import com.prodan.currency.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class ApplicationController : Application(), HasActivityInjector {
    lateinit var apiUrl: String

    companion object {
        lateinit var instance: ApplicationController
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppInjector.init(this)
        apiUrl = getString(R.string.api_host_url)
        ApplicationDatabase.getInstance(this)
    }

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}