package com.prodan.currency.di.modules

import com.prodan.currency.presentation.currencies.ActivityCurriencies
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {
    @ContributesAndroidInjector
    abstract fun contributeActivityCurriencies(): ActivityCurriencies
}