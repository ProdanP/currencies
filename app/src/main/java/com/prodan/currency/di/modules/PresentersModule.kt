package com.prodan.currency.di.modules

import com.prodan.currency.domain.interactors.InteractorCurrencies
import com.prodan.currency.presentation.currencies.PresenterCurrencies
import dagger.Module
import dagger.Provides

@Module
class PresentersModule {
    @Provides
    fun providesCurrenciesPresenter(interactor: InteractorCurrencies) =
        PresenterCurrencies<PresenterCurrencies.ICurrenciesView>(
            interactor
        )
}