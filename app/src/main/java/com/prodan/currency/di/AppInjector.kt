package com.prodan.currency.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.prodan.currency.ApplicationController
import dagger.android.AndroidInjection
import dagger.android.HasActivityInjector

object AppInjector {
    fun init(application: ApplicationController) {
        DaggerAppComponent.builder()
            .application(application)
            .build()
            .inject(application)

        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                println("AppInjector onActivityCreated " + activity)

                injectActivity(activity)
            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityResumed(activity: Activity) {
            }

        })
    }

    private fun injectActivity(activity: Activity) {
        if (activity is HasActivityInjector) {
            AndroidInjection.inject(activity)
        }
    }
}